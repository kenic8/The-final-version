package com.example.ShoppingListExam.Adapters



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ShoppingListExam.R
import com.example.ShoppingListExam.data.DAOProducts
import com.example.ShoppingListExam.data.Product



class ProductAdapter(var products: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {

        val inf = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)


        return ViewHolder(inf)

    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bind(products[position])

    }


    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val productOptions: ImageView = itemView.findViewById(R.id.product_options)
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

        }

        override fun onClick(view: View) {

            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                Log.d("click", position.toString())
                val dao = DAOProducts()
                dao.delete(products[position].key)
            }

        }


     /*   fun showPopup(key:String,view: View) {


            Log.d("show", "showhow")
            val popupView: View = LayoutInflater.from(view.context).inflate(R.layout.update_delete_popup, null)

            val updateBtn: Button = popupView.findViewById(R.id.submit_product)
            val deleteBtn: Button = popupView.findViewById(R.id.remove_product)
            val cancelBtn: Button = popupView.findViewById(R.id.Cancel)
            val productTitle = popupView.findViewById(R.id.edit_productname) as EditText
            val productDetail =  popupView.findViewById(R.id.edit_productdescription) as EditText
            updateBtn.setOnClickListener {
                val dao = DAOProducts()
                var hashMapProduct : HashMap<String, Any>
                        = HashMap<String, Any> ()
                ///get values from product view
                hashMapProduct.put("Prdouct",Product(productTitle.toString(),productDetail.toString(),2131165314,key))

                dao.update("Product",hashMapProduct)
            }

            deleteBtn.setOnClickListener {
                val dao = DAOProducts()
                dao.delete(key)

            }


        }*/
    }
}







