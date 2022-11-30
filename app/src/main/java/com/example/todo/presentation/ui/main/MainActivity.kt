package com.example.todo.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todo.R
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private lateinit var mBinding: ActivityMainBinding
    private val mNavController by lazy {
        findNavController(R.id.nav_host_fragment_content_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.appBarMain.toolbar)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.task_listing, R.id.task_creation, R.id.bin
            ),
            mBinding.drawerLayout
        )
        setupActionBarWithNavController(mNavController, mAppBarConfiguration)
        mBinding.navView.setupWithNavController(mNavController)
        mNavController.addOnDestinationChangedListener { _, destination, _ ->
            mBinding.appBarMain.fab.visibility =
                if (destination.id == R.id.bin) View.GONE else View.VISIBLE
            mBinding.appBarMain.fab.setImageResource(
                if (destination.id == R.id.task_creation)
                    R.drawable.ic_task_list
                else
                    R.drawable.ic_task_creation
            )
            mBinding.appBarMain.fab.setOnClickListener {
                mBinding.navView.menu.performIdentifierAction(
                    if (destination.id == R.id.task_creation)
                        R.id.task_listing
                    else
                        R.id.task_creation, 0
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp(mAppBarConfiguration) || super.onSupportNavigateUp()
    }
}