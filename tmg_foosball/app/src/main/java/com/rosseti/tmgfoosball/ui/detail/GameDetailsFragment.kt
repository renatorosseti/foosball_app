package com.rosseti.tmgfoosball.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
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
    private lateinit var viewModel: GameDetailsViewModel

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
        viewModel = ViewModelProvider(this, this.viewModelFactory).get(GameDetailsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        setupEntities()
        setupUi()
        observeActions()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("player", viewModel.playerDetail)
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupUi() {
        binding.apply {
            val adversaryNames = viewModel.playerDetail.adversaries.toList().map { it.second }

            val arrayAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                adversaryNames
            )

            spnAdversaryName.adapter = arrayAdapter
            val game = viewModel.gameDetail
            val playerAdversary = game.adversary
            val position = arrayAdapter.getPosition(playerAdversary)
            spnAdversaryName.setSelection(position)

            saveButton.setOnClickListener {
                val advId =
                    viewModel.playerDetail.adversaries.filter { it.value == spnAdversaryName.selectedItem }.keys.first()
                viewModel.requestNewGame(
                    playerName = editPlayer.text.toString(),
                    adversaryName = spnAdversaryName.selectedItem.toString(),
                    score = editScore.text.toString(),
                    scoreAdversary = editAdvScores.text.toString(),
                    adversaryId = advId
                )
            }
        }
    }

    private fun setupEntities() {
        if (arguments != null) {
            val playerEntity = arguments?.getParcelable("player") ?: PlayerEntity()
            val gameEntity = arguments?.getParcelable("game") ?: GameEntity()

            if (playerEntity.id.isNotEmpty()) {
                binding.editPlayer.isEnabled = false
            }
            binding.player = playerEntity
            viewModel.updatePlayerEntity(playerEntity)

            binding.game = gameEntity
            viewModel.updateGameEntity(gameEntity)
        }
    }

    private fun observeActions() {
        viewModel.response.observe(viewLifecycleOwner) {
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
        }
    }
}