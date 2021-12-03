package com.example.ShoppingListExam.data

import android.util.Log
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

        }
        Repository.getData()
    }

    // get reference to button


    fun update (key:String, hashMapProduct: HashMap<String,Any>){

        //var title:String = "", var detail:String="", var image:Int)

        ref.child(key).updateChildren(hashMapProduct)

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


}
