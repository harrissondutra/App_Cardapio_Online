package com.harrisson.cardapioonline.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.harrisson.cardapioonline.databinding.ActivityDestinyBinding

class DestinyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDestinyBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDestinyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var mesas: ArrayList<Int> = arrayListOf()
            for (i in 1..5) {  // 1..10 é um intervalo que vai de 1 a 10
                mesas.add(i)}

        var spinnerData = mesas.map {
            it.toString() }


        binding.spinnerMesa.adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerData)



            binding.btnLogin.setOnClickListener {
                var name = binding.edtNameLogin.text.toString()
                var tableNumber = binding.spinnerMesa.selectedItem.toString()

                if (name.isBlank()) {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                } else {
                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", name)
                    var index = spinnerData.indexOf(tableNumber) + 1
                    intent.putExtra("tableNumber", index.toString())
                    startActivity(intent)
                }
            }
    }
}