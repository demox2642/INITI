package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Attribute(
    val comment: String?,
    val name: String,
    @SerializedName("referenced-type")
    val referencedType: String?,
    val value: String?,
)
