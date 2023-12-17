// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
	repositories {
		jcenter()
	}

	dependencies {
		classpath ("io.realm:realm-gradle-plugin:10.8.0")
	}
}

plugins {
	id("com.android.application") version "8.2.0" apply false
	id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}