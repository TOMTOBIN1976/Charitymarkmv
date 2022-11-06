package ie.wit.contribution.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ie.wit.contribution.R
import ie.wit.contribution.databinding.ActivityContributeBinding
import ie.wit.contribution.main.ContributionApp
import ie.wit.contribution.models.ContributionModel
import timber.log.Timber

class Contribute : AppCompatActivity() {
    //Allow  'attach' to the views on our layout
    private lateinit var contributeLayout : ActivityContributeBinding
    lateinit var app: ContributionApp
    var totalDonated = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contributeLayout = ActivityContributeBinding.inflate(layoutInflater)
        setContentView(contributeLayout.root)
        app = this.application as ContributionApp

        //set the progressBar max value
        contributeLayout.progressBar.max = 10000

        contributeLayout.amountPicker.minValue = 1
        contributeLayout.amountPicker.maxValue = 1000

        contributeLayout.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            contributeLayout.paymentAmount.setText("$newVal")
        }

        //var totalDonated = 0
        // Contribute buttom
        contributeLayout.contributeButton.setOnClickListener {
            val amount = if (contributeLayout.paymentAmount.text.isNotEmpty())
                contributeLayout.paymentAmount.text.toString().toInt() else contributeLayout.amountPicker.value
            if(totalDonated >= contributeLayout.progressBar.max)
                Toast.makeText(applicationContext,"contribute Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(contributeLayout.paymentMethod.checkedRadioButtonId == R.id.Direct)
                    "Direct" else "Paypal"
                totalDonated += amount
                contributeLayout.totalSoFar.text = getString(R.string.totalSoFar,totalDonated)
                contributeLayout.progressBar.progress = totalDonated
                app.contributionsStore.create(ContributionModel(paymentmethod = paymentmethod,amount = amount))
                Timber.i("Total contributed so far $totalDonated")
            }
        }

    }
}