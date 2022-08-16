package ua.dimaevseenko.myapplication.network.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ua.dimaevseenko.myapplication.network.result.Post

interface RPost{
    @GET("post/{id}")
    fun getPost(@Path("id") id: String): Call<Post>
}