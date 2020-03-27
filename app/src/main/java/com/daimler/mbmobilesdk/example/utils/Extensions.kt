package com.daimler.mbmobilesdk.example.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

fun VehicleImage.getBitmap(): Bitmap? = imageBytes?.let {
    BitmapFactory.decodeByteArray(
        it,
        0,
        it.size
    )
}

fun <R, E> FutureTask<R, E>.stateful(state: MutableLiveData<Boolean>): FutureTask<R, E> {
    state.postValue(true)
    val task = TaskObject<R, E>()

    onComplete { task.complete(it) }
    onFailure { task.fail(it) }
    onAlways { _, _, _ -> state.postValue(false) }

    return task.futureTask()
}
