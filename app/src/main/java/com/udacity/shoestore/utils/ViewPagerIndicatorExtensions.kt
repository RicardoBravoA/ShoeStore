package com.udacity.shoestore.utils

import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.udacity.shoestore.R
import com.zhpan.indicator.IndicatorView
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

fun IndicatorView.init(viewPager: ViewPager) {
    setSliderColor(
        ContextCompat.getColor(context, R.color.indicator_color),
        ContextCompat.getColor(context, R.color.purple_500)
    )
        .setSlideMode(IndicatorSlideMode.WORM)
        .setIndicatorStyle(IndicatorStyle.CIRCLE)
        .setupWithViewPager(viewPager)
}