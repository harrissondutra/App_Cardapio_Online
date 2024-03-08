package com.harrisson.cardapioonline.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.harrisson.cardapioonline.R
import com.harrisson.cardapioonline.databinding.ActivityCategoriesBinding
import com.harrisson.cardapioonline.models.ChildItem
import com.harrisson.cardapioonline.models.ParentItem

class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding
    private var db =  Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbarTitle = findViewById<TextView>(R.id.txt_toolbar_title)
        toolbarTitle.text = "Cadastrar Categorias"

        val logoutButton = findViewById<TextView>(R.id.txt_logoff)

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCategoriesRegister.setOnClickListener {

            val category = binding.inputCategories.text.toString()

            if (category.isEmpty()) {
                binding.inputCategories.error = "Campo obrigatório"
                return@setOnClickListener
            }
            val childlist: ArrayList<ChildItem> = arrayListOf()

            val product = hashMapOf(
                "category" to category,
                "image" to R.drawable.semfoto,
                "products" to childlist
            )

            db.collection("categories").document(category)
                .set(product)
                .addOnSuccessListener { documentReference ->
                    createSnackBarLoginSuccesful(
                        binding.root,
                        "Categoria $category cadastrada com sucesso",
                        Color.rgb(13, 142, 9))
                    Log.d(ContentValues.TAG, "Adicionado categoria: $category")
                    binding.inputCategories.text?.clear()
                }
                .addOnFailureListener { e ->
                    createSnackBar(binding.root, "Falha ao cadastrar", Color.RED)
                    Log.w(ContentValues.TAG, "Erro ao adicionar categoria", e)
                }




           /* db.collection("products")
                .add(product)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "Adicionado produto: $category")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Erro ao adicionar categoria", e)
                }*/
        }
    }

    private fun createSnackBarLoginSuccesful(view: View, alerta: String, color: Int = Color.RED) {
        val snackbar =
            Snackbar.make(view, alerta, Snackbar.LENGTH_SHORT).setAction("OK") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        val view: View = snackbar.getView()
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
        view.layoutParams = params
        snackbar.setBackgroundTint(color)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
    }

    private fun createSnackBar(view: View, alerta: String, color: Int = Color.RED) {
        val snackbar = Snackbar.make(view, alerta, Snackbar.LENGTH_SHORT)
        val view: View = snackbar.getView()
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
        view.layoutParams = params
        snackbar.setBackgroundTint(color)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
    }
}