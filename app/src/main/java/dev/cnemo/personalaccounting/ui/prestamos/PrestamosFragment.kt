package dev.cnemo.personalaccounting.ui.prestamos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.cnemo.personalaccounting.databinding.FragmentPrestamosBinding

class PrestamosFragment : Fragment() {

    private var _binding: FragmentPrestamosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val prestamosViewModel = ViewModelProvider(this)[PrestamosViewModel::class.java]

        _binding = FragmentPrestamosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}