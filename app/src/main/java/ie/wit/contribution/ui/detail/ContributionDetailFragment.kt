package ie.wit.contribution.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.contribution.databinding.FragmentContributionDetailBinding
import ie.wit.contribution.ui.auth.LoggedInViewModel
import ie.wit.contribution.ui.report.ReportViewModel
import timber.log.Timber


class ContributionDetailFragment : Fragment() {

    private lateinit var detailViewModel: ContributionDetailViewModel
    private val args by navArgs<ContributionDetailFragmentArgs>()
    private var _fragBinding: FragmentContributionDetailBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val reportViewModel : ReportViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentContributionDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(ContributionDetailViewModel::class.java)
        detailViewModel.observableContribution.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editContributionButton.setOnClickListener {
            detailViewModel.updateContribution(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.contributionid, fragBinding.contributionvm?.observableContribution!!.value!!)
            findNavController().navigateUp()
        }

        fragBinding.deleteContributionButton.setOnClickListener {
            reportViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.email!!,
                detailViewModel.observableContribution.value?.uid!!)
            findNavController().navigateUp()
        }

        return root
    }

    private fun render() {


        fragBinding.editMessage.setText("A Message")
        fragBinding.editUpvotes.setText("0")
        fragBinding.contributionvm = detailViewModel
        Timber.i("Retrofit fragBinding.contributionvm == $fragBinding.contributionvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getContribution(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.contributionid)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}