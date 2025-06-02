package com.era.photosearch.features.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.era.photosearch.features.userdetail.UserDetailScreen
import com.era.photosearch.features.userdetail.models.UserDetailDestination
import com.era.photosearch.features.users.PhotoListScreen
import com.era.photosearch.features.users.components.WebViewScreen
import com.era.photosearch.features.users.models.PhotoListDestination
import com.era.photosearch.features.users.models.WebViewDestination

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = PhotoListDestination,
        modifier = modifier,
    ) {
        composable<PhotoListDestination> {
            PhotoListScreen(
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