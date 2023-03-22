package com.example.astonfinalproject.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharacterBinding
import com.example.astonfinalproject.databinding.FragmentSingleCharacterBinding
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.EpisodesListAdapter


class SingleCharacterFragment : BaseFragment<FragmentSingleCharacterBinding>() {

    companion object {

        private lateinit var viewModel: MainViewModel

        private const val ID = "id"
        private const val UNDEFINED = -1

        fun newInstance(vm: MainViewModel, id: Int): SingleCharacterFragment {
            viewModel = vm

            return SingleCharacterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                }
            }
        }
    }

    private lateinit var episodesListAdapter: EpisodesListAdapter
    private var characterId = UNDEFINED

    override fun loadData() {
        parseParams()
        viewModel.loadCharacter(characterId)
    }

    private fun parseParams() {
        val args = requireArguments()
        if (args.containsKey(ID)) {
            characterId = args.getInt(ID)
        }
    }

    override fun getViewBinding(): FragmentSingleCharacterBinding {
        return FragmentSingleCharacterBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}