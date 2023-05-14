plugins {
    kotlin("multiplatform") version "1.8.20"
}

group = "me.mark"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        nodejs()
    }
    mingwX64("win") {

    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")
            }
        }
        val jsTest by getting
        val winMain by getting
        val winTest by getting
    }
}
