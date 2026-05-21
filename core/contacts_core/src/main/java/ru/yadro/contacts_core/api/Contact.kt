package ru.yadro.contacts_core.api

data class Contact(
    val id: Long,
    val name: String,
    val mainPhone: String? = null,
)