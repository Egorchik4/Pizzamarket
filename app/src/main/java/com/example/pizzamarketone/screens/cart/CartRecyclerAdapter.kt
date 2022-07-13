package com.example.pizzamarketone.screens.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.battisq.pizzamarket.PizzaEntity
import com.bumptech.glide.Glide
import com.example.pizzamarketone.R
import com.example.pizzamarketone.databinding.PizzaItemBinding
import com.example.pizzamarketone.databinding.PizzacartItemBinding
import com.example.pizzamarketone.screens.details.DetailsFragment
import kotlinx.coroutines.delay

// класс Adapter(связь кода и View)
class CartRecyclerAdapter(val listener: Listener): RecyclerView.Adapter<CartRecyclerAdapter.PizzaHolder>(){

    var pizzaList: MutableList<PizzaEntity> = mutableListOf()   // лист ячеек списка тип Pizza
    var delateList: MutableList<PizzaEntity> = mutableListOf() // спискок хранения позиций на удаление
    var visibleCheck: Int = 0

    // добавление элемента в начало списка
    suspend fun addItem(newItem: PizzaEntity, index: Int){
        pizzaList.add(newItem)
        notifyItemChanged(index)
        delay(250)
    }

    // удаление элементов
    fun deleteItem(index: Int){
        //delay(250)
        pizzaList.removeAt(index)
        Log.e("eee",pizzaList.size.toString())
        notifyItemChanged(index)
    }


    // класс viewHolder(содержимое View)
    class PizzaHolder(item: View): RecyclerView.ViewHolder(item) {  // принимаем раздутую View(ячейку списка) из onCreateViewHolder
        val bindingg = PizzacartItemBinding.bind(item)  // viewBinding
        fun bind(pizza: PizzaEntity)  // заполнение View из класса Pizza(4 поля)
        {
            Glide
                .with(itemView)
                .load(pizza.imageUrl)
                .into(bindingg.cartimView)
            bindingg.carttextPizza.text = pizza.name
            bindingg.carttextPrice.text = pizza.price.toString() + "₽"

            bindingg.textQuantity.text = "1"
            bindingg.buttonPlus.text = "+"
            bindingg.buttonMinus.text = "-"
        }
    }
    // 3-ри метода для работы с RecyclerView:

    // "1" создание элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pizzacart_item,parent,false) // надуваем View(ячейку списка)
        return PizzaHolder(view) // возвращаем класс с надутой View
    }
    // "2" заполнение значениями(списка)
    override fun onBindViewHolder(holder: PizzaHolder, position: Int) {
        holder.bind(pizzaList[position])

        var n:Int = 1  // переременная для хранения количества пицц
        // обработка нажатия на элемент в списке
        holder.bindingg.buttonPlus.setOnClickListener {
            n++
            holder.bindingg.textQuantity.text = "$n"
            //calculateprice(position,n)
            listener.myOnClick(pizzaList[position],n)
        }

        holder.bindingg.buttonMinus.setOnClickListener {
            n--
            holder.bindingg.textQuantity.text = "$n"
            //calculateprice(position,n)
            listener.myOnClick(pizzaList[position],n)
        }

        // видимость объектов
        if(visibleCheck != 0){
            holder.bindingg.checkBox.visibility = View.VISIBLE  // видимость галочек
            holder.bindingg.CardView.setOnClickListener {
                holder.bindingg.checkBox.setChecked(true)

                dalateById(pizzaList[position])

                // корректировка списка удаления
                holder.bindingg.checkBox.setOnClickListener {
                    //Log.e("eee","Bin2")
                    //dalateById(pizzaList[position])
                    if(holder.bindingg.checkBox.isChecked){
                        //dalateById(pizzaList[position])
                    }else{
                        delateBy(pizzaList[position])
                    }
                }
            }
        }else{
            holder.bindingg.CardView.setOnClickListener(null) // удаления слушателя нажатий
            holder.bindingg.checkBox.visibility = View.GONE
            holder.bindingg.checkBox.setChecked(false)
        }



    }
    // "3"
    override fun getItemCount(): Int {
        return pizzaList.size
    }

    suspend fun addAll(Spisok: MutableList<PizzaEntity>?){
        if(Spisok != null){
            for (i in 0..(Spisok!!.size - 1)) {
                addItem(Spisok!!.get(i), i)
            }
        }
    }

    // Удаление элементов списка
    fun deleteALL(lst: MutableList<PizzaEntity>?){
        Log.e("eee","deleteAll "+lst?.size.toString())
        // удаление элементов
        if(pizzaList != null) {
            for(i in 0..(lst!!.size-1)){
                deleteItem(pizzaList.indexOf(lst.get(i)))
            }
        }
    }

    // видимость галочек
    fun visibleCheck(t: Int){
        visibleCheck = t
        notifyDataSetChanged()
    }

    // формирование списка на удаление
    fun dalateById(Spisok: PizzaEntity){
        if(delateList.contains(Spisok) == false){
            delateList.add(Spisok)
        }
        //Log.e("eee","Size1 = ${delateList.size}")
    }

    // корректировка списка на удаление
    fun delateBy(Spisok: PizzaEntity){
        for(i in 0..(delateList.size-1)){
            if(delateList[i] == Spisok){
                delateList.remove(Spisok)
            }
        }
        //Log.e("eee","Size2 = ${delateList.size}")
    }


    // возврат списка на удаления
    fun getDeleteList(): MutableList<PizzaEntity> {
        return delateList
    }


    fun cleardealeteList(){
        delateList.clear()
    }


    // интерфейс для передачи информации из RecyclerView
    interface Listener{
        fun myOnClick(list: PizzaEntity, quantity:Int)
    }


}