package com.daimler.mbcarkit.business.model.command

enum class CommandCondition {
    /**
     * <code>UNKOWN_CONDITION_TYPE = 0;</code>
     */
    UNKOWN_CONDITION_TYPE,

    /**
     * <code>NONE = 1;</code>
     */
    NONE,

    /**
     * <code>ACCEPTED = 2;</code>
     */
    ACCEPTED,

    /**
     * <code>REJECTED = 3;</code>
     */
    REJECTED,

    /**
     * <code>TERMINATE = 4;</code>
     */
    TERMINATE,

    /**
     * <code>SUCCESS = 5;</code>
     */
    SUCCESS,

    /**
     * <code>FAILED = 6;</code>
     */
    FAILED,

    /**
     * <code>OVERWRITTEN = 7;</code>
     */
    OVERWRITTEN,

    /**
     * <code>TIMEOUT = 8;</code>
     */
    TIMEOUT,

    UNKNOWN
}
