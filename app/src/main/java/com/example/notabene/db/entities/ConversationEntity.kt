package com.example.messagingapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("conversation_entity")
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "user_last_name") val userLastName: String
)
