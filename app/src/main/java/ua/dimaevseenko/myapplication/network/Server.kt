package ua.dimaevseenko.myapplication.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.myapplication.network.request.RHots
import ua.dimaevseenko.myapplication.network.request.RPost
import ua.dimaevseenko.myapplication.network.result.Hots
import ua.dimaevseenko.myapplication.network.result.Post
import ua.dimaevseenko.myapplication.network.result.Posts
import javax.inject.Inject

class Server @Inject constructor() {

    @Inject
    lateinit var rHots: RHots

    @Inject
    lateinit var rPost: RPost

    fun getHots(callback: Callback<Hots>){
        CoroutineScope(Dispatchers.Default).launch {
            rHots.getHots().enqueue(callback)
        }
    }

    fun getPosts(hots: Hots, completion: (posts: Posts)->Unit){
        CoroutineScope(Dispatchers.Default).launch {
            val posts = Posts()
            hots.forEach { id ->
                rPost.getPost(id.toString()).execute().body()?.let {
                    posts.add(it)
                }
            }
            launch(Dispatchers.Main) {
                completion(posts)
            }
        }
    }
}