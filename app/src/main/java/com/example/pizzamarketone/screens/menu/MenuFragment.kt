package com.example.pizzamarketone.screens.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.battisq.pizzamarket.PizzaDatabase
import com.battisq.pizzamarket.PizzaEntity
import com.example.pizzamarketone.R
import com.example.pizzamarketone.databinding.FragmentMenuBinding
import com.example.pizzamarketone.screens.cart.CartFragment
import com.example.pizzamarketone.screens.details.DetailsViewModel
import com.example.pizzamarketone.screens.end.EndFragment
import com.example.pizzamarketone.screens.preview.PreviewFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MenuFragment : Fragment() {

    lateinit var binding: FragmentMenuBinding
    private val VMMenu: MenuViewVodel by activityViewModels()
    private val VMDetails: DetailsViewModel by activityViewModels()  // viewmodel details


    var newlist: MutableList<PizzaEntity> = mutableListOf() // новый список для хранения отсортиованной иформации(пустой спиок)
    var oldlist: MutableList<PizzaEntity> = PizzaDatabase.pizzaDao.getAll() as MutableList<PizzaEntity>    // старый спискок



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleMenu.text = "Menu"
        // добаваление списка pizza
        val adapter = MenuRecyclerAdapter()  // создание объекта класса
        binding.rcView.layoutManager = LinearLayoutManager(activity)  // LinearLayoutManager: упорядочивает элементы в виде списка с одной колонкой
        binding.rcView.adapter = adapter
        binding.searchView.isFocusable = true
        //
        VMDetails.refresh(arguments?.getString("refresh")) // обновление значения цены
        //


        // следит за обновлениями списка
        VMMenu.PizzesLive.observe(viewLifecycleOwner){
            CoroutineScope(Dispatchers.Main).launch {
                adapter.addAll(VMMenu.PizzesLive.value)
            }
        }

        // "динамическое" изменение кнопки и цены на ней
        VMDetails.DetailsLive.observe(viewLifecycleOwner){
            if(VMDetails.DetailsLive.value != null){
                binding.cardViewAdd.visibility = View.VISIBLE
                binding.buttonAdd.text = "Checkout            ${VMDetails.DetailsLive.value} ₽"
            }else{
                binding.cardViewAdd.visibility = View.GONE
            }
        }

        // показ поиска
        binding.imageView.setOnClickListener {
            binding.imageView.visibility = View.GONE
            binding.titleMenu.visibility = View.GONE
            binding.searchView.visibility = View.VISIBLE
            binding.imageViewCancel.visibility = View.VISIBLE
            binding.searchView.requestFocus() // для фокусировки
        }

        binding.imageViewCancel.setOnClickListener{
            binding.imageView.visibility = View.VISIBLE
            binding.titleMenu.visibility = View.VISIBLE
            binding.searchView.visibility = View.GONE
            binding.imageViewCancel.visibility = View.GONE
            binding.searchView.setText("")
        }


        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newlist.clear()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p: Editable?) {
                val searchText: String = p.toString().lowercase()  // перевод в строчный шрифт

                if (searchText.isNotEmpty()) {
                    // идём мо каждому элементу списка
                    oldlist.forEach {
                        // проверяем на содержание вводимого текста в поле name
                        if (it.name.lowercase().contains(searchText)) {
                            newlist.add(it)  // добавляем класс в новый список классов
                        }
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        adapter.searchUpdate(newlist)  // отображение нового списка
                    }
                }else{
                    CoroutineScope(Dispatchers.Main).launch {
                        adapter.searchUpdate(oldlist)
                    }
                }
            }

        })


        binding.buttonAdd.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left)
                ?.replace(R.id.fragment_container, CartFragment())
                ?.commit()
        }
    }


    companion object{
        fun getNewInstance(args: Bundle?): MenuFragment {
            val menuFragment = MenuFragment()
            menuFragment.arguments = args
            return menuFragment
        }
    }
}