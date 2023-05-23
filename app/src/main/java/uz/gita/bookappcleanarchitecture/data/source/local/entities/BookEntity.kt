package uz.gita.bookappcleanarchitecture.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val coverUrl:String,
    val bookUrl:String,
    val title:String,
    val author:String,
    val rate:Double,
    val reference:String = ""
)
