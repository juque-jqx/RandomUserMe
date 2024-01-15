package com.julienquievreux.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.julienquievreux.data.results.CustomError
import com.julienquievreux.data.results.Result
import com.julienquievreux.data.network.api.ContactApiService
import com.julienquievreux.domain.interfaces.view.IContactView
import com.julienquievreux.domain.mappers.ContactMapper
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val apiService: ContactApiService,
    private val mapper: ContactMapper,
    private val dataStore: DataStore<Preferences>
) {
    private val pageSize = 20
    private var CURRENT_PAGE = intPreferencesKey("currentPage")

    private val allContacts = mutableListOf<IContactView>()

    suspend fun getNextPageOfUsers(resetPage: Boolean): Result<CustomError, List<IContactView>> {
        return try {
            if (resetPage) {
                resetPages()
                allContacts.clear()
            }
            val currentPage = getCurrentPage()
            val response = apiService.getContacts(currentPage, pageSize)
            if (response.isSuccessful) {
                val newContacts = response.body()?.contacts?.map { mapper.fromRemoteToView(it) }.orEmpty()
                allContacts.addAll(newContacts)
                upPage()
                setCurrentPage(getCurrentPage())
                Result.Success(allContacts.toList())
            } else {
                Result.Failure(CustomError.NetworkError)
            }
        } catch (e: Exception) {
            Result.Failure(CustomError.NetworkError)
        }
    }

    private suspend fun getCurrentPage(): Int {
        var currentPage = 1
        dataStore.data.map { preferences ->
            preferences[CURRENT_PAGE] ?: 1
        }.firstOrNull()?.let {
            currentPage = it
        }

        return currentPage
    }

    private suspend fun setCurrentPage(currentPage: Int) =
        dataStore.edit { data ->
            data[CURRENT_PAGE] = currentPage
        }

    private suspend fun upPage() =
        dataStore.edit { data ->
            val currentPage = data[CURRENT_PAGE] ?: 1
            data[CURRENT_PAGE] = currentPage + 1
        }

    private suspend fun resetPages() =
        dataStore.edit { data ->
            data[CURRENT_PAGE] = 1
        }
}