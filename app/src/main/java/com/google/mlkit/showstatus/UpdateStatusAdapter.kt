package com.google.mlkit.showstatus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.RowStatusDoneBinding
import com.google.mlkit.vision.demo.databinding.RowStatusPendingBinding

class UpdateStatusAdapter(
    var context: Context,
    var list_status: ArrayList<Status>,
    var updateStatusInterface: UpdateStatusInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isDoneVisible = false

    class MyViewHolder(var binding: RowStatusDoneBinding) : RecyclerView.ViewHolder(binding.root)
    class MyViewHolder1(var binding: RowStatusPendingBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding = DataBindingUtil.inflate<RowStatusDoneBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_status_done,
                parent,
                false
            )
            return MyViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<RowStatusPendingBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_status_pending,
                parent,
                false
            )
            return MyViewHolder1(binding)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (list_status.get(position).status.equals("1"))
            1
        else
            0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val status = list_status.get(position)
        if (holder is MyViewHolder) {
            holder.binding.status.text = status.name
        } else if (holder is MyViewHolder1) {
            holder.binding.status.text = status.name
            if (!isDoneVisible) {
                holder.binding.done.visibility = View.VISIBLE
                isDoneVisible = true
            } else {
                holder.binding.done.visibility = View.GONE
            }
            holder.binding.done.setOnClickListener {
                updateStatusInterface.onUpdateStatus(position)

            }
        }


    }

    override fun getItemCount(): Int {
        return list_status.size
    }

    fun refresh() {
        isDoneVisible = false
        notifyDataSetChanged()
    }
}
