package com.example.astonfinalproject.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharacterBinding
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.presentation.MainViewModel
import com.example.astonfinalproject.presentation.filter.CharacterFilterDialog
import com.example.astonfinalproject.presentation.filter.model.CharacterFilter
import com.example.astonfinalproject.presentation.recyclerView.adapters.CharactersListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class CharacterFragment : BaseFragment<FragmentCharacterBinding>() {

    companion object {
        fun newInstance(vm: MainViewModel): CharacterFragment {
            viewModel = vm
            return CharacterFragment()
        }
    }

    private lateinit var charactersListAdapter: CharactersListAdapter
    private var filter = CharacterFilter("", "", "")
    private var itemsList: List<CharacterInfo> = listOf()
    private val editTextSubject = PublishSubject.create<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNoDataTextView()
        setupRecyclerView()
        setupObserver()

    }

    private fun setupObserver() {
        viewModel.characterList.observe(viewLifecycleOwner) { characters ->

            viewModel.hideFragmentIfNoAvailableData(characters.isNullOrEmpty())

            if (itemsList.size != characters.size) {
                itemsList = characters
                charactersListAdapter.submitList(characters)
            }
        }

        viewModel.filterCharacter.observe(viewLifecycleOwner) { newFilter ->
            filter = newFilter
            applyFilter(binding.searchField.etSearch.text.toString())
        }
    }

    private fun setNoDataTextView() {
        noAvailableDataText = binding.tvNoData
    }

    override fun setUpOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(false) {
                override fun handleOnBackPressed() {
                }
            })
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

    private fun setupListeners() {
        setupImageLoadingListener()
        setupClickListener()
        setupTextChangedListener()
    }

    private fun setupImageLoadingListener() {
        charactersListAdapter.characterSavePictureFunc = { id, path ->
            viewModel.updateImagePath(id, path)
        }
    }

    private fun setupClickListener() {
        charactersListAdapter.characterClickListener = {
            viewModel.moveToScreen(MainViewModel.Companion.Screen.CHARACTER_DETAIL, it.id)
        }
        binding.searchField.ivFilter.setOnClickListener {
            val myDialogFragment = CharacterFilterDialog()
            myDialogFragment.initCharacterFilter = {
                viewModel.filterCharacter.value ?: CharacterFilter("", "", "")
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
        var characterListToShow = itemsList.filter { character ->
            character.name.contains(text)
                    && character.gender.indexOf(filter.gender) == 0
                    && character.status.contains(filter.status)
                    && character.species.contains(filter.species)
        }

        viewModel.hideFragmentIfNoAvailableData(characterListToShow.isEmpty())

        charactersListAdapter.submitList(characterListToShow)

    }
}