import dependencies.addEntityModule

plugins {
   plugins.`android-core-library`
}

android {
    namespace = "com.ashish.domain"
}

dependencies {
    addEntityModule(configurationName = "api")
}