package com.daimler.mbcarkit.implementation.exceptions

class JwtTokenExpiredException(jwtToken: String) : IllegalArgumentException("Expired JwtToken: $jwtToken")
