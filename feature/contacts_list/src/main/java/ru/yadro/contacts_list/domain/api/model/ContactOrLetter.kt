package ru.yadro.contacts_list.domain.api.model

internal sealed class ContactOrLetter(val key: String) {
    data class Contact(
        val id: Long,
        val name: String,
        val mainPhone: String? = null,
    ) : ContactOrLetter(id.toString())

    data class Letter(val chr: Char) : ContactOrLetter(chr.toString())
}