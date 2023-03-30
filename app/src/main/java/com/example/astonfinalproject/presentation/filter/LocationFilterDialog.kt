package com.example.astonfinalproject.presentation.filter

import android.widget.RadioButton
import com.example.astonfinalproject.databinding.FilterLocationsBinding
import com.example.astonfinalproject.presentation.filter.model.CharacterFilter
import com.example.astonfinalproject.presentation.filter.model.LocationFilter
import javax.inject.Inject

class LocationFilterDialog @Inject constructor() : FilterDialog<FilterLocationsBinding>() {

    var initLocationFilter: (() -> LocationFilter)? = null

    var filter = LocationFilter("", "")


    override fun getViewBinding(): FilterLocationsBinding {
        return FilterLocationsBinding.inflate(layoutInflater)
    }

    override fun initFilter() {
        filter = initLocationFilter?.invoke() ?: throw RuntimeException("Определите запись фильтра")

        when (filter.type) {
            "planet" -> {
                binding.rbPlanet.isChecked = true
            }

            "cluster" -> {
                binding.rbCluster.isChecked = true
            }

            "space station" -> {
                binding.rbStation.isChecked = true
            }
            "microverse" -> {
                binding.rbTypeMicroverse.isChecked = true
            }

            "resort" -> {
                binding.rbResort.isChecked = true
            }

            "dream" -> {
                binding.rbDream.isChecked = true
            }


            "unknown" -> {
                binding.rbTypeUnknown.isChecked = true
            }

            "" -> {
                binding.rbTypeAll.isChecked = true
            }
        }
        when (filter.dimension) {
            "cronenberged dimension" -> {
                binding.rbCronenberged.isChecked = true
            }

            "dimension c-137" -> {
                binding.rbC137.isChecked = true
            }

            "replacement dimension" -> {
                binding.rbReplacement.isChecked = true
            }
            "dimension c-131" -> {
                binding.rbc131.isChecked = true
            }

            "multiverse" -> {
                binding.rbMultiverse.isChecked = true
            }
            "unknown" -> {
                binding.rbDimensionUnknown.isChecked = true
            }

            "" -> {
                binding.rbDimensionAll.isChecked = true
            }
        }
    }

    override fun filterLogic() {
        setupDimensionRadioGroupListener()
        setupTypeRadioGroupListener()
    }

    private fun setupTypeRadioGroupListener() {
        binding.rgType.setOnCheckedChangeListener { _, buttonId ->
            when (binding.rgType.findViewById<RadioButton>(buttonId).text.toString()) {
                "all" -> filter.type = ""
                else -> {
                    filter.type = binding.rgType.findViewById<RadioButton>(buttonId).text.toString()
                }
            }
        }
    }

    private fun setupDimensionRadioGroupListener() {
        binding.rgDimension.setOnCheckedChangeListener { _, buttonId ->
            when (binding.rgDimension.findViewById<RadioButton>(buttonId).text.toString()) {
                "all" -> filter.dimension = ""
                else -> {
                    filter.dimension =
                        binding.rgDimension.findViewById<RadioButton>(buttonId).text.toString()
                }
            }
        }
    }
}