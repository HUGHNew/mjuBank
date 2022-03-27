package com.hugh.outsourcing.bank_acs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugh.outsourcing.bank_acs.databinding.AssetFragmentBinding
import com.hugh.outsourcing.bank_acs.vms.AssetViewModel

class AssetFragment(private val assets:List<Pair<String,String>>) : Fragment() {

    companion object {
        fun newInstance(items:List<Pair<String,String>>) = AssetFragment(items)
    }

    private lateinit var viewModel: AssetViewModel
    private var _binding: AssetFragmentBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AssetFragmentBinding.inflate(layoutInflater,container,false)
        initialization()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AssetViewModel::class.java]
        // TODO: Use the ViewModel
    }
    private fun initialization(){
        context?.let {
            binding.assetItems.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = ListItemAdapter(assets)
            }
        }
    }

}