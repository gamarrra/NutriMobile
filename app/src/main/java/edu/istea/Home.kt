package edu.istea

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.istea.adapter.Adapter
import edu.istea.dao.DBHelper
import edu.istea.model.Comida
import kotlinx.android.synthetic.main.home_layout.*
import kotlinx.android.synthetic.main.cargacomida_layout.*
import kotlinx.android.synthetic.main.resumen_layout.*
import kotlinx.android.synthetic.main.datos_layout.*
import java.text.SimpleDateFormat
import java.util.*


class Home : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_layout)

        lateinit var hand:DBHelper

        hand = DBHelper(this)

        lateinit var userId :Number

        userId = intent.getSerializableExtra("userId") as Number

        var linearLayout: ConstraintLayout = findViewById(R.id.pag_resumen_layout)
        val viewPagResumen:View = layoutInflater.inflate(R.layout.resumen_layout,null)
        linearLayout.addView(viewPagResumen)

        viewPagResumen.visibility = View.GONE

        val comidas = arrayOf("Desayuno", "Almuerzo", "Merienda","Cena")
        var comidasAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,comidas)
        comidasAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        val spinner1: Spinner =findViewById(R.id.spinner_comi)

        spinner1.adapter = comidasAdapter

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (spinner_comi.selectedItem.toString() == "Almuerzo" || spinner_comi.selectedItem.toString() == "Cena")
                {
                    pregunta_postre.visibility = View.VISIBLE
                    spinner_pos.visibility = View.VISIBLE

                }else
                {
                    pregunta_postre.visibility = View.GONE
                    spinner_pos.visibility = View.GONE
                }
            }
        }


        val ingirioPostre = arrayOf("No", "Si")
        var ingirioPostreAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, ingirioPostre)
        ingirioPostreAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        val spinner2: Spinner =findViewById(R.id.spinner_pos)
        spinner2.adapter = ingirioPostreAdapter

        btn_salir_resumen.setOnClickListener {
            botoneshome.visibility = View.VISIBLE
            botoneshome2.visibility = View.VISIBLE
            cargar_comida_layout.visibility = View.GONE
            pag_resumen_layout.visibility = View.GONE
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (spinner_pos.selectedItem.toString() == "Si" ){
                    insert_postre.visibility = View.VISIBLE

                }else{
                    insert_postre.visibility = View.GONE
                }
            }
        }


        val tentacion = arrayOf("No", "Si")
        var tentacionPostreAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, tentacion)
        tentacionPostreAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        val spinner3: Spinner =findViewById(R.id.spinner_tent)
        spinner3.adapter = tentacionPostreAdapter


        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (spinner_tent.selectedItem.toString() == "Si" ){
                    insert_tentacion.visibility = View.VISIBLE

                }else{
                    insert_tentacion.visibility = View.GONE
                }
            }
        }

        val quedoConHambre = arrayOf("No", "Si")
        var quedoConHambreAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, quedoConHambre)
        quedoConHambreAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        val spinner4: Spinner =findViewById(R.id.spinner_hambre)
        spinner4.adapter = quedoConHambreAdapter

        guardarComida.setOnClickListener {

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val dia = sdf.format(Date())
            var comidaNueva= Comida(spinner_comi.selectedItem.toString(),insert_comida.text.toString(),insert_secundaria.text.toString(),bebida.text.toString(),spinner_pos.selectedItem.toString(), insert_postre.text.toString(), spinner_tent.selectedItem.toString(), insert_tentacion.text.toString(),spinner_hambre.selectedItem.toString(),dia,userId.toString())

            hand.saveComida(comidaNueva)


            botoneshome.visibility = View.VISIBLE
            botoneshome2.visibility = View.VISIBLE
            cargar_comida_layout.visibility = View.GONE

        }

        btn_verhisto.setOnClickListener {

            lateinit var rvComidas: RecyclerView
            var comiInfo = hand.getComidaInfo(userId)
            rvComidas=recycleview_comida
            rvComidas.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
            rvComidas.adapter= Adapter(comiInfo)

            botoneshome.visibility = View.GONE
            botoneshome2.visibility = View.GONE
            cargar_comida_layout.visibility = View.GONE
            pag_resumen_layout.visibility = View.VISIBLE
        }

        btn_volver_datos.setOnClickListener {
            botoneshome.visibility = View.VISIBLE
            botoneshome2.visibility = View.VISIBLE
            cargar_comida_layout.visibility = View.GONE
            usuario_legajo_layout.visibility = View.GONE
        }

        btn_volver_comida.setOnClickListener {
            botoneshome.visibility = View.VISIBLE
            botoneshome2.visibility = View.VISIBLE
            cargar_comida_layout.visibility = View.GONE
            usuario_legajo_layout.visibility = View.GONE
        }

        btn_cargarcomi.setOnClickListener {
            botoneshome.visibility = View.GONE
            botoneshome2.visibility = View.GONE
            cargar_comida_layout.visibility = View.VISIBLE

        }

        btn_volvermenu.setOnClickListener {
            var intent = Intent(this, Intro::class.java)
            startActivity(intent)


        }

        btn_verdatos.setOnClickListener {

            var datos = hand.getUserInfo(userId)

            var userdato:TextView = username_legajo
            userdato.text = "Usuario: " + datos.name

            var maildato:TextView = email_legajo
            maildato.text = "Password: " + datos.pass

            var nombrepiladato:TextView = nombredepila_datos
            nombrepiladato.text = "Nombre: " + datos.npila

            var apellidoDato:TextView = apellido_datos
            apellidoDato.text = "Apellido: " + datos.surname

            var dniDato:TextView = dni_datos
            dniDato.text = "Documento: " + datos.dni

            var bithdato:TextView = btirth_datos
            bithdato.text = "Fecha de nacimiento: " + datos.birth

            var sexoDato:TextView = sexo_datos
            sexoDato.text = "Sexo: " + datos.sexo

            var cityDato:TextView = city_datos
            cityDato.text = "Localidad: " + datos.city

            var tratDato:TextView = trata_datos
            tratDato.text = "Tratamiento: " + datos.tratamiento

            botoneshome.visibility = View.GONE
            botoneshome2.visibility = View.GONE

            cargar_comida_layout.visibility = View.GONE
            usuario_legajo_layout.visibility = View.VISIBLE

        }
    }

}