package ie.wit.contribution.ui.report

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.contribution.R
import ie.wit.contribution.adapters.ContributionAdapter
import ie.wit.contribution.adapters.ContributionClickListener
import ie.wit.contribution.databinding.FragmentReportBinding
import ie.wit.contribution.main.ContributionApp
import ie.wit.contribution.models.ContributionModel
import ie.wit.contribution.utils.SwipeToDeleteCallback
import ie.wit.contribution.utils.SwipeToEditCallback

class ReportFragment : Fragment(), ContributionClickListener {

    lateinit var app: ContributionApp
    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var reportViewModel: ReportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        reportViewModel.observableContributionsList.observe(viewLifecycleOwner, Observer {
        //        contributions ->
        //    contributions?.let { render(contributions) }
                contributions ->
            contributions?.let {
                render(contributions as ArrayList<ContributionModel>)
                //hideLoader(loader)
                //checkSwipeRefresh()
            }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToContributeFragment()
            findNavController().navigate(action)
        }
        //setSwipeRefresh()
        // SWIPE LEFT - DELETE
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //showLoader(loader,"Deleting Donation")
                val adapter = fragBinding.recyclerView.adapter as ContributionAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                //reportViewModel.delete(viewHolder.itemView.tag as String)
                //hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_report, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())

            }     }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    //private fun render(contributionsList: List<ContributionModel>) {
    private fun render(contributionsList: ArrayList<ContributionModel>) {
        fragBinding.recyclerView.adapter = ContributionAdapter(contributionsList,this)
        if (contributionsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.donationsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.donationsNotFound.visibility = View.GONE
        }
    }

    override fun onContributionClick(contribution: ContributionModel) {
        val action = ReportFragmentDirections.actionReportFragmentToContributionDetailFragment(contribution.id)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        reportViewModel.load()
    }

    //override fun onDestroyView() {
    //    super.onDestroyView()
    //    _fragBinding = null
    //}
}