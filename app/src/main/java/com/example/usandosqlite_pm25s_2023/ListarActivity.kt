package com.example.usandosqlite_pm25s_2023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import com.example.usandosqlite_pm25s_2023.adapter.MeuAdapter
import com.example.usandosqlite_pm25s_2023.database.DatabaseHandler

class ListarActivity : AppCompatActivity() {

    private lateinit var lvPrincipal : ListView

    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        banco = DatabaseHandler( this )

        lvPrincipal = findViewById( R.id.lvPrincipal )

    }

    override fun onStart() {
        super.onStart()
        val cursor = banco.listCursor()
        val adapter = MeuAdapter( this, cursor )
        lvPrincipal.adapter = adapter
    }

    fun btIncluirOnClick(view: View) {
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }

}