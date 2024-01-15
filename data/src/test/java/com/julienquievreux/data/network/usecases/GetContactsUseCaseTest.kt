package com.julienquievreux.data.network.usecases

import com.julienquievreux.data.results.CustomError
import com.julienquievreux.data.results.Result
import com.julienquievreux.data.repositories.ContactRepository
import com.julienquievreux.domain.interfaces.view.IContactView
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetContactsUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: ContactRepository

    private lateinit var getContactsUseCase: GetContactsUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getContactsUseCase = GetContactsUseCase(repository)
    }

    @Test
    fun `getContacts should return Success with list from repository when resetPage is false`() = runBlocking {
        // Given
        val mockContactsList = listOf<IContactView>()
        coEvery { repository.getNextPageOfUsers(false) } returns Result.Success(mockContactsList)

        // When
        val result = getContactsUseCase.getContacts(false)

        // Then
        assert(result is Result.Success)
        assertEquals(mockContactsList, (result as Result.Success).value)
        coVerify { runBlocking { repository.getNextPageOfUsers(false) } }
    }

    @Test
    fun `getContacts should return Success with a list of 20 contacts when resetPage is true`() = runBlocking {
        // Given
        val dummyContactsList = generateDummyContactsList()
        coEvery { repository.getNextPageOfUsers(true) } returns Result.Success(dummyContactsList)

        // When
        val result = getContactsUseCase.getContacts(true)

        // Then
        assert(result is Result.Success)
        assertEquals(20, (result as Result.Success).value.size)
    }

    @Test
    fun `getContacts should return Failure with CustomError when repository fails`() = runBlocking {
        // Given
        val mockError = CustomError.NetworkError
        coEvery { repository.getNextPageOfUsers(any()) } returns Result.Failure(mockError)

        // When
        val result = getContactsUseCase.getContacts(true)

        // Then
        assert(result is Result.Failure)
        assertEquals(mockError, (result as Result.Failure).value)
        coVerify { runBlocking { repository.getNextPageOfUsers(true) } }
    }

    @Test
    fun `getContacts should return Success with an empty list when repository returns an empty list`() = runBlocking {
        // Given
        coEvery { repository.getNextPageOfUsers(any()) } returns Result.Success(emptyList())

        // When
        val result = getContactsUseCase.getContacts(true)

        // Then
        assert(result is Result.Success)
        assertTrue((result as Result.Success).value.isEmpty())
        coVerify { runBlocking { repository.getNextPageOfUsers(true) } }
    }

    private fun generateDummyContactsList(size: Int = 20): List<IContactView> {
        return List(size) { mockk<IContactView>(relaxed = true) }
    }
}