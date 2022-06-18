package com.rosseti.tmgfoosball.scores

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.databinding.FragmentScoreDetailsBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ScoreDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ScoreDetailsViewModel

    lateinit var binding: FragmentScoreDetailsBinding

    private var scoreEntity: ScoreEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_score_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        if (arguments != null) {
            scoreEntity = arguments?.getParcelable("score")
            if (scoreEntity != null) {
                binding.item = scoreEntity
            }
        }

        viewModel =
            ViewModelProvider(this, this.viewModelFactory).get(ScoreDetailsViewModel::class.java)
        binding.apply {
            updateScoreButton.setOnClickListener {
                viewModel.updateScore(
                    scoreEntity?.id.toString(),
                    editName.text.toString(),
                    editMatches.text.toString(),
                    editScores.text.toString()
                )
            }
        }

        observeActions()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeActions() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ScoreDetailsViewState.ShowContent -> {
                    Log.i("ObserveActions", "Score: ${it.score}")
                    binding.item = it.score
                }
                else -> {
                    Log.i("ObserveActions", "Else}")
                }
            }
        })
    }
}