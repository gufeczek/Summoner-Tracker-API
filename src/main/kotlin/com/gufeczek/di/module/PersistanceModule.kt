package com.gufeczek.di.module

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.gufeczek.SummonerQueries
import com.gufeczek.sqldelight.SummonerTrackerDatabase
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.Dotenv
import org.koin.dsl.module

val persistenceModule = module {
    single { provideDatabase(env = get()) }
    single { provideSummonerQuery(db = get()) }
}

private fun provideDatabase(env: Dotenv): SummonerTrackerDatabase {
    val dataSource = HikariDataSource(
        HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = env["JDBC_URL"]
            maximumPoolSize = env["MAX_POOL_SIZE"].toIntOrNull() ?: 3
            isAutoCommit = env["AUTO_COMMIT"].toBooleanStrictOrNull() ?: true
            transactionIsolation = env["TRANSACTION_ISOLATION"] ?: "TRANSACTION_REPEATABLE_READ"
            username = env["DB_USERNAME"] ?: "postgres"
            password = env["DB_PASSWORD"] ?: "root"
            validate()
        }
    )
    return SummonerTrackerDatabase(driver = dataSource.asJdbcDriver())
}

private fun provideSummonerQuery(db: SummonerTrackerDatabase): SummonerQueries = db.summonerQueries
