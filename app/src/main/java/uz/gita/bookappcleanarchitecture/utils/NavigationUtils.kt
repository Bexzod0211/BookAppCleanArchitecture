package uz.gita.bookappcleanarchitecture.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import uz.gita.bookappcleanarchitecture.R

fun Fragment.addScreen(fm: Fragment) {
    childFragmentManager.beginTransaction()
        .add(R.id.container_main,fm)
        .commit()
}


fun Fragment.replaceScreen(fm: Fragment){
    childFragmentManager
        .beginTransaction()
        .replace(R.id.container_main,fm)
        .commit()
}

fun Fragment.popBackStack(){
    childFragmentManager.popBackStack()
}


fun FragmentActivity.replaceScreenAddToStack(fm:Fragment){
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment,fm)
        .addToBackStack("null")
        .commit()
}

fun Fragment.replaceScreenAddToStack(fm:Fragment){
    requireActivity().replaceScreenAddToStack(fm)
}