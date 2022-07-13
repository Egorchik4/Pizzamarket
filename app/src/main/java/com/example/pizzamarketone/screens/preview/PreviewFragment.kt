package com.example.pizzamarketone.screens.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.pizzamarketone.R
import com.example.pizzamarketone.screens.cart.CartFragment
import com.example.pizzamarketone.databinding.FragmentPreviewBinding
import com.example.pizzamarketone.screens.details.DetailsViewModel
import com.example.pizzamarketone.screens.menu.MenuFragment

class PreviewFragment : Fragment() {

    lateinit var bindingg: FragmentPreviewBinding
    private val VMPreview: PreviewViewVodel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // через viewBinding
        bindingg = FragmentPreviewBinding.inflate(inflater)
        return bindingg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index: String? =  arguments?.getString("index")
        val photo: String? =  arguments?.getString("imageUrl")
        val namepizza: String? =  arguments?.getString("name")
        val price: String? =  arguments?.getString("price")

        bindingg.buttonAddto.text = "Add to Cart   ${price ?: 0.0}"
        Glide
            .with(this)
            .load(photo)
            .into(bindingg.imagePizza)
        bindingg.textPizzaView.text = namepizza

        bindingg.imageBack.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                ?.replace(R.id.fragment_container, MenuFragment.getNewInstance(putDataMenu()))
                ?.commit()
        }

        bindingg.buttonAddto.setOnClickListener {
            VMPreview.addItem(index)
        }
    }


    // для передачи данных от фрагмета к фрагмету через Bundle (companion - внутри класса)
    companion object{
        fun getNewInstance(args: Bundle?): PreviewFragment{
            val previewFragment = PreviewFragment()
            previewFragment.arguments = args
            return previewFragment
        }
    }


    // для передачи значений в MenuFragment
    fun putDataMenu():Bundle?{
        if(VMPreview.Live.value == 1){
            val bundle = Bundle()
            bundle.putString("refresh","RefreshData") // передача данных через bundle
            VMPreview.clean() // для очистки LiveData
            return bundle
        }
        return null
    }
}