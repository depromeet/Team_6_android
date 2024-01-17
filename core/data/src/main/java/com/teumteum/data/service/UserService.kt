package com.teumteum.data.service

import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.data.model.response.ResponseUserInfo
import com.teumteum.domain.entity.SignUpResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("users/me")
    suspend fun getMyUserInfo(): ResponseUserInfo

    @POST("users")
    suspend fun postUserInfo(
        @Body userInfo: RequestUserInfoWithOAuthId
    ): SignUpResult
}