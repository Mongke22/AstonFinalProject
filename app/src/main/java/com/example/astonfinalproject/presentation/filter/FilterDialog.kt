package com.example.astonfinalproject.presentation.filter

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FilterCharactersBinding

abstract class FilterDialog<VB: ViewBinding> : DialogFragment() {

    var onApplyFunc: (() -> Unit)? = null

    private var _binding: VB? = null
    val binding
        get() = _binding ?:throw RuntimeException("FragmentBinding is null")

    abstract fun getViewBinding(): VB

    abstract fun initFilter()

    abstract fun filterLogic()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            _binding = getViewBinding()

            initFilter()
            filterLogic()

            builder
                .setView(binding.root)
                .setPositiveButton("Применить"
                ) { dialog, id ->
                    onApplyFunc?.invoke()
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}