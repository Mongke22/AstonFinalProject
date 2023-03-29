package com.example.astonfinalproject.presentation.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentSingleCharacterBinding
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.presentation.AstonApp
import com.example.astonfinalproject.presentation.viewModel.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.EpisodesListAdapter
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


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

    @Inject
    lateinit var episodesListAdapter: EpisodesListAdapter

    private lateinit var character: CharacterInfo
    private var characterId = UNDEFINED

    private val component by lazy{
        (requireActivity().application as AstonApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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
            if (placeName != requireActivity().getString(R.string.string_unknown))
                viewModel.moveToScreen(MainViewModel.Companion.Screen.LOCATION_DETAIL,
                    place = placeName)
        }

        binding.llCurrentLocation.setOnClickListener {
            val placeName = binding.tvCharacterCurrentLocationInside.text.toString()
            if (placeName != requireActivity().getString(R.string.string_unknown))
                viewModel.moveToScreen(MainViewModel.Companion.Screen.LOCATION_DETAIL,
                    place = placeName)
        }
    }

    private fun setupInfo() {
        with(binding) {
            tvCharacterOriginInside.text = character.origin
            tvSingleCharacterId.text = character.id.toString()
            tvCharacterCurrentLocationInside.text = character.currentLocation
            tvCharacterGenderInside.text = character.gender
            tvCharacterNameInside.text = character.name
            tvCharacterSpeciesInside.text = character.species
            ivSingleCharacterImage.setImageURI(Uri.parse(character.imgSrc))
        }

    }

}