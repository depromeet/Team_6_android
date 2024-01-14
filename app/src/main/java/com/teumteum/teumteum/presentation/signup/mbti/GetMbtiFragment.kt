package com.teumteum.teumteum.presentation.signup.mbti

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentGetMbtiBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.presentation.signup.modal.MbtiModalBottomSheet
import com.teumteum.teumteum.presentation.signup.modal.SingleModalBottomSheet
import kotlinx.coroutines.launch

class GetMbtiFragment:
    BindingFragment<FragmentGetMbtiBinding>(R.layout.fragment_get_mbti) {

    private val viewModel by activityViewModels<SignUpViewModel>()
    private var bottomSheet: MbtiModalBottomSheet? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        initBottomSheet()
        checkValidInput()
    }

    private fun initBottomSheet() {
        val listener: (String) -> Unit = { item ->
            viewModel.updateMbtiText(item)
            bottomSheet?.dismiss()
        }

        bottomSheet = MbtiModalBottomSheet.newInstance("MBTI", listener)

        with(binding) {
            llStatus.setOnClickListener {
                bottomSheet?.setFocusedImageView(ivShow)
                val mbtiBoolean = BooleanArray(4)
                val mbtiCharArray = viewModel.mbtiText.value.toCharArray()
//                reloadLastMbti()
                bottomSheet?.initMbti()
                bottomSheet?.show(childFragmentManager, SingleModalBottomSheet.TAG)
                ivShow.setImageResource(R.drawable.ic_arrow_up_l)
            }
        }
    }

    /* 지난 mbti 선택에 따라 버튼 활성화 유지 -> 수정 필요 */
    private fun reloadLastMbti() {
        val mbtiBoolean = BooleanArray(4)
        val mbtiCharArray = viewModel.mbtiText.value.toCharArray()
        if (mbtiCharArray.size == 4) {
            mbtiBoolean[0] = mbtiCharArray[0] == 'e'
            mbtiBoolean[1] = mbtiCharArray[1] == 'n'
            mbtiBoolean[2] = mbtiCharArray[2] == 'f'
            mbtiBoolean[3] = mbtiCharArray[3] == 'p'
            bottomSheet?.setSelectedMbti(mbtiBoolean[0], mbtiBoolean[1], mbtiBoolean[2], mbtiBoolean[3])
        }
        else bottomSheet?.initMbti()
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.mbtiText.collect { mbti ->
                if (mbti.isNotBlank()) (activity as SignUpActivity).activateNextButton()
                else (activity as SignUpActivity).disableNextButton()
            }
        }
    }

    companion object {
    }
}