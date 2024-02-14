package com.harrisson.cardapioonline.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.harrisson.cardapioonline.models.ChildItem
import com.harrisson.cardapioonline.models.ParentItem
import com.harrisson.cardapioonline.R
import com.harrisson.cardapioonline.databinding.ActivityMainBinding
import com.harrisson.cardapioonline.ui.adapter.ChildRecyclerViewAdapter
import com.harrisson.cardapioonline.ui.adapter.ParentRecyclerViewAdapter

class MainActivity : AppCompatActivity(), ChildRecyclerViewAdapter.FoodInterface {

    private lateinit var parentRecyclerView : RecyclerView
    private var parentList : ArrayList<ParentItem> = arrayListOf()
    private lateinit var binding : ActivityMainBinding
    private var selectedFoodList : ArrayList<ChildItem> = arrayListOf()
    var totalPrice: Double = 0.0

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
        adapter.notifyDataSetChanged()

    }

    private fun getItemFood() {

        val childList = ArrayList<ChildItem>()
        childList.add(ChildItem("Batata Frita", R.drawable.batata_frita, 30.00, "300g de batata frita com bacon e cheddar"))
        childList.add(ChildItem("Bolinha de peixe", R.drawable.bolinha_de_peixe, 20.00, "Hamburguer de carne, frango, vegetariano, etc"))
        childList.add(ChildItem("Pastelzinho", R.drawable.pastelzinho, 10.00, "Pastel de carne, queijo, frango, pizza, etc"))
        childList.add(ChildItem("Macaxeira Frita", R.drawable.macaxeira, 5.00, "Coxinha de frango, carne, queijo, etc"))
        parentList.add(ParentItem("Petiscos", R.drawable.nuggets, childList))

        val childList2 = ArrayList<ChildItem>()
        childList2.add(ChildItem("Picanha", R.drawable.picanha, 70.00, "300g de picanha com arroz ou baião, farofa e batata frita"))
        childList2.add(ChildItem("Maminha", R.drawable.maminha, 56.00, "300g de maminha com arroz ou baião, farofa e batata frita"))
        childList2.add(ChildItem("Alcatra", R.drawable.alcatra, 50.00, "300g de alcatra com arroz ou baião, farofa e batata frita"))
        childList2.add(ChildItem("Frango", R.drawable.frango, 20.00, "Completo, com arroz ou baião, farofa e batata frita"))
        parentList.add(ParentItem("Comidas", R.drawable.almoco, childList2))

        val childList3 = ArrayList<ChildItem>()
        childList3.add(ChildItem("Coca-Cola 2l", R.drawable.coca_cola, 5.00, "Geladíssima"))
        childList3.add(ChildItem("São Geraldo 2l", R.drawable.sao_geraldo, 5.00, "Geladíssima"))
        childList3.add(ChildItem("Heineken 600ml", R.drawable.cerv_heine, 5.00, "Estupidamente gelada"))
        childList3.add(ChildItem("Stella Artois 600ml", R.drawable.cerv_stella, 3.00, "Estupidamente gelada"))
        parentList.add(ParentItem("Bebidas", R.drawable.bebida, childList3))

        val childList4 = ArrayList<ChildItem>()
        childList4.add(ChildItem("Sorvete", R.drawable.sorvete, 5.00, "Morango, chocolate, creme"))
        childList4.add(ChildItem("Açaí", R.drawable.acai, 5.00, "Com banana, granola, leite condensado"))
        childList4.add(ChildItem("Pudim", R.drawable.pudim, 5.00, "De leite, de pão, de chocolate"))
        childList4.add(ChildItem("Bolo", R.drawable.bolo, 3.00, "De cenoura, de chocolate, de laranja"))
        parentList.add(ParentItem("Sobremesas", R.drawable.sweets, childList4))
    }

    fun createSnackBarLoginSuccesful(
        view : View,
        alerta : String,
        color : Int = Color.rgb(101, 203, 0)
    ) {
        val snackbar =
            Snackbar.make(view, alerta, Snackbar.LENGTH_SHORT)
                .setAction("") {
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
                .setAction(" ") {
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

        totalPrice += priceFood
        createSnackBarLoginSuccesful(
            binding.root,
            "$foodName adicionado" + "     R$ " + "%.2f".format(food.price)
        )
        getRenewTotalValue(totalPrice)
        selectedFoodList.add(food)
    }

    override fun removeFood(food : ChildItem) {
        val price = food.price
        totalPrice -= price

        createSnackBarError(
            binding.root,
            "${food.title} removido" + "     -R$ " + "%.2f".format(food.price)
        )
        getRenewTotalValue(totalPrice)

        selectedFoodList.remove(food)
    }

    fun getRenewTotalValue(totalValue : Double) {
        val totalValueStr = "R$ " + "%.2f".format(totalValue)
        binding.footerText.text = totalValueStr
    }
}