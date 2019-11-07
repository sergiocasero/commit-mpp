package com.sergiocasero.commit.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * ViewPagerAdapter.
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val titles: MutableList<String> = mutableListOf()

    private val fragments: MutableList<Fragment> = mutableListOf()

    override fun getPageTitle(position: Int): CharSequence = titles[position]

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun clear() {
        fragments.clear()
        notifyDataSetChanged()
    }

    fun addFragment(title: String, fragment: Fragment) {
        titles.add(title)
        fragments.add(fragment)
    }

    fun addFragments(fragments: List<Pair<String, Fragment>>) {
        clear()
        fragments.forEach {
            addFragment(it.first, it.second)
        }
        notifyDataSetChanged()
    }

    // FIXME: Prevent fragment destroy
}