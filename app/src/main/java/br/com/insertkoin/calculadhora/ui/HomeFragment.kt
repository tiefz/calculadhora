package br.com.insertkoin.calculadhora.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import br.com.insertkoin.calculadhora.R
import br.com.insertkoin.calculadhora.databinding.FragmentHomeBinding
import br.com.insertkoin.calculadhora.viewmodel.CalcViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: CalcViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(CalcViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.mcvExitCalc.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCalculeTimeFragment()
            )
        }

        return binding.root
    }
}