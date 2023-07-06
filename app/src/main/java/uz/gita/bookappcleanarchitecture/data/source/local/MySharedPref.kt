package uz.gita.bookappcleanarchitecture.data.source.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.gita.bookappcleanarchitecture.data.model.BookData
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPref @Inject constructor(
    private val pref:SharedPreferences
) {
    private val LAST_READ_BOOK = "LAST_READ_BOOK"

    fun saveLastReadBook(book:BookData){
//        val value = "${book.id}#${book.coverUrl}#${book.bookUrl}#${book.title}#${book.author}#${book.rate}#${book.reference}"

        val value = Gson().toJson(book)

        pref.edit().putString(LAST_READ_BOOK,value).apply()
    }

    fun getLastReadBook():BookData?{
        val type:Type = object:TypeToken<BookData>(){}.type
        val json = pref.getString(LAST_READ_BOOK,null).toString()
        val book = Gson().fromJson<BookData>(json,type)
        return book
    }

}