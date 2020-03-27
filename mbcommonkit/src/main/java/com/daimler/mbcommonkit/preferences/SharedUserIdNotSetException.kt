package com.daimler.mbcommonkit.preferences

class SharedUserIdNotSetException(expectedId: String) :
    IllegalArgumentException("$expectedId must be set as sharedUserId in the manifest!")
