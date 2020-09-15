package com.ervin.pokedex.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity
    (
    tableName = ElementEntity.TABLE_NAME,
    indices = [Index(ElementEntity.ELEMENT_ID)]
)
data class ElementEntity(

    @ColumnInfo(name = ELEMENT_ID)
    @PrimaryKey
    val typeId: Int,

    @ColumnInfo(name = ELEMENT_NAME)
    val typeName: String,

    @ColumnInfo(name = ELEMENT_TYPE_COLOR)
    val typeColor: String
) {
    companion object {
        const val TABLE_NAME = "element"
        const val ELEMENT_ID = "element_id"
        const val ELEMENT_NAME = "element_name"
        const val ELEMENT_TYPE_COLOR = "element_type_color"
    }
}