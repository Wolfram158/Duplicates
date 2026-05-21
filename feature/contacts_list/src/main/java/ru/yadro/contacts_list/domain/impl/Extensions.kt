package ru.yadro.contacts_list.domain.impl

import ru.yadro.contacts_core.api.Contact
import ru.yadro.contacts_list.domain.api.model.ContactOrLetter

internal fun List<Contact>.getContactOrLetterList(): List<ContactOrLetter> {
    var currentLetter: Char? = null
    val result = mutableListOf<ContactOrLetter>()
    forEach {
        if (currentLetter != it.name.first()) {
            currentLetter = it.name.first()
            result.add(ContactOrLetter.Letter(currentLetter))
        }
        result.add(it.toContactOrLetter())
    }
    return result
}

internal fun Contact.toContactOrLetter() = ContactOrLetter.Contact(
    id = id,
    name = name,
    mainPhone = mainPhone
)