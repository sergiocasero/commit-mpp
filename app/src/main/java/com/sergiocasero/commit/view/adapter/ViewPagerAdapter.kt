package com.sergiocasero.commit.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * ViewPagerAdapter.
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles: MutableList<String> = mutableListOf()

    private val fragments: MutableList<Fragment> = mutableListOf()

    override fun getPageTitle(position: Int): CharSequence = titles[position]

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun clear() {
        titles.clear()
        fragments.clear()
        notifyDataSetChanged()
    }

    fun addFragment(title: String, fragment: Fragment) {
        titles.add(title)
        fragments.add(fragment)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun addFragments(fragments: List<Pair<String, Fragment>>) {
        clear()
        fragments.forEach {
            addFragment(it.first, it.second)
        }
        notifyDataSetChanged()
    }

}