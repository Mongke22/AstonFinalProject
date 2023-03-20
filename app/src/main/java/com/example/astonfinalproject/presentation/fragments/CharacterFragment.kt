package com.example.astonfinalproject.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharacterBinding
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.CharactersListAdapter


class CharacterFragment : BaseFragment<FragmentCharacterBinding>() {

    private lateinit var charactersListAdapter: CharactersListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.characterList.observe(viewLifecycleOwner) { characters ->
            //TODO(добавить текст о том, что нет данных)
            charactersListAdapter.submitList(characters)
        }
    }

    override fun getViewBinding(): FragmentCharacterBinding {
        return FragmentCharacterBinding.inflate(layoutInflater)
    }

    override fun loadData() {
        viewModel.loadCharacters()
    }


    private fun setupRecyclerView() {
        val rvCharactersList = binding.rvCharacters
        with(rvCharactersList) {
            charactersListAdapter = CharactersListAdapter()
            adapter = charactersListAdapter
        }
        setupListeners()
    }

    private fun setupListeners(){
        setupImageLoadingListener()
        //setupClickListener()
    }

    private fun setupImageLoadingListener(){
        charactersListAdapter.characterSavePictureFunc = { id, path ->
            viewModel.updateImagePath(id, path)
        }
    }

    companion object{

        private lateinit var viewModel: MainViewModel

        fun newInstance(vm: MainViewModel): CharacterFragment{
            viewModel = vm

            return CharacterFragment()
        }
    }
}