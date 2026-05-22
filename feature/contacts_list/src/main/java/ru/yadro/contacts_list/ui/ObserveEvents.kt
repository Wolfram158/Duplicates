package ru.yadro.contacts_list.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.Flow
import ru.yadro.common.R

@Composable
internal fun ObserveEvents(
    events: Flow<ContactsListScreenEvent>,
    snackbar: SnackbarHostState
) {
    val duplicatesWereDeleted = stringResource(R.string.duplicates_were_deleted)
    val errorWhenDeletingDuplicates = stringResource(R.string.error_when_deleting_duplicates)
    val duplicatesNotFound = stringResource(R.string.duplicates_not_found)
    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                ContactsListScreenEvent.Deleted -> snackbar.showSnackbar(duplicatesWereDeleted)
                ContactsListScreenEvent.Error -> snackbar.showSnackbar(errorWhenDeletingDuplicates)
                ContactsListScreenEvent.NoDuplicates -> snackbar.showSnackbar(duplicatesNotFound)
            }
        }
    }
}