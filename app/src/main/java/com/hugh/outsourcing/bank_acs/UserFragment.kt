package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hugh.outsourcing.bank_acs.databinding.UserFragmentBinding
import com.hugh.outsourcing.bank_acs.vms.UserViewModel

class UserFragment(val person: Person) : Fragment() {

    companion object {
        fun newInstance(person: Person) = UserFragment(person)
        const val tag = "UserFragment"
        const val GET_ID_DUE = 1
    }

    private lateinit var viewModel: UserViewModel

    private var _binding: UserFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserFragmentBinding.inflate(layoutInflater,container,false)
        initialization()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }
    private fun initialization(){
        infoLoad()
    }
    private fun infoLoad(){
        binding.name.text = person.name // last 4 number
        setRealInfo()
        binding.auth.setOnClickListener {
            startActivityForResult(Intent(context,AuthActivity::class.java), GET_ID_DUE)
        }
    }

    private fun setRealInfo(){
        if (person.realName.isEmpty()){
            binding.auth.text = "尚未实名认证 点击可添加实名信息"
        }else{
            binding.auth.text = "已实名\n${person.realName}到期(点击可修改)"
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GET_ID_DUE ->{
                if(resultCode== Activity.RESULT_OK){
                    data?.let {
                        person.realName = it.getStringExtra("id").toString()
                        setRealInfo()
                    }
                }
            }
        }
    }
}