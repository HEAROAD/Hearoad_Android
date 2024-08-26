package com.hearoad.hearoad.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hearoad.hearoad.ui.fragment.ChatListFragment
import com.hearoad.hearoad.ui.fragment.GuideFragment

class HomeAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GuideFragment()
            1 -> ChatListFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}