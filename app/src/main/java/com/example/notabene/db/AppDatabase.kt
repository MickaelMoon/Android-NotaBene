package com.example.messagingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.messagingapp.db.daos.ConversationsDao
import com.example.messagingapp.db.entities.ConversationEntity

@Database(entities = [ConversationEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun conversationDao(): ConversationsDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            var instance = INSTANCE
            if(INSTANCE == null){
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "messaging_app_local_db"
                ).build()
            }
            return instance!!
        }
    }
}