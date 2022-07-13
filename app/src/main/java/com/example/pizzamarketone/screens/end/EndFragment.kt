package com.example.pizzamarketone.screens.end

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pizzamarketone.R
import com.example.pizzamarketone.databinding.FragmentEndBinding
import com.example.pizzamarketone.screens.menu.MenuFragment


class EndFragment : Fragment() {
    lateinit var binding: FragmentEndBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // через viewBinding
        binding = FragmentEndBinding.inflate(inflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.Title.text = "Order was successfully sent!"
        binding.title.text = "We will call you soon to confirm order details"
        binding.buttonBacktoMenu.text = "Back to Menu"

        binding.buttonBacktoMenu.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, MenuFragment.getNewInstance(putDataMenu()))
                ?.commit()
        }
    }

    // для передачи значений в MenuFragment
    fun putDataMenu():Bundle?{
        return null
    }
}