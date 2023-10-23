package com.riandinp.freegamesdb

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import com.riandinp.freegamesdb.databinding.ActivityMainBinding
import com.riandinp.freegamesdb.ui.category.CategoryListFragment
import com.riandinp.freegamesdb.ui.home.HomeFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.title = null

        if (savedInstanceState == null) {
            moveNavigation(HomeFragment(), getString(R.string.app_name))
        }
        binding.bottomNavigation.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_nav -> {
                moveNavigation(HomeFragment(), getString(R.string.app_name))
            }

            R.id.category_nav -> {
                moveNavigation(CategoryListFragment(), getString(R.string.category_label))
            }

            R.id.favorite_nav -> {
                val moduleFragment = try {
                    Class.forName("com.riandinp.freegamesdb.favorite.FavoriteFragment")
                        .getDeclaredConstructor().newInstance() as Fragment
                } catch (e: Exception) {
                    Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
                    null
                }

                if (moduleFragment != null) {
                    moveNavigation(moduleFragment, getString(R.string.favorite_label))
                }
            }
        }
        return true
    }

    private fun moveNavigation(fragment: Fragment, routeTitle: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
        binding.appBarMain.tvToolbarTitle.text = routeTitle
    }
}