package ie.wit.contribution.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.contribution.R
import ie.wit.contribution.databinding.ActivityReportBinding
import ie.wit.contribution.main.ContributionApp
import ie.wit.contribution.adapters.ContributionAdapter

lateinit var app: ContributionApp
lateinit var reportLayout : ActivityReportBinding

class Report : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_report)
        reportLayout = ActivityReportBinding.inflate(layoutInflater)
        setContentView(reportLayout.root)

        app = this.application as ContributionApp
        reportLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        reportLayout.recyclerView.adapter = ContributionAdapter(app.contributionsStore.findAll())
    }

    // Add menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_report, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_contribute -> { startActivity(
                Intent(this,
                    Contribute::class.java)
            )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}