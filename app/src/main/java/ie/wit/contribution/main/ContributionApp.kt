package ie.wit.contribution.main

import android.app.Application
import ie.wit.contribution.models.ContributionMemStore
import ie.wit.contribution.models.ContributionStore
import timber.log.Timber

class ContributionApp : Application() {

    lateinit var contributionsStore: ContributionStore

    override fun onCreate() {
        super.onCreate()
        // initialize the Timber library
        Timber.plant(Timber.DebugTree())
        contributionsStore = ContributionMemStore()
        Timber.i("Starting Contribution Application")
    }
}