package uz.gita.bookappcleanarchitecture.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.bookappcleanarchitecture.data.source.local.daos.BookDao
import uz.gita.bookappcleanarchitecture.data.source.local.entities.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun getBookDao():BookDao

    /*companion object {
        private lateinit var instance:BookDatabase

        fun init(context: Context){
            if (!::instance.isInitialized){
                instance = Room.databaseBuilder(context,BookDatabase::class.java,"books.db")
                    .allowMainThreadQueries()
                    .build()
            }
        }

        fun getInstance():BookDatabase = instance

    }*/

}