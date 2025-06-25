package com.oliva.samuel.tricountclone.utils.converters

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConverter {

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid?.toString()

    @TypeConverter
    fun stringToUUID(string: String?): UUID? = string?.let { UUID.fromString(string) }

}
