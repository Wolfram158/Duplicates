package ru.yadro.duplicates

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.yadro.contacts_list.ui.ContactsListScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.ContactsList
    ) {
        composable<Routes.ContactsList> {
            ContactsListScreen()
        }
    }
}