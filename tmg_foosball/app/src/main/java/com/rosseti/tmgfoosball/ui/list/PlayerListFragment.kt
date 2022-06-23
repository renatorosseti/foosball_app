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
import androidx.paging.PagingData
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.base.BaseFragment
import com.rosseti.tmgfoosball.databinding.FragmentPlayerListBinding
import com.rosseti.tmgfoosball.ui.adapter.PlayerViewAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PlayerListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PlayerListViewModel

    lateinit var binding: FragmentPlayerListBinding

    lateinit var adapter: PlayerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, this.viewModelFactory).get(PlayerListViewModel::class.java)
        setupAdapter()
        setupButton()
        observeActions()
        viewModel.getPlayerList()
    }

    private fun setupAdapter() {
        adapter = PlayerViewAdapter {
            findNavController().navigate(
                R.id.action_playerListFragment_to_playerDetailsFragment,
                bundleOf("player" to it)
            )
        }
        binding.list.adapter = adapter
    }

    private fun setupButton() {
        binding.newPlayer.setOnClickListener {
            findNavController().navigate(
                R.id.action_PlayerListFragment_to_gameDetailsFragment
            )
        }
    }

    private fun observeActions() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it) {
                is PlayerListViewState.ShowLoadingState -> {
                    progressDialog.show(requireContext())
                }
                is PlayerListViewState.ShowContentFeed -> {
                    adapter.submitData(lifecycle, PagingData.from(it.players))
                    progressDialog.hide()
                }
                is PlayerListViewState.ShowNetworkError -> {
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