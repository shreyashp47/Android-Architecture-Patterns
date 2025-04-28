package com.shreyash.mvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyash.mvp.adapter.TaskAdapter
import com.shreyash.mvp.contract.MainContract
import com.shreyash.mvp.databinding.ActivityMvpmainBinding
import com.shreyash.mvp.model.Task
import com.shreyash.mvp.presenter.MainPresenter

class MVPMainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMvpmainBinding
    private lateinit var presenter: MainContract.Presenter
    private lateinit var taskAdapter: TaskAdapter

    private var taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMvpmainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        presenter = MainPresenter(this)

        setupRecyclerView()
        setupListeners()

        presenter.loadTasks()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            taskList,
            onToggleComplete = { task ->
                presenter.toggleTaskComplete(task)
            },
            onDelete = { task ->
                presenter.deleteTask(task)
            }
        )

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(this@MVPMainActivity)
            adapter = taskAdapter
        }
    }

    private fun setupListeners() {
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            presenter.addTask(title, description)
        }
    }

    // --- View Interface Implementation ---

    override fun showTasks(tasks: List<Task>) {
        taskList.clear()
        taskList.addAll(tasks)
        taskAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun clearInputFields() {
        binding.etTitle.text.clear()
        binding.etDescription.text.clear()
    }
}