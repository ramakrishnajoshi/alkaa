package com.escodro.task.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.escodro.task.databinding.FragmentTaskListBinding

/**
 * Fragment containing the task list.
 */
internal class TaskListFragment : Fragment() {

    @Suppress("LateinitUsage")
    private lateinit var binding: FragmentTaskListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
