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
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.recyclerView.adapters.CharactersListAdapter
import com.example.astonfinalproject.presentation.recyclerView.adapters.EpisodesListAdapter

class SingleEpisodeFragment : BaseFragment<FragmentSingleEpisodeBinding>() {
    companion object {

        private const val ID = "id"
        private const val UNDEFINED = -1

        private lateinit var viewModel: MainViewModel

        fun newInstance(vm: MainViewModel, myId: Int): SingleEpisodeFragment {
            viewModel = vm
            return SingleEpisodeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, myId)
                }
            }
        }
    }

    private lateinit var characterListAdapter: CharactersListAdapter
    private var characterId = UNDEFINED

    override fun loadData() {
        parseParams()
        viewModel.loadCharacter(characterId)
    }

    private fun parseParams() {
        val args = arguments
        if(args == null){
            Toast.makeText(requireContext(),"no params", Toast.LENGTH_SHORT).show()
        }
        else{
            if (args.containsKey(ID)) {
                characterId = args.getInt(ID)
            }
        }
    }

    override fun getViewBinding(): FragmentSingleEpisodeBinding {
        return FragmentSingleEpisodeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

    }

    private fun setupRecyclerView(){
       /* val rvEpisodesList = binding.rvEpisodesInside
        with(rvEpisodesList) {
            characterListAdapter = EpisodesListAdapter()
            adapter = characterListAdapter
        }
        setupListeners()*/
    }

    private fun setupListeners(){
        characterListAdapter.characterClickListener = {
            Toast.makeText(requireContext(),"Эпизод: ${it.name}", Toast.LENGTH_LONG).show()
        }
    }

}