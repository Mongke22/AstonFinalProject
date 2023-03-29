package com.example.astonfinalproject.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharacterBinding
import com.example.astonfinalproject.databinding.FragmentEpisodeBinding
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.filter.CharacterFilterDialog
import com.example.astonfinalproject.presentation.filter.EpisodeFilterDialog
import com.example.astonfinalproject.presentation.filter.model.CharacterFilter
import com.example.astonfinalproject.presentation.filter.model.EpisodeFilter
import com.example.astonfinalproject.presentation.recyclerView.adapters.CharactersListAdapter
import com.example.astonfinalproject.presentation.recyclerView.adapters.EpisodesListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class EpisodeFragment : BaseFragment<FragmentEpisodeBinding>() {

    companion object {

        fun newInstance(vm: MainViewModel): EpisodeFragment {
            viewModel = vm
            return EpisodeFragment()
        }
    }

    private lateinit var episodesListAdapter: EpisodesListAdapter
    private var itemsList: List<EpisodeInfo> = listOf()
    private val editTextSubject = PublishSubject.create<String>()
    private var filter = EpisodeFilter("", "")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setNoDataTextView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.episodesList.observe(viewLifecycleOwner) { episodes ->
            viewModel.hideFragmentIfNoAvailableData(episodes.isNullOrEmpty())

            if (itemsList.size != episodes.size) {
                itemsList = episodes
                episodesListAdapter.submitList(episodes)
            }
        }

        viewModel.stateList.observe(viewLifecycleOwner){
            for(state in it){
                if(state.screen == "episodes" && state.dataIsReady){
                    binding.swipeRefreshLayoutEpisodes.isRefreshing = false
                }
            }
        }

        viewModel.filterEpisode.observe(viewLifecycleOwner) { newFilter ->
            filter = newFilter
            applyFilter(binding.searchField.etSearch.text.toString())
        }
    }

    private fun setNoDataTextView() {
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

    private fun setupListeners() {
        setupOnClickListener()
        setupTextChangedListener()
        setupSwipeListener()
    }

    private fun setupSwipeListener(){
        binding.swipeRefreshLayoutEpisodes.setOnRefreshListener {
            viewModel.loadEpisodes()
        }
    }

    private fun setupOnClickListener() {
        episodesListAdapter.episodeClickListener = {
            viewModel.moveToScreen(MainViewModel.Companion.Screen.EPISODE_DETAIL, it.id)
        }
        binding.searchField.ivFilter.setOnClickListener {
            val myDialogFragment = EpisodeFilterDialog()
            myDialogFragment.initEpisodeFilter = {
                viewModel.filterEpisode.value ?: EpisodeFilter("", "")
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

    private fun applyFilter(text: String) {
        val episodeListToShow =
            itemsList.filter { episode ->
                episode.name.contains(text)
                        && episode.number.contains("s0${filter.seasonNumber}")
                        && episode.date.contains(filter.yearNumber)
            }

        viewModel.hideFragmentIfNoAvailableData(episodeListToShow.isEmpty())
        episodesListAdapter.submitList(episodeListToShow)
    }

}