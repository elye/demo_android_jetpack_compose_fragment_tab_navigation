package com.simple.composetabfragmentnavigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.simple.composetabfragmentnavigation.activities.RestorableActivity
import com.simple.composetabfragmentnavigation.activities.RestorableTabSelfNavActivity
import com.simple.composetabfragmentnavigation.ui.theme.ComposeTabFragmentNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTabFragmentNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column {
            Button(onClick = {
                startActivity(
                    Intent(
                        this@MainActivity,
                        RestorableActivity::class.java)
                )
            }) {
                Text("Pop and Restore-able Fragments in Bottom Sheet")
            }

            Button(onClick = {
                startActivity(
                    Intent(
                        this@MainActivity,
                        RestorableTabSelfNavActivity::class.java)
                )
            }) {
                Text("Pop and Restore-able Fragments in Self Made Navigation Tab")
            }
        }
    }
}
