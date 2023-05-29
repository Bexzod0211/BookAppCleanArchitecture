package uz.gita.bookappcleanarchitecture.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.bookappcleanarchitecture.data.source.local.MySharedPref
import uz.gita.bookappcleanarchitecture.data.source.local.database.BookDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFireStore():FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideFireStorage():FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context):BookDatabase = Room.databaseBuilder(context,BookDatabase::class.java,"books.db")
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context):SharedPreferences = context.getSharedPreferences("BookApp",Context.MODE_PRIVATE)

}