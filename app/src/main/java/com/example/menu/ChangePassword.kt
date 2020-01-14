package com.example.menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.menu.GlobalVariable.LoginPassword
import com.example.menu.GlobalVariable.userID
import com.example.menu.databinding.FragmentChangePasswordBinding
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePassword : Fragment() {

    lateinit var handler: AccDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentChangePasswordBinding>(inflater,
            R.layout.fragment_change_password,container,false)

        handler = AccDatabase(binding.root.context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editpass_button.setOnClickListener {
            if (editcurrentpass.text.toString() == LoginPassword) {
                if (editpass.text.toString() == editpassword.text.toString()) {
                    handler.changePass(userID, editpass.text.toString())
                    Toast.makeText(activity, getString(R.string.ChangePass_Successful), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, getString(R.string.RPassw_Incorrect), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, getString(R.string.OPassw_Incorrect), Toast.LENGTH_SHORT).show()
            }
        }
    }

}
