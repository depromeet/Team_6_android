package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val id: Long,
    val hostId: Long,
    val topic: String,
    val name: String,
    val introduction: String,
    val photoUrls: List<String>,
    val date: String
)