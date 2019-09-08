package com.daimler.mbmobilesdk.legal.license

data class Library(
    val name: String,
    val version: String,
    val dependency: String,
    val fileUrl: String,
    val file: String,
    val licenses: List<License>
)