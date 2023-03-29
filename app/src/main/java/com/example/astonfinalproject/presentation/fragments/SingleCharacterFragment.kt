package com.example.astonfinalproject.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharacterBinding
import com.example.astonfinalproject.databinding.FragmentSingleCharacterBinding
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.CharactersListAdapter
import com.example.astonfinalproject.presentation.recyclerView.adapters.EpisodesListAdapter


class SingleCharacterFragment : BaseFragment<FragmentSingleCharacterBinding>() {

    companion object {

        fun newInstance(vm: MainViewModel, getId: Int): SingleCharacterFragment {
            viewModel = vm
            return SingleCharacterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, getId)
                }
            }
        }
    }

    private lateinit var episodesListAdapter: EpisodesListAdapter
    private var characterId = UNDEFINED
    private lateinit var character: CharacterInfo

    override fun loadData() {
        parseParams()
        viewModel.loadCharacter(characterId)
    }

    private fun parseParams() {
        val args = arguments
        if (args == null) {
            Toast.makeText(requireContext(), "no params", Toast.LENGTH_SHORT).show()
        } else {
            if (args.containsKey(ID)) {
                characterId = args.getInt(ID)
            }
        }
    }

    override fun getViewBinding(): FragmentSingleCharacterBinding {
        return FragmentSingleCharacterBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getCharacter(characterId)
        viewModel.characterInfo.observe(viewLifecycleOwner) {
            character = it
            setupInfo()
        }
        viewModel.characterEpisodeList.observe(viewLifecycleOwner) {

            episodesListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        val rvEpisodesList = binding.rvCharacterEpisodesInside
        with(rvEpisodesList) {
            episodesListAdapter = EpisodesListAdapter()
            adapter = episodesListAdapter
        }
        setupListeners()
    }

    private fun setupListeners() {
        episodesListAdapter.episodeClickListener = {
            viewModel.moveToScreen(MainViewModel.Companion.Screen.EPISODE_DETAIL, it.id)
        }
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.llOrigin.setOnClickListener {
            val placeName = binding.tvCharacterOriginInside.text.toString()
            if (placeName != "unknown")
                viewModel.moveToScreen(MainViewModel.Companion.Screen.LOCATION_DETAIL,
                    place = placeName)
        }

        binding.llCurrentLocation.setOnClickListener {
            val placeName = binding.tvCharacterCurrentLocationInside.text.toString()
            if (placeName != "unknown")
                viewModel.moveToScreen(MainViewModel.Companion.Screen.LOCATION_DETAIL,
                    place = placeName)
        }
    }

    private fun setupInfo() {
        with(binding) {
            tvCharacterOriginInside.text = character.origin
            tvSingleCharacterId.text = character.id.toString()
            tvSingleCharacterName.text = character.name
            tvCharacterCurrentLocationInside.text = character.currentLocation
            tvCharacterGenderInside.text = character.gender
            tvCharacterNameInside.text = character.name
            tvCharacterSpeciesInside.text = character.species
            Log.i("uriImage", character.imgSrc)
            ivSingleCharacterImage.setImageURI(Uri.parse(character.imgSrc))
        }

    }

}