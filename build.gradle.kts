buildscript {
    val agp_version by extra("8.1.1")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false

    //enable KSP processor used by Room
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false

    id("com.google.gms.google-services") version "4.4.2" apply false
}
