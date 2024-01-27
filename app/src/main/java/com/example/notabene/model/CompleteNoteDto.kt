package com.example.notabene.model

import com.example.messagingapp.model.conversation_model.MessageData



data class CompleteNoteDto(
    val infos: NoteData,
    val conversations: MutableList<MessageData>
) {
    fun getFormattedConversationCount(): String = if (this.conversations.size > 99)  "+99" else  this.conversations.size.toString()

    fun getFormattedFullUserName(): String = this.infos.lastName + " " + this.infos.name
}
