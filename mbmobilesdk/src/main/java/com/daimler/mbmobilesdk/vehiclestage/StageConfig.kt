package com.daimler.mbmobilesdk.vehiclestage

import android.os.Parcel
import android.os.Parcelable
import com.daimler.mbcommonkit.extensions.readBoolean
import com.daimler.mbcommonkit.extensions.writeBoolean

data class StageConfig(
    val stagesVisible: Boolean,
    val selectedStage: Int,
    val stages: Int,
    val stageText: String?,
    val stageSubText: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readBoolean(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeBoolean(stagesVisible)
        parcel.writeInt(selectedStage)
        parcel.writeInt(stages)
        parcel.writeString(stageText)
        parcel.writeString(stageSubText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val EMPTY = StageConfig(false, 0, 0, null, null)

        @JvmField
        val CREATOR = object : Parcelable.Creator<StageConfig> {
            override fun createFromParcel(parcel: Parcel): StageConfig {
                return StageConfig(parcel)
            }

            override fun newArray(size: Int): Array<StageConfig?> {
                return arrayOfNulls(size)
            }
        }

        private const val RIS_STAGES = 4

        fun createDefaultVisible(selectedStage: Int, text: String, subText: String? = null) =
            StageConfig(true, selectedStage, RIS_STAGES, text, subText)
    }
}