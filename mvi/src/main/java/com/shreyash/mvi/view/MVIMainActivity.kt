package com.shreyash.mvi.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyash.mvi.R
import com.shreyash.mvi.databinding.ActivityMvimainBinding
import com.shreyash.mvi.intent.TaskIntent
import com.shreyash.mvi.model.Task
import com.shreyash.mvi.state.TaskState
import com.shreyash.mvi.view.adapter.TaskAdapter
import com.shreyash.mvi.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

class MVIMainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMvimainBinding
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()
    private val viewModel: TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMvimainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupListeners()
        observeState()

        lifecycleScope.launch {
            viewModel.intentChannel.send(TaskIntent.LoadTasks)
        }
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            taskList,
            onToggleComplete = { task ->
                lifecycleScope.launch {
                    viewModel.intentChannel.send(
                        TaskIntent.ToggleTaskComplete(
                            task
                        )
                    )
                }
            },
            onDelete = { task ->
                lifecycleScope.launch { viewModel.intentChannel.send(TaskIntent.DeleteTask(task)) }
            }
        )
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(this@MVIMainActivity)
            adapter = taskAdapter
        }
    }

    private fun setupListeners() {
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            lifecycleScope.launch {
                viewModel.intentChannel.send(TaskIntent.AddTask(title, description))
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is TaskState.Tasks -> {
                        taskList.clear()
                        taskList.addAll(state.taskList)
                        taskAdapter.notifyDataSetChanged()
                    }

                    is TaskState.Error -> {
                        Toast.makeText(this@MVIMainActivity, state.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
    }
}