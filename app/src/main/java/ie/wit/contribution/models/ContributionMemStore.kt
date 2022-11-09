package ie.wit.contribution.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ContributionMemStore : ContributionStore {

    val contributions = ArrayList<ContributionModel>()

    override fun findAll(): List<ContributionModel> {
        return contributions
    }

    override fun findById(id:Long) : ContributionModel? {
        val foundContribution: ContributionModel? = contributions.find { it.id == id }
        return foundContribution
    }

    override fun create(contribution: ContributionModel) {
        contribution.id = getId()
        contributions.add(contribution)
        logAll()
    }

    fun logAll() {
        Timber.v("** contributions List **")
        contributions.forEach { Timber.v("Contribute ${it}") }
    }
}