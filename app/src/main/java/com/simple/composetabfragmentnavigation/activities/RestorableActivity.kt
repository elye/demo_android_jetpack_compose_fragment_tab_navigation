package com.simple.composetabfragmentnavigation.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.SparseArray
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.compose.rememberNavController
import com.simple.composetabfragmentnavigation.navigation.BottomNavigationBar
import com.simple.composetabfragmentnavigation.navigation.NavHostNavigation
import com.simple.composetabfragmentnavigation.navigation.TopBar
import com.simple.composetabfragmentnavigation.ui.theme.ComposeTabFragmentNavigationTheme


class RestorableActivity : FragmentActivity() {
    private var savedStateSparseArray = SparseArray<Fragment.SavedState>()
    private var currentSelectItemId = 0

    companion object {
        const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            savedStateSparseArray = savedInstanceState.getSparseParcelableArray(
                SAVED_STATE_CONTAINER_KEY
            )
                ?: savedStateSparseArray
            currentSelectItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        }
        setContent {
            ComposeTabFragmentNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray)
        outState.putInt(SAVED_STATE_CURRENT_TAB_KEY, currentSelectItemId)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment != null && fragment.isVisible) {
                with(fragment.childFragmentManager) {
                    if (backStackEntryCount > 0) {
                        popBackStack()
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    fun MainScreen() {
        val navController = rememberNavController()
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomNavigationBar(navController) }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavHostNavigation(navController, ::getCommitFunction)
            }
        }
    }

    private fun getCommitFunction(
        fragment: Fragment,
        tag: String
    ): FragmentTransaction.(containerId: Int) -> Unit =
        {
            saveAndRetrieveFragment(supportFragmentManager, it, fragment)
            replace(it, fragment, tag)
        }

    private fun saveAndRetrieveFragment(
        supportFragmentManager: FragmentManager,
        tabId: Int,
        fragment: Fragment
    ) {
        val currentFragment = supportFragmentManager.findFragmentById(currentSelectItemId)
        if (currentFragment != null) {
            savedStateSparseArray.put(
                currentSelectItemId,
                supportFragmentManager.saveFragmentInstanceState(currentFragment)
            )
        }
        currentSelectItemId = tabId
        fragment.setInitialSavedState(savedStateSparseArray[currentSelectItemId])
    }
}
