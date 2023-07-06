package uz.gita.bookappcleanarchitecture.presentation.ui.screens.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.bookappcleanarchitecture.BuildConfig
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.databinding.ScreenHomeBinding
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.HomeViewModel
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls.HomeViewModelImpl
import uz.gita.bookappcleanarchitecture.presentation.ui.adapter.BookAdapter
import uz.gita.bookappcleanarchitecture.presentation.ui.adapter.SavedAdapter
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.description.DescriptionScreen
import uz.gita.bookappcleanarchitecture.utils.replaceScreenAddToStack
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    @Inject
    lateinit var adapter:SavedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openDescriptionScreenLiveData.observe(this,openDescriptionScreenObserver)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.booksLiveData.observe(viewLifecycleOwner,booksObserver)
        viewModel.progressBarLiveData.observe(viewLifecycleOwner,progressBarObserver)
        viewModel.toastLiveData.observe(viewLifecycleOwner,toastObserver)
        viewModel.placeHolderVisibilityLiveData.observe(viewLifecycleOwner,placeHolderVisibilityObserver)
        viewModel.openShareMenuLiveData.observe(viewLifecycleOwner,openShareMenuObserver)
        viewModel.lastReadBookLiveData.observe(viewLifecycleOwner,lastReadBookObserver)
        viewModel.recentViewVisibilityLiveData.observe(viewLifecycleOwner,recentViewVisibilityObserver)

        binding.apply {
            recyclerHome.adapter = adapter
            recyclerHome.layoutManager = GridLayoutManager(requireContext(),2)
           /* viewOpenMore.setOnClickListener {
                viewModel.moreClickedBy("recommendation")
            }*/
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.onQueryChanged(query?:"")
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    lifecycleScope.launch {
                        delay(500)
                        viewModel.onQueryChanged(newText)
                    }
                    return true
                }

            })

        }

        adapter.setOnItemClickListener {
            viewModel.itemClicked(it)
        }
        attachClickListeners()

    }
    private fun attachClickListeners(){
        binding.apply {
            btnShare.setOnClickListener {
                viewModel.btnShareClicked()
            }
            binding.innerView2.setOnClickListener {
                viewModel.openReadScreen()
            }
        }
    }

    private val lastReadBookObserver = Observer<BookData> {
        binding.apply {
            Glide
                .with(requireContext())
                .load(it.coverUrl)
                .into(imageBook)
            txtAuthor.text = it.author
            txtTitle.text = it.title
        }
    }

    private val recentViewVisibilityObserver = Observer<Int> {
        binding.innerView2.visibility = it
    }

    private val openShareMenuObserver = Observer<Unit> {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
        val shareMessage = "Download E-Books app there are a lot of books, you can download and read them\nhttps://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private val placeHolderVisibilityObserver = Observer<Int> {
        binding.searchPlaceHolder.visibility = it
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