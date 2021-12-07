package edu.istea.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class User(var name: String,
           var surname: String,
           var dni: String,
           var sexo: String,
           var birth: String,
           var city: String,
           var tratamiento: String,
           var npila: String,
           var pass: String) : Parcelable {
}