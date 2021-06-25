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

class ShowStatusAdapter(var context: Context, var list_status: ArrayList<Status>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        if (list_status.get(position).status.equals("1"))
            return 1
        else
            return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val status = list_status.get(position)
        if (holder is MyViewHolder) {
            setViewHeight(holder.binding.progress, position)
            holder.binding.status.text = status.name
        } else if (holder is MyViewHolder1) {
            setViewHeight(holder.binding.progress, position)
            holder.binding.status.text = status.name
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
        return list_status.size
    }
}
