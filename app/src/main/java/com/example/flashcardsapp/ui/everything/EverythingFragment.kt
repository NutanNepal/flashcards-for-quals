package com.example.flashcardsapp.ui.everything

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flashcardsapp.databinding.FragmentEverythingBinding

class EverythingFragment : Fragment() {

    private var _binding: FragmentEverythingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val everythingViewModel =
            ViewModelProvider(this)[EverythingViewModel::class.java]

        _binding = FragmentEverythingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEverything
        everythingViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}