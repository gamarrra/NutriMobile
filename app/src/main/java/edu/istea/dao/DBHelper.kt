package edu.istea.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import edu.istea.model.User
import edu.istea.model.Comida
import java.lang.Exception

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "saludable.db"
        private val DATABASE_VERSION = 11
        internal val factory = null
        val TABLE_USER = "users"
        val COLUMN_NAME = "name"
        val COLUMN_SURNAME ="surname"
        val COLUMN_DNI="dni"
        val COLUMN_SEXO="sexo"
        val COLUMN_BIRTH="birth"
        val COLUMN_CITY="city"
        val COLUMN_TRAT="tratamiento"
        val COLUMN_NPILA = "npila"
        val COLUMN_PASS = "pass"
        val COLUMN_ID = "id"
        val TABLE_COMIDA="comidas"
        val COLUMN_IDC = "idc"
        val COLUMN_TIPO="tipoComida"
        val COLUMN_COMIDAPRINCIPAL="comidaPrincipal"
        val COLUMN_COMIDASECUNDARIA="comidaSecundaria"
        val COLUMN_BEBIDA="bebida"
        val COLUMN_POSTREBOOLEAN="postreBoolean"
        val COLUMN_POSTRE="postre"
        val COLUMN_TENTACIONBOOOLEAN="tentacionBoolean"
        val COLUMN_TENTACION="tentacion"
        val COLUMN_HAMBREBOOLEAN="hambreBoolean"
        val COLUMN_DIA="dia"
        val COLUMN_HORA="hora"
        val COLUMN_USUARIOID="usuarioId"
        var ID:Int=0
    }
    override fun onCreate(db: SQLiteDatabase?) {

        val createTableUser = ("CREATE TABLE " + TABLE_USER +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_SURNAME + " TEXT," +
                COLUMN_DNI + " TEXT," +
                COLUMN_SEXO + " TEXT," +
                COLUMN_BIRTH + " TEXT," +
                COLUMN_CITY + " TEXT," +
                COLUMN_TRAT + " TEXT," +
                COLUMN_NPILA + " TEXT," +
                COLUMN_PASS + " TEXT )"
                )

        db?.execSQL(createTableUser)

        val createComidaUser = ("CREATE TABLE " + TABLE_COMIDA +
                "(" + COLUMN_IDC + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TIPO + " TEXT," +
                COLUMN_COMIDAPRINCIPAL + " TEXT," +
                COLUMN_COMIDASECUNDARIA + " TEXT," +
                COLUMN_BEBIDA + " TEXT," +
                COLUMN_POSTREBOOLEAN + " TEXT," +
                COLUMN_POSTRE + " TEXT," +
                COLUMN_TENTACIONBOOOLEAN + " TEXT," +
                COLUMN_TENTACION + " TEXT," +
                COLUMN_HAMBREBOOLEAN + " TEXT," +
                COLUMN_DIA + " TEXT," +
                COLUMN_HORA + " TEXT," +
                COLUMN_USUARIOID + " TEXT )"
                )

        db?.execSQL(createComidaUser)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_COMIDA)
        onCreate(db)
    }

    fun saveUser(user: User) {
        var db = this.writableDatabase

        val values = ContentValues()

        values.put(COLUMN_NAME, user.name)
        values.put(COLUMN_SURNAME, user.surname)
        values.put(COLUMN_DNI, user.dni)
        values.put(COLUMN_SEXO, user.sexo)
        values.put(COLUMN_BIRTH, user.birth.toString())
        values.put(COLUMN_CITY,user.city)
        values.put(COLUMN_TRAT, user.tratamiento)
        values.put(COLUMN_NPILA, user.npila)
        values.put(COLUMN_PASS, user.pass)
        try {
            db.insert(TABLE_USER, null, values)
        } catch (e: Exception) {
            Log.e("error insert", e.message.toString())
        }


    }

    fun saveComida(comida: Comida) {
        var db = this.writableDatabase

        val values = ContentValues()

        values.put(COLUMN_TIPO, comida.tipoComida)
        values.put(COLUMN_COMIDAPRINCIPAL, comida.comidaPrincipal)
        values.put(COLUMN_COMIDASECUNDARIA, comida.comidaSecundaria)
        values.put(COLUMN_BEBIDA, comida.bebida)
        values.put(COLUMN_POSTREBOOLEAN, comida.postreBoolean)
        values.put(COLUMN_POSTRE, comida.postre)
        values.put(COLUMN_TENTACIONBOOOLEAN, comida.tentacionBoolean)
        values.put(COLUMN_TENTACION, comida.tentacion)
        values.put(COLUMN_HAMBREBOOLEAN, comida.hambreBoolean)
        values.put(COLUMN_DIA, comida.dia)
        values.put(COLUMN_USUARIOID, comida.usuarioId)
        try {
            db.insert(TABLE_COMIDA, null, values)
        } catch (e: Exception) {
            Log.e("error insert", e.message.toString())
        }


    }
    // validamos si el usuario esta en la base de datos , sino devuelve falso
    fun validateUser(user: User): Boolean {

        var db = this.readableDatabase

        var query = "SELECT * FROM " + TABLE_USER

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {

            do {
                val nameDB = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val passDB = cursor.getString(cursor.getColumnIndex(COLUMN_PASS))
                // == compara valor literal equals compara valor + tipe
                if (user.name.equals(nameDB) && user.pass.equals(passDB)) {
                    return true
                }

            } while (cursor.moveToNext())
        }

        return false

    }

    fun userActual(name: String, password: String):Int{
        val db=writableDatabase
        var idUser:Int = 0
        val query= "SELECT * FROM " + TABLE_USER +" where name = '$name' and pass = '$password'"
        val cursor = db.rawQuery(query,null)
        if(cursor.count<=0){
            cursor.close()
            return 0
        }
        if(cursor.moveToFirst()){

            do{
                idUser =  cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            }while(cursor.moveToNext())
        }
        cursor.close()
        return idUser
    }

    fun getUserInfo(userId: Number):User{
        val query = "SELECT * FROM "+TABLE_USER +" where id = '$userId'"
        val db=writableDatabase
        val cursor = db.rawQuery(query,null)
        val lisTaLegajos: ArrayList<User> = ArrayList<User>();
        if(cursor.moveToFirst()){
            do{

                val name =  cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val surname =  cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME))
                val dni =  cursor.getString(cursor.getColumnIndex(COLUMN_DNI))
                val sexo =  cursor.getString(cursor.getColumnIndex(COLUMN_SEXO))
                val birth =  cursor.getString(cursor.getColumnIndex(COLUMN_BIRTH))
                val city =  cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
                val tratamiento =  cursor.getString(cursor.getColumnIndex(COLUMN_TRAT))
                val npila =  cursor.getString(cursor.getColumnIndex(COLUMN_NPILA))
                val password =  cursor.getString(cursor.getColumnIndex(COLUMN_PASS))
                lisTaLegajos.add( User(name,surname,dni,sexo,birth,city,tratamiento,npila,password))
            }while(cursor.moveToNext())
        }
        cursor.close()
        return lisTaLegajos.first()
    }

    fun getComidaInfo(userId:Number):ArrayList<Comida>{

        val lisTaComidas: ArrayList<Comida> = ArrayList<Comida>();
        val query = "SELECT * FROM " + TABLE_COMIDA+" where "+ COLUMN_USUARIOID+" = '$userId'"
        val db= this.readableDatabase
        val cursor = db.rawQuery(query,null)
        if(cursor.moveToFirst()){
            do{
                ID =  cursor.getInt(cursor.getColumnIndex(COLUMN_IDC))
                val tipoComida = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO))
                val comidaPrincipal = cursor.getString(cursor.getColumnIndex(COLUMN_COMIDAPRINCIPAL))
                val comidaSecundaria = cursor.getString(cursor.getColumnIndex(
                    COLUMN_COMIDASECUNDARIA))
                val bebida = cursor.getString(cursor.getColumnIndex(COLUMN_BEBIDA))
                val postreBoolean = cursor.getString(cursor.getColumnIndex(COLUMN_POSTREBOOLEAN))
                val postre = cursor.getString(cursor.getColumnIndex(COLUMN_POSTRE))
                val tentacionBoolean = cursor.getString(cursor.getColumnIndex(COLUMN_TENTACIONBOOOLEAN))
                val tentacion = cursor.getString(cursor.getColumnIndex(COLUMN_TENTACION))
                val hambreBoolean = cursor.getString(cursor.getColumnIndex(COLUMN_HAMBREBOOLEAN))
                val dia = cursor.getString(cursor.getColumnIndex(COLUMN_DIA))
                val usuarioID = cursor.getString(cursor.getColumnIndex(COLUMN_USUARIOID))



                lisTaComidas.add(Comida(tipoComida,comidaPrincipal,comidaSecundaria,bebida,postreBoolean,postre,tentacionBoolean,tentacion,hambreBoolean,dia,usuarioID))

            }while(cursor.moveToNext())
        }


        return lisTaComidas
    }
}