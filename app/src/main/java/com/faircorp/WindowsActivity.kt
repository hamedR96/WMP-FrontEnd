package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.model.ApiServices
import com.faircorp.model.OnWindowSelectedListener
import com.faircorp.model.RoomDto
import com.faircorp.model.WindowsAdapterView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WindowsActivity : BasicActivity(), OnWindowSelectedListener {

    val tempList = mutableListOf<RoomDto?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_windows)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.list_windows) // (2)
        val adapter = WindowsAdapterView.WindowAdapter(this) // (3)
        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findById(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        tempList.clear()
                        tempList.add(it.body())
                        showTemperatures(id);
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
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findByIdOfRoom(id).execute() } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) { // (3)
                            adapter.update(it.body() ?: emptyList())
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

    override fun onWindowSelected(id: Long) {
        val intent = Intent(this, WindowActivity::class.java).apply {
            putExtra(WINDOW_NAME_PARAM, id)
        }
        val ids = intent.getLongExtra(WINDOW_NAME_PARAM, 0)
        startActivity(intent)

    }

    fun showTemperatures(id: Long) {
        val room = tempList.firstOrNull { it!!.id == id }
        if (room != null) {
            findViewById<TextView>(R.id.textView8).text = room.name;
            findViewById<TextView>(R.id.textView10).text = room.currentTemperature?.toString()
            findViewById<TextView>(R.id.textView11).text = room.targetTemperature?.toString()
        } else {
            System.out.println("NULL")
        }
    }
}