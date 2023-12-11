package com.example.usandosqlite_pm25s_2023.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.usandosqlite_pm25s_2023.MainActivity
import com.example.usandosqlite_pm25s_2023.R
import com.example.usandosqlite_pm25s_2023.entity.Locate


private const val COD = 0
private const val LATITUDE = 1
private const val LONGITUDE = 2
private const val DESCRICAO = 3

class MeuAdapter (val context: Context, val cursor : Cursor ) : BaseAdapter() {
    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(id: Int): Any {
        cursor.moveToPosition( id )
        val locate = Locate( cursor.getInt(COD), cursor.getString(LATITUDE), cursor.getString(LONGITUDE),cursor.getString(
            DESCRICAO) )
        return locate
    }

    override fun getItemId(id: Int): Long {
        cursor.moveToPosition( id )
        return cursor.getInt(COD).toLong()
    }

    override fun getView(id: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elemento_lista, null)

        val tvNomeElementoLista = v.findViewById<TextView>(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista = v.findViewById<TextView>(R.id.tvTelefoneElementoLista)
        val tvDescricaoElementoLista = v.findViewById<TextView>(R.id.textView3)
        val btEditarElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)

        cursor.moveToPosition(id)

        tvNomeElementoLista.text = cursor.getString(LATITUDE)
        tvTelefoneElementoLista.text = cursor.getString(LONGITUDE)
        tvDescricaoElementoLista.text = cursor.getString(DESCRICAO)

        btEditarElementoLista.setOnClickListener {
            cursor.moveToPosition(id)
            val intent = Intent( context, MainActivity::class.java )
            intent.putExtra( "cod", cursor.getInt(COD))
            intent.putExtra( "nome", cursor.getString(LATITUDE))
            intent.putExtra( "telefone", cursor.getString(LATITUDE))
            intent.putExtra("descricao",cursor.getString(DESCRICAO))
            context.startActivity( intent )
            //Toast.makeText(context, "Item ${id} pressionado", Toast.LENGTH_SHORT).show()
        }

        return v

    }




}