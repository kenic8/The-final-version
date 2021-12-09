package com.example.ShoppingListExam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ShareActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val extras = intent.extras ?: return
        val String = extras.getString(Constants.SHARE_KEY)
        if (String != null) {
           val text: TextView = findViewById(R.id.textViewShare) as TextView
            text.text = String
        }
}
}