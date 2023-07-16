package uz.gita.bookappcleanarchitecture.presentation.ui.screens.description

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.databinding.ScreenDescriptionBinding
import uz.gita.bookappcleanarchitecture.presentation.ui.screens.read.ReadScreen
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.DescriptionViewModel
import uz.gita.bookappcleanarchitecture.presentation.ui.viewmodels.impls.DescriptionViewModelImpl
import uz.gita.bookappcleanarchitecture.utils.replaceScreenAddToStack
import java.io.File

@AndroidEntryPoint
class DescriptionScreen : Fragment(R.layout.screen_description) {
    private val binding by viewBinding(ScreenDescriptionBinding::bind)
    private val viewModel:DescriptionViewModel by viewModels<DescriptionViewModelImpl>()
    private lateinit var book:BookData
    private val args:DescriptionScreenArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.openScreenReadLiveData.observe(this,openScreenReadObserver)
        viewModel.changeImgResLiveData.observe(this,changeImgResObserver)
        viewModel.messageLiveData.observe(this,messageObserver)
        viewModel.enablingDownloadBtnLiveData.observe(this,enablingDownloadBtnObserver)
        viewModel.enablingReadBtnLiveData.observe(this,enablingReadBtnObserver)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        book = args.book


        if (File(requireContext().filesDir,book.title).exists()){
            binding.btnDownload.setImageResource(R.drawable.ic_downloaded)
            binding.btnContinueReading.isEnabled = true
            binding.btnDownload.isEnabled = false
        }

        binding.apply {
            Glide.with(requireContext())
                .load(book.coverUrl)
                .into(imgCover)
            txtStar.text = "${book.rate}"
            txtTitle.text = book.title
            txtAuthor.text = "By ${book.author}"
        }

        attachClickListeners()
    }



    private val changeImgResObserver = Observer<Int>{
        binding.btnDownload.setImageResource(it)
    }

    private val messageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val enablingDownloadBtnObserver = Observer<Boolean> {
        binding.btnDownload.isEnabled = it
    }

   /* private val openScreenReadObserver = Observer<BookData> {
        val bundle = Bundle()
        bundle.putSerializable("book",it)
        val fragment = ReadScreen()
        fragment.arguments = bundle
        replaceScreenAddToStack(fragment)
    }*/

    private val enablingReadBtnObserver = Observer<Boolean> {
        binding.btnContinueReading.isEnabled = it
    }

    private fun attachClickListeners(){
        binding.apply {
            btnDownload.setOnClickListener {
                viewModel.btnDownLoadClicked(requireContext(),book)
            }
            btnContinueReading.setOnClickListener {
                viewModel.btnReadClicked(book)
            }
            btnBack.setOnClickListener {
                viewModel.btnBackClicked()
            }
        }
    }

}