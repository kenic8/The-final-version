package com.example.ShoppingListExam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.example.ShoppingListExam.data.DAOProducts
import kotlin.math.log


class EditActivity : AppCompatActivity() {

override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when (item.itemId) {
           ///run settings activity
           item.itemId -> this.finish();
        }
        return super.onOptionsItemSelected(item)
  }
    override fun onCreate(savedInstanceState: Bundle?) {

        val dao = DAOProducts()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val data = intent.extras

        if (data != null) {
           // Run dao up date with values from inputs
            //text inputs
            val submit:Button = findViewById(R.id.submit_product) as Button
            val titlei: TextView = findViewById(R.id.edit_productname) as TextView
            val detaili: TextView = findViewById(R.id.edit_productdescription) as TextView
            val quantityi: TextView = findViewById(R.id.edit_productquantity) as TextView

            submit.setOnClickListener {
                //intent values
                var title = intent.getStringExtra("title");
                Log.d("title",title.toString())
                Log.d("titles",titlei.text.toString())
                var detail = intent.getStringExtra("detail");
                var quantity = intent.getStringExtra("quantity");
                var key = intent.getStringExtra("key");


                val titleNew: String =  if (TextUtils.isEmpty(titlei.text))  title.toString() else  titlei.text.toString()
                val detailNew: String = if (TextUtils.isEmpty(detaili.text))  detail.toString() else  detaili.text.toString()
                val quantityNew: String = if (TextUtils.isEmpty(quantityi.text))  quantity.toString() else  quantityi.text.toString()

                val map: HashMap<String, Any> = HashMap()
                map["title"] = titleNew
                map["detail"] = detailNew
                map["quantity"] = quantityNew

                dao.update(key.toString(),map);
                this.finish()
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}