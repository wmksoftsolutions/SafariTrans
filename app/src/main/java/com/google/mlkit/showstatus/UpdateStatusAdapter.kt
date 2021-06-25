package com.google.mlkit.showstatus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ViewStatusDoneBinding
import com.google.mlkit.vision.demo.databinding.ViewStatusPendingBinding

class UpdateStatusAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(var binding: ViewStatusDoneBinding) : RecyclerView.ViewHolder(binding.root)
    class MyViewHolder1(var binding: ViewStatusPendingBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding = DataBindingUtil.inflate<ViewStatusDoneBinding>(
                LayoutInflater.from(parent.context),
                R.layout.view_status_done,
                parent,
                false
            )
            return MyViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<ViewStatusPendingBinding>(
                LayoutInflater.from(parent.context),
                R.layout.view_status_pending,
                parent,
                false
            )
            return MyViewHolder1(binding)
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (position < 3)
            return 1
        else
            return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            setViewHeight(holder.binding.progress, position)
        } else if (holder is MyViewHolder1) {
            setViewHeight(holder.binding.progress, position)
        }

    }

    private fun setViewHeight(progress: View, position: Int) {
        val param = progress.layoutParams
        if (position == 4) {
            param.height = context.resources.getDimension(R.dimen.height_progress_small).toInt()
        } else {
            param.height = context.resources.getDimension(R.dimen.height_progress).toInt()
        }
        progress.layoutParams = param
    }

    override fun getItemCount(): Int {
        return 5
    }
}
