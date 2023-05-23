package uz.gita.bookappcleanarchitecture.presentation.ui.screens.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.databinding.ScreenHomeBinding
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.HomeViewModel
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls.HomeViewModelImpl
import uz.gita.bookappcleanarchitecture.presentation.ui.adapter.BookAdapter
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.description.DescriptionScreen
import uz.gita.bookappcleanarchitecture.utils.replaceScreenAddToStack
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    @Inject
    lateinit var adapter:BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openDescriptionScreenLiveData.observe(this,openDescriptionScreenObserver)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.booksLiveData.observe(viewLifecycleOwner,booksObserver)
        viewModel.progressBarLiveData.observe(viewLifecycleOwner,progressBarObserver)
        viewModel.toastLiveData.observe(viewLifecycleOwner,toastObserver)

        binding.apply {
            recyclerHome.adapter = adapter
            recyclerHome.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }

        adapter.setOnItemClickListener {
            viewModel.itemClicked(it)
        }

    }

    private val booksObserver = Observer<List<BookData>> {
        adapter.submitList(it)
    }
    private val progressBarObserver = Observer<Boolean> {
        if (it){
            binding.loadingProgressBar.show()
        }else {
            binding.loadingProgressBar.hide()
        }
    }


    private val toastObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val openDescriptionScreenObserver = Observer<BookData> {
        val bundle = Bundle()
        bundle.putSerializable("book",it)
        val fragment = DescriptionScreen()
        fragment.arguments = bundle
        replaceScreenAddToStack(fragment)
//        findNavController().navigate(MainScreenDirections.actionMainScreenToDescriptionScreen(it))
    }

    private val refreshProgressBarObserver = Observer<Boolean> {
//        binding.swipeRefresh.isRefreshing = false
    }

}