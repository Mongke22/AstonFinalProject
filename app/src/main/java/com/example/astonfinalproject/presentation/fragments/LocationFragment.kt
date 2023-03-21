package com.example.astonfinalproject.presentation.fragments

import android.os.Bundle
import android.view.View
import com.example.astonfinalproject.databinding.FragmentLocationBinding
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.LocationsListAdapter


class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    companion object {

        private lateinit var viewModel: MainViewModel

        fun newInstance(vm: MainViewModel): LocationFragment {
            viewModel = vm

            return LocationFragment()
        }
    }

    private lateinit var locationsListAdapter: LocationsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.locationsList.observe(viewLifecycleOwner) { locations ->
            //TODO(добавить текст о том, что нет данных)
            locationsListAdapter.submitList(locations)
        }
    }

    override fun loadData() {
        viewModel.loadLocations()
    }

    override fun getViewBinding(): FragmentLocationBinding {
        return FragmentLocationBinding.inflate(layoutInflater)
    }

    private fun setupRecyclerView() {
        val rvLocationsList = binding.rvLocations
        with(rvLocationsList) {
            locationsListAdapter = LocationsListAdapter()
            adapter = locationsListAdapter
        }
        setupListeners()
    }

    private fun setupListeners() {
        //setupOnClickListener()
    }
}
