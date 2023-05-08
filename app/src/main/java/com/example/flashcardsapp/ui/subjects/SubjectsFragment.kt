package com.example.flashcardsapp.ui.subjects

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flashcardsapp.databinding.FragmentSubjectsBinding

class SubjectsFragment : Fragment() {

    private var _binding: FragmentSubjectsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val subjectsViewModel =
            ViewModelProvider(this)[SubjectsViewModel::class.java]

        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.subjectsList
        val listItems = arrayOf("Algebra", "Smooth Manifolds and Algebraic Topology", "Analysis")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter

        //val context = this
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = listItems[position]
            val detailIntent =
                ActivityFlashcardsSubjects.newIntent(requireContext(), "$selectedItem.xml")
            startActivity(detailIntent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}