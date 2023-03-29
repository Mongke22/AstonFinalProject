package com.example.astonfinalproject.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentSingleCharacterBinding
import com.example.astonfinalproject.databinding.FragmentSingleEpisodeBinding
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.CharactersListAdapter
import com.example.astonfinalproject.presentation.recyclerView.adapters.EpisodesListAdapter

class SingleEpisodeFragment : BaseFragment<FragmentSingleEpisodeBinding>() {
    companion object {
        fun newInstance(vm: MainViewModel, getId: Int): SingleEpisodeFragment {
            viewModel = vm
            return SingleEpisodeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, getId)
                }
            }
        }
    }

    private lateinit var characterListAdapter: CharactersListAdapter
    private var episodeId = UNDEFINED
    private lateinit var episode: EpisodeInfo

    override fun loadData() {
        parseParams()
        viewModel.loadEpisode(episodeId)
    }

    private fun parseParams() {
        val args = arguments
        if(args == null){
            Toast.makeText(requireContext(),"no params", Toast.LENGTH_SHORT).show()
        }
        else{
            if (args.containsKey(ID)) {
                episodeId = args.getInt(ID)
            }
        }
    }

    override fun getViewBinding(): FragmentSingleEpisodeBinding {
        return FragmentSingleEpisodeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getEpisode(episodeId)
        viewModel.episodeCharacterList.observe(viewLifecycleOwner){
            characterListAdapter.submitList(it)
        }
        viewModel.episodeInfo.observe(viewLifecycleOwner) {
            episode = it
            setupInfo()
        }
    }

    private fun setupInfo(){
        with(binding){
            tvSingleEpisodeDate.text = episode.date
            tvSingleEpisodeNumber.text = episode.number
            tvSingleEpisodeName.text = episode.name
        }
    }

    private fun setupRecyclerView(){
        val rvEpisodesList = binding.rvSingleEpisodeCharacters
        with(rvEpisodesList) {
            characterListAdapter = CharactersListAdapter()
            adapter = characterListAdapter
        }
        setupListeners()
    }

    private fun setupListeners(){
        characterListAdapter.characterClickListener = {
            viewModel.moveToScreen(MainViewModel.Companion.Screen.CHARACTER_DETAIL, it.id)
        }
        characterListAdapter.characterSavePictureFunc = { id, path ->
            viewModel.updateImagePath(id, path)
        }
    }

}