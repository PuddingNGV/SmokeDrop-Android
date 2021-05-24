package com.example.smokedrop.time

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smokedrop.R
import com.example.smokedrop.databinding.FragmentTimeBinding


class TimeFragment : Fragment() {

    private lateinit var binding: FragmentTimeBinding
    private lateinit var viewModel: ViewModelTime

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_time,
            container,
            false
        )
        Log.i("TimeFragment", "Called ViewModelProvider.get")

        viewModel = ViewModelProvider(this).get(ViewModelTime::class.java)

        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.viewModelTime = viewModel

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val textTime = requireView().findViewById(R.id.textTime) as TextView
        val textTimeLast = requireView().findViewById(R.id.textTimeLast) as TextView
        val buttonStartTime = requireView().findViewById(R.id.circle_time) as RelativeLayout
        val textSmokeCount = requireView().findViewById(R.id.textCountLeft) as TextView
        viewModel.timeNowLD.observe(viewLifecycleOwner, {
            textTimeLast.text = it
        })

    }


}






