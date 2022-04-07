package com.hugh.outsourcing.bank_acs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugh.outsourcing.bank_acs.databinding.HomeFragmentBinding
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.vms.HomeViewModel

class HomeFragment(private val items:LiveData<List<Product>>) : Fragment() {
    companion object {
        fun newInstance(args:LiveData<List<Product>>):HomeFragment{
            return HomeFragment(args)
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
        _binding = HomeFragmentBinding.inflate(layoutInflater,container, false)
        initialization()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        // TODO: Use the ViewModel
    }
    private fun setAdapterData(products:List<Product>){
        binding.items.adapter?.let { adapter ->
            (adapter as ProductsAdapter).items = products
            adapter.notifyDataSetChanged()
        }
    }
    private fun initialization(){
        L.d(Tag,"load data to recycler")
        context?.let {
            binding.items.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = ProductsAdapter((activity as MainActivity).getToken(), listOf())
                addItemDecoration(
                    DividerItemDecoration(this.context,
                        DividerItemDecoration.VERTICAL)
                )
            }
        }
        items.observe(this.requireActivity(), {
            setAdapterData(it)
        })
        binding.swiper.setOnRefreshListener {
            binding.swiper.isRefreshing = false
        }
    }
}