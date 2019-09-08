package com.daimler.mbmobilesdk.familyapps

import android.os.Handler
import com.daimler.mbdeeplinkkit.FamilyAppsService
import com.daimler.mbdeeplinkkit.common.FamilyApp
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal typealias FamilyAppsResult = FutureTask<List<FamilyApp>, ResponseError<out RequestError>?>
internal typealias FamilyAppsTaskObject = TaskObject<List<FamilyApp>, ResponseError<out RequestError>?>

internal class FamilyAppsService(
    private val appsService: FamilyAppsService
) {

    private val handler = Handler()

    fun loadApps(jwtToken: String): FamilyAppsResult {
        return appsService.loadAppsAndDeepLinks()?.takeIf {
            it.isNotEmpty()
        }?.let {
            val task = FamilyAppsTaskObject()
            handler.post { task.complete(it) }
            task.futureTask()
        } ?: fetchApps(jwtToken)
    }

    private fun fetchApps(jwtToken: String): FamilyAppsResult {
        return appsService.fetchAppsAndDeepLinks(jwtToken)
    }
}