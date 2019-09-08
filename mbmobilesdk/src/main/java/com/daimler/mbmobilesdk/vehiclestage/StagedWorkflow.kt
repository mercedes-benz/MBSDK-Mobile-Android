package com.daimler.mbmobilesdk.vehiclestage

internal sealed class StagedWorkflow {

    object Registration : StagedWorkflow()
    object VehicleAssignment : StagedWorkflow()
}