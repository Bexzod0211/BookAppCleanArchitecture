package uz.gita.bookappcleanarchitecture.presentation.ui.screens.read

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookappcleanarchitecture.R
import uz.gita.bookappcleanarchitecture.data.model.BookData
import uz.gita.bookappcleanarchitecture.databinding.ScreenReadBinding
import uz.gita.bookappcleanarchitecture.utils.myLog
import java.io.File

@AndroidEntryPoint
class ReadScreen : Fragment(R.layout.screen_read) {
    private val binding:ScreenReadBinding by viewBinding()
    private lateinit var book:BookData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        book = arguments?.getSerializable("book") as BookData

        val file = File(requireContext().filesDir,book.title)

        if (file.exists()) {
            binding.pdfView
                .fromFile(file)
                .enableSwipe(true)
                .defaultPage(0)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .spacing(0)
                .load()
        } else {
            myLog("File not exists!")
        }

    }

}