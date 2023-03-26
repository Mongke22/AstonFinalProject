package com.example.astonfinalproject.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharacterBinding
import com.example.astonfinalproject.databinding.FragmentEpisodeBinding
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.CharactersListAdapter
import com.example.astonfinalproject.presentation.recyclerView.adapters.EpisodesListAdapter

class EpisodeFragment : BaseFragment<FragmentEpisodeBinding>() {

    companion object {

        fun newInstance(vm: MainViewModel): EpisodeFragment {
            viewModel = vm
            return EpisodeFragment()
        }
    }

    private lateinit var episodesListAdapter: EpisodesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        setNoDataTextView()
        viewModel.episodesList.observe(viewLifecycleOwner) { episodes ->
            //TODO(добавить текст о том, что нет данных)
            episodesListAdapter.submitList(episodes)
        }
    }

    private fun setNoDataTextView(){
        noAvailableDataText = binding.tvNoData
    }

    override fun loadData() {
        viewModel.loadEpisodes()
    }

    override fun getViewBinding(): FragmentEpisodeBinding {
        return FragmentEpisodeBinding.inflate(layoutInflater)
    }

    private fun setupRecyclerView() {
        val rvEpisodesList = binding.rvEpisodes
        with(rvEpisodesList) {
            episodesListAdapter = EpisodesListAdapter()
            adapter = episodesListAdapter
        }
        setupListeners()
    }

    private fun setupListeners(){
        //setupOnClickListener()
    }

}