package com.example.ShoppingListExam.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ShoppingListExam.MainActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


val database = Firebase.database
val ref = database.getReference("products")

class DAOProducts {


    fun add(product: Product?) {

        Log.d("postDao", product.toString())
        ref.push().setValue(product).addOnSuccessListener {

            Repository.getData()

        }.addOnFailureListener {
///do nothing
        }
        Repository.getData()
    }

    // get reference to button


    fun update (key:String, hashMapProduct: HashMap<String,Any>){
        ref.child(key).updateChildren(hashMapProduct)
        Repository.getData()

    }
    fun delete (key:String){
        ref.child(key).removeValue()
            .addOnSuccessListener {
                Repository.getData()

            }
            .addOnFailureListener {
                Log.d("failed to remove",key)
            }
    }

    fun clear(){
        ref
            .removeValue()
            .addOnSuccessListener { Log.d("deleted", "DocumentSnapshot successfully deleted!")
            Repository.getData()}
            .addOnFailureListener { e -> Log.w("failed", "Error deleting document", e) }
    }


    fun sort() {
        //for at være sikker på det nyeste i databasen er med..
        var products = mutableListOf<Product>()
        ref.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("task", task.toString())
                val result = task.result?.children
                result?.let {
                    for (p in it) {
                        products.add(
                            Product(
                                p.child("title").value.toString(),
                                p.child("detail").value.toString(),
                                p.child("quantity").value.toString(),
                                p.key.toString()
                            )
                        )
                    }
                    val sorted = products.sortedBy { Product -> Product.quantity }.toMutableList()
                    Repository.productListener.value?.clear()
                    Repository.productListener.value = sorted;

                }

            }

        }

    }


}
