package ie.wit.contribution.ui.contribute

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.contribution.R
import ie.wit.contribution.databinding.FragmentContributeBinding
import ie.wit.contribution.main.ContributionApp
import ie.wit.contribution.models.ContributionModel

class ContributeFragment : Fragment() {

    lateinit var app: ContributionApp
    private var totalcontributed = 0
    private var _fragBinding: FragmentContributeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    //lateinit var navController: NavController
    private lateinit var contributeViewModel: ContributeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as ContributionApp
        //setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _fragBinding = FragmentContributeBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_contribute)
        setupMenu()
        contributeViewModel =
            ViewModelProvider(this).get(contributeViewModel::class.java)
        //val textView: TextView = root.findViewById(R.id.text_home)
        contributeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
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
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ContributeFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun setButtonListener(layout: FragmentContributeBinding) {
        layout.contributeButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
            if(totalcontributed >= layout.progressBar.max)
                Toast.makeText(context,"contribute Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalcontributed += amount
                layout.totalSoFar.text = getString(R.string.totalSoFar,totalcontributed)
                layout.progressBar.progress = totalcontributed
                app.contributionsStore.create(ContributionModel(paymentmethod = paymentmethod,amount = amount))
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_contribute, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(item,
//                requireView().findNavController()) || super.onOptionsItemSelected(item)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        totalcontributed = app.contributionsStore.findAll().sumOf { it.amount }
        fragBinding.progressBar.progress = totalcontributed
        fragBinding.totalSoFar.text = getString(R.string.totalSoFar,totalcontributed)
    }
}