package com.example.assignment2.utils

import androidx.annotation.StringRes
import com.example.assignment2.R

enum class SwitchScreen(@StringRes val title: Int) {
    Login(R.string.login),
    Landing(R.string.landing),
    Register(R.string.register),
    Profile(R.string.profile),
    Detail(R.string.detail),
    Review(R.string.reviews),
    Search(R.string.search),
    Favourite(R.string.favourite),
    FavouriteDetail(R.string.favourite_detail),
    Similar(R.string.similar)
}
