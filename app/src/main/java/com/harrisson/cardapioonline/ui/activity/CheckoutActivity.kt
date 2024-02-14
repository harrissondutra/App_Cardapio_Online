package com.harrisson.cardapioonline.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harrisson.cardapioonline.databinding.ActivityCheckoutBinding
import com.harrisson.cardapioonline.databinding.ItemCheckoutBinding
import com.harrisson.cardapioonline.models.ChildItem

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCheckoutBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedFoodList = intent.getParcelableArrayListExtra<ChildItem>("selectedFoodList")!!

        var recyclerView = binding.listaCheckout

        recyclerView.setHasFixedSize(true)
        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = CheckoutAdapter(selectedFoodList, this)
        recyclerView.adapter = adapter

        recyclerView.layoutManager = linearLayoutManager

        var mesa = intent.getStringExtra("tableNumber")
        var name = intent.getStringExtra("name")

        binding.txtTopoCheckout.text = "Mesa: $mesa"
        binding.totalValueCheckout.text =
            "Total: R$ " + intent.getDoubleExtra("totalPrice", 0.0).toString()

        var randomPedido = (0..10000).random()

        binding.btnCheckout.setOnClickListener {

                AlertDialog.Builder(this)
                    .setTitle("Confirmação de Pedido")
                    .setMessage("Podemos confirmar o pedido?")
                    .setPositiveButton("Confirmar") { dialog, which ->
                        dialog.dismiss()
                        AlertDialog.Builder(this)
                            .setTitle("Tudo certo, $name!")
                            .setMessage("Mesa: $mesa \nSeu pedido foi confirmado com sucesso! \nO número do seu pedido é: $randomPedido")
                            .setPositiveButton("Ok") { dialog, which ->
                                dialog.dismiss()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }.create().show()
                    }.setNegativeButton("Cancelar") { dialog, which ->
                        dialog.dismiss()
                    }.create().show()


        }
    }
}

class CheckoutAdapter(
    private val selectedFoodList : ArrayList<ChildItem>,
    private val context : Context
) : RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

    override fun onCreateViewHolder(
        parent : ViewGroup, viewType : Int
    ) : CheckoutViewHolder {
        val binding =
            ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder : CheckoutViewHolder, position : Int) {
        val checkoutItem = selectedFoodList[position]

        holder.bind(checkoutItem)


    }

    override fun getItemCount() : Int {
        return selectedFoodList.size
    }

    class CheckoutViewHolder(private val binding : ItemCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(checkoutItem : ChildItem) {
            binding.nomeItem.text = checkoutItem.title
            binding.valorItem.text = "R$ " + checkoutItem.price.toString()

        }
    }
}
