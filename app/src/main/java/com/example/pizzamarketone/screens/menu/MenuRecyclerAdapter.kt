package com.example.pizzamarketone.screens.menu


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.battisq.pizzamarket.Pizza
import com.battisq.pizzamarket.PizzaEntity
import com.bumptech.glide.Glide
import com.example.pizzamarketone.screens.details.DetailsFragment
import com.example.pizzamarketone.R
import com.example.pizzamarketone.databinding.PizzaItemBinding
import kotlinx.coroutines.delay


// класс Adapter(связь кода и View)
class MenuRecyclerAdapter: RecyclerView.Adapter<MenuRecyclerAdapter.PizzaHolder>(){

    var pizzaList: MutableList<PizzaEntity> = mutableListOf()   // лист ячеек списка тип Pizza

    // добавление элемента в начало списка
    suspend fun addItem(newItem: PizzaEntity, index: Int){
        pizzaList.add(newItem)
        notifyItemChanged(index)
        delay(50)
    }


    // класс viewHolder(содержимое View)
    class PizzaHolder(item: View):RecyclerView.ViewHolder(item) {  // принимаем раздутую View(ячейку списка) из onCreateViewHolder
        val bindingg = PizzaItemBinding.bind(item) // viewBinding

        fun bind(pizza: PizzaEntity)  // заполнение View из класса Pizza(4 поля)
        {
            Glide
                .with(itemView)
                .load(pizza.imageUrl)
                .into(bindingg.imPizza)

            bindingg.textPizza.text = pizza.description
            bindingg.textName.text = pizza.name
            bindingg.textPrice.text = pizza.price.toString() + "₽"
        }

        // запуск showBottomSheet
        fun showBottomSheet(context: Context, data: PizzaEntity) {
            val bottomSheetFragment = DetailsFragment()   // bottomSheetFragment, который DetailsFragment()
            val bundle = Bundle()

            bundle.putString("index", data.id.toString())
            bundle.putString("price",data.price.toString()+"₽")
            bundle.putString("imageUrl", data.imageUrl)     // передача данных через bundle!!!!
            bundle.putString("name", data.name)
            bundle.putString("description", data.description)

            bottomSheetFragment.arguments = bundle
            //запуск bottomSheetFragment
            bottomSheetFragment.show((context as AppCompatActivity).supportFragmentManager, "bottomSheetFragment")
        }
    }
    // 3-ри метода для работы с RecyclerView:

    // "1" создание элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pizza_item,parent,false) // надуваем View(ячейку списка)
        return PizzaHolder(view) // возвращаем класс с надутой View
    }
    // "2" заполнение значениями(списка)
    override fun onBindViewHolder(holder: PizzaHolder, position: Int) {
        holder.bind(pizzaList[position])
        // обработка нажатия на элемент в списке
        holder.bindingg.CardView.setOnClickListener {
            holder.showBottomSheet(holder.bindingg.CardView.context,pizzaList[position])
        }
    }
    // "3"
    override fun getItemCount(): Int {
        return pizzaList.size
    }

    suspend fun addAll(Spisok: List<PizzaEntity>?){
        if(Spisok != null){
            for (i in 0..(Spisok!!.size - 1)) {
                addItem(Spisok!!.get(i), i)
            }
        }
    }

    suspend fun searchUpdate(Spisok: List<PizzaEntity>?){
        pizzaList.clear()
        notifyDataSetChanged()
        if(Spisok != null){
            for (i in 0..(Spisok!!.size - 1)) {
                addItem(Spisok!!.get(i), i)
            }
        }
    }
}