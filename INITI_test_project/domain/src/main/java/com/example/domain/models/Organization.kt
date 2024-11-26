package com.example.domain.models

data class Organization(
    val orgId: String,
    val orgName: String,
    val orgCountry: String,
    val address: String?,
)
