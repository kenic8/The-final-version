package com.example.ShoppingListExam.data

import java.io.Serializable

data class Product(var title:String = "", var detail:String="", var quantity: String, var key:String) : Serializable
