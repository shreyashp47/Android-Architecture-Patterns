package com.shreyash.cleanarchitecture

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyash.cleanarchitecture.databinding.ActivityCamainBinding
import com.shreyash.cleanarchitecture.domain.model.Task
import com.shreyash.cleanarchitecture.presentation.state.TaskState
import com.shreyash.cleanarchitecture.presentation.view.adapter.TaskAdapter
import com.shreyash.cleanarchitecture.presentation.viewmodel.TaskViewModel
import com.shreyash.cleanarchitecture.presentation.viewmodel.TaskViewModelFactory
import kotlinx.coroutines.launch

class CAMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCamainBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCamainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this, TaskViewModelFactory())[TaskViewModel::class.java]

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            taskList,
            onToggleComplete = { task ->
                viewModel.toggleTaskComplete(task)
            },
            onDelete = { task ->
                viewModel.deleteTask(task)
            }
        )
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(this@CAMainActivity)
            adapter = taskAdapter
        }
    }

    private fun setupListeners() {
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                viewModel.addTask(title, description)
                binding.etTitle.text.clear()
                binding.etDescription.text.clear()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.taskState.collect { state ->
                when (state) {
                    is TaskState.Loading -> {
                        // Show loading if needed
                    }
                    is TaskState.Success -> {
                        taskList.clear()
                        taskList.addAll(state.tasks)
                        taskAdapter.notifyDataSetChanged()
                    }
                    is TaskState.Error -> {
                        Toast.makeText(this@CAMainActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}