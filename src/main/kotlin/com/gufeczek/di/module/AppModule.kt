package com.gufeczek.di.module

import io.github.cdimascio.dotenv.Dotenv
import org.koin.dsl.module

val appModule = module {
    single { Dotenv.load() }
}
