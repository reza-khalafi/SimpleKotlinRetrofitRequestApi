package com.example.simplekotlinretrofitrequestapi

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {


    companion object {

        private val baseUrl:String = "https://reqres.in/"

        fun create(): Services {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()

            return retrofit.create(Services::class.java)
        }
    }




    @GET("api/users")
    fun getUser(@Query("id") id : Int) : Observable<UserDataModel>
}