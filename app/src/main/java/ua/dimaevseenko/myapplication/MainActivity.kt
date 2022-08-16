package ua.dimaevseenko.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.dimaevseenko.myapplication.databinding.ActivityMainBinding
import ua.dimaevseenko.myapplication.fragment.MainFragment
import ua.dimaevseenko.myapplication.network.request.RHots
import ua.dimaevseenko.myapplication.network.request.RPost
import ua.dimaevseenko.myapplication.network.result.Posts
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainFragment: MainFragment

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.bind(layoutInflater.inflate(R.layout.activity_main, null, false))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(binding.root)

        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(binding.mainContainer.id, mainFragment, MainFragment.TAG)
                .commit()
    }
}