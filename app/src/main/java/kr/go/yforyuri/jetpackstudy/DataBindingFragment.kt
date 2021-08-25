package kr.go.yforyuri.jetpackstudy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.go.yforyuri.jetpackstudy.databinding.FragmentDataBindingBinding

class DataBindingFragment : Fragment() {

    private lateinit var binding: FragmentDataBindingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_binding, container, false)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner

            return root
        }
    }
}