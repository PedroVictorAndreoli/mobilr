package com.example.usandosqlite_pm25s_2023

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.usandosqlite_pm25s_2023.database.DatabaseHandler
import com.example.usandosqlite_pm25s_2023.entity.Locate
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() , LocationListener {

    private lateinit var etCod : EditText
    private lateinit var etNome : EditText
    private lateinit var etTelefone : EditText
    private lateinit var etDescricao: EditText
    private lateinit var locationManager : LocationManager
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCod = findViewById( R.id.etCod )
        etNome = findViewById( R.id.etNome )
        etTelefone = findViewById( R.id.etTelefone )
        etDescricao = findViewById(R.id.etDescricao)
        banco = DatabaseHandler( this )
        locationManager = getSystemService( Context.LOCATION_SERVICE ) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0f,this)

        ActivityCompat.requestPermissions( this, arrayOf( Manifest.permission.ACCESS_FINE_LOCATION ), 1 )

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        if ( intent.getIntExtra( "cod",0 ) != 0 ) {
            val cod = intent.getIntExtra("cod", 0)
            val nome = intent.getStringExtra("nome")
            val telefone = intent.getStringExtra("telefone")
            val descricao = intent.getStringExtra("descricao")

            etCod.setText( cod.toString() )
            etNome.setText( nome )
            etTelefone.setText( telefone )
            etDescricao.setText(descricao)
        } else {
            val btExcluir = findViewById<Button>( R.id.btExcluir )
            val btPesquisar = findViewById<Button>( R.id.btPesquisar )

            btExcluir.visibility = View.GONE
            btPesquisar.visibility = View.GONE

        }




    }

    fun btAlterarOnClick(view: View) {

        if ( etCod.text.toString().isEmpty() ) {
            val locate = Locate( 0, etNome.text.toString(), etTelefone.text.toString(),etDescricao.text.toString() )
            banco.insert( locate )
            Toast.makeText( this, "Inclusão realizada com sucesso", Toast.LENGTH_SHORT ).show()
            limparTela()
        } else {
            val pessoa = Locate( etCod.text.toString().toInt(), etNome.text.toString(), etTelefone.text.toString(),etDescricao.text.toString() )
            banco.update( pessoa )
            Toast.makeText( this, "Alteração realizada com sucesso", Toast.LENGTH_SHORT ).show()
        }

        finish()

    }
    fun btExcluirOnClick(view: View) {

        banco.delete( etCod.text.toString().toInt() )

        Toast.makeText( this, "Exclusão realizada com sucesso", Toast.LENGTH_SHORT ).show()

        finish()
    }
    fun btPesquisarOnClick(view: View) {
        val cod = etCod.text.toString().toInt()

        val locate = banco.find( cod )

        if ( locate != null ) {
            etNome.setText( locate.latitude )
            etTelefone.setText( locate.longitude )
            etDescricao.setText(locate.descricao)
        } else {
            Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_SHORT ).show()
        }
    }
    fun btListarOnClick(view: View) {
        //val saida = banco.list()

        //Toast.makeText( this, saida, Toast.LENGTH_SHORT ).show()

        val intent = Intent( this, ListarActivity::class.java)
        startActivity( intent )

    }

    fun limparTela() {
        etCod.setText( "" )
        etNome.setText( "" )
        etTelefone.setText( "" )
        etDescricao.setText("")
    }

    override fun onLocationChanged(position: Location) {
        etNome.setText( position.latitude.toString() )
        etTelefone.setText( position.longitude.toString() )
    }
}