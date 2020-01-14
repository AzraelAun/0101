package com.example.menu


import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.menu.databinding.FragmentFailPracticalBinding

/**
 * A simple [Fragment] subclass.
 */
class FailPractical : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFailPracticalBinding>(inflater,
            R.layout.fragment_fail_practical,container,false)

        val lose = MediaPlayer.create(activity, R.raw.youlose)
        lose.start()

        binding.button.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_failPractical_to_practical)}

            return binding.root
    }


}
