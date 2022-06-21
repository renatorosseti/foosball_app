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
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.base.BaseFragment
import com.rosseti.tmgfoosball.databinding.FragmentGameDetailBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class GameDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PlayerDetailsViewModel

    lateinit var binding: FragmentGameDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this, this.viewModelFactory).get(PlayerDetailsViewModel::class.java)
        setupEntities()
        setupButton()
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

    private fun setupButton() {
        binding.apply {
            saveButton.setOnClickListener {
                viewModel.requestNewGame(editName.text.toString(), editAdversary.text.toString(), editScore.text.toString(), editAdversary.text.toString())
            }
        }
    }

    private fun setupEntities() {
        if (arguments != null) {
            val playerEntity = arguments?.getParcelable<PlayerEntity>("gamer")
            val gameEntity = arguments?.getParcelable<GameEntity>("game")
            if (playerEntity != null) {
                binding.player = playerEntity
                viewModel.updateGamerEntity(playerEntity)
            }
            if (gameEntity != null) {
                binding.game = gameEntity
                viewModel.updateGameEntity(gameEntity)
            }
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