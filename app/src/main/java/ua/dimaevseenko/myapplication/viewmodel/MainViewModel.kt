package ua.dimaevseenko.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.myapplication.network.Server
import ua.dimaevseenko.myapplication.network.result.Hots
import ua.dimaevseenko.myapplication.network.result.Post
import ua.dimaevseenko.myapplication.network.result.Posts
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var server: Server

    private var posts: Posts? = null

    var listener: Listener? = null

    interface Listener{
        fun onPostsLoaded(posts: Posts)
    }

    fun getPosts(): Posts?{
        if(posts == null)
            loadHots()
        return posts
    }

    private fun loadHots(){
        server.getHots(HotsCallback())
    }

    private fun loadPost(hots: Hots){
        server.getPosts(hots){ posts ->
            this.posts = posts
            listener?.onPostsLoaded(this.posts!!)
        }
    }

    inner class HotsCallback: Callback<Hots>{
        override fun onResponse(call: Call<Hots>, response: Response<Hots>) {
            response.body()?.let { loadPost(it) }
        }

        override fun onFailure(call: Call<Hots>, t: Throwable) {
            t.printStackTrace()
        }
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject
        lateinit var mainViewModel: MainViewModel
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return mainViewModel as T
        }
    }
}