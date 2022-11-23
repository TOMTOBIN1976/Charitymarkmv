package ie.wit.contribution.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import ie.wit.contribution.R

class ContributionDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ContributionDetailFragment()
    }

    private lateinit var viewModel: ContributionDetailViewModel
    private val args by navArgs<ContributionDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_contribution_detail, container, false)

        Toast.makeText(context,"Contribution ID Selected : ${args.contributionid}",Toast.LENGTH_LONG).show()

        return view
    }


}