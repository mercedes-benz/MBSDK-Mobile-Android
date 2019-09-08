package com.daimler.mbmobilesdk.featuretoggling

import android.app.Application
import androidx.annotation.WorkerThread
import com.daimler.mbcommonkit.tasks.SimpleTask
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject
import com.launchdarkly.android.FeatureFlagChangeListener
import com.launchdarkly.android.LDClient
import com.launchdarkly.android.LDConfig
import com.launchdarkly.android.LDUser

/* Only allow one instance of this service since the underlying LDClient is also a singleton
 * with unchangeable parameters after the first initialization.
 */
internal object LaunchDarklyFeatureService : BaseFeatureService(), FeatureFlagChangeListener {

    private lateinit var client: LDClient

    // We need to maintain some configuration parameters since the LDClient cannot be re-initialized.
    private lateinit var initializedEnvironment: FeatureToggleEnvironment
    private lateinit var currentUser: UserContext

    private val observers = HashMap<String, MutableList<OnFeatureChangedListener>>()

    fun init(
        app: Application,
        userContext: UserContext,
        mainEnvironment: FeatureToggleEnvironment,
        vararg additionalEnvironments: FeatureToggleEnvironment
    ) {
        initializeClientIfNecessary(app, userContext, mainEnvironment, additionalEnvironments)
    }

    override fun swapUserContext(userContext: UserContext): FutureTask<String, Throwable> {
        val task = TaskObject<String, Throwable>()
        SimpleTask { swapUserInternal(userContext) }
            .onComplete {
                currentUser = userContext
                task.complete(it)
            }
            .onError { task.fail(it) }
            .execute()
        return task.futureTask()
    }

    override fun registerFeatureListener(key: String, listener: OnFeatureChangedListener) {
        var list = observers[key]
        if (list == null) {
            list = mutableListOf(listener)
            observers[key] = list
            client.registerFeatureFlagListener(key, this)
        } else {
            list.add(listener)
        }
    }

    override fun unregisterFeatureListener(key: String, listener: OnFeatureChangedListener) {
        val list = observers[key]
        list?.let {
            it.remove(listener)
            if (it.isEmpty()) {
                observers.remove(key)
                client.unregisterFeatureFlagListener(key, this)
            }
        }
    }

    override fun isToggleEnabled(key: String, default: Boolean): Boolean =
        client.boolVariation(key, default)

    override fun isToggleEnabled(key: String): Boolean =
        isToggleEnabled(key, default<Boolean>(key) == true)

    override fun getAllFlags(): Map<String, Any?> = client.allFlags()

    override fun onFeatureFlagChange(flagKey: String) {
        MBLoggerKit.d("$flagKey changed, notifying ${observers[flagKey]?.size ?: 0} observers.")
        observers[flagKey]?.forEach { it.onFeatureChanged(flagKey) }
    }

    @WorkerThread
    private fun swapUserInternal(userContext: UserContext): String {
        val user = mapUserContextToLDUser(userContext)
        client.identify(user).get()
        return userContext.id
    }

    private fun initializeClientIfNecessary(
        app: Application,
        userContext: UserContext,
        mainEnvironment: FeatureToggleEnvironment,
        additionalEnvironments: Array<out FeatureToggleEnvironment>
    ) {
        when {
            !::client.isInitialized -> {
                // First initialization.
                MBLoggerKit.d("Initializing LaunchDarkly.")
                initializedEnvironment = mainEnvironment
                val config = LDConfig.Builder()
                    .apply {
                        setMobileKey(mainEnvironment.apiKey)
                        if (additionalEnvironments.isNotEmpty()) {
                            setSecondaryMobileKeys(additionalEnvironments.map { it.id to it.apiKey }.toMap())
                        }
                    }.build()
                val user = LDUser.Builder(mapUserContextToLDUser(userContext)).build()
                currentUser = userContext
                LDClient.init(app, config, user)
                client = LDClient.get()
            }
            initializedEnvironment.id != mainEnvironment.id -> {
                // LDClient was already initialized but the environment changed.
                MBLoggerKit.d("Changing environment for LaunchDarkly: ${initializedEnvironment.id} -> ${mainEnvironment.id}.")
                client = LDClient.getForMobileKey(mainEnvironment.id)
                swapUserIfNecessary(userContext)
            }
            else -> {
                // LDClient was already initialized and the environment is still the same.
                client = LDClient.get()
                swapUserIfNecessary(userContext)
            }
        }
    }

    private fun swapUserIfNecessary(newUserContext: UserContext): FutureTask<String, Throwable>? {
        return if (!::currentUser.isInitialized || currentUser != newUserContext) {
            swapUserContext(newUserContext)
        } else {
            null
        }
    }

    private fun mapUserContextToLDUser(userContext: UserContext) =
        LDUser.Builder(userContext.id).apply {
            anonymous(true)
            userContext.mail?.let { email(it) }
            userContext.countryCode?.let { country(it) }
            userContext.firstName?.let { firstName(it) }
            userContext.lastName?.let { lastName(it) }
        }.build()
}