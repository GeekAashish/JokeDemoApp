import dependencies.addTimberDependencies

plugins {
    plugins.`android-core-library`
}
android {
    namespace = "com.ashish.di"
}
dependencies {
    addTimberDependencies(configurationName = "api")
}
