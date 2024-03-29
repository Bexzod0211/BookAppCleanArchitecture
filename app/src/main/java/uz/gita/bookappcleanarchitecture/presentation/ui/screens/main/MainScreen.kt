package uz.gita.bookappcleanarchitecture.presentation.ui.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.databinding.ScreenMainBinding
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.explore.ExploreScreen
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.home.HomeScreen
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.saved.SavedScreen
import uz.gita.bookappcleanarchitecture.utils.addScreen
import uz.gita.bookappcleanarchitecture.utils.replaceScreen

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private var currentScreen:Fragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (currentScreen == null){
            replaceScreen(HomeScreen())
        }else
            replaceScreen(currentScreen?:HomeScreen())


        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home->{
                    currentScreen = HomeScreen()
                    addScreen(currentScreen!!)
                }
                R.id.menu_saved->{
                    currentScreen = SavedScreen()
                    addScreen(SavedScreen())
                }
                R.id.menu_explore->{
                    currentScreen = ExploreScreen()
                    addScreen(ExploreScreen())
                }
//                R.id.menu_profile->{
//                    replaceScreen(ProfileScreen())
//                }
            }
            true
        }
    }
}