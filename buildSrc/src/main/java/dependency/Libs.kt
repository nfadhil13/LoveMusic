package dependency


/*
 Place where we define other libraries


 Tempat untuk mendifinisikan library-library yang tidak terlalu banyak
 */
object Libs {


    object Google {
        private const val materialVersion  = "1.2.1"
        const val material = "com.google.android.material:material:$materialVersion"

        private const val serviceVersion = "4.3.4"
        const val service = "com.google.gms:google-services:$serviceVersion"


        private const val playServiceAuthVersion = "19.0.0"
        const val playServiceAuth = "com.google.android.gms:play-services-auth:$playServiceAuthVersion"
    }


    object Firebase{
        private const val crashlyticsVersionGradle = "2.4.1"
        private const val bomVersion = "26.3.0"


        const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsVersionGradle"
        const val firebaseBOM = "com.google.firebase:firebase-bom:$bomVersion"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val auth = "com.google.firebase:firebase-auth-ktx"
        const val firestore = "com.google.firebase:firebase-firestore-ktx"
        const val storage = "com.google.firebase:firebase-storage-ktx"
    }

    object Hilt{
        private const val version = "2.31.2-alpha"
        private const val viewmodelVersion = "1.0.0-alpha02"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val hiltAndroid = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
        const val viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$viewmodelVersion"

    }

}