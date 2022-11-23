package ie.wit.contribution.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.contribution.R
import ie.wit.contribution.databinding.CardContributionBinding
import ie.wit.contribution.models.ContributionModel

interface ContributionClickListener {
    fun onContributionClick(contribution: ContributionModel)
}

class ContributionAdapter constructor(private var contributions: List<ContributionModel>,
                                  private val listener: ContributionClickListener)
    : RecyclerView.Adapter<ContributionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardContributionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val contribution = contributions[holder.adapterPosition]
        holder.bind(contribution,listener)
    }

    override fun getItemCount(): Int = contributions.size

    inner class MainHolder(val binding : CardContributionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contribution: ContributionModel, listener: ContributionClickListener) {
            binding.contribution = contribution
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onContributionClick(contribution) }
            binding.executePendingBindings()
        }
    }
}
