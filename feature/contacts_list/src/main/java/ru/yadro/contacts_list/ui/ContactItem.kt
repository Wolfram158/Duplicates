package ru.yadro.contacts_list.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.yadro.common.R
import ru.yadro.contacts_list.domain.api.model.ContactOrLetter

@Composable
internal fun ContactItem(
    contact: ContactOrLetter.Contact,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(1.dp, Color.Black),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            model = contact.id.getContactPhotoUri(),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = painterResource(android.R.drawable.ic_menu_call)
        )
        Spacer(Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(contact.name)
            Spacer(Modifier.height(4.dp))
            Text(contact.mainPhone ?: stringResource(R.string.no_main_phone_number))
        }
    }
}