package com.example.assignment2.utils

import androidx.annotation.StringRes
import com.example.assignment2.R

enum class SwitchScreen(@StringRes val title: Int) {
    Login(R.string.login),
    Landing(R.string.landing),
    Register(R.string.register),
    ViewProfile(R.string.profile),
    Detail(R.string.detail)
}
