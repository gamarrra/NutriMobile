package edu.istea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.istea.R
import edu.istea.model.Comida
import kotlinx.android.synthetic.main.resumen_item.view.*

class Adapter (private val dataSet: ArrayList<Comida>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val TipoComida: TextView
        val comidaPrincipal: TextView
        val comidaSecundaria: TextView
        val bebida: TextView
        val postreBoolean: TextView
        val postre: TextView
        val tentacionBoolean: TextView
        val tentacion: TextView
        val hambreBoolean: TextView
        val rowPostre:TableRow
        val rowTentacion:TableRow
        val rowPostreBoolean:TableRow
        val dia:TextView

        init {
            TipoComida = view.tipo_comida_tabla
            comidaPrincipal = view.comida_principal_tabla
            comidaSecundaria = view.comida_secundaria_tabla
            bebida = view.bebida_tabla
            postreBoolean = view.ingirio_postre_tabla
            postre = view.postre_tabla
            tentacionBoolean = view.tentacion_pregunta_tabla
            tentacion = view.tentacion_tabla
            hambreBoolean = view.hambre_pregunta_tabla
            rowPostre = view.row_postre
            rowTentacion = view.row_tentacion
            rowPostreBoolean = view.row_ingirio_postre

            dia = view.fecha_tabla
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(dataSet[position].postreBoolean != "Si"){
            holder.rowPostre.visibility = View.GONE
        }

        if(dataSet[position].tentacionBoolean != "Si"){
            holder.rowTentacion.visibility = View.GONE
        }

        if(dataSet[position].tipoComida != "Almuerzo" && dataSet[position].tipoComida != "Cena"){
            holder.rowPostreBoolean.visibility = View.GONE
        }
        holder.TipoComida.text =  dataSet[position].tipoComida
        holder.comidaPrincipal.text = dataSet[position].comidaPrincipal
        holder.comidaSecundaria.text = dataSet[position].comidaSecundaria
        holder.bebida.text = dataSet[position].bebida
        holder.postreBoolean.text = dataSet[position].postreBoolean
        holder.postre.text = dataSet[position].postre
        holder.tentacionBoolean.text = dataSet[position].tentacionBoolean
        holder.tentacion.text = dataSet[position].tentacion
        holder.hambreBoolean.text = dataSet[position].hambreBoolean
        holder.dia.text = dataSet[position].dia


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.resumen_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}