package com.faircorp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"



class WindowActivity : BasicActivity(), OnWindowSelectedListener {

    val superList = mutableListOf<WindowDto?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = WindowsAdapterView.WindowAdapter(this)
        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)

        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.findById(id).execute() } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) { // (3)
                            superList.add(it.body())
                            showValues(id);
                        }
                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                    applicationContext,
                                    "Error on windows loading $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

     fun showValues(id: Long){
        val window = superList.firstOrNull { it!!.id == id}
        if (window != null) {
            findViewById<TextView>(R.id.txt_window_name).text = window.name
            findViewById<TextView>(R.id.txt_room_name).text = window.roomDto.name
            findViewById<TextView>(R.id.txt_window_current_temperature).text = window.roomDto.currentTemperature?.toString()
            findViewById<TextView>(R.id.txt_window_target_temperature).text = window.roomDto.targetTemperature?.toString()
            findViewById<TextView>(R.id.txt_window_status).text = window.windowStatus.toString()
        }
        else {
            System.out.println("NULL")
        }
    }

    override fun onWindowSelected(id: Long) {
        TODO("Not yet implemented")
    }
}