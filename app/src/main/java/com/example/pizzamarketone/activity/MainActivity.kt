package com.example.pizzamarketone.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzamarketone.screens.menu.MenuFragment
import com.example.pizzamarketone.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreen)   // экран загрузки
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //показ MenuFragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MenuFragment())
            .commit()

    }


}
