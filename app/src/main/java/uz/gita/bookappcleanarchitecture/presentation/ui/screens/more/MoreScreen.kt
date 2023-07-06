package uz.gita.bookappcleanarchitecture.presentation.ui.screens.more

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.databinding.ScreenMoreBinding
import uz.gita.bookappcleanarchitecture.presentation.ui.adapter.SavedAdapter
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.MoreViewModel
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls.MoreViewModelImpl

@AndroidEntryPoint
class MoreScreen : Fragment(R.layout.screen_more){
    private val binding by viewBinding(ScreenMoreBinding::bind)
    private val viewModel:MoreViewModel by viewModels<MoreViewModelImpl>()
    private val adapter by lazy { SavedAdapter() }
    private val args:MoreScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val name = args.name

        viewModel.loadBooksBy(name)

        viewModel.booksLiveData.observe(viewLifecycleOwner,booksObserver)
        viewModel.progressBarLiveData.observe(viewLifecycleOwner,progressBarObserver)
        viewModel.messageLiveData.observe(viewLifecycleOwner,messageObserver)
        viewModel.placeHolderVisibilityLiveData.observe(viewLifecycleOwner,placeHolderVisibilityObserver)

        adapter.setOnItemClickListener {
            viewModel.itemClicked(it)
        }

        binding.apply {
            txtAppBar.text = if (name == "recommendation"){
              "Our Recommendation"
            }else {
                name
            }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            btnBack.setOnClickListener {
                viewModel.btnBackClicked()
            }

            searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.onQueryChanged(query,args.name)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.onQueryChanged(newText,args.name)
                    return true
                }

            })
        }
    }

    private val placeHolderVisibilityObserver = Observer<Int> {
        binding.searchPlaceHolder.visibility = it
    }

    private val booksObserver = Observer<List<BookData>>{
        adapter.submitList(it)
    }

    private val progressBarObserver = Observer<Boolean> {
        if (it)
            binding.loadingProgressBar.show()
        else binding.loadingProgressBar.hide()
    }

    private val messageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
}