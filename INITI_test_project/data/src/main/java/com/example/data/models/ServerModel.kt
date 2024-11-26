package com.example.data.models

import com.google.gson.annotations.SerializedName

data class ServerModel(
    val attributes: Attributes,
    val type: String,
    @SerializedName("primary-key")
    val primaryKey: Attributes?,
)
