package com.example.ShoppingListExam.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Repository {


    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()


    fun getData(): MutableLiveData<MutableList<Product>> {
        var products = mutableListOf<Product>()
        // Read from the database
        val database = Firebase.database
        val ref = database.getReference("products")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.children
                for (p in value) {
                    products.add(
                        Product(
                            p.child("title").value.toString(),
                            p.child("detail").value.toString(),
                            p.child("quantity").value.toString(), //indsæt quantity
                            p.key.toString()
                        )
                    )
                }
                Log.d(TAG, "Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })


        if (products == emptyList<Product>()) {
            var products = mutableListOf<Product>()
            Log.d("prod", products.toString())
//            createTestData()
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
                                    p.child("quantity").value.toString(), //indsæt quantity
                                    p.key.toString()
                                )
                            )
                        }
                        productListener.value = products
                    }
                }

            }

             //getData()
            //we inform the listener we have new data
            return productListener
        }

      /*  fun createTestData() {
            //add some products to the products list - for testing purposes
            Log.d("Repository", "create testdata")
            products.add(
                Product(
                    title = "tomater",
                    detail = "someoaksdomodmsoadmoamsdomasod",
                    image = R.drawable.image1,
                    ""
                )
            )


        }*/

        return productListener
    }
}