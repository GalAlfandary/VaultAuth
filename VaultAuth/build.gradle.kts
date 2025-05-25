plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)  // ← add this

}

android {
    namespace = "com.example.vaultauth"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId    = "com.github.GalAlfandary"     // ← your GitHub user/org
                artifactId = "VaultAuth"                   // ← your repo / module name
                version    = "1.0.0"                       // ← pick your first version

                artifact(tasks.getByName("bundleReleaseAar"))

                pom {
                    withXml {
                        val depsNode = asNode().appendNode("dependencies")
                        // expose Retrofit as API (so consumers don’t have to redeclare it)
                        configurations.api.get().dependencies.forEach { dep ->
                            val depNode = depsNode.appendNode("dependency")
                            depNode.appendNode("groupId",    dep.group)
                            depNode.appendNode("artifactId", dep.name)
                            depNode.appendNode("version",    dep.version)
                            depNode.appendNode("scope",      "compile")
                        }
                        // internal libs as runtime
                        configurations.implementation.get().dependencies.forEach { dep ->
                            val depNode = depsNode.appendNode("dependency")
                            depNode.appendNode("groupId",    dep.group)
                            depNode.appendNode("artifactId", dep.name)
                            depNode.appendNode("version",    dep.version)
                            depNode.appendNode("scope",      "runtime")
                        }
                    }
                }
            }
        }
    }
}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Rest API calls
    implementation (libs.retrofit)
    implementation(libs.converter.gson)
}