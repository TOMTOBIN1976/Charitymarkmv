package ie.wit.contribution.models

interface ContributionStore {
    fun findAll() : List<ContributionModel>
    fun findById(id: Long) : ContributionModel?
    fun create(contribution: ContributionModel)
}