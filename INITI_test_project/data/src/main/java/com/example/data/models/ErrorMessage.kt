package com.example.data.models

import com.google.gson.annotations.SerializedName

data class ErrorMessage(
    @SerializedName("text")
    val message: List<String>,
)
