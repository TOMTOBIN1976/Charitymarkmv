package ie.wit.contribution.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.contribution.firebase.FirebaseDBManager
//import ie.wit.contribution.models.ContributionManager
import ie.wit.contribution.models.ContributionModel
import timber.log.Timber

class ContributionDetailViewModel : ViewModel() {
    private val contribution = MutableLiveData<ContributionModel>()

    var observableContribution: LiveData<ContributionModel>
        get() = contribution
        set(value) {contribution.value = value.value}

    fun getContribution(userid:String, id: String) {
        try {
            //DonationManager.findById(email, id, donation)
            FirebaseDBManager.findById(userid, id, contribution)
            Timber.i("Detail getContribution() Success : ${
                contribution.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getContribution() Error : $e.message")
        }
    }

    fun updateContribution(userid:String, id: String,contribution: ContributionModel) {
        try {
            //DonationManager.update(email, id, donation)
            FirebaseDBManager.update(userid, id, contribution)
            Timber.i("Detail update() Success : $contribution")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}