package com.hugh.outsourcing.bank_acs.fragments

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.hugh.outsourcing.bank_acs.AuthActivity
import com.hugh.outsourcing.bank_acs.databinding.UserFragmentBinding
import com.hugh.outsourcing.bank_acs.service.User
import com.hugh.outsourcing.bank_acs.vms.UserViewModel

class UserFragment(private val person: User) : Fragment() {

    companion object {
        fun newInstance(person: User) = UserFragment(person)
    }

    private lateinit var viewModel: UserViewModel
    private var _binding: UserFragmentBinding? = null
    private val binding get() = _binding!!

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
            if(result.resultCode== Activity.RESULT_OK){
                result.data?.let {
                    person.validity = it.getStringExtra("id").toString()
                    setRealInfo()
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserFragmentBinding.inflate(layoutInflater,container,false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        initialization()
        return binding.root
    }

    private fun initialization(){
        infoLoad()
    }
    private fun infoLoad(){
        binding.name.text = person.name // last 4 number
        setRealInfo()
        binding.auth.setOnClickListener {
            launcher.launch(Intent(context, AuthActivity::class.java))
        }
    }

    private fun setRealInfo(){
        if (person.validity.isEmpty()){
            binding.auth.text = "尚未实名认证 点击可添加实名信息"
        }else{
            binding.auth.text = "已实名\n${person.validity}到期(点击可修改)"
        }
    }
}