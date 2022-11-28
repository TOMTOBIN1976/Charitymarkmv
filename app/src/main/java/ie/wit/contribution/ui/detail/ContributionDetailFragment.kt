package ie.wit.contribution.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ie.wit.contribution.databinding.FragmentContributionDetailBinding


class ContributionDetailFragment : Fragment() {

    private lateinit var detailViewModel: ContributionDetailViewModel
    private val args by navArgs<ContributionDetailFragmentArgs>()
    private var _fragBinding: FragmentContributionDetailBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentContributionDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(ContributionDetailViewModel::class.java)
        detailViewModel.observableContribution.observe(viewLifecycleOwner, Observer { render() })
        return root
    }

    private fun render(/*contribution: ContributionModel*/) {
        // fragBinding.editAmount.setText(contribution.amount.toString())
        // fragBinding.editPaymenttype.text = contribution.paymentmethod
        fragBinding.editMessage.setText("A Message")
        fragBinding.editUpvotes.setText("0")
        fragBinding.contributionvm = detailViewModel
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getContribution(args.contributionid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}