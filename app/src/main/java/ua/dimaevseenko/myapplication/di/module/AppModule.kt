package ua.dimaevseenko.myapplication.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ua.dimaevseenko.myapplication.network.request.RHots
import ua.dimaevseenko.myapplication.network.request.RPost

@Module(includes = [RetrofitModule::class, NetworkModule::class])
object AppModule

@Module
object NetworkModule{

    @Provides
    fun provideHots(retrofit: Retrofit): RHots{
        return retrofit.create(RHots::class.java)
    }

    @Provides
    fun providePost(retrofit: Retrofit): RPost{
        return retrofit.create(RPost::class.java)
    }
}

@Module
object RetrofitModule{

    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://demo7877231.mockable.io/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}