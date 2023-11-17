plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.flyway)
    alias(libs.plugins.dotenv)
    alias(libs.plugins.ktlint)
}

tasks {
    compileKotlin {
        dependsOn("generateMainSummonerTrackerDatabaseMigrations")
    }
}

group = "com.gufeczek"
version = "0.0.1"

application {
    mainClass.set("$group.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    google()
    mavenCentral()
}

sqldelight {
    databases {
        create("SummonerTrackerDatabase") {
            packageName.set("$group.sqldelight")
            dialect(libs.persistance.postgres.dialect)
            deriveSchemaFromMigrations.set(true)
            migrationOutputDirectory.set(file(layout.buildDirectory.file("/generated/migrations")))
            verifyMigrations.set(true)
        }
    }
}

flyway {
    val migrationsLocation = layout.buildDirectory.file("/generated/migrations").get().asFile.absolutePath
    locations = arrayOf("filesystem:$migrationsLocation")
    url = env.JDBC_URL.value
    user = env.DB_USERNAME.value
    password = env.DB_PASSWORD.value
}

ktlint {
    enableExperimentalRules.set(true)
    filter {
        exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
    }
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.persistance)
    implementation(libs.bundles.di)
    implementation(libs.bundles.logging)
    implementation(libs.bundles.testing)
    implementation(libs.bundles.other)
}

kotlin {
    jvmToolchain(17)
}
