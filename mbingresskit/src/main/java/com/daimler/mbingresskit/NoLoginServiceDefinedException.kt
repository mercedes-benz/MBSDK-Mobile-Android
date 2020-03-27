package com.daimler.mbingresskit

class NoLoginServiceDefinedException : IllegalStateException("A LoginService must be set to start login or handle LoginStates")
