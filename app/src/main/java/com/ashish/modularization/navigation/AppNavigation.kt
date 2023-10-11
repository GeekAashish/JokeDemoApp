package com.ashish.modularization.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashish.common.utils.NavRoute
import com.ashish.repolist.RepoListRoute
import com.ashish.repolist.RepoListViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
){
    NavHost(navController = navController, startDestination = NavRoute.repoListScreen ){

        composable(NavRoute.repoListScreen){
            val viewModel:RepoListViewModel = hiltViewModel()
            RepoListRoute(viewModel = viewModel){
            }
        }
    }
}