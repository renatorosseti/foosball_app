package com.rosseti.tmgfoosball.ui.list

import android.os.Bundle
import android.util.Log
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
import com.rosseti.domain.entity.ResultEntity
import com.rosseti.tmgfoosball.R
import com.rosseti.tmgfoosball.base.BaseFragment
import com.rosseti.tmgfoosball.databinding.FragmentResultListBinding
import com.rosseti.tmgfoosball.databinding.FragmentScoreListBinding
import com.rosseti.tmgfoosball.ui.list.adapter.ResultViewAdapter
import com.rosseti.tmgfoosball.ui.list.adapter.ScoreViewAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ResultListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ScoreListViewModel

    lateinit var binding: FragmentResultListBinding

    lateinit var adapter: ResultViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        viewModel =
            ViewModelProvider(this, this.viewModelFactory).get(ScoreListViewModel::class.java)
        setupAdapter()
        setupButton()
        viewModel.getScoreList()
    }

    private fun setupAdapter() {
        adapter = ResultViewAdapter {
            Log.i("ObserveActions", "Item: $it")
            findNavController().navigate(
                R.id.action_ScoreListFragment_to_scoreDetailsFragment,
                bundleOf("score" to it)
            )
        }
        binding.list.adapter = adapter
        if (arguments != null) {
            val results: List<ResultEntity> =
                arguments?.getParcelableArrayList<ResultEntity>("results")?.toList() ?: emptyList()
            adapter.submitData(lifecycle, PagingData.from(results))
            adapter.addLoadStateListener { loadState ->
                if (loadState.source.append == LoadState.NotLoading(endOfPaginationReached = true)) {
                    progressDialog.hide()
                }
            }
        }
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
        binding.newScore.setOnClickListener {
            findNavController().navigate(
                R.id.action_ScoreListFragment_to_scoreDetailsFragment
            )
        }
    }
}