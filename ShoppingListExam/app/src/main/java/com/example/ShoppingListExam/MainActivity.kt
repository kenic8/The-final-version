package com.example.ShoppingListExam
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ShoppingListExam.Adapters.ProductAdapter
import com.example.ShoppingListExam.data.DAOProducts
import com.example.ShoppingListExam.data.Product
import com.example.ShoppingListExam.databinding.ActivityMainBinding
import androidx.annotation.NonNull
import com.example.ShoppingListExam.data.Repository


class MainActivity : AppCompatActivity() {
   // private val REQUEST_CODE = 5

   lateinit var adapter: ProductAdapter
   lateinit var binding : ActivityMainBinding
   lateinit var viewModel : MainViewModel
    val dao = DAOProducts()

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //settings
        val intent = Intent(this,SettingsActivity::class.java)

        /*///sharing if sending to extnal apps
        val shareintent = Intent(Intent.ACTION_SEND)
        shareintent.type ="text/plain"
        val sep = "-"
        val chooser = Intent.createChooser(shareintent,"Share using..")
        val stringlist = Repository.productListener.value!!.joinToString(sep)
        Log.d("stringlist",stringlist)
        shareintent.putExtra("Shared list",stringlist)*/

        ///sharing
        val shareintent = Intent(this,ShareActivity::class.java)
        shareintent.type ="text/plain"
        val sep = "-"
        val chooser = Intent.createChooser(shareintent,"Share using..")
        if(Repository.productListener.value != null ){
           val stringlist = Repository.productListener.value!!.joinToString(sep)
            Log.d("stringlist",stringlist)
            shareintent.putExtra("String",stringlist)
        }

        when (item.itemId){
            R.id.nav_delete -> showDialog(binding.root)
        }

        when (item.itemId){
            ///run settings activity
                //run intent with settings activity
          R.id.nav_settings -> startActivity(intent)

        }

        when (item.itemId){
            ///run settings activity
            //run intent with settings activity
            R.id.nav_share -> startActivity(chooser)

        }



        ///

        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

/// some listners

        binding.submitProduct.setOnClickListener{
                Log.d("post", "posting")
          dao.add( Product( binding.editProductname.text.toString(),binding.editProductdescription.text.toString(),binding.editProductquantity.text.toString(),""))
            hideKeyboard()
        }

        binding.sortProduct.setOnClickListener{
            //dao sort
            dao.sort()
            Log.d("sort","sortclicked")
        }

        //

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getData().observe(this, Observer {
            Log.d("Products","Found ${it.size} products")
            updateUI(it)

        })

        load_settings()

    }


    ///Change background based on settings
    override fun onResume() {
        super.onResume()
        load_settings()
    }



    ///settings
    fun load_settings()
    {
        val SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        val chk_night = SharedPreferences.getBoolean("night", false)

        Log.d("night", chk_night.toString())
        if (chk_night) {
            Toast.makeText(this, "should Now be using darkmode", Toast.LENGTH_SHORT).show()
            binding.main.setBackgroundColor(Color.parseColor("#585858"))


        } else {
            Toast.makeText(this, "should Now be using normal", Toast.LENGTH_SHORT).show()
            binding.main.setBackgroundColor(Color.parseColor("#ffffff"))
        }
    }



    //callback function from yes/no dialog - for yes choice
    private fun positiveClicked() {
        val toast = Toast.makeText(
            this,
            "positive button clicked", Toast.LENGTH_LONG
        )
        toast.show()
        /// kør dao med clear af databasen
       dao.clear()

    }

    //callback function from yes/no dialog - for no choice
    private fun negativeClick() {
        //Here we override the method and can now do something
        val toast = Toast.makeText(
            this,
            "Canceled", Toast.LENGTH_LONG
        )
        toast.show()
    }


    fun showDialog(v: View) {
        //showing our dialog.

        val dialog = DialogFragment(::positiveClicked, ::negativeClick)
        //Here we show the dialog
        //The tag "MyFragement" is not important for us.
        dialog.show(supportFragmentManager, "myFragment")
    }


    fun showsettings(v: View) {
        //showing our dialog.

        val dialog = settingsFragment(::positiveClicked, ::negativeClick)
        //Here we show the dialog
        //The tag "MyFragement" is not important for us.
        dialog.show(supportFragmentManager, "myFragment")
    }



    fun updateUI(products : MutableList<Product>) {

        val layoutManager = LinearLayoutManager(this)

        /*you need to have a defined a recylerView in your
        xml file - in this case the id of the recyclerview should
        be "recyclerView" - as the code line below uses that */

       binding.recyclerView.layoutManager = layoutManager

       adapter = ProductAdapter(products,this)
      /*connecting the recyclerview to the adapter  */
      binding.recyclerView.adapter = adapter

    }


}