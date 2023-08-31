package com.simple.composetabfragmentnavigation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TabHeaderSelfNav(navController: (String) -> Unit) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf(
        NavigationItem.Home,
        NavigationItem.Music,
        NavigationItem.Movies,
        NavigationItem.Books,
        NavigationItem.Profile
    )
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        tabs.forEachIndexed { index, item ->
            Tab(
                text = {
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painterResource(id = item.icon), contentDescription = item.title)
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController(item.route)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
            )
        }
    }
}