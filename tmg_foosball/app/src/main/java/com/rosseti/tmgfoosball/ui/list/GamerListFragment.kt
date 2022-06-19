package com.rosseti.tmgfoosball.ui.list

import android.os.Bundle
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
import com.rosseti.tmgfoosball.databinding.FragmentGamerListBinding
import com.rosseti.tmgfoosball.ui.adapter.GamerViewAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class GamerListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GameListViewModel

    lateinit var binding: FragmentGamerListBinding

    lateinit var adapter: GamerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gamer_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, this.viewModelFactory).get(GameListViewModel::class.java)
        setupAdapter()
        setupButton()
        observeActions()
        viewModel.getScoreList()
    }

    private fun setupAdapter() {
        adapter = GamerViewAdapter {
            findNavController().navigate(
                R.id.action_gamerListFragment_to_gamerDetailsFragment,
                bundleOf("gamer" to it)
            )
        }
        binding.list.adapter = adapter
    }

    private fun setupButton() {
        binding.newScore.setOnClickListener {
            findNavController().navigate(
                R.id.action_gamerListFragment_to_gamerDetailsFragment
            )
        }
    }

    private fun observeActions() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it) {
                is GamerListViewState.ShowLoadingState -> {
                    progressDialog.show(requireContext())
                }
                is GamerListViewState.ShowContentFeed -> {
                    adapter.submitData(lifecycle, it.scores)
                    adapter.addLoadStateListener { loadState ->
                        if (loadState.source.append == LoadState.NotLoading(endOfPaginationReached = true)) {
                            progressDialog.hide()
                        }
                    }
                }
                is GamerListViewState.ShowNetworkError -> {
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