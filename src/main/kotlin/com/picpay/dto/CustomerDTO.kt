package com.picpay.dto

import kotlinx.serialization.Serializable

@Serializable
data class CustomerDTO(
    var id: Int,
    var name: String,
    var document: String
)