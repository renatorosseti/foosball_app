package com.rosseti.tmgfoosball.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.base.BaseFragment
import com.rosseti.tmgfoosball.databinding.FragmentScoreListBinding
import com.rosseti.tmgfoosball.ui.list.adapter.ScoreViewAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ScoreListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ScoreListViewModel

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
        viewModel = ViewModelProvider(this, this.viewModelFactory).get(ScoreListViewModel::class.java)
        setupAdapter()
        setupButton()
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

    private fun setupButton() {
        binding.newScore.setOnClickListener {
            findNavController().navigate(
                R.id.action_ScoreListFragment_to_scoreDetailsFragment
            )
        }
    }

    private fun observeActions() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ScoreListViewState.ShowLoadingState -> {
                    progressDialog.show(requireContext())
                }
                is ScoreListViewState.ShowContentFeed -> {
                    adapter.submitData(lifecycle, it.scores)
                    adapter.addLoadStateListener { loadState ->
                        if (loadState.source.append == LoadState.NotLoading(endOfPaginationReached = true)) {
                            progressDialog.hide()
                        }
                    }
                }
                is ScoreListViewState.ShowNetworkError -> {
                    dialog.show(
                        context = requireContext(),
                        message = getString(R.string.error_internet)
                    )
                }
            }
        })
    }
}