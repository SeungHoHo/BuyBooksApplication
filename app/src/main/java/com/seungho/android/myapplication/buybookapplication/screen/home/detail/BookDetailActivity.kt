package com.seungho.android.myapplication.buybookapplication.screen.home.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.seungho.android.myapplication.buybookapplication.MainTabMenu
import com.seungho.android.myapplication.buybookapplication.util.event.MenuChangeEventBus
import com.seungho.android.myapplication.buybookapplication.screen.base.BaseActivity
import com.seungho.android.myapplication.buybookapplication.databinding.ActivityBookDetailBinding
import com.seungho.android.myapplication.buybookapplication.data.response.Item
import com.seungho.android.myapplication.buybookapplication.data.entity.RoomBook
import com.seungho.android.myapplication.buybookapplication.data.db.RoomHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookDetailActivity: BaseActivity<BookDetailViewModel, ActivityBookDetailBinding>() {

    override fun getViewBinding(): ActivityBookDetailBinding = ActivityBookDetailBinding.inflate(layoutInflater)

    override val viewModel by viewModel<BookDetailViewModel>()

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val menuChangeEventBus by inject<MenuChangeEventBus>()

    private var roomHelper: RoomHelper? = null

    override fun observeData() = viewModel.bookDetailStateLiveData.observe(this) {
        when (it) {
            is BookDetailState.Loading -> {

            }
            is BookDetailState.Success -> {
                handleSuccess(it)
            }
            else -> Unit
        }
    }

    override fun initViews() = with(binding) {
        btnBack.setOnClickListener {
            finish()
        }
        btnBuy.setOnClickListener {
            if (firebaseAuth.currentUser == null) {
                alertLoginNeed {
                    lifecycleScope.launch {
                        menuChangeEventBus.changeMenu(MainTabMenu.MY)
                        finish()
                    }
                }
            } else {
                Toast.makeText(this@BookDetailActivity, "구매가 완료되었습니다. My탭을 확인해주세요", Toast.LENGTH_SHORT).show()
                insertMemo()
                finish()
            }
        }
    }

    @SuppressLint("SetTextI18n", "NewApi")
    private fun handleSuccess(state: BookDetailState.Success) = with(binding) {
        val bookData = intent.getSerializableExtra("Naver") as Item
        Glide
            .with(this@BookDetailActivity)
            .load(bookData.image)
            .into(binding.bookDetailImageView)
        val title = bookData.title
        val afterTitle1 = title?.replace("<b>", "")
        val afterTitle2 = afterTitle1?.replace("</b>", "")
        binding.bookDetailTitleText.text = afterTitle2
        binding.bookDetailTitleTextView.text = afterTitle2
        binding.bookDetailDateText.text = "${bookData.pubdate} 출간됨"

        binding.bookDetailAuthorText.text = "${bookData.author} | ${bookData.publisher}"
        binding.bookDetailPriceText.text = "정가 : ${bookData.price}원"
        binding.bookDetailDiscountText.text = "할인가 : ${bookData.discount}원"
        binding.bookDetailDescriptionText.text = bookData.description
    }

    private fun alertLoginNeed(afterAction: () -> Unit) {
            AlertDialog.Builder(this)
                .setTitle("로그인이 필요합니다.")
                .setMessage("구매하려면 로그인이 필요합니다. My탭으로 이동하시겠습니까?")
                .setPositiveButton("이동") { dialog, _ ->
                    afterAction()
                    dialog.dismiss()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

    private fun insertMemo() {
        val bookData = intent.getSerializableExtra("Naver") as Item
        roomHelper = Room.databaseBuilder(applicationContext, RoomHelper::class.java, "room_book").build()
        val bookRoomData = RoomBook(bookData.isbn.toString(), bookData.title.toString(), bookData.author.toString(), bookData.publisher.toString(), bookData.discount.toString(), bookData.price.toString())
        GlobalScope.launch(Dispatchers.IO) {
            roomHelper?.roomBookDao()?.insert(bookRoomData)
        }
    }
}