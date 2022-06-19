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
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.base.BaseFragment
import com.rosseti.tmgfoosball.databinding.FragmentGamerDetailsBinding
import com.rosseti.tmgfoosball.ui.adapter.GameViewAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class GamerDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GamerDetailsViewModel

    lateinit var binding: FragmentGamerDetailsBinding

    lateinit var adapter: GameViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_gamer_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        viewModel =
            ViewModelProvider(this, this.viewModelFactory).get(GamerDetailsViewModel::class.java)
        setupAdapter()
        setupScoreEntity()
        setupUiButtons()
        observeActions()
    }

    private fun setupAdapter() {
        adapter = GameViewAdapter {
            findNavController().navigate(
                R.id.action_gamerDetailsFragment_to_gameDetailsFragment,
                bundleOf("gamer" to viewModel.gamerDetail, "game" to it)
            )
        }
        binding.list.adapter = adapter


    }

    private fun setupScoreEntity() {
        if (arguments != null) {
            val gamerEntity = arguments?.getParcelable<GamerEntity>("gamer")
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
                    bundleOf("gamer" to viewModel.gamerDetail)
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
                is GamerDetailsViewState.ShowLoadingState -> {
                    progressDialog.show(requireContext())
                }
                is GamerDetailsViewState.ShowContent -> {
                    progressDialog.hide()
                    binding.item = it.gamer
                }
                is GamerDetailsViewState.ShowNetworkError -> {
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