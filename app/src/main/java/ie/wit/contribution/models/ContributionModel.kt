package ie.wit.contribution.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContributionModel(var id: Long = 0,
                         val paymentmethod: String = "N/A",
                         val amount: Int = 0) : Parcelable