package edu.istea.model

import java.io.Serializable

data class Comida(val tipoComida:String,
                  val comidaPrincipal:String,
                  val comidaSecundaria:String,
                  val bebida:String,
                  val postreBoolean:String,
                  val postre:String,
                  val tentacionBoolean:String,
                  val tentacion:String,
                  val hambreBoolean:String,
                  val dia:String,
                  val usuarioId: String):Serializable



