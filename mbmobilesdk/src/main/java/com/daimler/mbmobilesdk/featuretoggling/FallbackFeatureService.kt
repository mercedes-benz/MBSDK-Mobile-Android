package com.daimler.mbmobilesdk.featuretoggling

import android.os.Handler
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class FallbackFeatureService : BaseFeatureService() {

    override fun swapUserContext(userContext: UserContext): FutureTask<String, Throwable> {
        val task = TaskObject<String, Throwable>()
        Handler().post { task.complete(userContext.id) }
        return task.futureTask()
    }

    override fun registerFeatureListener(key: String, listener: OnFeatureChangedListener) = Unit

    override fun unregisterFeatureListener(key: String, listener: OnFeatureChangedListener) = Unit

    override fun isToggleEnabled(key: String, default: Boolean): Boolean = default

    override fun isToggleEnabled(key: String): Boolean = default<Boolean>(key) == true

    override fun getAllFlags(): Map<String, Any?> = emptyMap()
}