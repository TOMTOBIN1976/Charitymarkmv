package ie.wit.contribution.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.contribution.R
import ie.wit.contribution.databinding.CardContributionBinding
import ie.wit.contribution.models.ContributionModel

class ContributionAdapter constructor(private var contributions: List<ContributionModel>)
    : RecyclerView.Adapter<ContributionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardContributionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val contribution = contributions[holder.adapterPosition]
        holder.bind(contribution)
    }

    override fun getItemCount(): Int = contributions.size

    inner class MainHolder(val binding : CardContributionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contribution: ContributionModel) {
            binding.paymentamount.text = contribution.amount.toString()
            binding.paymentmethod.text = contribution.paymentmethod
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}
