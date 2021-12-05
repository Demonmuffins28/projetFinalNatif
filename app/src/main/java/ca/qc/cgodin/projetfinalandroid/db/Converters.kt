package ca.qc.cgodin.projetfinalandroid.db

import androidx.room.TypeConverter
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur
import com.bumptech.glide.util.Util
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromList(value : List<Int>) = Json.encodeToString(value)
//    fun fromList(utilisateur: Utilisateur) : List<Int>? {
//        return utilisateur.partisans
//    }


    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<Int>>(value)
}