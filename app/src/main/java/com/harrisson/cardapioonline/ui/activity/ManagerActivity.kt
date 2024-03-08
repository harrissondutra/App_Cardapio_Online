package com.harrisson.cardapioonline.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.harrisson.cardapioonline.R
import com.harrisson.cardapioonline.databinding.ActivityManagerBinding

class ManagerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txtToolbar = findViewById<TextView>(R.id.txt_toolbar_title)
        txtToolbar.text = "Gerenciar Cardápio"
        // Verificar onde foi clicado e encaminhar para a Activity correta



        binding.cardRegister.setOnClickListener(this)
        binding.cardEdit.setOnClickListener(this)
        binding.cardRemove.setOnClickListener(this)
        binding.cardList.setOnClickListener(this)

        val logoutButton = findViewById<TextView>(R.id.txt_logoff)

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            true
        }


    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.card_register -> {
                val intent = Intent(this, RegisterFoodActivity::class.java)
                startActivity(intent)
            }

            R.id.card_edit -> {
                /*val intent = Intent(this, RegisterFoodActivity::class.java)
                startActivity(intent)*/
                Toast.makeText(this, "Função em implementação", Toast.LENGTH_SHORT).show()
            }

            R.id.card_remove -> {
                /*val intent = Intent(this, RegisterFoodActivity::class.java)
                startActivity(intent)*/
                Toast.makeText(this, "Função em implementação", Toast.LENGTH_SHORT).show()
            }

            R.id.card_list -> {
                /* val intent = Intent(this, ManagerActivity::class.java)
                 startActivity(intent)*/
                Toast.makeText(this, "Função em implementação", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


