package com.shreyash.mvvm.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyash.mvvm.R
import com.shreyash.mvvm.databinding.ActivityMvvmmainBinding
import com.shreyash.mvvm.model.Task
import com.shreyash.mvvm.view.adapter.TaskAdapter
import com.shreyash.mvvm.viewmodel.MainViewModel

class MVVMMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmmainBinding
    private lateinit var taskAdapter: TaskAdapter

    private val taskList = mutableListOf<Task>()
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMvvmmainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            taskList,
            onToggleComplete = { task -> viewModel.toggleTaskComplete(task) },
            onDelete = { task -> viewModel.deleteTask(task) }
        )

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(this@MVVMMainActivity)
            adapter = taskAdapter
        }
    }

    private fun setupListeners() {
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            viewModel.addTask(title, description)
        }
    }

    private fun observeViewModel() {
        viewModel.tasks.observe(this) { tasks ->
            taskList.clear()
            taskList.addAll(tasks)
            taskAdapter.notifyDataSetChanged()
        }

        viewModel.error.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}