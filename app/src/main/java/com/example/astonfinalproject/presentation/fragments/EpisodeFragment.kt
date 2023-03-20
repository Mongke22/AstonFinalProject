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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EpisodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EpisodeFragment : BaseFragment<FragmentEpisodeBinding>() {

    companion object {

        private lateinit var viewModel: MainViewModel

        fun newInstance(vm: MainViewModel): EpisodeFragment {
            viewModel = vm

            return EpisodeFragment()
        }
    }

    private lateinit var episodesListAdapter: CharactersListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        CharacterFragment.viewModel.characterList.observe(viewLifecycleOwner) { characters ->
            //TODO(добавить текст о том, что нет данных)
            charactersListAdapter.submitList(characters)
        }
    }

    override fun loadData() {
        viewModel.loadEpisodes()
    }

    override fun getViewBinding(): FragmentEpisodeBinding {
        return FragmentEpisodeBinding.inflate(layoutInflater)
    }

    private fun setupRecyclerView() {
        val rvCharactersList = binding.rvCharacters
        with(rvCharactersList) {
            charactersListAdapter = CharactersListAdapter()
            adapter = charactersListAdapter
        }
        setupListeners()
    }

}