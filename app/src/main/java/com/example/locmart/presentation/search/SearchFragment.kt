package com.example.locmart.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.databinding.FragmentSeaarchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment(){
    private lateinit var binding:FragmentSeaarchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private val args by navArgs<SearchFragmentArgs>()
    private val adapter by lazy { SearchProductsAdapter(this::onClick, this::liked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args.category?.let { viewModel.setCategory(it) }

        adapter.addLoadStateListener {
            viewModel.setLoadState(it)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeaarchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner){
          loadingLayout.root.isVisible = it
        }

        viewModel.products.observe(viewLifecycleOwner){
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    private fun initUI() = with(binding) {
        close.setOnClickListener {
            findNavController().popBackStack()
        }

        products.adapter = adapter
    }

    private fun onClick(product: Product){

    }

    private fun liked(product: Product){

    }
}