package com.rosseti.tmgfoosball.scores

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.ScoreViewAdapter
import com.rosseti.tmgfoosball.databinding.FragmentScoreListBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ScoreListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ScoreViewModel

    lateinit var binding: FragmentScoreListBinding

    lateinit var adapter: ScoreViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, this.viewModelFactory).get(ScoreViewModel::class.java)
        setupAdapter()
        observeActions()
        viewModel.getScoreList()
    }

    private fun setupAdapter() {
        adapter = ScoreViewAdapter {
            Log.i("ObserveActions", "Item: $it")
            findNavController().navigate(
                R.id.action_ScoreListFragment_to_scoreDetailsFragment,
                bundleOf("score" to it)
            )
        }
        binding.list.adapter = adapter

    }

    private fun observeActions() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ScoreListViewState.ShowContentFeed -> {
                    Log.i("ObserveActions", "Scores: ${it.scores}")
                    adapter.submitData(lifecycle, it.scores)

                }
            }
        })
    }
}