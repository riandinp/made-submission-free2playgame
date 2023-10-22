package com.riandinp.freegamesdb

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.riandinp.freegamesdb.databinding.ActivityMain2Binding
import com.riandinp.freegamesdb.ui.category.CategoryListFragment
import com.riandinp.freegamesdb.ui.favorite.FavoriteFragment
import com.riandinp.freegamesdb.ui.home.HomeFragment

class MainActivity2 : AppCompatActivity(), NavigationBarView.OnItemSelectedListener{

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.title = null

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HomeFragment.newInstance())
                .commit()
            binding.appBarMain.tvToolbarTitle.text = getString(R.string.app_name)
        }
        binding.bottomNavigation.setOnItemSelectedListener(this)
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when(item.itemId) {
            R.id.home_nav -> {
                fragment = HomeFragment.newInstance()
                title = getString(R.string.app_name)
            }
            R.id.category_nav -> {
                fragment = CategoryListFragment.newInstance()
                title = getString(R.string.category_label)
            }
            R.id.favorite_nav -> {
                fragment = FavoriteFragment.newInstance()
                title = getString(R.string.favorite_label)
            }
        }
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
        }
        binding.appBarMain.tvToolbarTitle.text = title
        return true
    }
}