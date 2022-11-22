package ie.wit.contribution.ui.contribute

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.contribution.models.ContributionManager
import ie.wit.contribution.models.ContributionModel

//class ContributeViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
//}

class ContributeViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addDonation(contribution: ContributionModel) {
        status.value = try {
            ContributionManager.create(contribution)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}