package ca.qc.cgodin.projetfinalandroid.db

import androidx.room.TypeConverter

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converters {
    @TypeConverter
    fun fromList(value : List<Int>) = Json.encodeToString(value)
//    fun fromList(utilisateur: Utilisateur) : List<Int>? {
//        return utilisateur.partisans
//    }


    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<Int>>(value)
}