package com.escodro.task.presentation.list

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.escodro.core.extension.createSnackbar
import com.escodro.core.extension.hideKeyboard
import com.escodro.core.extension.itemDialog
import com.escodro.core.extension.items
import com.escodro.core.extension.showKeyboard
import com.escodro.core.viewmodel.ToolbarViewModel
import com.escodro.task.R
import com.escodro.task.model.TaskWithCategory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.android.synthetic.main.item_add_task.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

/**
 * [Fragment] responsible to show and handle all [TaskWithCategory]s.
 */
class TaskListFragment : Fragment() {

    private val viewModel: TaskListViewModel by stateViewModel()

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val sharedViewModel: ToolbarViewModel by sharedViewModel()

    private var navigator: NavController? = null

    private var isLandscape: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView()")

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated()")

        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        updateRecyclerView()
        navigator = NavHostFragment.findNavController(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onStart() {
        super.onStart()

        loadTasks()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d("onConfigurationChanged()")

        isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        updateRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy()")

        adapter.clear()
    }

    private fun updateRecyclerView() {
        Timber.d("bindComponents()")

        recyclerview_tasklist_list?.adapter = adapter
        recyclerview_tasklist_list?.layoutManager = getLayoutManager()
    }

    private fun loadTasks() {
        Timber.d("loadTasks()")

        val state = arguments?.getParcelable<TaskListState>(EXTRA_TASK_LIST_STATE)
            ?: TaskListState.ShowAllTasks

        val defaultTitle = getString(R.string.task_list_default_title)
        val taskName = arguments?.getString(EXTRA_CATEGORY_NAME) ?: defaultTitle

        viewModel.loadTasks(state, onTasksLoaded = ::onTaskLoaded, onLoadError = ::onLoadError)
        sharedViewModel.updateTitle(taskName)
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        val layoutSpan = if (isLandscape) LAYOUT_SPAN_LANDSCAPE else LAYOUT_SPAN_PORTRAIT
        return GridLayoutManager(context, layoutSpan)
    }

    private fun onInsertTask(description: String) {
        hideKeyboard()
        viewModel.addTask(description)
    }

    private fun onTaskLoaded(list: List<TaskWithCategory>, showAddButton: Boolean) {
        Timber.d("onTaskLoaded() - Size = ${list.size}")

        val items = list
            .map { TaskItem(it, ::onItemClicked, ::onItemLongPressed, ::onItemCheckedChanged) }
            .toMutableList<Item>()

        if (showAddButton) {
            items.add(TaskAddItem(::onAddClicked, ::onInsertTask, viewModel.taskName))
        }

        adapter.update(items)

        if (list.isEmpty() && !showAddButton) {
            recyclerview_tasklist_list?.visibility = View.INVISIBLE
            textview_tasklist_empty?.visibility = View.VISIBLE
        } else {
            recyclerview_tasklist_list?.visibility = View.VISIBLE
            textview_tasklist_empty?.visibility = View.INVISIBLE
        }
    }

    private fun onLoadError() {
        arguments?.clear()
        loadTasks()
    }

    private fun onItemClicked(taskWithCategory: TaskWithCategory) {
        Timber.d("onItemClicked() - Task = ${taskWithCategory.task.title}")

        val action = TaskListFragmentDirections.actionDetail(taskWithCategory.task.id)
        navigator?.navigate(action)
    }

    private fun onItemCheckedChanged(taskWithCategory: TaskWithCategory, value: Boolean) {
        Timber.d("onItemCheckedChanged() - Task = ${taskWithCategory.task.title} - Value = $value")

        viewModel.updateTaskStatus(taskWithCategory.task)

        if (value) {
            constraint_tasklist_root
                .createSnackbar(R.string.task_snackbar_completed)
                .setAction(R.string.task_snackbar_undo) {
                    Timber.d("Undo completed action - Task = ${taskWithCategory.task.title}")
                    viewModel.updateTaskStatus(taskWithCategory.task)
                }.show()
        }
    }

    private fun onItemLongPressed(taskWithCategory: TaskWithCategory): Boolean {
        Timber.d("onItemLongPressed() - Task = ${taskWithCategory.task.title}")

        itemDialog(taskWithCategory.task.title) {
            items(R.array.task_dialog_options) { item ->
                when (item) {
                    0 -> viewModel.deleteTask(taskWithCategory)
                }
            }
        }?.show()

        return true
    }

    private fun onAddClicked() {
        Timber.d("onAddClicked")

        edittext_itemadd_description.requestFocus()
        showKeyboard()
    }

    companion object {

        private const val LAYOUT_SPAN_PORTRAIT = 1

        private const val LAYOUT_SPAN_LANDSCAPE = 2

        /**
         * Represent the extra to store the category name.
         */
        const val EXTRA_CATEGORY_NAME = "category_name"

        /**
         * Represent the extra to store the task list state.
         */
        const val EXTRA_TASK_LIST_STATE = "task_list_state"
    }
}
