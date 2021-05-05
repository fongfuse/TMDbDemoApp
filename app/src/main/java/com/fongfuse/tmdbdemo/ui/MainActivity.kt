package com.fongfuse.tmdbdemo.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fongfuse.tmdbdemo.R
import com.fongfuse.tmdbdemo.ui.base.BaseActivity
import com.fongfuse.tmdbdemo.databinding.ActivityMainBinding
import com.fongfuse.tmdbdemo.extension.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModel()

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initRecyclerView()
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    private fun initView() {
        binding.swRefresh.setOnRefreshListener {
            viewModel.getAllCars()
        }
    }

    override fun initViewModel() {
        viewModel.getAllCars()

        viewModel.loading.observe(this, {
            if (it) {
                if (!binding.swRefresh.isRefreshing) {
                    binding.layoutLoading.root.visibility = View.VISIBLE
                }
            } else {
                binding.swRefresh.isRefreshing = false
                binding.layoutLoading.root.visibility = View.GONE
            }
        })

        viewModel.error.observe(this, {
            toast(it)
        })

        viewModel.networkError.observe(this, {
            if (it) {
                toast(getString(R.string.no_internet))
            }
        })

        viewModel.items.observe(this, {
            mainAdapter.setItems(it.toMutableList())
        })
    }

    private fun initRecyclerView() {
        binding.rvItem.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mainAdapter
        }
    }
}