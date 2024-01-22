package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.data.service.UserService
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.entity.Users
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val service: UserService
) {
    suspend fun getMyUserInfo(): UserInfo {
        return service.getMyUserInfo()
    }

    suspend fun postUserInfo(
        userInfo: RequestUserInfoWithOAuthId
    ): SignUpResult {
        return service.postUserInfo(userInfo)
    }

    suspend fun getUserFriend(userId: Long): List<Friend> {
        return service.getUserFriends(userId)
    }

    suspend fun getUser(userId: Long): Friend {
        return service.getUser(userId)
    }

    suspend fun getUsers(id: String): Users {
        return service.getUsers(id)
    }
}