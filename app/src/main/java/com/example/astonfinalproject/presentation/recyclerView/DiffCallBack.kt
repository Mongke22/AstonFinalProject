package com.example.astonfinalproject.presentation.recyclerView

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.astonfinalproject.domain.Model.BaseDataInfo

class DiffCallBack<ClassInfo: BaseDataInfo>: DiffUtil.ItemCallback<ClassInfo>() {
    override fun areItemsTheSame(oldItem: ClassInfo, newItem: ClassInfo): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ClassInfo, newItem: ClassInfo): Boolean {
        return oldItem == newItem
    }
}