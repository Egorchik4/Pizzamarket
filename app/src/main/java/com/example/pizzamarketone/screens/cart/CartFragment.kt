package com.example.pizzamarketone.screens.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.battisq.pizzamarket.PizzaEntity
import com.example.pizzamarketone.screens.end.EndFragment
import com.example.pizzamarketone.screens.menu.MenuFragment
import com.example.pizzamarketone.R
import com.example.pizzamarketone.database.pizzaSpisokCart
import com.example.pizzamarketone.databinding.FragmentCartBinding
import com.example.pizzamarketone.screens.details.DetailsViewModel
import com.example.pizzamarketone.screens.menu.MenuRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartFragment : Fragment(), CartRecyclerAdapter.Listener {
    lateinit var binding: FragmentCartBinding
    val VMCart: CartViewVodel by activityViewModels()  // viewmodel details

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // добаваление списка pizza
        val adapter = CartRecyclerAdapter(this)  // создание объекта класса и передаём интерфейс(object)CartRecyclerAdapter.Listener(с помощью "this")
        binding.cartrcView.layoutManager = LinearLayoutManager(activity)  // LinearLayoutManager: упорядочивает элементы в виде списка с одной колонкой
        binding.cartrcView.adapter = adapter


        VMCart.Live.observe(viewLifecycleOwner){
            CoroutineScope(Dispatchers.Main).launch {
                adapter.addAll(VMCart.Live.value!!.toMutableList())
            }
        }

        VMCart.QuantityCartLive.observe(viewLifecycleOwner){
            binding.buttonplaceorder.text = "Place order   ${VMCart.QuantityCartLive.value}₽"  // вставка полной цены
        }

        VMCart.DeleteLive.observe(viewLifecycleOwner){
            adapter.deleteALL(VMCart.DeleteLive.value)
        }


        // активация галочек
        VMCart.ActivateCheckLive.observe(viewLifecycleOwner){
            adapter.visibleCheck(VMCart.ActivateCheckLive.value ?: 0)
        }

        binding.imDelete.setOnClickListener {
            if(VMCart.ActivateCheckLive.value == 0){
                VMCart.activateCheck(1)  // активация галочек
            }else{
                VMCart.activateCheck(0)
                VMCart.deleteList(adapter.getDeleteList())  // взятие листа на удаление
                adapter.cleardealeteList() // очистка листа удаления
            }
        }
        binding.buttonplaceorder.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left)
                ?.replace(R.id.fragment_container, EndFragment())
                ?.commit()

            pizzaSpisokCart.clearListOrder() //очистка списка заказов
        }
    }

    // получение информции из RecyclerView
    override fun myOnClick(list: PizzaEntity, quantity:Int) {
        VMCart.refreshQuantity(list,quantity)
    }
}