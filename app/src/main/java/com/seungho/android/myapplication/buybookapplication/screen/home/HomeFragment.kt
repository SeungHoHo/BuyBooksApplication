package com.seungho.android.myapplication.buybookapplication.screen.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.seungho.android.myapplication.buybookapplication.R
import com.seungho.android.myapplication.buybookapplication.adapter.BookRecyclerAdapter
import com.seungho.android.myapplication.buybookapplication.screen.base.BaseFragment
import com.seungho.android.myapplication.buybookapplication.databinding.FragmentHomeBinding
import com.seungho.android.myapplication.buybookapplication.data.response.Item
import com.seungho.android.myapplication.buybookapplication.data.response.Naver
import com.seungho.android.myapplication.buybookapplication.data.repository.NaverApiRepository
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(), Callback<Naver> {

    private val naverAPI: NaverApiRepository by inject()
    private val clientId = "V5mFQJyJwjTxSbG9RLVD"
    private val clientSecret = "OiqxNW56Pj"
    private var word: String = ""


    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.bookHomeStateLiveData.observe(viewLifecycleOwner) {

    }

    override fun initViews() = with(binding) {
        btnSearch.setOnClickListener {
            getSimRepository()
        }
        btnSim.setOnClickListener {
            getSimRepository()
        }
        btnDate.setOnClickListener {
            getDateRepository()
        }
        btnCount.setOnClickListener {
            getCountRepository()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun getSimRepository() {
        word = binding.searchEditText.text.toString()
        binding.btnSim.setBackgroundResource(R.drawable.able_button)
        binding.btnSim.setTextColor(R.color.black)
        binding.btnDate.setBackgroundResource(R.drawable.disable_button)
        binding.btnDate.setTextColor(R.color.white)
        binding.btnCount.setBackgroundResource(R.drawable.disable_button)
        binding.btnCount.setTextColor(R.color.white)
        if (word == "" || word.isEmpty()) {
            Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        } else {
            naverAPI.getApiList(clientId, clientSecret, word, 1, 100, "sim").enqueue(this)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun getDateRepository() {
        word = binding.searchEditText.text.toString()
        binding.btnSim.setBackgroundResource(R.drawable.disable_button)
        binding.btnSim.setTextColor(R.color.white)
        binding.btnDate.setBackgroundResource(R.drawable.able_button)
        binding.btnDate.setTextColor(R.color.black)
        binding.btnCount.setBackgroundResource(R.drawable.disable_button)
        binding.btnCount.setTextColor(R.color.white)
        if (word == "" || word.isEmpty()) {
            Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        } else {
            naverAPI.getApiList(clientId, clientSecret, word, 1, 100, "date").enqueue(this)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun getCountRepository() {
        word = binding.searchEditText.text.toString()
        binding.btnSim.setBackgroundResource(R.drawable.disable_button)
        binding.btnSim.setTextColor(R.color.white)
        binding.btnDate.setBackgroundResource(R.drawable.disable_button)
        binding.btnDate.setTextColor(R.color.white)
        binding.btnCount.setBackgroundResource(R.drawable.able_button)
        binding.btnCount.setTextColor(R.color.black)
        if (word == "" || word.isEmpty()) {
            Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        } else {
            naverAPI.getApiList(clientId, clientSecret, word, 1, 100, "count").enqueue(this)
        }
    }

    override fun onResponse(call: Call<Naver>, response: Response<Naver>) {
        if (response.isSuccessful) {
            val body = response.body()
            body.let {
                Toast.makeText(requireContext(), "응답 성공", Toast.LENGTH_SHORT).show()
                Log.d(">>", response.raw().toString())
                Log.d(">>", response.body().toString())
                Log.d(">>", response.errorBody().toString())
                updateUI(it!!.items!!)
            }
        }
    }

    override fun onFailure(call: Call<Naver>, t: Throwable) {
        Toast.makeText(requireContext(), "응답 실패", Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(listData: List<Item>) {
        val adapter = BookRecyclerAdapter(listData, requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance() = HomeFragment()
        const val TAG = "HomeFragment"
    }
}