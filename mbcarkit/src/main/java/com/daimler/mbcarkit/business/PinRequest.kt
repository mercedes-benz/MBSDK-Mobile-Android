package com.daimler.mbcarkit.business

/**
 * The PinRequest is used to confirm or cancel commands that require a PIN. To continue with processing
 * of the pin, at least one of the methods has to be called. If not, the processing of the command will
 * stopped and this might lead to memory leaks. The PinRequest will be done before parsing of a command
 * is started.
 */
interface PinRequest {
    /**
     * If called, the related command will be parsed and send.
     */
    fun confirmPin(pin: String)

    /**
     * If called, the processing of the related command will be stopped and sending will fail
     */
    fun cancel(cause: String?)
}
