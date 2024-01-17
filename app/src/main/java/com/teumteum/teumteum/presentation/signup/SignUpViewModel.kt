package com.teumteum.teumteum.presentation.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.entity.JobEntity
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: UserRepository,
    private val dataStore: TeumTeumDataStore
) : ViewModel() {

    private val _signUpProgress = MutableStateFlow<SignUpProgress>(SignUpProgress.Character)
    val signUpProgress: StateFlow<SignUpProgress> = _signUpProgress.asStateFlow()

    private val _currentStep = MutableStateFlow<Int>(1)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _characterId = MutableStateFlow<Int>(-1)
    val characterId: StateFlow<Int> = _characterId.asStateFlow()

    fun updateCharacterId(characterId: Int) {
        _characterId.value = characterId
        Log.d("teum-card", "characterId updated $characterId")
    }

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    fun updateUserName(userName: String) {
        _userName.value = userName
    }

    private val _birthYear = MutableStateFlow("")
    val birthYear: StateFlow<String> = _birthYear.asStateFlow()
    private val _birthMonth = MutableStateFlow("")
    val birthMonth: StateFlow<String> = _birthMonth.asStateFlow()
    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()
    val birthValid: StateFlow<Boolean> = combine(
        birthYear,
        birthMonth,
        birthDate
    ) { year, month, date ->
        year.toIntOrNull() in 1000..2122
                && month.toIntOrNull() in 1..12
                && date.toIntOrNull() in 1..31
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    fun updateBirthYear(birthYear: String) {
        _birthYear.value = birthYear
    }

    fun updateBirthMonth(birthMonth: String) {
        _birthMonth.value = birthMonth
    }

    fun updateBirthDate(birthDate: String) {
        _birthDate.value = birthDate
    }

    private val _community = MutableStateFlow<String>("")
    val community: StateFlow<String> = _community.asStateFlow()

    fun updateCommunity(community: String) {
        _community.value = community
    }

    private val _companyName = MutableStateFlow<String>("")
    val companyName: StateFlow<String> = _companyName.asStateFlow()

    fun updateCompanyName(companyName: String) {
        _companyName.value = companyName
    }

    private val _jobClass = MutableStateFlow<String>("")
    val jobClass: StateFlow<String> = _jobClass.asStateFlow()

    fun updateJobClass(jobClass: String) {
        _jobClass.value = jobClass
    }

    private val _jobDetailClass = MutableStateFlow<String>("")
    val jobDetailClass: StateFlow<String> = _jobDetailClass.asStateFlow()

    fun updateJobDetailClass(jobDetailClass: String) {
        _jobDetailClass.value = jobDetailClass
    }

    val currentJobValid: StateFlow<Boolean> = combine(
        companyName,
        jobClass,
        jobDetailClass
    ) { companyName, jobClass, jobDetailClass ->
        companyName.trim().length in 2..13 && jobClass.isNotBlank() && jobDetailClass.isNotBlank()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _schoolName = MutableStateFlow<String>("")
    val schoolName: StateFlow<String> = _schoolName.asStateFlow()

    fun updateSchoolName(school: String) {
        _schoolName.value = school
    }

    private val _readyJobClass = MutableStateFlow<String>("")
    val readyJobClass: StateFlow<String> = _readyJobClass.asStateFlow()

    private val _readyJobDetailClass = MutableStateFlow<String>("")
    val readyJobDetailClass: StateFlow<String> = _readyJobDetailClass.asStateFlow()

    fun updateReadyJobClass(readyJobClass: String) {
        _readyJobClass.value = readyJobClass
    }

    fun updateReadyJobDetailClass(readyJobDetailClass: String) {
        _readyJobDetailClass.value = readyJobDetailClass
    }

    val readyJobValid: StateFlow<Boolean> = combine(
        readyJobClass,
        readyJobDetailClass
    ) { readyJobClass, readyJobDetailClass ->
        readyJobClass.isNotBlank() && readyJobDetailClass.isNotBlank()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _preferredCity = MutableStateFlow<String>("")
    val preferredCity: StateFlow<String> = _preferredCity.asStateFlow()

    private val _preferredStreet = MutableStateFlow<String>("")
    val preferredStreet: StateFlow<String> = _preferredStreet.asStateFlow()

    fun updatePreferredArea(city: String, street: String) {
        _preferredCity.value = city
        _preferredStreet.value = street
    }

    val preferredArea: StateFlow<String> = combine(
        preferredCity,
        preferredStreet
    ) { preferredCity, preferredStreet ->
        if (preferredStreet.isNotBlank() && preferredCity.isNotBlank()) {
            if (preferredStreet.split(" ").last().equals("전체")) preferredStreet
            else "$preferredCity $preferredStreet"
        }
        else ""
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, "")


    private val _mbtiText = MutableStateFlow<String>("")
    val mbtiText: StateFlow<String> = _mbtiText.asStateFlow()

    fun updateMbtiText(mbti: String) {
        _mbtiText.value = mbti
    }

    private val _interestSelf = MutableStateFlow<ArrayList<String>>(ArrayList())
    val interestSelf: StateFlow<ArrayList<String>> = _interestSelf.asStateFlow()

    fun removeInterestSelf(interest: String) {
        if (_interestSelf.value.contains(interest)) _interestSelf.value.remove(interest)
        updateInterestCount()
    }

    fun addInterestSelf(interest: String) {
        if (!_interestSelf.value.contains(interest)) _interestSelf.value.add(interest)
        updateInterestCount()
    }

    private val _interestField = MutableStateFlow<ArrayList<String>>(ArrayList())
    val interestField: StateFlow<ArrayList<String>> = _interestField.asStateFlow()

    fun removeInterestField(interest: String) {
        if (_interestField.value.contains(interest)) _interestField.value.remove(interest)
        updateInterestCount()
    }

    fun addInterestField(interest: String) {
        if (!_interestField.value.contains(interest)) _interestField.value.add(interest)
        updateInterestCount()
    }

    private var _interestCount = MutableStateFlow<Int>(0)
    val interestCount: StateFlow<Int> = _interestCount.asStateFlow()

    fun updateInterestCount() {
        _interestCount.value = interestField.value.size + interestSelf.value.size
    }

    private var _goalText = MutableStateFlow<String>("")
    val goalText: StateFlow<String> = _goalText.asStateFlow()

    fun updateGoalText(goal: String) {
        _goalText.value = goal
    }

    fun goToNextScreen() {
        _signUpProgress.value =
            when (_signUpProgress.value) {
                SignUpProgress.Character -> SignUpProgress.Name
                SignUpProgress.Name -> SignUpProgress.Birthday
                SignUpProgress.Birthday -> SignUpProgress.Community
                SignUpProgress.Community -> {
                    when (_community.value) {
                        COMMUNITY_WORKER -> SignUpProgress.CurrentJob
                        COMMUNITY_STUDENT -> SignUpProgress.School
                        COMMUNITY_TRAINEE -> SignUpProgress.ReadyJob
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.CurrentJob -> SignUpProgress.Area
                SignUpProgress.School -> SignUpProgress.ReadyJob
                SignUpProgress.ReadyJob -> SignUpProgress.Area
                SignUpProgress.Area -> SignUpProgress.Mbti
                SignUpProgress.Mbti -> SignUpProgress.Interests
                SignUpProgress.Interests -> SignUpProgress.Goal
                SignUpProgress.Goal -> SignUpProgress.Complete
                SignUpProgress.Complete -> SignUpProgress.Fix
                else -> _signUpProgress.value
            }
        goToNextStep()
    }

    fun goToPreviousScreen() {
        _signUpProgress.value =
            when (_signUpProgress.value) {
                SignUpProgress.Name -> SignUpProgress.Character
                SignUpProgress.Birthday -> SignUpProgress.Name
                SignUpProgress.Community -> SignUpProgress.Birthday
                SignUpProgress.CurrentJob -> SignUpProgress.Community
                SignUpProgress.School -> SignUpProgress.Community
                SignUpProgress.ReadyJob -> {
                    when (_community.value) {
                        COMMUNITY_STUDENT -> SignUpProgress.School
                        COMMUNITY_TRAINEE -> SignUpProgress.Community
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.Area -> {
                    when (_community.value) {
                        COMMUNITY_WORKER -> SignUpProgress.CurrentJob
                        COMMUNITY_STUDENT -> SignUpProgress.ReadyJob
                        COMMUNITY_TRAINEE -> SignUpProgress.ReadyJob
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.Mbti -> SignUpProgress.Area
                SignUpProgress.Interests -> SignUpProgress.Mbti
                SignUpProgress.Goal -> SignUpProgress.Interests
                SignUpProgress.Fix -> SignUpProgress.Complete
                else -> _signUpProgress.value
            }
        goToPreviousStep()
    }

    fun goToNextStep() {
        val isWorkerInCurrentJob = _signUpProgress.value == SignUpProgress.CurrentJob
                && _community.value == COMMUNITY_WORKER
        val isTraineeInReadyJob = _signUpProgress.value == SignUpProgress.ReadyJob
                && _community.value == COMMUNITY_TRAINEE
        if (isWorkerInCurrentJob || isTraineeInReadyJob) _currentStep.value++
        val nextStep = _currentStep.value + 1
        _currentStep.value = nextStep.coerceIn(1, 10)
    }

    fun goToPreviousStep() {
        val isWorkerInArea = _signUpProgress.value == SignUpProgress.Area
                && _community.value == COMMUNITY_WORKER
        val isTraineeInArea = _signUpProgress.value == SignUpProgress.Area
                && _community.value == COMMUNITY_TRAINEE
        if (isWorkerInArea || isTraineeInArea) _currentStep.value--
        val previousStep = _currentStep.value - 1
        _currentStep.value = previousStep.coerceAtLeast(0)
    }

    var oauthId = ""

    fun updateOauthId(id: String) {
        oauthId = id
    }

    private var _userInfoState = MutableStateFlow<UserInfoUiState>(UserInfoUiState.Init)
    val userInfoState: StateFlow<UserInfoUiState> = _userInfoState.asStateFlow()

    fun postSignUp(oauthId: String, serviceAgreed: Boolean, privatePolicyAgreed: Boolean) {
        var userInfo = getUserInfo()
        val dateString = combineDateStrings()
        if (userInfo == null) {
            _userInfoState.value = UserInfoUiState.Failure("유저 정보 만들기 실패")
        }
        else {
            viewModelScope.launch {
                repository.postUserInfo(
                    userInfo!!, oauthId, serviceAgreed, privatePolicyAgreed, dateString
                )
                    .onSuccess {
                        // dataStore 로그인 상태 ok
                        dataStore.isLogin = true
                        // dataStore에 현재 유저 아이디 저장 필요 시 it 값 저장
                        // userInfo에 임시로 넣어뒀던 아이디 -> 서버에서 받은 id 값으로 변경
                        userInfo = userInfo!!.changeUserInfoId(it)
                        _userInfoState.value = UserInfoUiState.Success
                    }
                    .onFailure {
                        _userInfoState.value = UserInfoUiState.Failure("유저 정보 업로드 실패")
                    }
            }
        }
    }

    private fun combineDateStrings(): String {
        // 숫자를 두 자리로 맞추기 위해 %02d 형식을 사용
        val formattedMonth = String.format("%02d", birthMonth.value.toInt())
        val formattedDate = String.format("%02d", birthDate.value.toInt())

        // "YYYYMMDD" 형태의 문자열로 합치기
        val combinedString = "${birthYear.value.toInt()}$formattedMonth$formattedDate"

        return combinedString
    }

    private fun getUserInfo(): UserInfo? {
        val jobEntity =
            when (community.value) {
                COMMUNITY_WORKER -> JobEntity(
                    companyName.value,
                    jobClass.value,
                    jobDetailClass.value
                )
                COMMUNITY_STUDENT -> JobEntity(
                    schoolName.value,
                    readyJobClass.value,
                    readyJobDetailClass.value
                )
                COMMUNITY_TRAINEE -> JobEntity(
                    null,
                    readyJobClass.value,
                    readyJobDetailClass.value
                )
                else -> null
            }

        val interests = ArrayList<String>()
        interests.addAll(interestField.value)
        interests.addAll(interestSelf.value)

        return if (jobEntity != null) {
            UserInfo(0L, userName.value, characterId.value, preferredArea.value, mbtiText.value,
                community.value, jobEntity, interests, goalText.value)
        } else null
    }

    companion object {
        private const val REGEX_ID_PATTERN = "^([A-Za-z0-9_.]*)\$"
        private const val COMMUNITY_WORKER = "직장인"
        private const val COMMUNITY_STUDENT = "학생"
        private const val COMMUNITY_TRAINEE = "취업준비생"
    }
}

enum class SignUpProgress {
    Character, Name, Birthday, Community, CurrentJob, School, ReadyJob, Area, Mbti, Interests, Goal,
    Complete, Fix
}

sealed interface UserInfoUiState {
    object Init: UserInfoUiState
    object Success: UserInfoUiState
    data class Failure(val msg: String): UserInfoUiState
}