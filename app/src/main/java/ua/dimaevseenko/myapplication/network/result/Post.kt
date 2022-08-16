package ua.dimaevseenko.myapplication.network.result

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class Post(
    @SerializedName("type")
    private val type: String,

    @SerializedName("payload")
    val payload: Payload
){

    fun getType(): Type {
        return if(Type.TEXT.name.equals(type, ignoreCase = true))
            Type.TEXT
        else
            Type.WEBPAGE
    }

    enum class Type{
        TEXT, WEBPAGE
    }

    data class Payload(
        val url: String?,
        val text: String?
    )
}

class Posts: ArrayList<Post>()