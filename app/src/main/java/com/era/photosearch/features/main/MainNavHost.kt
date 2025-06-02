package com.era.photosearch.features.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.era.photosearch.features.userdetail.UserDetailScreen
import com.era.photosearch.features.userdetail.models.UserDetailDestination
import com.era.photosearch.features.users.UserListScreen
import com.era.photosearch.features.users.components.WebViewScreen
import com.era.photosearch.features.users.models.UserListDestination
import com.era.photosearch.features.users.models.WebViewDestination

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = UserListDestination,
        modifier = modifier,
    ) {
        composable<UserListDestination> {
            UserListScreen(
                navController = navController,
            )
        }

        composable<UserDetailDestination> {
            UserDetailScreen(
                navController = navController,
            )
        }
        composable<WebViewDestination>{
            WebViewScreen(
                navController = navController,
            )
        }
    }
}