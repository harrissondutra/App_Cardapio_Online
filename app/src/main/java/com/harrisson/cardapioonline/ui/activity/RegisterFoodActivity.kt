package com.harrisson.cardapioonline.ui.activity

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.harrisson.cardapioonline.R
import com.harrisson.cardapioonline.databinding.ActivityRegisterFoodBinding
import com.harrisson.cardapioonline.models.ChildItem
import com.harrisson.cardapioonline.models.ParentItem

class RegisterFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txtToolbar = findViewById<TextView>(R.id.txt_toolbar_title)
        txtToolbar.text = "Cadastrar Produto"


        val items = ArrayList<String>()
        items.add("Entradas")
        items.add("Pratos")
        items.add("Bebidas")
        items.add("Sobremesas")

        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.list_category_view)
        autoCompleteTextView.setAdapter(adapter)

        binding.btnRegisterFood.setOnClickListener {


            val productCategory = binding.listCategoryView.text.toString()
            val productName = binding.edtRegisterNameFood.text.toString()
            val productDescription = binding.edtDescNameFood.text.toString()
            val productPrice = binding.edtRegisterPriceFood.text.toString()

            /*    if(productPrice.isBlank()){
                binding.txtRegisterPriceFood.error = "Campo obrigatório"
                Toast.makeText(this, "Verifique se todos os campos foram preenchidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }*/

            if(productCategory.isBlank() ){
                binding.listCategoryView.error = "Campo obrigatório"
                Toast.makeText(this, "Verifique se todos os campos foram preenchidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                binding.listCategory.text = null
                binding.listCategoryView.error = null

            }


            if (productName.isBlank()) {
                binding.edtRegisterNameFood.error = "Campo obrigatório"
                Toast.makeText(this, "Verifique se todos os campos foram preenchidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                binding.edtRegisterNameFood.error = null
            }

            if (productDescription.isBlank()) {
                binding.edtDescNameFood.error = "Campo obrigatório"
                Toast.makeText(this, "Verifique se todos os campos foram preenchidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                binding.edtDescNameFood.error = null

            }

            if (productPrice.isBlank()) {
                binding.edtRegisterPriceFood.error = "Campo obrigatório"
                Toast.makeText(this, "Verifique se todos os campos foram preenchidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                binding.edtRegisterPriceFood.error = null
            }

            if (productPrice.toDouble() <= 0) {
                binding.edtRegisterPriceFood.error = "Preço inválido"
                Toast.makeText(this, "Preço inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val productCreated = ArrayList<ChildItem>()
            productCreated.add(
                ChildItem(
                    productName,
                    R.drawable.semfoto,
                    productPrice.toDouble(),
                    productDescription
                )
            )


            val productList: ArrayList<ParentItem> = arrayListOf()
            productList.add(ParentItem(productCategory, R.drawable.almoco, productCreated))

            AlertDialog.Builder(this)
                .setTitle("Produto Cadastrado")
                .setMessage(
                    "Categoria: $productCategory\n" +
                            "Produto: $productName\n" +
                            "Preço: $productPrice\n" +
                            "Descrição: $productDescription"

                )
                .setPositiveButton("OK") { dialog, _ ->
                    AlertDialog.Builder(this)
                        .setTitle("Cadastrar outro produto?")

                        .setPositiveButton("Sim") { dialog, _ ->
                            dialog.dismiss()
                            binding.listCategoryView.text.clear()
                            binding.edtRegisterNameFood.text?.clear()
                            binding.edtDescNameFood.text?.clear()
                            binding.edtRegisterPriceFood.text?.clear()

                            binding.edtRegisterNameFood.isCursorVisible = true


                        }
                        .setNegativeButton("Não") { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        }
                        .show()
                }
                .show()




        }
    }
}