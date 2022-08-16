package ua.dimaevseenko.myapplication.network.request

import retrofit2.Call
import retrofit2.http.GET
import ua.dimaevseenko.myapplication.network.result.Hots

interface RHots {
    @GET("hot")
    fun getHots(): Call<Hots>
}