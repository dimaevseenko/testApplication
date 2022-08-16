package ua.dimaevseenko.myapplication.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ua.dimaevseenko.myapplication.MainActivity
import ua.dimaevseenko.myapplication.di.module.AppModule
import ua.dimaevseenko.myapplication.fragment.MainFragment
import ua.dimaevseenko.myapplication.fragment.dialog.MainDialogFragment

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(mainDialogFragment: MainDialogFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): AppComponent
    }
}