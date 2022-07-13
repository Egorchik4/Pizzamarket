package com.example.pizzamarketone.database

import android.util.Log
import com.battisq.pizzamarket.Pizza
import com.battisq.pizzamarket.PizzaEntity

// для доступа со всех классов (имеет только один экземпляр класса)
object pizzaSpisokCart{
    var pizzaListCart: MutableList<PizzaEntity> = mutableListOf() // список содержащий только элементы добавленные в Cart!!! + без дублирования

    var positionMap: MutableMap<Int,Int> = mutableMapOf()

    fun clearListOrder(){
        pizzaListCart.clear()
    }
    // заполнение pizzaListCart из Details(VM)
    fun addpizza(pizza: PizzaEntity){
        // для бездублирования позиций
        if(pizzaListCart.contains(pizza) != true){
            pizzaListCart.add(pizza)    // заполнение pizzaListCart
            positionMap.put(pizza.id, 1)  // заполнение PositionMap
        }
    }

    // удаляем из БД
    fun deletepizza(Spisok: MutableList<PizzaEntity>){
        if(Spisok.size != 0){
            for(i in 0..(Spisok.size-1)){
                pizzaListCart.removeIf {
                    it.id == Spisok[i].id
                }
            }
        }
    }

    // запись в PositionMap количество позиций конкретной пиццы
    fun sum(list: PizzaEntity,quantity: Int){
        var key: Int = 0
        pizzaListCart.forEach {
            if(it.id == list.id){
                key = it.id
            }
        }
        positionMap.put(key, quantity)  // ключ-позиция(position), значение-количество(quantity)
    }

    //отображение цены всего заказа + количество пицц
    fun getPriceOrderALL():Double{
        var AllPrice: Double = 0.0
        // идём по списку пицц
        pizzaListCart.forEach {
            AllPrice += it.price * (positionMap.getValue(it.id))
        }
        return AllPrice
    }
}