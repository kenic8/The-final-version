package com.example.ShoppingListExam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

class EditActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            ///run settings activity
            item.itemId -> this.finish();
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val extras = intent.extras ?: return
        val KeyString = extras.getString(Constants.Edit_key)
        if (KeyString != null) {
           // Run dao up date with values from inputs
            Log.d("key",KeyString.toString())
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}