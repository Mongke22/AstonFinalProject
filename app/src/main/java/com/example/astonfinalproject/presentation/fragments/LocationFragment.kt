package com.example.astonfinalproject.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.example.astonfinalproject.databinding.FragmentLocationBinding
import com.example.astonfinalproject.domain.Model.LocationInfo
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.LocationsListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    companion object {
        fun newInstance(vm: MainViewModel): LocationFragment {
            viewModel = vm
            return LocationFragment()
        }
    }

    private lateinit var locationsListAdapter: LocationsListAdapter
    private var itemsList: List<LocationInfo> = listOf()
    private val editTextSubject = PublishSubject.create<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNoDataTextView()
        setupRecyclerView()
        viewModel.locationsList.observe(viewLifecycleOwner) { locations ->
            viewModel.hideFragmentIfNoAvailableData(locations.isNullOrEmpty())

            if (itemsList.size != locations.size) {
                itemsList = locations
                locationsListAdapter.submitList(locations)
            }
        }
    }

    private fun setNoDataTextView(){
        noAvailableDataText = binding.tvNoData
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
        setupOnClickListener()
        setupTextChangedListener()
    }

    private fun setupOnClickListener(){
        locationsListAdapter.locationClickListener = {
            viewModel.moveToScreen(MainViewModel.Companion.Screen.LOCATION_DETAIL, it.id)
        }
    }


    @SuppressLint("CheckResult")
    private fun setupTextChangedListener() {
        binding.searchField.etSearch.doOnTextChanged { text, _, _, _ ->
            editTextSubject.onNext(text.toString())
        }
        editTextSubject.debounce(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                applyFilter(it)
            }
    }

    private fun applyFilter(text: String){
        var locationListToShow: ArrayList<LocationInfo> = ArrayList()
        locationListToShow.clear()
        for (location in itemsList) {
            if (location.name.contains(text)) {
                locationListToShow.add(location)
            }
        }
        viewModel.hideFragmentIfNoAvailableData(locationListToShow.isEmpty())

        locationsListAdapter.submitList(locationListToShow)
    }
}
