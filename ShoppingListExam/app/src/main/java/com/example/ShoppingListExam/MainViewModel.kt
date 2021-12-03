package com.example.ShoppingListExam

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ShoppingListExam.data.Product
import com.example.ShoppingListExam.data.Repository

class MainViewModel: ViewModel() {

    fun getData(): MutableLiveData<MutableList<Product>> {
        return Repository.getData()
    }

}