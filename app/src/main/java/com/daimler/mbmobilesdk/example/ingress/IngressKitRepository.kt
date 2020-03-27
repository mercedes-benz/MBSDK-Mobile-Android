package com.daimler.mbmobilesdk.example.ingress

import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.defaultErrorMapping
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject
import java.util.Locale

class IngressKitRepository {

    private var user: String? = null

    fun sendTan(user: String): FutureTask<LoginData, ResponseError<out RequestError>?> {
        val task = TaskObject<LoginData, ResponseError<out RequestError>?>()
        MBIngressKit.userService().sendPin(user, Locale.getDefault().country)
            .onComplete { loginUser ->
                val loginUserName = loginUser.userName.takeIf { it.isNotBlank() } ?: user
                this.user = loginUserName
                task.complete(
                    LoginData(
                        loginUserName,
                        loginUser.userName.isNotBlank(),
                        loginUser.isMail
                    )
                )
            }.onFailure {
                task.fail(it)
            }
        return task.futureTask()
    }

    fun login(tan: String): FutureTask<Unit, ResponseError<out RequestError>?> =
        user?.takeIf { it.isNotBlank() }?.let {
            MBIngressKit.login(UserCredentials(it, tan))
        } ?: TaskObject<Unit, ResponseError<out RequestError>?>().also {
            it.fail(ResponseError.requestError(NotRegisteredError()))
        }.futureTask()

    fun loadUser(): FutureTask<User, ResponseError<out RequestError>?> {
        val task = TaskObject<User, ResponseError<out RequestError>?>()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                MBIngressKit.userService().loadUser(token.accessToken)
                    .onComplete {
                        task.complete(it)
                    }.onFailure {
                        task.fail(it)
                    }
            }.onFailure {
                task.fail(defaultErrorMapping(it))
            }
        return task.futureTask()
    }

    class NotRegisteredError : RequestError
}
