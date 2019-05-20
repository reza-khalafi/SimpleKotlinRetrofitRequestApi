package com.example.simplekotlinretrofitrequestapi



data class UserModel (
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)


data class UserDataModel (
    val data:UserModel
)