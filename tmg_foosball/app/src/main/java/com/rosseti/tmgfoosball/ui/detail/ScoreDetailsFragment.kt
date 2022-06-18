package com.rosseti.tmgfoosball.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.base.BaseFragment
import com.rosseti.tmgfoosball.databinding.FragmentScoreDetailsBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ScoreDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ScoreDetailsViewModel

    lateinit var binding: FragmentScoreDetailsBinding

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
        viewModel =
            ViewModelProvider(this, this.viewModelFactory).get(ScoreDetailsViewModel::class.java)
        setupScoreEntity()
        setupButtonScore()
        observeActions()
    }

    private fun setupScoreEntity() {
        if (arguments != null) {
            val scoreEntity = arguments?.getParcelable<ScoreEntity>("score")
            if (scoreEntity != null) {
                viewModel.updateScoreEntity(scoreEntity)
                binding.item = scoreEntity
            }
        }
    }

    private fun setupButtonScore() {
        binding.apply {
            updateScoreButton.setOnClickListener {
                viewModel.requestScore(
                    name = editName.text.toString(),
                    matches = editMatches.text.toString(),
                    scores = editScores.text.toString()
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                hideSoftKeyboard(binding.editName.windowToken)
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeActions() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ScoreDetailsViewState.ShowLoadingState -> {
                    progressDialog.show(requireContext())
                }
                is ScoreDetailsViewState.ShowContent -> {
                    progressDialog.hide()
                    binding.item = it.score
                }
                is ScoreDetailsViewState.ShowNetworkError -> {
                    progressDialog.hide()
                    dialog.show(
                        context = requireContext(),
                        message = getString(R.string.error_internet)
                    )
                }
            }
        })
    }
}