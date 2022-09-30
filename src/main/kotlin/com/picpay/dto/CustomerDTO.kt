package com.picpay.dto

import kotlinx.serialization.Serializable

@Serializable
data class CustomerDTO(
    var id: Int,
    val name: String,
    val document: String
)