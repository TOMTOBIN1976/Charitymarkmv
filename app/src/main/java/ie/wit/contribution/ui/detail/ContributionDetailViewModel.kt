package ie.wit.contribution.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.contribution.models.ContributionManager
import ie.wit.contribution.models.ContributionModel

class ContributionDetailViewModel : ViewModel() {
    private val contribution = MutableLiveData<ContributionModel>()

    val observableContribution: LiveData<ContributionModel>
        get() = contribution

    fun getContribution(id: Long) {
        contribution.value = ContributionManager.findById(id)
    }
}