package com.shreyash.mvvm.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shreyash.mvvm.databinding.ItemTaskBinding
import com.shreyash.mvvm.model.Task

class TaskAdapter(
    private val tasks: List<Task>,
    private val onToggleComplete: (Task) -> Unit,
    private val onDelete: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.tvTitle.text = task.title
        holder.binding.tvDescription.text = task.description
        holder.binding.btnToggleComplete.text = if (task.isCompleted) "Mark as Pending" else "Mark as Done"

        holder.binding.btnToggleComplete.setOnClickListener {
            onToggleComplete(task)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDelete(task)
        }
    }
}
