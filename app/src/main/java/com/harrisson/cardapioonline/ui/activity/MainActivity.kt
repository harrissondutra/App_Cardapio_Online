package com.harrisson.cardapioonline.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.harrisson.cardapioonline.R
import com.harrisson.cardapioonline.databinding.ActivityMainBinding
import com.harrisson.cardapioonline.models.ChildItem
import com.harrisson.cardapioonline.models.ParentItem
import com.harrisson.cardapioonline.ui.adapter.ChildRecyclerViewAdapter
import com.harrisson.cardapioonline.ui.adapter.ParentRecyclerViewAdapter

class MainActivity : AppCompatActivity(), ChildRecyclerViewAdapter.FoodInterface {

    private lateinit var parentRecyclerView : RecyclerView
    private var parentList : ArrayList<ParentItem> = arrayListOf()
    private lateinit var binding : ActivityMainBinding
    private var selectedFoodList : ArrayList<ChildItem> = arrayListOf()
    var totalPrice : Double = 0.0
    var alertSnackBar : String = ""
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parentRecyclerView = findViewById(R.id.parentRecyclerView)
        parentRecyclerView.setHasFixedSize(true)


        val adapter = ParentRecyclerViewAdapter(parentList, this)
        parentRecyclerView.adapter = adapter

        parentRecyclerView.layoutManager = LinearLayoutManager(this)
        parentRecyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                LinearLayoutManager.HORIZONTAL
            )
        )
        getItemFood()

        binding.clearButton.setOnClickListener {

            // Limpar carrinho de compras e alterar todas as quantidades para 0
            totalPrice = 0.0
            getRenewTotalValue(totalPrice)

            // Percorrer a lista de itens e atualizar a quantidade
            for (item in parentList) {
                for (child in item.childItemList) {
                    child.qtd = 0
                }
            }

            // Exibir Toast para notificar se nenhum item estiver selecionado
            // e após remover todos os itens exibit Toast com mensagem de sucesso
            if (selectedFoodList.isEmpty()) {
                Toast.makeText(this, "Nenhum item selecionado", Toast.LENGTH_SHORT).show()
            } else {
                selectedFoodList.clear()
                Toast.makeText(this, "Todos os itens removidos", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }

        }

        var name = intent.getStringExtra("name")
        var tableNumber = intent.getStringExtra("tableNumber")

        binding.btnCheckout.setOnClickListener {

            if (selectedFoodList.size <= 0) {
                Toast.makeText(this, "Selecione algum item", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putParcelableArrayListExtra("selectedFoodList", selectedFoodList)
                intent.putExtra("totalPrice", totalPrice)
                intent.putExtra("name", name)
                intent.putExtra("tableNumber", tableNumber)
                startActivity(intent)
            }

        }


        val logoutButton = findViewById<TextView>(R.id.txt_logoff)

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            true
        }

    }



    private fun getItemFood() {

        val childList = ArrayList<ChildItem>()
        childList.add(
            ChildItem(
                "1",
                "Batata Frita",
                R.drawable.batata_frita,
                30.00,
                "300g de batata frita com bacon e cheddar"
            )
        )
        childList.add(
            ChildItem(
                "2",
                "Bolinha de peixe",
                R.drawable.bolinha_de_peixe,
                20.00,
                "Bolinha de Peixe com molho de pimenta"
            )
        )
        childList.add(
            ChildItem(
                "3",
                "Pastelzinho",
                R.drawable.pastelzinho,
                12.50,
                "12 pastéis de carne, queijo ou frango"
            )
        )
        childList.add(
            ChildItem(
                "4",
                "Macaxeira Frita",
                R.drawable.macaxeira,
                30.00,
                "Macaxeira frita"
            )
        )
        parentList.add(ParentItem("Petiscos", R.drawable.nuggets, childList))

       /* val childList2 = ArrayList<ChildItem>()
        childList2.add(
            ChildItem(
                "1",
                "Picanha",
                R.drawable.picanha,
                70.00,
                "300g de picanha com arroz ou baião, farofa e batata frita"
            )
        )
        childList2.add(
            ChildItem(
                "2",
                "Maminha",
                R.drawable.maminha,
                56.00,
                "300g de maminha com arroz ou baião, farofa e batata frita"
            )
        )
        childList2.add(
            ChildItem(
                "3",
                "Alcatra",
                R.drawable.alcatra,
                50.00,
                "300g de alcatra com arroz ou baião, farofa e batata frita"
            )
        )
        childList2.add(
            ChildItem(
                "4",
                "Frango",
                R.drawable.frango,
                20.00,
                "Completo, com arroz ou baião, farofa e batata frita"
            )
        )
        parentList.add(ParentItem("Comidas", R.drawable.almoco, childList2))

        val childList3 = ArrayList<ChildItem>()
        childList3.add(ChildItem(
            "1",
            "Coca-Cola 2l"
            , R.drawable.coca_cola,
            12.00,
            "Geladíssima"))
        childList3.add(
            ChildItem(
                "2",
                "São Geraldo 2l",
                R.drawable.sao_geraldo,
                15.00,
                "Geladíssima"))
        childList3.add(
            ChildItem(
                "3",
                "Heineken 600ml",
                R.drawable.cerv_heine,
                25.00,
                "Estupidamente gelada"
            )
        )
        childList3.add(
            ChildItem(
                "4",
                "Stella Artois 600ml",
                R.drawable.cerv_stella,
                20.00,
                "Estupidamente gelada"
            )
        )
        parentList.add(ParentItem("Bebidas", R.drawable.bebida, childList3))

        val childList4 = ArrayList<ChildItem>()
        childList4.add(ChildItem("1","Sorvete", R.drawable.sorvete, 10.00, "Morango, chocolate, creme"))
        childList4.add(
            ChildItem(
                "2",
                "Açaí",
                R.drawable.acai,
                13.00,
                "Com banana, granola, leite condensado"
            )
        )

        childList4.add(
            ChildItem(
                "3",
                "Pudim",
                R.drawable.pudim,
                6.50,
                "De leite, de pão, de chocolate"))

        childList4.add(
            ChildItem(
                "4",
                "Bolo",
                R.drawable.bolo,
                5.00,
                "De cenoura, de chocolate, de laranja"
            )
        )
        parentList.add(ParentItem("Sobremesas", R.drawable.sweets, childList4))
*/
    }



    fun createSnackBarLoginSuccesful(
        view : View,
        alerta : String,
        color : Int = Color.rgb(101, 203, 0)
    ) {
        val snackbar =
            Snackbar.make(view, alerta, Snackbar.LENGTH_SHORT)
                .setAction("$alertSnackBar") {
                }
        val view : View = snackbar.getView()
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
        view.layoutParams = params
        snackbar.setBackgroundTint(color)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()

    }

    fun createSnackBarError(
        view : View,
        alerta : String,
        color : Int = Color.RED
    ) {
        val snackbar =
            Snackbar.make(view, alerta, Snackbar.LENGTH_SHORT)
                .setAction("$alertSnackBar") {
                }
        val view : View = snackbar.getView()
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
        view.layoutParams = params
        snackbar.setBackgroundTint(color)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
    }


    override fun addFood(food : ChildItem) {
        val priceFood = food.price
        val foodName = food.title
        alertSnackBar = "+ R$ " + "%.2f".format(food.price)

        totalPrice += priceFood
        createSnackBarLoginSuccesful(
            binding.root,
            "$foodName adicionado"
        )
        getRenewTotalValue(totalPrice)
        selectedFoodList.add(food)
    }

    override fun removeFood(food : ChildItem) {
        val price = food.price
        totalPrice -= price
        alertSnackBar = "- R$ " + "%.2f".format(food.price)

        createSnackBarError(
            binding.root,
            "${food.title} removido"
        )
        getRenewTotalValue(totalPrice)

        selectedFoodList.remove(food)
    }

    fun getRenewTotalValue(totalValue : Double) {
        val totalValueStr = "R$ " + "%.2f".format(totalValue)
        binding.footerText.text = totalValueStr
    }
}