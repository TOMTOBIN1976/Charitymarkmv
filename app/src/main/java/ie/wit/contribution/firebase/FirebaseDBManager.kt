package ie.wit.contribution.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.contribution.models.ContributionModel
import ie.wit.contribution.models.ContributionStore
import timber.log.Timber

object FirebaseDBManager : ContributionStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(contributionsList: MutableLiveData<List<ContributionModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, contributionsList: MutableLiveData<List<ContributionModel>>) {
        TODO("Not yet implemented")
    }

    override fun findById(userid: String, contributionid: String, contribution: MutableLiveData<ContributionModel>) {
        database.child("user-donations").child(userid)
            .child(contributionid).get().addOnSuccessListener {
                contribution.value = it.getValue(ContributionModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, contribution: ContributionModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("contributions").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        contribution.uid = key
        val contributionValues = contribution.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/contributions/$key"] = contributionValues
        childAdd["/user-donations/$uid/$key"] = contributionValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, contributionid: String) {
        TODO("Not yet implemented")
    }

    override fun update(userid: String, donationid: String, donation: ContributionModel) {
        TODO("Not yet implemented")
    }
}