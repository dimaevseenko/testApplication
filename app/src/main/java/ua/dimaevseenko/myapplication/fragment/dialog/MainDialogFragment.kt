package ua.dimaevseenko.myapplication.fragment.dialog

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.myapplication.R
import ua.dimaevseenko.myapplication.appComponent
import ua.dimaevseenko.myapplication.databinding.DialogFragmentMainBinding
import ua.dimaevseenko.myapplication.network.result.Post
import ua.dimaevseenko.myapplication.network.result.Posts
import ua.dimaevseenko.myapplication.viewmodel.MainViewModel
import javax.inject.Inject

class MainDialogFragment @Inject constructor(): DialogFragment() {

    companion object{
        const val TAG = "MainDialogFragment"
    }

    private lateinit var binding: DialogFragmentMainBinding

    private var position = 0

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogFragmentMainBinding.bind(inflater.inflate(R.layout.dialog_fragment_main, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { position = it.getInt("pos") }
        mainViewModel = ViewModelProvider(requireParentFragment())[MainViewModel::class.java]

        binding.next.setOnClickListener { next() }
        binding.previous.setOnClickListener { previous() }
        binding.contentText.setOnClickListener {
            mainViewModel.getPosts()?.let {
                if(it[position].getType() == Post.Type.WEBPAGE)
                    openLink(it[position].payload.url!!)
            }
        }

        updateText()
    }

    private fun openLink(url: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun previous(){
        mainViewModel.getPosts()?.let {
            if(position == 0)
                position = it.size-1
            else
                position--
        }
        updateText()
    }

    private fun next(){
        mainViewModel.getPosts()?.let {
            if(position+1 == it.size)
                position = 0
            else
                position++
        }
        updateText()
    }

    private fun updateText(){
        mainViewModel.getPosts()?.let {
            it[position].let { post->
                binding.typeText.text = post.getType().toString()
                binding.contentText.text = post.payload.text?:post.payload.url
            }
        }
    }

    override fun toString(): String {
        return TAG
    }
}