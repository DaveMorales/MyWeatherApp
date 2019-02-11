package com.david.myweather

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.NavController
import android.content.BroadcastReceiver
import com.david.myweather.utils.ConnectivityChangeReceiver
import android.net.ConnectivityManager
import android.content.IntentFilter
import android.os.Build
import com.david.myweather.utils.ConnectivityListener


class MainActivity : AppCompatActivity(), ConnectivityListener {

    val TAG: String = "MainActivity"
    lateinit var menuItem: MenuItem
    lateinit var mNavController: NavController
    lateinit var progressBar: ProgressBar
    private var conectivityCahngeReceiver: BroadcastReceiver? = null
    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNavController = findNavController(this, R.id.nav_host_fragment)
        conectivityCahngeReceiver = ConnectivityChangeReceiver()
        progressBar = findViewById(R.id.progress)
        registerBroadcast()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcast()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.add_city -> {
                menuItem = item
                menuItem.isVisible = false
                mNavController.navigate(R.id.addLocationFragment)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (mNavController.currentDestination!!.id.equals(R.id.addLocationFragment)) {
            menuItem.isVisible = true
        }
        super.onBackPressed()
    }

    fun showDialog(message: String) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("My weatherResult message")
        builder.setMessage(message)
        builder.setPositiveButton("Acept") { dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun setLoaderState(state: Boolean) {
        progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            progressBar, message,
            Snackbar.LENGTH_LONG
        ).setAction("Accept", null)
        snackbar.show()
    }

    fun registerBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(conectivityCahngeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(conectivityCahngeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    fun unregisterBroadcast() {
        try {
            unregisterReceiver(conectivityCahngeReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }

    override fun onConectivityChanged(isConnected: Boolean) {
        showSnackBar(if (isConnected) "Conected :)" else "Not Connected :(" )
    }
}
