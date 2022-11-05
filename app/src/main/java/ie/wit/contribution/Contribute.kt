package ie.wit.contribution

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.contribution.databinding.ActivityContributeBinding

class Contribute : AppCompatActivity() {
    //Allow  'attach' to the views on our layout
    private lateinit var contribueLayout : ActivityContributeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contribueLayout = ActivityContributeBinding.inflate(layoutInflater)
        setContentView(contribueLayout.root)
        //app = this.application as ContributionApp

        //set the progressBar max value
        contribueLayout.progressBar.max = 10000

        contribueLayout.amountPicker.minValue = 1
        contribueLayout.amountPicker.maxValue = 1000

        contribueLayout.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            contribueLayout.paymentAmount.setText("$newVal")
        }

    }
}