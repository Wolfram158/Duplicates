package ru.yadro.contacts_core.impl

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.yadro.contacts_core.api.Contact
import kotlin.test.assertEquals

class GetDuplicatesTest {
    @ParameterizedTest
    @MethodSource("getParams")
    fun `GIVEN contacts WHEN find duplicates THEN duplicates found correctly`(testData: TestData) {
        assertEquals(testData.contacts.getDuplicates(), testData.expectedIds)
    }

    class TestData(
        val contacts: List<Contact>,
        val expectedIds: List<Long>
    )

    companion object {
        @JvmStatic
        fun getParams(): List<TestData> = listOf(
            TestData(
                contacts = listOf(
                    Contact(
                        id = 1,
                        name = "1 2",
                        mainPhone = "123"
                    ),
                    Contact(
                        id = 2,
                        name = "1 2 3",
                        mainPhone = "123"
                    ),
                    Contact(
                        id = 3,
                        name = "1 2 3",
                        mainPhone = "123"
                    )
                ),
                expectedIds = listOf(2, 3)
            ),
            TestData(
                contacts = listOf(
                    Contact(
                        id = 1,
                        name = "1 2",
                        mainPhone = "123"
                    ),
                    Contact(
                        id = 2,
                        name = "1 2 3",
                        mainPhone = "123"
                    ),
                    Contact(
                        id = 3,
                        name = "1 2 3 4",
                        mainPhone = "123"
                    )
                ),
                expectedIds = emptyList()
            ),
            TestData(
                contacts = listOf(
                    Contact(
                        id = 1,
                        name = "1 2",
                        mainPhone = "123"
                    ),
                    Contact(
                        id = 2,
                        name = "1 2 3 4",
                        mainPhone = "1234"
                    ),
                    Contact(
                        id = 3,
                        name = "1 2 3 4",
                        mainPhone = "123"
                    ),
                    Contact(
                        id = 4,
                        name = "1 2",
                        mainPhone = "12345"
                    ),
                    Contact(
                        id = 5,
                        name = "1 2",
                        mainPhone = "123"
                    )
                ),
                expectedIds = listOf(1, 5)
            )
        )
    }
}