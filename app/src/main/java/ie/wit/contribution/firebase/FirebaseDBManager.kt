package ie.wit.contribution.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.contribution.models.ContributionModel
import ie.wit.contribution.models.ContributionStore
import timber.log.Timber

object FirebaseDBManager : ContributionStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(contributionsList: MutableLiveData<List<ContributionModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, contributionsList: MutableLiveData<List<ContributionModel>>) {

        database.child("user-donations").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Contribution error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ContributionModel>()
                    val children = snapshot.children
                    children.forEach {
                        val contribution = it.getValue(ContributionModel::class.java)
                        localList.add(contribution!!)
                    }
                    database.child("user-contributions").child(userid)
                        .removeEventListener(this)

                    contributionsList.value = localList
                }
            })
    }

    override fun findById(userid: String, contributionid: String, contribution: MutableLiveData<ContributionModel>) {

        database.child("user-contributions").child(userid)
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
        val key = database.child("donations").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        contribution.uid = key
        val contributionValues = contribution.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/donations/$key"] = contributionValues
        childAdd["/user-donations/$uid/$key"] = contributionValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, contributionid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/donations/$contributionid"] = null
        childDelete["/user-donations/$userid/$contributionid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, contributionid: String, contribution: ContributionModel) {

        val contributionValues = contribution.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["contributions/$contributionid"] = contributionValues
        childUpdate["user-donations/$userid/$contributionid"] = contributionValues

        database.updateChildren(childUpdate)
    }
}