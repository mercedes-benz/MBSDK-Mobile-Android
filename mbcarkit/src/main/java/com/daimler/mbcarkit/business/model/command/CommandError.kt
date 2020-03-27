package com.daimler.mbcarkit.business.model.command

sealed class CommandError(val code: Int) {

    class PinInvalid(code: Int, val attempts: Int) : CommandError(code)

    class CiamIdBlocked(code: Int, val attempts: Int, val blockingTimeSeconds: Long) : CommandError(code)

    class Unknown(code: Int) : CommandError(code)
}
