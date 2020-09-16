package com.ervin.about.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutMeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ervin Suriandi - ervinsuriandi@yahoo.co.id"
    }
    val text: LiveData<String> = _text
}