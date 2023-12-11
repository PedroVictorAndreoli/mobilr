package com.example.usandosqlite_pm25s_2023.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION_CODES.P
import com.example.usandosqlite_pm25s_2023.entity.Locate
import java.lang.StringBuilder

class DatabaseHandler ( context : Context ) : SQLiteOpenHelper ( context, DATABASE_NAME, null, DATABASE_VERSION ) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "dbfile.sqlite"
        private val TABLE_NAME = "pesso"
        private val KEY_ID = "_id"
        private val KEY_LATITUDE = "latitude"
        private val KEY_LONGITUDE = "longitude"
        private val KEY_DESCRICAO = "descricao"
    }


    override fun onCreate(bd: SQLiteDatabase?) {
        bd?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( ${KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${KEY_LATITUDE} TEXT, ${KEY_LONGITUDE} TEXT, ${KEY_DESCRICAO} TEXT)" )
    }

    override fun onUpgrade(bd: SQLiteDatabase?, p1: Int, p2: Int) {
        bd?.execSQL( "DROP TABLE ${TABLE_NAME}" )
        onCreate( bd )
    }

    fun insert( locate : Locate) {
        val registro = ContentValues()
        registro.put( KEY_LATITUDE, locate.latitude )
        registro.put( KEY_LONGITUDE, locate.longitude )
        registro.put(KEY_DESCRICAO,locate.descricao)

        val bd = this.writableDatabase
        bd.insert( TABLE_NAME, null, registro )
    }

    fun update( locate : Locate) {
        val registro = ContentValues()
        registro.put( KEY_LATITUDE, locate.latitude )
        registro.put( KEY_LONGITUDE, locate.longitude )
        registro.put(KEY_DESCRICAO,locate.descricao)

        val bd = this.writableDatabase
        bd.update( TABLE_NAME, registro, "_id=${locate._id}", null )
    }

    fun delete( _id : Int) {
        val bd = this.writableDatabase
        bd.delete( TABLE_NAME, "_id=${_id}", null )
    }

    fun find( _id : Int) : Locate? {
        val bd = this.writableDatabase
        val cursor = bd.query( TABLE_NAME,
            null,
            "_id=${_id}",
            null,
            null,
            null,
            null
        )

        if ( cursor.moveToNext() ) {
            val locate = Locate( _id, cursor.getString( 1 ), cursor.getString( 2 ),cursor.getString(3) )
            return locate
        } else {
            return null
        }
    }

    fun list() : MutableList<Locate> {
        val bd = this.writableDatabase

        val cursor = bd.query( TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val registros = mutableListOf<Locate>()

        while ( cursor.moveToNext() ) {
            val locate = Locate( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ),cursor.getString(3) )
            registros.add( locate )
        }

        return registros
    }

    fun listCursor() : Cursor {
        val bd = this.writableDatabase

        val cursor = bd.query( TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        return cursor
    }
}