package com.hugh.outsourcing.bank_acs.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugh.outsourcing.bank_acs.adapters.AssetsAdapter
import com.hugh.outsourcing.bank_acs.databinding.AssetFragmentBinding
import com.hugh.outsourcing.bank_acs.service.Asset
import com.hugh.outsourcing.bank_acs.vms.AssetViewModel

class AssetFragment(private val token: String, private val balance:Int) : Fragment() {

    companion object {
        fun newInstance(token:String,balance:Int) = AssetFragment(token, balance)
    }

    private lateinit var viewModel: AssetViewModel
    private var _binding: AssetFragmentBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AssetFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AssetViewModel::class.java]
        initialization()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapter(items:List<Asset>){
        binding.assetItems.adapter?.let { adapter ->
            (adapter as AssetsAdapter).items = items
            adapter.notifyDataSetChanged()
        }
    }
    private fun initialization() {
        context?.let {
            binding.assetItems.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = AssetsAdapter(viewModel.updateAssets(token) {})
                addItemDecoration(
                    DividerItemDecoration(
                        this.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
        binding.assetShow.text = "资产: $balance"
        viewModel.assets.observe(this.requireActivity(),{
            setAdapter(it)
        })
        binding.swiper.setOnRefreshListener {
            binding.swiper.isRefreshing = false
        }
    }
}