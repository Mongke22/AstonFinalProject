package com.example.astonfinalproject.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentSingleLocationBinding
import com.example.astonfinalproject.presentation.MainViewModel


class SingleLocationFragment : BaseFragment<FragmentSingleLocationBinding>() {

    companion object{

        private const val ID = "id"
        private const val UNDEFINED = -1

        private lateinit var viewModel: MainViewModel

        fun newInstance(vm: MainViewModel, getId: Int): SingleLocationFragment{
           viewModel = vm
            return SingleLocationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, getId)
                }
            }
        }

    }

    override fun loadData() {
        TODO("Not yet implemented")
    }

    override fun getViewBinding(): FragmentSingleLocationBinding {
        TODO("Not yet implemented")
    }

}