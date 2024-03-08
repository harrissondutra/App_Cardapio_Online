package com.harrisson.cardapioonline.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.harrisson.cardapioonline.R
import com.harrisson.cardapioonline.databinding.ActivityRegisterFoodBinding

class RegisterFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterFoodBinding
//    private var newFood: Boolean = true
//    private var childItem: ChildItem? = null
//    private var parentList: ArrayList<ParentItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        binding = ActivityRegisterFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txtToolbar = findViewById<TextView>(R.id.txt_toolbar_title)
        txtToolbar.text = "Cadastrar Produto"

        val logoutButton = findViewById<TextView>(R.id.txt_logoff)

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.list_category_view)

        // Buscar IDs dos documentos da coleção "categories"
        db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                val categoryIds = mutableListOf<String>()
                for (document in result) {
                    // Acessar o ID do documento
                    val id = document.id
                    categoryIds.add(id)
                }

                // Exibir os IDs dos documentos
                for (id in categoryIds) {
                    Log.d(TAG, "ID da Categoria: $id")
                }
                val adapter = ArrayAdapter(this, R.layout.list_item, categoryIds)
                autoCompleteTextView.setAdapter(adapter)
            }
            .addOnFailureListener { exception ->
                // Tratar erros
                Toast.makeText(
                    this,
                    "Erro ao carregar IDs das categorias: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        binding.btnRegisterFood.setOnClickListener {

            val productPrice = binding.edtRegisterPriceFood.text.toString()
            val productName = binding.edtRegisterNameFood.text.toString()
            val productDescription = binding.edtDescNameFood.text.toString()
            val productCategory = binding.listCategoryView.text.toString()

            /*          childItem = ChildItem(
                          "",
                          productName,
                          R.drawable.semfoto,
                          productPrice.toDouble(),
                          productDescription,
                          0

                      )*/
//            parentItem: ArrayList<ParentItem> = arrayListOf()

            if (productCategory.isBlank()) {
                Toast.makeText(this, "Selecione uma categoria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a HashMap to store product data
            val productData = HashMap<String, Any>()
            productData["name"] = productName
            productData["price"] = productPrice.toDouble()
            productData["description"] = productDescription
            productData["image"] = R.drawable.semfoto // Assuming image resource

            // Add the product data to the selected category within "products" collection
            // Corrected path:
            db.collection("categories").document(productCategory)
                .collection(productName)
                .add(productData)

                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Produto cadastrado com sucesso", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao cadastrar produto", Toast.LENGTH_SHORT).show()
                }


            if (productCategory.isBlank()) {
                binding.listCategoryView.error = "Campo obrigatório"
                Toast.makeText(
                    this,
                    "Verifique se todos os campos foram preenchidos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                binding.listCategory.text = null
                binding.listCategoryView.error = null

            }

            /*
                        if (childItem.title!!.isBlank()) {
                            binding.edtRegisterNameFood.error = "Campo obrigatório"
                            Toast.makeText(
                                this,
                                "Verifique se todos os campos foram preenchidos",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@setOnClickListener
                        } else {
                            binding.edtRegisterNameFood.error = null
                        }

                        if (childItem.description!!.isBlank()) {
                            binding.edtDescNameFood.error = "Campo obrigatório"
                            Toast.makeText(
                                this,
                                "Verifique se todos os campos foram preenchidos",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@setOnClickListener
                        } else {
                            binding.edtDescNameFood.error = null

                        }

                        if (childItem.price.toString().isBlank()) {
                            binding.edtRegisterPriceFood.error = "Campo obrigatório"
                            Toast.makeText(
                                this,
                                "Verifique se todos os campos foram preenchidos",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@setOnClickListener
                        } else {
                            binding.edtRegisterPriceFood.error = null
                        }

                        if (childItem.price <= 0) {
                            binding.edtRegisterPriceFood.error = "Preço inválido"
                            Toast.makeText(this, "Preço inválido", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }*/




            AlertDialog.Builder(this)
                .setTitle("Produto Cadastrado")
                .setMessage(
                    "Categoria: $productCategory\n'" +
                            "Produto: ${productName}\n" +
                            "Preço: ${productPrice.toDouble()}\n" +
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
                            binding.listCategoryView.text.clear()
                            val intent = Intent(this, ManagerActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .show()
                }
                .show()


        }


    }
}