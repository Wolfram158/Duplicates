package ru.yadro.contacts_list.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.yadro.common.R
import ru.yadro.contacts_list.domain.api.model.ContactOrLetter

@Composable
internal fun NonEmptyContactsList(
    contacts: List<ContactOrLetter>,
    onDeleteDuplicateContacts: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(contacts, key = { it.key }) { item ->
                when (item) {
                    is ContactOrLetter.Contact -> ContactItem(item)
                    is ContactOrLetter.Letter -> LetterItem(item)
                }
            }
        }
        Button(
            onClick = onDeleteDuplicateContacts,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RectangleShape
        ) {
            Text(stringResource(R.string.delete_duplicate_contacts))
        }
    }
}