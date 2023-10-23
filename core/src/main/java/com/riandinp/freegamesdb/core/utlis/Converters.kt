package com.riandinp.freegamesdb.core.utlis

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(data: List<String>?) : String? {
        return data?.joinToString(";")
    }

    @TypeConverter
    fun fromString(value: String?) : List<String>? {
        return value?.split(";")
    }
}