package com.example.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.menu.GlobalVariable.userID
import com.example.menu.databinding.FragmentEditProfileBinding
import kotlinx.android.synthetic.main.fragment_edit_profile.*

/**
 * A simple [Fragment] subclass.
 */
class EditProfile : Fragment() {

    lateinit var handler: AccDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentEditProfileBinding>(inflater,
            R.layout.fragment_edit_profile,container,false)

        handler = AccDatabase(binding.root.context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var data = handler.retrieveData(userID)
        for(i in 0..(data.size-1)){
            editusername.append(data.get(i).username)
            editemail.append(data.get(i).email)
            editaddress.append(data.get(i).address)
        }

        edit_button.setOnClickListener {
            if (editemail.text.toString().isNotEmpty()) {
                if (editaddress.text.toString().isNotEmpty()) {
                    handler.editProfile(userID, editemail.text.toString(), editaddress.text.toString())
                    Toast.makeText(activity, getString(R.string.EProfile_Successful), Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(activity, getString(R.string.Address_cannot_empty), Toast.LENGTH_SHORT).show()
                }
            } else
                Toast.makeText(activity, getString(R.string.Email_cannot_empty), Toast.LENGTH_SHORT).show()
        }
    }


}
