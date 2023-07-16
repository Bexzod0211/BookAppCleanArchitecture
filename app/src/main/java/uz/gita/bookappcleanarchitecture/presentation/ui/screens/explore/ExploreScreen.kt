package uz.gita.bookappcleanarchitecture.presentation.ui.screens.explore

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
import uz.gita.bookappcleanarchitecture.data.model.CategoryData
import uz.gita.bookappcleanarchitecture.databinding.ScreenExploreBinding
import uz.gita.bookappcleanarchitecture.presentation.ui.adapter.CategoryAdapter
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.description.DescriptionScreen
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.ExploreViewModel
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls.ExploreViewModelImpl
import uz.gita.bookappcleanarchitecture.utils.replaceScreenAddToStack
import javax.inject.Inject

@AndroidEntryPoint
class ExploreScreen : Fragment(R.layout.screen_explore) {
    private val binding by viewBinding(ScreenExploreBinding::bind)
    private val viewModel: ExploreViewModel by viewModels<ExploreViewModelImpl>()
    @Inject
    lateinit var adapter:CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.openDescriptionScreenLiveData.observe(this, openDescriptionScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.allBooksLiveData.observe(viewLifecycleOwner, allBooksObserver)
        viewModel.progressBarLiveData.observe(viewLifecycleOwner, progressBarObserver)
        viewModel.messageLiveData.observe(viewLifecycleOwner, messageObserver)

        binding.apply {
            recyclerExplore.adapter = adapter
            recyclerExplore.layoutManager = LinearLayoutManager(requireContext())
        }

        adapter.setOnItemClickListener {
            viewModel.itemClicked(it)
        }

        adapter.setOnMoreBtnClicked {
            viewModel.openMoreScreen(it)
        }
    }

    private val allBooksObserver = Observer<List<CategoryData>> {
        adapter.submitList(it)
    }

    private val progressBarObserver = Observer<Boolean> {
        if (it) {
            binding.loadingProgressBar.show()
        } else {
            binding.loadingProgressBar.hide()
        }
    }

    private val messageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    /*private val openDescriptionScreenObserver = Observer<BookData> {
        val bundle = Bundle()
        bundle.putSerializable("book",it)
        val fragment = DescriptionScreen()
        fragment.arguments = bundle
        replaceScreenAddToStack(fragment)
    }*/

}
