package com.rosseti.tmgfoosball.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.rosseti.tmgfoosball.databinding.FragmentGameDetailsBinding
import com.rosseti.tmgfoosball.extension.setupInputError
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class GameDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GameDetailsViewModel

    lateinit var binding: FragmentGameDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        viewModel =
            ViewModelProvider(this, this.viewModelFactory).get(GameDetailsViewModel::class.java)
        setupEntities()
        setupUi()
        observeActions()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onPopBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupUi() {
        val adversaryNames = viewModel.playerDetail.adversaries.toList().map { it.second }

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            adversaryNames
        )
        val spinnerAdversary = binding.spnAdversaryName
        spinnerAdversary.adapter = arrayAdapter
        val game = viewModel.gameDetail
        val playerAdversary = game.adversary
        val position = arrayAdapter.getPosition(playerAdversary)
        spinnerAdversary.setSelection(position)
        setupButton()

    }

    private fun setupButton() {
        setupListeners()
        binding.apply {
            saveButton.setOnClickListener {
                val playerName = editPlayer.text.toString()
                val playerScore = editScore.text.toString()
                val adversaryScore = editAdvScores.text.toString()
                val adversaryName = spnAdversaryName.selectedItem.toString()
                val adversaryId =
                    viewModel.playerDetail?.adversaries.filter { it.value == spnAdversaryName.selectedItem }.keys.first()
                if (validateFields()) {
                    viewModel.requestNewGame(
                        playerName = playerName,
                        adversaryName = adversaryName,
                        score = playerScore,
                        scoreAdversary = adversaryScore,
                        adversaryId = adversaryId
                    )
                }
            }
        }
    }

    private fun validateFields() = validatePlayerName() && validateScore() && validateAdversaryScore()

    private fun setupEntities() {
        if (arguments != null) {
            val playerEntity = arguments?.getParcelable(PLAYER_BUNDLE) ?: PlayerEntity()
            val gameEntity = arguments?.getParcelable(GAME_BUNDLE) ?: GameEntity()

            if (playerEntity.id.isNotEmpty()) {
                binding.editPlayer.isEnabled = false
            }
            binding.player = playerEntity
            viewModel.updatePlayerEntity(playerEntity)

            viewModel.updateGameEntity(gameEntity)
            binding.game = viewModel.gameDetail
        }
    }

    private fun observeActions() {
        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is GameDetailsViewState.ShowLoadingState -> {
                    progressDialog.show(requireContext())
                }
                is GameDetailsViewState.ShowContent -> {
                    hideSoftKeyboard(binding.editAdvScores.windowToken)
                    onPopBackStack()
                    progressDialog.hide()

                }
                is GameDetailsViewState.ShowNetworkError -> {
                    progressDialog.hide()
                    errorDialog.show(
                        context = requireContext(),
                        message = getString(R.string.error_internet)
                    )
                }
            }
        }
    }

    private fun onPopBackStack() {
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set(
            PLAYER_BUNDLE,
            viewModel.playerDetail
        )
        navController.popBackStack()
    }

    private fun validateScore() = (binding.editScore.text.toString() > "0").also { isValid ->
        binding.inputLayoutScores.setupInputError(isValid,requireContext())
    }

    private fun validateAdversaryScore() = (binding.editAdvScores.text.toString() > "0").also { isValid ->
        binding.inputLayoutAdvScores.setupInputError(isValid,requireContext())
    }

    private fun validatePlayerName() = binding.editPlayer.text.toString().isNotEmpty().also { isValid ->
        binding.inputLayoutPlayer.setupInputError(isValid,requireContext())
    }

    private fun setupListeners() {
        binding.editPlayer.addTextChangedListener(TextFieldValidation(binding.editPlayer))
        binding.editScore.addTextChangedListener(TextFieldValidation(binding.editScore))
        binding.editAdvScores.addTextChangedListener(TextFieldValidation(binding.editAdvScores))
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view) {
                binding.editPlayer -> {
                    validatePlayerName()
                }
                binding.editScore -> {
                    validateScore()
                }
                binding.editAdvScores -> {
                    validateAdversaryScore()
                }
            }
        }
    }
}