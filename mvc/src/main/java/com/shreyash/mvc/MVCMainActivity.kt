package com.shreyash.mvc

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyash.mvc.adapter.TaskAdapter
import com.shreyash.mvc.databinding.ActivityMainMvcBinding
import com.shreyash.mvc.model.Task
import com.shreyash.mvc.model.TaskRepository

class MVCMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMvcBinding
    private lateinit var taskAdapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainMvcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            TaskRepository.getTasks(),
            onToggleComplete = { task ->
                TaskRepository.toggleTaskComplete(task)
                refreshTasks()
            },
            onDelete = { task ->
                TaskRepository.deleteTask(task)
                refreshTasks()
            }
        )

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(this@MVCMainActivity)
            adapter = taskAdapter
        }
    }

    private fun setupListeners() {
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()

            if (title.isNotBlank() && description.isNotBlank()) {
                val newTask = Task(
                    id = System.currentTimeMillis(),
                    title = title,
                    description = description
                )
                TaskRepository.addTask(newTask)
                refreshTasks()
                clearInputFields()
            } else {
                Toast.makeText(this, "Please enter both title and description", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshTasks() {
        taskAdapter.notifyDataSetChanged()
    }

    private fun clearInputFields() {
        binding.etTitle.text.clear()
        binding.etDescription.text.clear()
    }
}