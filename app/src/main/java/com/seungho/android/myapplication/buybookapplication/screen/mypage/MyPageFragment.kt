package com.seungho.android.myapplication.buybookapplication.screen.mypage

import android.app.Activity
import android.content.res.loader.ResourcesProvider
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.seungho.android.myapplication.buybookapplication.adapter.BuyBookRecyclerAdapter
import com.seungho.android.myapplication.buybookapplication.screen.base.BaseFragment
import com.seungho.android.myapplication.buybookapplication.databinding.FragmentMypageBinding
import com.seungho.android.myapplication.buybookapplication.data.db.RoomHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment: BaseFragment<MyPageViewModel, FragmentMypageBinding>() {

    override val viewModel by viewModel<MyPageViewModel>()

    override fun getViewBinding(): FragmentMypageBinding = FragmentMypageBinding.inflate(layoutInflater)

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("559721374462-iss6obc80hkep2oos21a2e17h6eu2q5b.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    private val gsc by lazy { GoogleSignIn.getClient(requireActivity(), gso) }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                task.getResult(ApiException::class.java)?.let { account ->
                    viewModel.saveToken(account.idToken ?: throw Exception())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val resourcesProvider by inject<ResourcesProvider>()

    var roomHelper: RoomHelper? = null

    private var isFirstShown = false

    override fun onResume() {
        super.onResume()
        if (isFirstShown.not()) {
            isFirstShown = true
        } else {
            viewModel.fetchData()
        }
    }

    override fun initViews() = with(binding) {
        btnSign.setOnClickListener {
            signInGoogle()
        }
        btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            viewModel.signOut()
        }
    }

    private fun signInGoogle() {
        val signIntent = gsc.signInIntent
        loginLauncher.launch(signIntent)
    }

    override fun observeData() = viewModel.myPageStateLiveData.observe(viewLifecycleOwner) {
        when (it) {
            is MyPageState.Loading -> handleLoadingState()
            is MyPageState.Success -> handleSuccessState(it)
            is MyPageState.Login -> handleLoginState(it)
            is MyPageState.Error -> handleErrorState(it)
            else -> Unit
        }
    }

    private fun handleLoadingState() {
        binding.loginRequiredGroup.isGone = true
        binding.progressBar.isVisible = true
    }

    private fun handleSuccessState(state: MyPageState.Success) = with(binding) {
        progressBar.isGone = true
        when (state) {
            is MyPageState.Success.Registered -> {
                handleRegisteredState(state)
            }
            is MyPageState.Success.NotRegistered -> {
                profileGroup.isGone = true
                loginRequiredGroup.isVisible = true
            }
        }
    }

    private fun handleRegisteredState(state: MyPageState.Success.Registered) = with(binding) {
        profileGroup.isVisible = true
        loginRequiredGroup.isGone = true
        Glide.with(requireActivity())
            .load(state.profileImageUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(120, 120)
            .into(profileImageView)
        userNameTextView.text = state.userName
        roomHelper = Room.databaseBuilder(requireContext(), RoomHelper::class.java, "room_book")
            .build()

        val adapter = BuyBookRecyclerAdapter()
        adapter.roomHelper = roomHelper
        GlobalScope.launch(Dispatchers.IO) {
            adapter.listData.addAll(roomHelper?.roomBookDao()?.getBookList()?: listOf())
        }
        binding.myRecyclerView.adapter = adapter
        binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.notifyDataSetChanged()
    }

    private fun handleLoginState(state: MyPageState.Login) {
        binding.progressBar.isVisible = true
        val credential = GoogleAuthProvider.getCredential(state.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    viewModel.setUserInfo(user)
                } else {
                    firebaseAuth.signOut()
                    viewModel.setUserInfo(null)
                }
            }
    }

    private fun handleErrorState(state: MyPageState.Error) {
        Toast.makeText(requireContext(), state.messageId, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = MyPageFragment()

        const val TAG = "MyPageFragment"
    }
}