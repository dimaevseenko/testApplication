package ua.dimaevseenko.myapplication.fragment

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.myapplication.R
import ua.dimaevseenko.myapplication.databinding.RecyclerViewItemBinding
import ua.dimaevseenko.myapplication.network.result.Post
import ua.dimaevseenko.myapplication.network.result.Posts

class RecyclerAdapter @AssistedInject constructor(
    @Assisted("posts")
    private val posts: Posts,
    private val context: Context
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position%2==0)
            0
        else
            1
    }

    interface Listener{
        fun onPostClick(position: Int)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var binding = RecyclerViewItemBinding.bind(view)

        fun bind(post: Post){

            if(itemViewType == 1)
                binding.root.setBackgroundColor(Color.WHITE)
            else
                binding.root.setBackgroundColor(Color.parseColor("#DCDCDC"))

            if(post.getType() == Post.Type.TEXT)
                binding.type.setTextColor(Color.BLACK)
            else
                binding.type.setTextColor(Color.BLUE)

            binding.type.text = "Type: ${post.getType()}"
            binding.content.text = "Content: ${post.payload.text?:post.payload.url}"

            binding.root.setOnClickListener {
                listener?.onPostClick(adapterPosition)
            }
        }
    }

    @AssistedFactory
    interface Factory{
        fun createAdapter(
            @Assisted("posts")
            posts: Posts
        ): RecyclerAdapter
    }
}