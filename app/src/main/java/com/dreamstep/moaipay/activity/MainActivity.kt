package com.dreamstep.moaipay.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dreamstep.moaipay.R
import com.dreamstep.moaipay.activity.moai.group.MoaiRegistActivity
import com.dreamstep.moaipay.interfaces.callback.MainTabCallback
import com.dreamstep.moaipay.ui.main.SectionsPagerAdapter
import com.dreamstep.moaipay.utils.MoaiPayGlobal
import com.dreamstep.moaipay.utils.ViewUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainTabCallback {

    enum class StartFragment {
        CHAT_ROOM,
        MOAI_START,
        MOAI_MAIN,
        PROFILE
    }

    private var mAuth = FirebaseAuth.getInstance()

    var startFragment: StartFragment? = null
    lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFragment = intent.getSerializableExtra("startFragment") as StartFragment?

        setupViewPager()
        setupTabs()

        if (mAuth.currentUser == null) {
            mAuth.currentUser?.let {
                val intent = Intent(this, StartTutorialActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        } else {
            MoaiPayGlobal.AuthUserId = mAuth.currentUser!!.uid

            when (startFragment) {
                StartFragment.CHAT_ROOM -> {
                    view_pager.currentItem = 0
                    btnMoaiList.visibility = View.INVISIBLE
                }
                StartFragment.MOAI_START, StartFragment.MOAI_MAIN -> {
                    view_pager.currentItem = 1
                    btnMoaiList.visibility = View.VISIBLE
                    btnMoaiList.setOnClickListener {
                        val inAnimation =
                            AnimationUtils.loadAnimation(this, R.anim.in_animation)
                        val outAnimation =
                            AnimationUtils.loadAnimation(this, R.anim.out_animation)
                        if (menuMoaiList.visibility == View.GONE) {
                            menuMoaiList.startAnimation(inAnimation)
                            menuMoaiList.visibility = View.VISIBLE
                        } else {
                            menuMoaiList.startAnimation(outAnimation)
                            menuMoaiList.visibility = View.GONE
                        }
                    }
                    btnCreate.setOnClickListener {
                        val intent = Intent(this, MoaiRegistActivity::class.java)
                        startActivity(intent)
                    }
                }
                StartFragment.PROFILE -> {
                    view_pager.currentItem = 2
                    btnMoaiList.visibility = View.INVISIBLE
                }
            }
        }

    }

    override fun changeTitle(title: String) {
        ViewUtils.putText(titleHeader, title)
    }

    private fun setupViewPager() {

        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        if (startFragment is StartFragment) {
            sectionsPagerAdapter.startFragment = startFragment!!
        }
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                viewPager.currentItem = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    private fun setupTabs() {
//        mTabChat = view.findViewById(R.id.tabChat)
//        mTabMoai = view.findViewById(R.id.tabMoai)
//        mTabProf = view.findViewById(R.id.tabProf)

        if (startFragment is StartFragment) {
            viewPager.currentItem = 3
        } else {
            viewPager.currentItem = 1
        }

        tabChat.setOnClickListener {
            viewPager.currentItem = 0
        }
        tabMoai.setOnClickListener {
            viewPager.currentItem = 1
        }
        tabProf.setOnClickListener {
            viewPager.currentItem = 2
        }
    }
}
