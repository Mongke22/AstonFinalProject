package com.example.astonfinalproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.astonfinalproject.presentation.viewModel.MainViewModel

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    companion object{
        const val ID = "id"
        const val UNDEFINED = -1
        const val UNKNOWN = ""
        lateinit var viewModel: MainViewModel
    }

    private var _binding: VB? = null
    val binding
        get() = _binding ?:throw RuntimeException("FragmentBinding is null")

    protected var noAvailableDataText: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        loadData()
        setUpOnBackPressed()
        setAvailableDataListener()
        return binding.root
    }

    protected open fun setUpOnBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.popBackStack()
                    viewModel.updateMenu()
                    //viewModel.navigator.moveToCharactersScreen(viewModel)
                }
            })
    }

    private fun setAvailableDataListener(){
        viewModel.dataAvailable.observe(viewLifecycleOwner){ dataAvailable ->
            if(dataAvailable){
                noAvailableDataText?.visibility = View.GONE
            }else{
                noAvailableDataText?.visibility = View.VISIBLE
            }

        }
    }


    protected abstract  fun loadData()

    protected abstract fun getViewBinding(): VB
}