package uz.gita.bookappcleanarchitecture.data.model

import java.io.Serializable

data class BookData(
    val id:Int,
    val coverUrl:String,
    val bookUrl:String,
    val title:String,
    val author:String,
    val rate:Double,
    val reference:String = ""
): Serializable
