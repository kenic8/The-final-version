package com.example.ShoppingListExam.Adapters



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ShoppingListExam.EditActivity
import com.example.ShoppingListExam.R
import com.example.ShoppingListExam.SettingsActivity
import com.example.ShoppingListExam.ShareActivity
import com.example.ShoppingListExam.data.DAOProducts
import com.example.ShoppingListExam.data.Product
import com.example.ShoppingListExam.data.Repository


class ProductAdapter(var products: MutableList<Product>, var context: Context) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {

        val inf = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)


        return ViewHolder(inf,context)

    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bind(products[position])

    }


    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View,context: Context) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val productOptions: ImageView = itemView.findViewById(R.id.product_options)
        val productEdit:ImageView = itemView.findViewById(R.id.product_edit)
        val productTitle: TextView = itemView.findViewById(R.id.product_title)
        val productDetail: TextView = itemView.findViewById(R.id.product_detail)
        fun bind(product: Product) {

            productTitle.text = product.title
            productDetail.text = product.detail
            productQuantity.text = product.quantity
          //  productImage.setImageResource(product.image) // bug
            ///listners for buttons
        }

        init {
            productOptions.setOnClickListener(this)
            productEdit.setOnClickListener(this)


        }



        override fun onClick(view: View) {

            val position = adapterPosition
            val dao = DAOProducts()
            if (position != RecyclerView.NO_POSITION && view == productOptions ) {
                Log.d("click", position.toString())
                dao.delete(products[position].key)
            }
            if (position != RecyclerView.NO_POSITION && view == productEdit ) {
                Log.d("click", position.toString())
                val key = products[position].key
                ///sharing
              /* val data = Bundle()
                data.putString("detail",products[position].detail)
                data.putString("title",products[position].title)
                data.putString("quantity",products[position].quantity)
                data.putString("key",products[position].key)
*/

                val editIntent = Intent(context, EditActivity::class.java)
                editIntent.putExtra("detail",products[position].detail)
                editIntent.putExtra("title",products[position].title)
                editIntent.putExtra("quantity",products[position].quantity)
                editIntent.putExtra("key",products[position].key)

                context.startActivity(editIntent)

                Log.d("stringlist",key)
                /// run intent with edit
                Log.d("click","editlii")
            }

        }

    }
}







