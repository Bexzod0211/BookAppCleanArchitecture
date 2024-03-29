package uz.gita.bookappcleanarchitecture.domain.repository

import android.content.Context
import androidx.room.util.query
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.data.model.CategoryData
import uz.gita.bookappcleanarchitecture.data.source.local.MySharedPref
import uz.gita.bookappcleanarchitecture.data.source.local.database.BookDatabase
import uz.gita.bookappcleanarchitecture.data.source.local.entities.BookEntity
import uz.gita.bookappcleanarchitecture.utils.myLog
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val bookDatabase: BookDatabase,
    private val localStorage:MySharedPref
) : AppRepository {


    private val bookDao = bookDatabase.getBookDao()
    override suspend fun getAllRecommendedBooks(): Result<List<BookData>> = withContext(Dispatchers.IO) {
        val list = arrayListOf<BookData>()
        try {

            val documents = fireStore.collection("books").whereGreaterThan("rate", 3.9).orderBy("rate").get().await()

            if (documents.isEmpty) return@withContext Result.failure(Exception("Empty list"))

            documents.forEach {
                list.add(
                    BookData(
                        (it.get("id") as Long).toInt(),
                        it.get("coverUrl") as String,
                        it.get("bookUrl") as String,
                        it.get("title") as String,
                        it.get("author") as String,
                        it.get("rate") as Double,
                        it.get("reference") as String
                    )
                )
            }

            return@withContext Result.success(list)
        } catch (exception: Exception) {
            return@withContext Result.failure(exception)
        }
    }

    override suspend fun downloadBook(context: Context, book: BookData): Result<Unit> = withContext(Dispatchers.IO) {
        val file = File(context.filesDir, book.title)

        try {
            val task = storage.reference.child("books/${book.reference}")
                .getFile(file)
                .await()
            if (task.bytesTransferred == task.totalByteCount) {
                bookDao.addBook(BookEntity(book.id, book.coverUrl, book.bookUrl, book.title, book.author, book.rate, book.reference))
                return@withContext Result.success(Unit)
            } else Result.failure(java.lang.Exception("Can't download"))
        } catch (e: java.lang.Exception) {
            return@withContext Result.failure(e)
        }
    }

    override suspend fun getAllSavedBooks(): Result<List<BookData>> {
        return Result.success(bookDao.getAllSavedBooks())
    }

    override suspend fun getAllBooks(): Result<List<CategoryData>>  {
        val genres = getAllGenres()
        val list = arrayListOf<CategoryData>()

        genres.forEach {genre->
            list.add(CategoryData(genre, getAllBooksByGenre(genre)))
        }


        return Result.success(list)
    }

    private suspend fun getAllGenres():List<String>{
        val documents = fireStore.collection("category").get().await()
        val list = mutableListOf<String>()
        documents.forEach {
            list.add(
                it.get("genre") as String
            )
        }

        return list
    }

    override suspend fun getBooksByGenre(genre: String): Result<List<BookData>> = withContext(Dispatchers.IO){
        val _genre = genre.lowercase()

//        myLog("getAllBooksByGenre")

        try {

            val documents = fireStore.collection("books").whereEqualTo("genre", _genre).get()
                .await()


            val books = mutableListOf<BookData>()

            documents.forEach {
                books.add(
                    BookData(
                        (it.get("id") as Long).toInt(),
                        it.get("coverUrl") as String,
                        it.get("bookUrl") as String,
                        it.get("title") as String,
                        it.get("author") as String,
                        it.get("rate") as Double,
                        it.get("reference") as String
                    )
                )
            }

            return@withContext Result.success(books)
        }catch (e:Exception){
            return@withContext Result.failure(e)
        }
    }

    override suspend fun getRecommendedBooksByQuery(query: String): Result<List<BookData>> = withContext(Dispatchers.IO) {
//        val documents = fireStore.collection("books").whereArrayContains("title",query).get().await()
//        myLog(documents.documents[0].get("title") as String)
        val documents = fireStore.collection("books").whereGreaterThan("rate", 3.9).orderBy("rate").get().await()

        try {
            val books = mutableListOf<BookData>()

            documents.forEach {
                val title =  it.get("title") as String
//                myLog("${title.contains(query.lowercase(),true)}  title = $title , query = ${query.lowercase()}")
                if (title.contains(query.lowercase(),true)) {
                    books.add(
                        BookData(
                            (it.get("id") as Long).toInt(),
                            it.get("coverUrl") as String,
                            it.get("bookUrl") as String,
                            it.get("title") as String,
                            it.get("author") as String,
                            it.get("rate") as Double,
                            it.get("reference") as String
                        )
                    )
                }
            }

            return@withContext Result.success(books)
        }catch (e:Exception){
            return@withContext Result.failure(e)
        }
    }

    override suspend fun getBooksByQueryAndGenre(query: String, genre: String): Result<List<BookData>>  = withContext(Dispatchers.IO){
        val _genre = genre.lowercase()
//        val documents = fireStore.collection("books").whereArrayContains("title",query).get().await()

        val documents = fireStore.collection("books").whereEqualTo("genre", _genre).get()
            .await()

        try {
            val books = mutableListOf<BookData>()

            documents.forEach {
                val title = it.get("title") as String
                if (title.contains(query.lowercase(),true)) {
                    books.add(
                        BookData(
                            (it.get("id") as Long).toInt(),
                            it.get("coverUrl") as String,
                            it.get("bookUrl") as String,
                            it.get("title") as String,
                            it.get("author") as String,
                            it.get("rate") as Double,
                            it.get("reference") as String
                        )
                    )
                }
            }

            return@withContext Result.success(books)
        }catch (e:Exception){
            return@withContext Result.failure(e)
        }
    }

    override suspend fun saveLastReadBook(book: BookData): Result<Unit> {
        localStorage.saveLastReadBook(book)
        return Result.success(Unit)
    }

    override suspend fun getLastReadBook(): Result<BookData?> {
        return Result.success(localStorage.getLastReadBook())
    }


    private suspend fun getAllBooksByGenre(genre: String): List<BookData> {
        val _genre = genre.lowercase()

        myLog("getAllBooksByGenre")
        val documents = fireStore.collection("books").whereEqualTo("genre", _genre).get()
            .await()


        val books = arrayListOf<BookData>()

        documents.forEach {
            books.add(
                BookData(
                    (it.get("id") as Long).toInt(),
                    it.get("coverUrl") as String,
                    it.get("bookUrl") as String,
                    it.get("title") as String,
                    it.get("author") as String,
                    it.get("rate") as Double,
                    it.get("reference") as String
                )
            )
        }

        myLog("books $books")
        return books
    }


}

