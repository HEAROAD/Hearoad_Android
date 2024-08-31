package com.hearoad.hearoad.data.model.response

//data class VoiceResponse()

data class AddVoiceResponse(
    val message: String,
)

data class EmergencyVoiceResponse(
    val id: String,
    val word: String,
    val emoji: String,
    val filePath: String
)
