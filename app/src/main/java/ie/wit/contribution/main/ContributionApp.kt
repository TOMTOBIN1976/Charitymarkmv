package ie.wit.contribution.main

import android.app.Application
import timber.log.Timber

class ContributionApp : Application() {

    //lateinit var contributionsStore: ContributionStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //contributionsStore = ContributionMemStore()
        Timber.i("Starting Contribution Application")
    }
}