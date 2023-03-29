package com.example.astonfinalproject.presentation.filter

import android.icu.util.Calendar
import com.example.astonfinalproject.databinding.FilterEpisodesBinding
import com.example.astonfinalproject.presentation.filter.model.CharacterFilter
import com.example.astonfinalproject.presentation.filter.model.EpisodeFilter
import javax.inject.Inject

class EpisodeFilterDialog @Inject constructor(): FilterDialog<FilterEpisodesBinding>() {

    var initEpisodeFilter: (() -> EpisodeFilter)? = null

    var filter = EpisodeFilter("","")

    override fun getViewBinding(): FilterEpisodesBinding {
        return FilterEpisodesBinding.inflate(layoutInflater)
    }

    override fun initFilter() {
        filter = initEpisodeFilter?.invoke() ?: throw RuntimeException("Определите запись фильтра")

        binding.npSeasonNumber.minValue = 1
        binding.npSeasonNumber.maxValue = 10

        binding.npYearsNumber.minValue = 2013
        binding.npYearsNumber.maxValue = Calendar.getInstance().get(Calendar.YEAR)

        if(filter.seasonNumber == ""){
            binding.cbSeasonsAll.isChecked = true
        }else{
            binding.npSeasonNumber.value = filter.seasonNumber.toInt()
        }

        if(filter.yearNumber == ""){
            binding.cbYearsAll.isChecked = true
        }
        else{
            binding.npYearsNumber.value = filter.yearNumber.toInt()
        }
    }

    override fun filterLogic() {
        setupYearsCheckBoxListener()
        setupSeasonsCheckBoxListener()
        setupYearsNumberPickerListener()
        setupSeasonsNumberPickerListener()
    }

    private fun setupSeasonsCheckBoxListener(){
        binding.cbSeasonsAll.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                filter.seasonNumber = ""
            }else{
                filter.seasonNumber = binding.npSeasonNumber.value.toString()
            }
        }
    }

    private fun setupYearsCheckBoxListener(){
        binding.cbYearsAll.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                filter.yearNumber = ""
            }else{
                filter.yearNumber = binding.npYearsNumber.value.toString()
            }
        }
    }

    private fun setupYearsNumberPickerListener(){
        binding.npYearsNumber.setOnValueChangedListener{ picker, oldVal, newVal ->
            if(!binding.cbYearsAll.isChecked){
                filter.yearNumber = newVal.toString()
            }
        }
    }
    private fun setupSeasonsNumberPickerListener(){
        binding.npSeasonNumber.setOnValueChangedListener{ picker, oldVal, newVal ->
            if(!binding.cbSeasonsAll.isChecked){
                filter.seasonNumber = newVal.toString()
            }
        }
    }

}