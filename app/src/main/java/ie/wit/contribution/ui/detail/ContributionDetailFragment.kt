package ie.wit.contribution.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.wit.contribution.R

class ContributionDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ContributionDetailFragment()
    }

    private lateinit var viewModel: ContributionDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contribution_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContributionDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}