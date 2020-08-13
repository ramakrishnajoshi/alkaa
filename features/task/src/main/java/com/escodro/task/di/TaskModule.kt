package com.escodro.task.di

import androidx.lifecycle.SavedStateHandle
import com.escodro.task.mapper.AlarmIntervalMapper
import com.escodro.task.mapper.CategoryMapper
import com.escodro.task.mapper.TaskMapper
import com.escodro.task.mapper.TaskWithCategoryMapper
import com.escodro.task.presentation.detail.TaskDetailProvider
import com.escodro.task.presentation.detail.TaskDetailProviderImpl
import com.escodro.task.presentation.detail.alarm.TaskAlarmViewModel
import com.escodro.task.presentation.detail.category.TaskCategoryViewModel
import com.escodro.task.presentation.detail.main.TaskDetailViewModel
import com.escodro.task.presentation.list.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Task dependency injection module.
 */
val taskModule = module {

    single<TaskDetailProvider> { TaskDetailProviderImpl(get(), get()) }

    viewModel { (handle: SavedStateHandle) ->
        TaskListViewModel(get(), get(), get(), get(), get(), get(), handle, get(), get())
    }
    viewModel { TaskDetailViewModel(get(), get(), get(), get()) }
    viewModel { TaskCategoryViewModel(get(), get(), get(), get(), get()) }
    viewModel { TaskAlarmViewModel(get(), get(), get(), get(), get()) }

    // Mappers
    factory { AlarmIntervalMapper() }
    factory { TaskMapper(get()) }
    factory { CategoryMapper() }
    factory { TaskWithCategoryMapper(get(), get()) }
}
