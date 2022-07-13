package com.example.pizzamarketone.screens.end

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EndViewVodel : ViewModel(){

    // создание LiveData
    private var MutLive = MutableLiveData<Int>()
    val Live: LiveData<Int> = MutLive

}