package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: Int,
    val characterId: Int,
    val name: String,
    val job: JobEntity,
    val authenticated: String,
    val mbti: String,
    val activityArea: String,
    val mannerTemperature: Int,
    val interests: List<String>
) : java.io.Serializable

@Serializable
data class Friends(
    val friends: List<Friend>
) : java.io.Serializable