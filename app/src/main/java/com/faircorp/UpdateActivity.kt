package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.*
import com.faircorp.model.ApiServices
import com.faircorp.model.OnWindowSelectedListener
import com.faircorp.model.RoomDto
import com.faircorp.model.WindowDto
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UpdateActivity : BasicActivity(){

    val tempList = mutableListOf<WindowDto?>()
    var superId = 0L
    var roomId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)

        val callUpdate = findViewById<Button>(R.id.button5)
        callUpdate.setOnClickListener {
            val room1 = "Room 1"
            val room2 = "Room 2"
            val room3 = "Room 3"
            val room4 = "Room 4"
            val textRoom: String = spinner2.selectedItem.toString()
            if (textRoom!= null){
                if (textRoom.equals(room1, true) && window1up.isChecked) {
                    superId = 1
                    roomId = 1
                }
                else if (textRoom.equals(room1, true) && window2up.isChecked) {
                    superId = 2
                    roomId = 1
                }
                else if (textRoom.equals(room1, true) && window3up.isChecked) {
                    superId = 3
                    roomId = 1
                }
                else if (textRoom.equals(room2, true) && window1up.isChecked) {
                    superId = 4
                    roomId = 2
                }
                else if (textRoom.equals(room2, true) && window2up.isChecked) {
                    superId = 5
                    roomId = 2
                }
                else if (textRoom.equals(room2, true) && window3up.isChecked) {
                    superId = 6
                    roomId = 2
                }
                else if (textRoom.equals(room3, true) && window1up.isChecked) {
                    superId = 7
                    roomId = 3
                }
                else if (textRoom.equals(room3, true) && window2up.isChecked) {
                    superId = 8
                    roomId = 3
                }
                else if (textRoom.equals(room3, true) && window3up.isChecked) {
                    superId = 9
                    roomId = 3
                }
                else if (textRoom.equals(room4, true) && window1up.isChecked) {
                    superId = 10
                    roomId = 4
                }
                else if (textRoom.equals(room4, true) && window2up.isChecked) {
                    superId = 11
                    roomId = 4
                }
                else if (textRoom.equals(room4, true) && window3up.isChecked) {
                    superId = 12
                    roomId = 4
                }
            }
            else{
                println("this step")
            }
            lifecycleScope.launch(context = Dispatchers.IO) { // (1)
                runCatching { ApiServices().windowsApiService.findById(superId).execute() } // (2)
                        .onSuccess {
                            withContext(context = Dispatchers.Main) { // (3)
                                tempList.add(it.body())
                                updateStatusWindow(superId)
                                goToReload()
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
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun goToReload(){
        val intent = Intent(this, WindowsActivity::class.java).apply {
            putExtra(WINDOW_NAME_PARAM, roomId)
        }
        startActivity(intent)
    }

    fun updateStatusWindow(id: Long) {
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.switchStatus(id).execute() } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                    applicationContext,
                                    "Update Successful",
                                    Toast.LENGTH_LONG
                            ).show()
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
}