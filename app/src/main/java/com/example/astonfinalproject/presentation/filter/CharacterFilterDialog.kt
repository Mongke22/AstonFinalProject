package com.example.astonfinalproject.presentation.filter

import android.widget.RadioButton
import com.example.astonfinalproject.databinding.FilterCharactersBinding
import com.example.astonfinalproject.presentation.filter.model.CharacterFilter
import javax.inject.Inject

class CharacterFilterDialog @Inject constructor() : FilterDialog<FilterCharactersBinding>() {

    var initCharacterFilter: (() -> CharacterFilter)? = null

    var filter: CharacterFilter = CharacterFilter("", "", "")

    override fun getViewBinding(): FilterCharactersBinding {
        return FilterCharactersBinding.inflate(layoutInflater)
    }

    override fun initFilter() {
        filter =
            initCharacterFilter?.invoke() ?: throw RuntimeException("Определите запись фильтра")

        when (filter.gender) {
            "male" -> {
                binding.rbMale.isChecked = true
            }

            "female" -> {
                binding.rbFemale.isChecked = true
            }

            "genderless" -> {
                binding.rbGenderless.isChecked = true
            }

            "unknown" -> {
                binding.rbGenderUnknown.isChecked = true
            }

            "" -> {
                binding.rbGenderAll.isChecked = true
            }
        }
        when (filter.species) {
            "human" -> {
                binding.rbHuman.isChecked = true
            }
            "alien" -> {
                binding.rbAlien.isChecked = true
            }
            "unknown" -> {
                binding.rbSpeciesUnknown.isChecked = true
            }
            "" -> {
                binding.rbSpeciesAll.isChecked = true
            }
        }
        when (filter.status) {
            "alive" -> {
                binding.rbAlive.isChecked = true
            }
            "dead" -> {
                binding.rbDead.isChecked = true
            }
            "unknown" -> {
                binding.rbStatusUnknown.isChecked = true
            }
            "" -> {
                binding.rbStatusAll.isChecked = true
            }
        }
    }

    override fun filterLogic() {
        setupGenderRadioGroupListener()
        setupStatusRadioGroupListener()
        setupSpeciesRadioGroupListener()
    }

    private fun setupGenderRadioGroupListener() {
        binding.rgGender.setOnCheckedChangeListener { _, buttonId ->
            when (binding.rgGender.findViewById<RadioButton>(buttonId).text.toString()) {
                "all" -> {
                    filter.gender = ""
                }
                else -> {
                    filter.gender =
                        binding.rgGender.findViewById<RadioButton>(buttonId).text.toString()
                }
            }
        }
    }

    private fun setupSpeciesRadioGroupListener() {
        binding.rgSpecies.setOnCheckedChangeListener { _, buttonId ->
            when (binding.rgSpecies.findViewById<RadioButton>(buttonId).text.toString()) {
                "all" -> {
                    filter.species = ""
                }
                else -> {
                    filter.species =
                        binding.rgSpecies.findViewById<RadioButton>(buttonId).text.toString()
                }
            }
        }
    }

    private fun setupStatusRadioGroupListener() {
        binding.rgStatus.setOnCheckedChangeListener { _, buttonId ->
            when (binding.rgStatus.findViewById<RadioButton>(buttonId).text.toString()) {
                "all" -> {
                    filter.status = ""
                }
                else -> {
                    filter.status =
                        binding.rgStatus.findViewById<RadioButton>(buttonId).text.toString()
                }
            }
        }
    }
}