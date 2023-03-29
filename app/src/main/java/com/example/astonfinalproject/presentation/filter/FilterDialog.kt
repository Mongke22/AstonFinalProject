package com.example.astonfinalproject.presentation.filter

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class FilterDialog<VB: ViewBinding> : DialogFragment() {

    var onApplyFunc: (() -> Unit)? = null

    private var _binding: VB? = null
    val binding
        get() = _binding ?:throw RuntimeException("FragmentBinding is null")

    abstract fun getViewBinding(): VB

    abstract fun initFilter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            initFilter()

            _binding = getViewBinding()

            builder.setTitle("Фильтр персонажей")
                .setView(binding.root)
                .setPositiveButton("Применить"
                ) { dialog, id ->
                    onApplyFunc?.invoke() ?: throw RuntimeException("Определите запись фильтра")
                    dialog.cancel()
                }
                .setNegativeButton("Отмена") {
                        dialog, id ->
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