package com.hugh.outsourcing.bank_acs.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugh.outsourcing.bank_acs.L
import com.hugh.outsourcing.bank_acs.MainActivity
import com.hugh.outsourcing.bank_acs.adapters.ProductsAdapter
import com.hugh.outsourcing.bank_acs.databinding.HomeFragmentBinding
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.vms.HomeViewModel

class HomeFragment(private val token: String) : Fragment() {
    companion object {
        fun newInstance(token:String): HomeFragment {
            return HomeFragment(token)
        }
        const val Tag = "HomeFragment"
    }

    private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        L.e("HomeFragment","Create View!")
        _binding = HomeFragmentBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        initialization()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateProducts(token){
            L.d(MainActivity.tag, "onResume reload products")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapterData(products:List<Product>){
        binding.items.adapter?.let { adapter ->
            (adapter as ProductsAdapter).items = products
            adapter.notifyDataSetChanged()
        }
    }
    private fun initialization(){
        L.d(Tag, "load data to recycler")
        context?.let {
            binding.items.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = ProductsAdapter(token, listOf())
                addItemDecoration(
                    DividerItemDecoration(this.context,
                        DividerItemDecoration.VERTICAL)
                )
            }
        }
        viewModel.products.observe(this.requireActivity(), {
            setAdapterData(it)
        })
        binding.swiper.setOnRefreshListener {
            viewModel.updateProducts(token) {
                L.d(MainActivity.tag, "in HomeFragment Force to Update Products:$it")
            }
            binding.swiper.isRefreshing = false
        }
    }
}