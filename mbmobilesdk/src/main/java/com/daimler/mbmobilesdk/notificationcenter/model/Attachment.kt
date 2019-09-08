package com.daimler.mbingresskit.notificationcenter.model

data class Attachment(val title: String, val link: String, val type: Type) {

    enum class Type {
        IMAGE,
        PDF,
        DOC
    }
}