package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Objects(
    @SerializedName("object")
    val model: List<ServerModel>,
)
