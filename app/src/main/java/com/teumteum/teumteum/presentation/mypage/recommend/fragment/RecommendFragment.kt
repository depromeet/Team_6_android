package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentRecommendBinding
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendScreen

class RecommendFragment: BindingFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeRecommend.setContent {
            RecommendScreen()
        }

    }

    companion object {
    }
}