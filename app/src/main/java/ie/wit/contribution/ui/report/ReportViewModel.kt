package ie.wit.contribution.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.contribution.models.ContributionManager
import ie.wit.contribution.models.ContributionModel

class ReportViewModel : ViewModel() {

    private val contributionsList = MutableLiveData<List<ContributionModel>>()

    val observableContributionsList: LiveData<List<ContributionModel>>
        get() = contributionsList

    init {
        load()
    }

    fun load() {
        contributionsList.value = ContributionManager.findAll()
    }
}