package com.julienquievreux.data.network.usecases

import com.julienquievreux.data.results.CustomError
import com.julienquievreux.data.results.Result
import com.julienquievreux.data.extensions.ifFailed
import com.julienquievreux.data.extensions.ifSuccessful
import com.julienquievreux.data.repositories.ContactRepository
import com.julienquievreux.domain.interfaces.view.IContactView
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val repository: ContactRepository
) {
    suspend fun getContacts(resetPage: Boolean): Result<CustomError, List<IContactView>> {
        return repository.getNextPageOfUsers(resetPage)
            .ifFailed { error ->
                Result.Failure(error)
            }.ifSuccessful { contactList ->
                Result.Success(contactList)
            }
    }
}