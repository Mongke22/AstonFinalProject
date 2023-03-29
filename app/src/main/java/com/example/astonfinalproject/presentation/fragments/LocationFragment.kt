package com.example.astonfinalproject.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.example.astonfinalproject.databinding.FragmentLocationBinding
import com.example.astonfinalproject.domain.Model.LocationInfo
import com.example.astonfinalproject.presentation.viewModel.MainViewModel
import com.example.astonfinalproject.presentation.filter.LocationFilterDialog
import com.example.astonfinalproject.presentation.filter.model.LocationFilter
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
    private var filter = LocationFilter("", "")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNoDataTextView()
        setupRecyclerView()
        setupObserver()

    }

    private fun setupObserver(){
        viewModel.locationsList.observe(viewLifecycleOwner) { locations ->
            viewModel.hideFragmentIfNoAvailableData(locations.isNullOrEmpty())

            if (itemsList.size != locations.size) {
                itemsList = locations
                locationsListAdapter.submitList(locations)
            }
        }
        viewModel.stateList.observe(viewLifecycleOwner){
            for(state in it){
                if(state.screen == "locations" && state.dataIsReady){
                    binding.swipeRefreshLayoutLocations.isRefreshing = false
                }
            }
        }
        viewModel.filterLocation.observe(viewLifecycleOwner){ newFilter ->
            filter = newFilter
            applyFilter(binding.searchField.etSearch.text.toString())
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
        setupSwipeListener()
    }

    private fun setupSwipeListener(){
        binding.swipeRefreshLayoutLocations.setOnRefreshListener {
            viewModel.loadLocations()
        }
    }

    private fun setupOnClickListener(){
        locationsListAdapter.locationClickListener = {
            viewModel.moveToScreen(MainViewModel.Companion.Screen.LOCATION_DETAIL, it.id)
        }
        binding.searchField.ivFilter.setOnClickListener {
            val myDialogFragment = LocationFilterDialog()
            myDialogFragment.initLocationFilter = {
                viewModel.filterLocation.value ?: LocationFilter("", "")
            }
            myDialogFragment.onApplyFunc = {
                viewModel.setFilter(myDialogFragment.filter)
            }
            val manager = childFragmentManager
            myDialogFragment.show(manager, "dialog")
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
        var locationListToShow = itemsList.filter { location ->
            location.name.contains(text)
                    && location.type.contains(filter.type)
                    && location.dimension.contains(filter.dimension)
        }


        viewModel.hideFragmentIfNoAvailableData(locationListToShow.isEmpty())

        locationsListAdapter.submitList(locationListToShow)
    }
}
