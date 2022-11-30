package com.example.todo.presentation.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivityTaskDetailsBinding
import com.example.todo.presentation.alert.Alerter
import com.example.todo.presentation.ui.tasks.TasksFragment
import com.example.todo.presentation.utils.closeSoftInputKeyboard
import com.example.todo.presentation.utils.observeEvent
import com.example.todo.presentation.utils.obtainViewModel

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTaskDetailsBinding
    private val mAlerter by lazy { Alerter(mBinding.btnUpdate) }
    private val mTaskDetailsViewModel: TaskDetailsViewModel by obtainViewModel()
    private val mTaskId by lazy { intent.extras?.getString(TasksFragment.TASK_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        title = "Task Update"
        mTaskDetailsViewModel.setup()
        setupUi()
    }

    private fun setupUi() {
        mBinding.btnUpdate.setOnClickListener {
            val titleEditText = mBinding.edTextTitle
            val descriptionEditText = mBinding.edTextDescription
            mTaskDetailsViewModel.updateTask(
                titleEditText.text.toString(),
                descriptionEditText.text.toString()
            ) {
                mAlerter.showSuccess("Updated", it)
            }
            closeSoftInputKeyboard()
            titleEditText.clearFocus()
            descriptionEditText.clearFocus()
        }
    }

    private fun TaskDetailsViewModel.setup(): Unit = with(this@TaskDetailsActivity) {
        mTaskId?.let { setCurrentTask(it) }
        emptyError.observeEvent(this) {
            mAlerter.showError("Empty", it.toString())
        }
        currentTask.observeEvent(this) {
            mBinding.edTextTitle.setText(it.title)
            mBinding.edTextDescription.setText(it.description)
        }
    }
}