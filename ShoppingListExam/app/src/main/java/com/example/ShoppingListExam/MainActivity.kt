package com.example.ShoppingListExam
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ShoppingListExam.Adapters.ProductAdapter
import com.example.ShoppingListExam.data.DAOProducts
import com.example.ShoppingListExam.data.Product
import com.example.ShoppingListExam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //you need to have an Adapter for the products
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
        //val intent = Intent(this,SettingsActivity::class.java)

        when (item.itemId){
            R.id.nav_delete -> showDialog(binding.root)
        }

        when (item.itemId){
            ///run settings activity

            //R.id.nav_settings ->
            //R.id.nav_settings -> showsettings(binding.root)
        }
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

        binding.clearProduct.setOnClickListener{
            showDialog(view)
        }

        //

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getData().observe(this, Observer {
            Log.d("Products","Found ${it.size} products")
            updateUI(it)
        })

    }
//Dialog


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

       adapter = ProductAdapter(products)
      /*connecting the recyclerview to the adapter  */
      binding.recyclerView.adapter = adapter

    }


}