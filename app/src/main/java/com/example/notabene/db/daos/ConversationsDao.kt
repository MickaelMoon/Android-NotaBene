package com.example.messagingapp.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.messagingapp.db.entities.ConversationEntity

@Dao
interface ConversationsDao {
    @Query("SELECT * FROM conversation_entity")
    fun getAllConversations(): List<ConversationEntity>

    @Insert
    fun addNewConversation(data: ConversationEntity)
}