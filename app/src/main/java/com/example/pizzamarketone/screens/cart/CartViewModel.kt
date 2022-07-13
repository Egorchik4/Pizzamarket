package com.example.pizzamarketone.screens.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.battisq.pizzamarket.PizzaEntity
import com.example.pizzamarketone.database.pizzaSpisokCart

class CartViewVodel : ViewModel(){
    // создание LiveData
    private var MutLive = MutableLiveData<MutableList<PizzaEntity>>()
    val Live: LiveData<MutableList<PizzaEntity>> = MutLive

    private var QuantityCartMutLive = MutableLiveData<Double>()
    val QuantityCartLive: LiveData<Double> = QuantityCartMutLive

    // для активации галочек
    private var ActivateCheckMutLive = MutableLiveData<Int>()
    val ActivateCheckLive: LiveData<Int> = ActivateCheckMutLive

    // для хранения списка элементов, которые надо удалить
    private var DeleteListLive = MutableLiveData<MutableList<PizzaEntity>>()
    val DeleteLive: LiveData<MutableList<PizzaEntity>> = DeleteListLive

    init{
        MutLive.value = pizzaSpisokCart.pizzaListCart
        QuantityCartMutLive.value = pizzaSpisokCart.getPriceOrderALL()
        activateCheck(0)
    }

    // обновление цены
    fun refreshQuantity(list: PizzaEntity,quantity: Int){
        pizzaSpisokCart.sum(list,quantity)
        QuantityCartMutLive.value = pizzaSpisokCart.getPriceOrderALL()
    }

    fun deleteList(spisok: MutableList<PizzaEntity>){
        DeleteListLive.value = spisok
        pizzaSpisokCart.deletepizza(spisok)  // удаление из БД

        QuantityCartMutLive.value = pizzaSpisokCart.getPriceOrderALL()  // обновление цены
    }


    fun activateCheck(t: Int){
        ActivateCheckMutLive.value = t
    }



}