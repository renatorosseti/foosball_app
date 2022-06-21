package com.rosseti.tmgfoosball.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.base.BaseFragment
import com.rosseti.tmgfoosball.databinding.FragmentPlayerDetailsBinding
import com.rosseti.tmgfoosball.ui.adapter.GameViewAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PlayerDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PlayerDetailsViewModel

    lateinit var binding: FragmentPlayerDetailsBinding

    lateinit var adapter: GameViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_player_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        viewModel =
            ViewModelProvider(this, this.viewModelFactory).get(PlayerDetailsViewModel::class.java)
        setupAdapter()
        setupScoreEntity()
        setupUiButtons()
        observeActions()
    }

    private fun setupAdapter() {
        adapter = GameViewAdapter {
            findNavController().navigate(
                R.id.action_gamerDetailsFragment_to_gameDetailsFragment,
                bundleOf("gamer" to viewModel.playerDetail, "game" to it)
            )
        }
        binding.list.adapter = adapter
    }

    private fun setupScoreEntity() {
        if (arguments != null) {
            val gamerEntity = arguments?.getParcelable<PlayerEntity>("gamer")
            if (gamerEntity != null) {
                viewModel.updateGamerEntity(gamerEntity)
                binding.item = gamerEntity
                adapter.submitData(lifecycle, PagingData.from(gamerEntity.games))
                adapter.addLoadStateListener { loadState ->
                    if (loadState.source.append == LoadState.NotLoading(endOfPaginationReached = true)) {
                        progressDialog.hide()
                    }
                }
            }
        }
    }

    private fun setupUiButtons() {
        binding.apply {
            newGameButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_gamerDetailsFragment_to_gameDetailsFragment,
                    bundleOf("gamer" to viewModel.playerDetail)
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
                is PlayerDetailsViewState.ShowLoadingState -> {
                    progressDialog.show(requireContext())
                }
                is PlayerDetailsViewState.ShowContent -> {
                    progressDialog.hide()
                    binding.item = it.player
                }
                is PlayerDetailsViewState.ShowNetworkError -> {
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