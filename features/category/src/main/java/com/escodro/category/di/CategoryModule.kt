package com.escodro.category.di

import com.escodro.category.mapper.CategoryMapper
import org.koin.dsl.module

/**
 * Category dependency injection module.
 */
val categoryModule = module {

    factory { CategoryMapper() }
}
