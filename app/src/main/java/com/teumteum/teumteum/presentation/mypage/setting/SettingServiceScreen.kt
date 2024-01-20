package com.teumteum.teumteum.presentation.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R

@Composable
fun SettingServiceScreen(viewModel: SettingViewModel) {
    TmScaffold(
        topbarText = stringResource(id = R.string.setting_service_guide_topbar),
        onClick = { viewModel.updateSettingStatus(SettingStatus.SETTING) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background)
        ) {
            TmMarginVerticalSpacer(size = 58)
            SettingServiceColumn2()
        }
    }
}

@Composable
fun SettingServiceColumn2() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = TmtmColorPalette.current.elevation_color_elevation_level01),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        items(getServiceGuide()) { item->
            SettingTitle(
                title = item.title,
                onClick = {
                    item.onClick()
                }
            )
        }
    }
}