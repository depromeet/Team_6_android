package com.teumteum.domain

interface TeumTeumDataStore {
    var userToken: String
    var refreshToken: String
    var isLogin: Boolean
    var isFirstAfterInstall: Boolean

    fun clearLocalPref()
}
