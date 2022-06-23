package com.rosseti.tmgfoosball.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.base.BaseFragment
import com.rosseti.tmgfoosball.databinding.FragmentPlayerDetailsBinding
import com.rosseti.tmgfoosball.ui.adapter.GameViewAdapter
import dagger.android.support.AndroidSupportInjection

class PlayerDetailsFragment : BaseFragment() {

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
        val playerEntity = setupPlayerEntity()
        setupAdapter(playerEntity)
        setupUiButtons(playerEntity)
    }

    private fun setupAdapter(playerEntity: PlayerEntity) {
        adapter = GameViewAdapter {
            findNavController().navigate(
                R.id.action_gamerDetailsFragment_to_gameDetailsFragment,
                bundleOf("player" to playerEntity, "game" to it)
            )
        }
        adapter.submitData(lifecycle, PagingData.from(playerEntity.games))
        progressDialog.hide()
        binding.list.adapter = adapter
    }

    private fun setupPlayerEntity(): PlayerEntity {
        val playerEntity = arguments?.getParcelable("player") ?: PlayerEntity()
        binding.item = playerEntity
        return playerEntity
    }

    private fun setupUiButtons(playerEntity: PlayerEntity) {
        binding.apply {
            newGameButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_gamerDetailsFragment_to_gameDetailsFragment,
                    bundleOf("player" to playerEntity)
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
}