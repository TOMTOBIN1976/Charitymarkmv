package ie.wit.contribution.ui.contribute

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.contribution.firebase.FirebaseDBManager
//import ie.wit.contribution.models.ContributionManager
import ie.wit.contribution.models.ContributionModel

class ContributeViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addContribution(firebaseUser: MutableLiveData<FirebaseUser>,
                        donation: ContributionModel) {
        status.value = try {
            //ContributionManager.create(contribution)
            FirebaseDBManager.create(firebaseUser,donation)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}