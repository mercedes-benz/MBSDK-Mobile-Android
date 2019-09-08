package com.daimler.mbmobilesdk.assets

import android.content.Context

internal object MBMobileSDKAssetManager {

    fun createAssetService(context: Context, type: AssetSubType): AssetsService =
        MBMobileSDKAssetsService(context, type.relativePath)
}