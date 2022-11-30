package com.example.todo.presentation.ui.bin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.FragmentBinBinding
import com.example.todo.presentation.adapters.TaskListAdapter
import com.example.todo.presentation.alert.Alerter
import com.example.todo.presentation.utils.MarginItemDecoration
import com.example.todo.presentation.utils.observeEvent
import com.example.todo.presentation.utils.obtainViewModel

class BinFragment : Fragment() {

    private lateinit var mBinding: FragmentBinBinding
    private val mAlerter by lazy { Alerter(mBinding.rcvTasks) }
    private val mBinViewModel by lazy { requireActivity().obtainViewModel(BinViewModel::class.java) }
    private val mTaskListAdapter by lazy {
        TaskListAdapter {
            mAlerter.show("Task", it.title)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentBinBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinViewModel.setup()
        setUpRecycler()
    }

    private fun BinViewModel.setup() {
        binTasks.observe(viewLifecycleOwner) {
            mTaskListAdapter.addAll(it)
        }
        taskDeleted.observeEvent(viewLifecycleOwner) {
            mAlerter.showWarning("Deleted", it.title)
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
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                if (swipeDir == ItemTouchHelper.LEFT) {
                    mBinViewModel.delete(mTaskListAdapter.deleteItemAt(position))
                } else {
                    mBinViewModel.restore(mTaskListAdapter.getItemAt(position)) {
                        mAlerter.showSuccess("Restored", it.title)
                    }
                }
            }
        }
        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(mBinding.rcvTasks)
    }
}