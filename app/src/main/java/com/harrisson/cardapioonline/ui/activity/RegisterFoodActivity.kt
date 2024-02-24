package com.harrisson.cardapioonline.ui.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.harrisson.cardapioonline.R
import com.harrisson.cardapioonline.databinding.ActivityRegisterFoodBinding

class RegisterFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txtToolbar = findViewById<TextView>(R.id.txt_toolbar_title)
        txtToolbar.text = "Cadastrar Produto"

    }
}