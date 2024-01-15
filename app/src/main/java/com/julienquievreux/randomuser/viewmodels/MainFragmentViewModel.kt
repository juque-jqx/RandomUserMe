package com.julienquievreux.randomuser.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julienquievreux.data.extensions.ifFailed
import com.julienquievreux.data.extensions.ifSuccessful
import com.julienquievreux.data.network.usecases.GetContactsUseCase
import com.julienquievreux.data.results.CustomError.NetworkError
import com.julienquievreux.randomuser.R
import com.julienquievreux.randomuser.mappers.toContactView
import com.julienquievreux.randomuser.models.ContactView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject  constructor(
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var contacts = emptyList<ContactView>()

    var isLoading = false
    private var isDataLoaded = false

    init {
        loadContacts()
    }

    private fun loadContacts(){
        _uiState.value = UiState.Loading
        if (isDataLoaded.not() && contacts.isEmpty()){
            viewModelScope.launch {
                try {
                    getContactsUseCase.getContacts(true)
                        .ifFailed { error ->
                            when (error){
                                is NetworkError -> { _uiState.value = UiState.Error(R.string.network_error) }
                                else -> {
                                    _uiState.value = UiState.Loading
                                }
                            }
                        }.ifSuccessful { contactList ->
                            contacts = contactList.map { it.toContactView() }
                            isDataLoaded = true
                            _uiState.value = UiState.Success(contacts)
                        }
                } catch (e: Exception) {
                    _uiState.value = UiState.Error(R.string.network_error)
                }
            }
        }
    }

    fun loadMoreContacts() {
        if (!isLoading) {
            viewModelScope.launch {
                isLoading = true
                try {
                    getContactsUseCase.getContacts(false)
                        .ifFailed { error ->
                            when (error){
                                is NetworkError -> { _uiState.value = UiState.Error(R.string.network_error) }
                                else -> {}
                            }
                        }.ifSuccessful { contactList ->
                            contacts = contactList.map { it.toContactView() }
                            _uiState.value = UiState.Success(contacts)
                        }
                } catch (e: Exception) {
                    _uiState.value = UiState.Error(R.string.network_error)
                } finally {
                    isLoading = false
                }
            }
        }
    }

    fun filterContacts(char: String){
        val filteredContacts = contacts.map { it.copy() }.filter { contact ->
            contact.fullName.lowercase().contains(char.lowercase()) || contact.email.lowercase().contains(char.lowercase())
        }
        _uiState.value = UiState.FilteredList(filteredContacts)
    }

    fun restoreContacts(){
        _uiState.value = UiState.Success(contacts)
    }

    fun refreshContacts(){
        isDataLoaded = false
        viewModelScope.launch {
            getContactsUseCase.getContacts(true)
                .ifFailed { error ->
                    when (error){
                        is NetworkError -> { _uiState.value = UiState.Error(R.string.network_error) }
                        else -> {}
                    }
                }.ifSuccessful { contactList ->
                    contacts = contactList.map { it.toContactView() }
                    _uiState.value = UiState.RefreshSuccess(contacts)
                }
        }
    }
}

sealed class UiState {
//    data object Empty : UiState()
    data object Loading : UiState()
    data class Success(val contacts: List<ContactView>?) : UiState()
    data class RefreshSuccess(val contacts: List<ContactView>?) : UiState()
    data class FilteredList(val filteredContacts: List<ContactView>?) : UiState()
    data class Error(val message: Int) : UiState()
}