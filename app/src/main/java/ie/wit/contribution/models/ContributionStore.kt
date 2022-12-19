package ie.wit.contribution.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface ContributionStore {
        fun findAll(contributionsList:
                    MutableLiveData<List<ContributionModel>>)
        fun findAll(userid:String,
                    contributionsList:
                    MutableLiveData<List<ContributionModel>>)
        fun findById(userid:String, contributionid: String,
                     contribution: MutableLiveData<ContributionModel>)
        fun create(firebaseUser: MutableLiveData<FirebaseUser>, contribution: ContributionModel)
        fun delete(userid:String, contributionid: String)
        fun update(userid:String, contributionid: String, contribution: ContributionModel)
}