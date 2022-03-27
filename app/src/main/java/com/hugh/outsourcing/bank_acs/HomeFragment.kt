package com.hugh.outsourcing.bank_acs

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugh.outsourcing.bank_acs.databinding.HomeFragmentBinding
import com.hugh.outsourcing.bank_acs.vms.HomeViewModel

class HomeFragment(private val items:List<Pair<String,String>>) : Fragment() {
    companion object {
        fun newInstance(args:List<Pair<String,String>>):HomeFragment{
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
        L.d(Tag,"load data to recycler")
        context?.let {
            binding.items.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = ListItemAdapter(items)
            }
        }
        checkLoginStatus()
        binding.loginButton.setOnClickListener {
            startActivity(Intent(context,LoginActivity::class.java))
        }
    }
    private fun checkLoginStatus(){
        binding.loginButton.visibility= View.INVISIBLE
    }
}