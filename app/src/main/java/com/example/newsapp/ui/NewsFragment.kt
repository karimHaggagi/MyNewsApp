package com.example.newsapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.newsapp.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: NewsAdapter
    private val viewModel: NewsViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = NewsAdapter {
            val action = NewsFragmentDirections.actionNewsFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter

        viewModel.article.observe(requireActivity(), Observer {
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        })

        binding.editText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                adapter.filter?.filter(s.toString())
            }
        })
        return binding.root
    }


}








