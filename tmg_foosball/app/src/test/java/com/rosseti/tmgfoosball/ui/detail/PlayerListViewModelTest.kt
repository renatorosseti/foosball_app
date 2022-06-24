package com.rosseti.tmgfoosball.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.usecase.GetGamesUseCase
import com.rosseti.domain.usecase.GetPlayersUseCase
import com.rosseti.tmgfoosball.ui.list.PlayerListViewModel
import com.rosseti.tmgfoosball.ui.list.PlayerListViewState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PlayerListViewModelTest {
    private lateinit var viewModel: PlayerListViewModel

    @MockK
    lateinit var getPlayersUseCase: GetPlayersUseCase

    @MockK
    lateinit var getGamesUseCase: GetGamesUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = PlayerListViewModel(
            getPlayersUseCase,
            getGamesUseCase
        )
    }

    @Test
    fun `Given empty players, when call api playerList, should return the player list`() {
        //given
        val listPlayers = listOf(PlayerEntity())

        every {
            getPlayersUseCase()
        } returns Single.just(listPlayers)
        every {
            getGamesUseCase(listPlayers)
        } returns Single.just(listPlayers)

        //when
        viewModel.getPlayerList()

        //to check the one value for testing
        Assert.assertNotNull(viewModel.playerList)
        Assert.assertEquals(
            PlayerListViewState.ShowContentFeed(listPlayers),
            viewModel.response.value
        )
    }

    @Test
    fun `Given error emission, when call api playerList with error, should return error`() {
        //Given
        val error = RuntimeException("Unknown error")

        every {
            getPlayersUseCase()
        } returns Single.error(error)

        //when
        viewModel.getPlayerList()

        //then
        Assert.assertEquals(
            PlayerListViewState.ShowNetworkError(error),
            viewModel.response.value
        )
    }
}