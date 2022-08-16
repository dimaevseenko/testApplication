package ua.dimaevseenko.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import ua.dimaevseenko.myapplication.R
import ua.dimaevseenko.myapplication.appComponent
import ua.dimaevseenko.myapplication.databinding.FragmentMainBinding
import ua.dimaevseenko.myapplication.fragment.dialog.MainDialogFragment
import ua.dimaevseenko.myapplication.network.result.Post
import ua.dimaevseenko.myapplication.network.result.Posts
import ua.dimaevseenko.myapplication.viewmodel.MainViewModel
import javax.inject.Inject

class MainFragment @Inject constructor(): Fragment(), MainViewModel.Listener, RecyclerAdapter.Listener {

    companion object{
        const val TAG = "MainFragment"
    }

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var mainDialogFragment: MainDialogFragment

    @Inject
    lateinit var mainViewModelFactory: MainViewModel.Factory

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var adapterFactory: RecyclerAdapter.Factory

    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.bind(inflater.inflate(R.layout.fragment_main, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        mainViewModel.listener = this
        mainViewModel.getPosts()?.let { loadAdapter(it) }
    }

    override fun onPostsLoaded(posts: Posts) {
        loadAdapter(posts)
    }

    private fun loadAdapter(posts: Posts){
        recyclerAdapter = adapterFactory.createAdapter(posts)
        recyclerAdapter.listener = this
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onPostClick(position: Int) {
        mainDialogFragment.apply {
            arguments = Bundle().apply { putInt("pos", position) }
            show(this@MainFragment.childFragmentManager, MainDialogFragment.TAG)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.listener = null
        recyclerAdapter.listener = null
    }

    override fun toString(): String {
        return TAG
    }
}