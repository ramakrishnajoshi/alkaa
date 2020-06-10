package com.escodro.search.di

import com.escodro.search.mapper.TaskSearchMapper
import org.koin.dsl.module

/**
 * Search dependency injection module.
 */
val searchModule = module {

    // Mappers
    factory { TaskSearchMapper() }
}
