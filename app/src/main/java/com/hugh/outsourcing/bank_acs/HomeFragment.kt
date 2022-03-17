package com.hugh.outsourcing.bank_acs

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hugh.outsourcing.bank_acs.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
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

    override fun onResume() {
        super.onResume()
        checkLoginStatus()
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

    private fun initialization(){
        context?.let { binding.items.dataFetch(it) }
        checkLoginStatus()
        binding.loginButton.setOnClickListener {
            startActivity(Intent(context,LoginActivity::class.java))
        }
    }
    private fun checkLoginStatus(){
        if(Info.status!=Info.LoginStatus.NO){
            binding.loginButton.visibility= View.INVISIBLE
        }
    }
}