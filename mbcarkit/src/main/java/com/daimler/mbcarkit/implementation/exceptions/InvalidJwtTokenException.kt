package com.daimler.mbcarkit.implementation.exceptions

class InvalidJwtTokenException(jwtToken: String) : IllegalArgumentException("Invalid JwtToken: $jwtToken")
