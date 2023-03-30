package com.example.astonfinalproject.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.astonfinalproject.databinding.FragmentSingleLocationBinding
import com.example.astonfinalproject.domain.Model.LocationInfo
import com.example.astonfinalproject.presentation.AstonApp
import com.example.astonfinalproject.presentation.viewModel.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.CharactersListAdapter
import javax.inject.Inject


class SingleLocationFragment : BaseFragment<FragmentSingleLocationBinding>() {

    companion object {

        private const val LOCATION = "location"

        fun newInstance(vm: MainViewModel, getId: Int): SingleLocationFragment {
            viewModel = vm
            return SingleLocationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, getId)
                }
            }
        }

        fun newInstance(vm: MainViewModel, placeName: String): SingleLocationFragment {
            viewModel = vm
            return SingleLocationFragment().apply {
                arguments = Bundle().apply {
                    putString(LOCATION, placeName)
                }
            }
        }

    }

    @Inject
    lateinit var characterListAdapter: CharactersListAdapter

    private var locationId = UNDEFINED
    private var locationName = UNKNOWN
    private lateinit var location: LocationInfo

    private val component by lazy {
        (requireActivity().application as AstonApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun loadData() {
        parseParams()
        if (locationId != UNDEFINED)
            viewModel.loadLocation(locationId)
    }

    private fun parseParams() {
        val args = arguments
        if (args == null) {
            Toast.makeText(requireContext(), "no params", Toast.LENGTH_SHORT).show()
        } else {
            if (args.containsKey(ID)) {
                locationId = args.getInt(ID)
            }
            if (args.containsKey(LOCATION)) {
                locationName = args.getString(LOCATION) ?: ""
            }
        }
    }

    override fun getViewBinding(): FragmentSingleLocationBinding {
        return FragmentSingleLocationBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getLocation(locationId, locationName)
        viewModel.locationCharacterList.observe(viewLifecycleOwner) {
            characterListAdapter.submitList(it)
        }
        viewModel.locationInfo.observe(viewLifecycleOwner) {
            location = it
            setupInfo()
        }
    }

    private fun setupRecyclerView() {
        val rvEpisodesList = binding.rvSingleEpisodeCharacters
        with(rvEpisodesList) {
            characterListAdapter = CharactersListAdapter()
            adapter = characterListAdapter
        }
        setupListeners()
    }

    private fun setupListeners() {
        characterListAdapter.characterClickListener = {
            viewModel.moveToScreen(MainViewModel.Companion.Screen.CHARACTER_DETAIL, it.id)
        }
        characterListAdapter.characterSavePictureFunc = { id, path ->
            viewModel.updateImagePath(id, path)
        }
    }

    private fun setupInfo() {
        with(binding) {
            tvType.text = location.type
            tvDimension.text = location.dimension
            tvLocationName.text = location.name
        }

    }

}