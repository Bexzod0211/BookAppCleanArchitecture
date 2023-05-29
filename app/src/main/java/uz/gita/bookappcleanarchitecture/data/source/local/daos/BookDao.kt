package uz.gita.bookappcleanarchitecture.data.source.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.data.source.local.entities.BookEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book:BookEntity)

    @Query("SELECT * FROM books")
    fun getAllSavedBooks():List<BookData>
}