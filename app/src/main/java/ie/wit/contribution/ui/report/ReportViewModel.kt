package ie.wit.contribution.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.contribution.firebase.FirebaseDBManager
import ie.wit.contribution.models.ContributionModel
import timber.log.Timber
import java.lang.Exception

class ReportViewModel : ViewModel() {

    private val contributionsList =
        MutableLiveData<List<ContributionModel>>()

    val observableContributionsList: LiveData<List<ContributionModel>>
        get() = contributionsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun load() {
        try {
            //DonationManager.findAll(liveFirebaseUser.value?.email!!, donationsList)
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,contributionsList)
            Timber.i("Report Load Success : ${contributionsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            //DonationManager.delete(userid,id)
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}

