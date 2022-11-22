package ie.wit.contribution.ui.contribute

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.contribution.R
import ie.wit.contribution.databinding.FragmentContributeBinding
import ie.wit.contribution.main.ContributionApp
import ie.wit.contribution.models.ContributionModel
import ie.wit.contribution.ui.report.ReportViewModel

class DonateFragment : Fragment() {

    var totalDonated = 0
    private var _fragBinding: FragmentContributeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    private lateinit var donateViewModel: ContributeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragBinding = FragmentContributeBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_contribute)
        setupMenu()

        donateViewModel = ViewModelProvider(this).get(ContributeViewModel::class.java)
        donateViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        fragBinding.progressBar.max = 10000
        fragBinding.amountPicker.minValue = 1
        fragBinding.amountPicker.maxValue = 1000

        fragBinding.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            fragBinding.paymentAmount.setText("$newVal")
        }
        setButtonListener(fragBinding)
        return root;
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_contribute, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }       }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.contributionError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentContributeBinding) {
        layout.contributeButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
            if(totalDonated >= layout.progressBar.max)
                Toast.makeText(context,"Donate Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalDonated += amount
                layout.totalSoFar.text = getString(R.string.totalSoFar,totalDonated)
                layout.progressBar.progress = totalDonated

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        val reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        reportViewModel.observableDonationsList.observe(viewLifecycleOwner, Observer {
            totalDonated = reportViewModel.observableDonationsList.value!!.sumOf { it.amount }
            fragBinding.progressBar.progress = totalDonated
            fragBinding.totalSoFar.text = getString(R.string.totalSoFar,totalDonated)
        })
    }
}