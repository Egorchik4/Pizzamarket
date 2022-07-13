package com.example.pizzamarketone.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.pizzamarketone.screens.preview.PreviewFragment
import com.example.pizzamarketone.R
import com.example.pizzamarketone.databinding.FragmentDetailsBinding
import com.example.pizzamarketone.screens.preview.PreviewViewVodel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentDetailsBinding
    private val VMDetails: DetailsViewModel by activityViewModels()
    //private val VMPreview: PreviewViewVodel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater)

        //  Для ЗАПОЛНЕНИЯ bottomshielddialogfragment!
        val photo: String? =  arguments?.getString("imageUrl")
        val namepizza: String? =  arguments?.getString("name")
        val description: String? =  arguments?.getString("description")

        Glide
            .with(this)
            .load(photo)
            .into(binding.imaPizza)

        binding.tName.text = namepizza
        binding.tPizza.text = description

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddtoCart.text = "Add to Cart  ${arguments?.getString("price")}"

        binding.buttonAddtoCart.setOnClickListener {
            VMDetails.add(arguments?.getString("index")!!.toInt())  // выбранные пиццы
        }

        binding.imaPizza.setOnClickListener {
            //PreviewFragment.getNewInstance(putData())
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, PreviewFragment.getNewInstance(putDataPreview()))
                ?.commit()
        }
    }


    fun putDataPreview():Bundle{
        val bundle = Bundle()
        // передача данных через bundle
        bundle.putString("index",arguments?.getString("index"))
        bundle.putString("price",arguments?.getString("price"))
        bundle.putString("imageUrl", arguments?.getString("imageUrl"))
        bundle.putString("name", arguments?.getString("name"))
        return bundle
    }
}