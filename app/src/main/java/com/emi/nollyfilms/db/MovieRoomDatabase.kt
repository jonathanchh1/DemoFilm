package com.emi.nollyfilms.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.emi.nollyfilms.R
import com.emi.nollyfilms.model.Movies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Movies::class], version = 2)
abstract class MovieRoomDatabase : RoomDatabase(){


    abstract fun movieDao() : MovieDao

    companion object{

        @Volatile
        private var INSTANCE : MovieRoomDatabase?=null

        fun getDatabase(context: Context, scope : CoroutineScope) : MovieRoomDatabase{

            return synchronized(this){

                val instance = Room.databaseBuilder(context.applicationContext,
                     MovieRoomDatabase::class.java,
                     context.getString(R.string.database_name))

                    .fallbackToDestructiveMigration()
                    .addCallback(MovieRoomDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }

        class MovieRoomDatabaseCallback(var scope : CoroutineScope) : RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->

                    scope.launch(Dispatchers.IO) {
                        Populatedatabase(database.movieDao())
                    }
                }
            }
        }

        suspend fun Populatedatabase(movieDao : MovieDao){
            movieDao.deleteAll()
        }
    }


}