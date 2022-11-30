package com.example.todo.presentation.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.domain.CompletionStatus
import com.example.todo.R
import com.example.todo.databinding.FragmentMyTasksBinding
import com.example.todo.presentation.adapters.TaskListAdapter
import com.example.todo.presentation.alert.Alerter
import com.example.todo.presentation.ui.details.TaskDetailsActivity
import com.example.todo.presentation.utils.MarginItemDecoration
import com.example.todo.presentation.utils.observeEvent
import com.example.todo.presentation.utils.obtainViewModel

class TasksFragment : Fragment() {

    private lateinit var mBinding: FragmentMyTasksBinding
    private val mAlerter by lazy { Alerter(mBinding.rcvTasks) }
    private val mTasksViewModel by lazy {
        requireActivity().obtainViewModel(TasksViewModel::class.java)
    }
    private val mTaskListAdapter by lazy {
        TaskListAdapter { task ->
            startActivity(
                Intent(requireActivity(), TaskDetailsActivity::class.java).apply {
                    putExtra(TASK_ID, task.id)
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMyTasksBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTasksViewModel.setup()
        setUpRecycler()
    }

    private fun TasksViewModel.setup() {
        allTasks.observe(viewLifecycleOwner) {
            mTaskListAdapter.addAll(it)
        }
        taskMovedToBin.observeEvent(viewLifecycleOwner) {
            mAlerter.showWarning("Moved to bin", it.title)
        }
    }

    private fun setUpRecycler() {
        mBinding.rcvTasks.adapter = mTaskListAdapter
        mBinding.rcvTasks.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        mBinding.rcvTasks.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(R.dimen.task_list_item_margins_vertical),
                resources.getDimensionPixelSize(R.dimen.task_list_item_margins_horizontal)
            )
        )
        addItemTouchListeners()
    }

    private fun addItemTouchListeners() {
        val simpleItemTouchRightCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val position = viewHolder.adapterPosition
                val task = mTaskListAdapter.getItemAt(position)
                return if (task.completionStatus == CompletionStatus.InComplete)
                    super.getSwipeDirs(recyclerView, viewHolder)
                else
                    0
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                mTasksViewModel.complete(mTaskListAdapter.getItemAt(position)) {
                    mAlerter.showSuccess("Completed", it.title)
                }
            }
        }
        val simpleItemTouchLeftCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                mTasksViewModel.moveToBin(mTaskListAdapter.deleteItemAt(position))
            }
        }
        ItemTouchHelper(simpleItemTouchRightCallback).attachToRecyclerView(mBinding.rcvTasks)
        ItemTouchHelper(simpleItemTouchLeftCallback).attachToRecyclerView(mBinding.rcvTasks)
    }

    companion object {
        const val TASK_ID = "TASK_ID"
    }
}