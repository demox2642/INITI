package com.example.initi_test_project.ui.screens.main

import android.net.InetAddresses
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Inet
import com.example.domain.models.Organization
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.usecases.CleanSearchHistoryUseCase
import com.example.domain.usecases.GetSearchHistoryUseCase
import com.example.domain.usecases.SearchByCompanyNameUseCase
import com.example.domain.usecases.SearchByIPUseCase
import com.example.initi_test_project.di.ConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel
    @Inject
    constructor(
        private val searchByIPUseCase: SearchByIPUseCase,
        private val searchByCompanyNameUseCase: SearchByCompanyNameUseCase,
        private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
        private val cleanSearchHistoryUseCase: CleanSearchHistoryUseCase,
        private val connectionManager: ConnectionManager,
    ) : ViewModel() {
        private val _searchResultIP = MutableStateFlow<PresentationModel<Inet>>(PresentationModel<Inet>(screenState = ScreenState.DEFAULT))
        val searchResultIP = _searchResultIP.asStateFlow()

        private val _searchResultOrgName = MutableStateFlow(PresentationModel<List<Organization>>(screenState = ScreenState.DEFAULT))
        val searchResultOrgName = _searchResultOrgName.asStateFlow()

        private val _searchHistory = MutableStateFlow(emptyList<String>())
        val searchHistory = _searchHistory.asStateFlow()

        init {
            getSearchHistory()
        }

        fun search(searchText: String) {
            if (isIpValid(searchText)) {
                searchIp(searchText)
            } else {
                searchCompanyName(searchText)
            }
        }

        private fun isIpValid(ip: String): Boolean =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                InetAddresses.isNumericAddress(ip)
            } else {
                Patterns.IP_ADDRESS.matcher(ip).matches()
            }

        private fun searchIp(ip: String) {
            viewModelScope.launch(Dispatchers.IO) {
                _searchResultIP.value = PresentationModel<Inet>(screenState = ScreenState.LOADING)
                _searchResultOrgName.value = PresentationModel<List<Organization>>(screenState = ScreenState.DEFAULT)
                if (connectionManager.isConnected()) {
                    searchByIPUseCase.searchIp(ip).collect {
                        _searchResultIP.value = it
                        getSearchHistory()
                    }
                } else {
                    searchByIPUseCase.searchIpFromDB(ip).collect {
                        _searchResultIP.value = it
                        getSearchHistory()
                    }
                }
            }
        }

        private fun searchCompanyName(companyName: String) {
            viewModelScope.launch(Dispatchers.IO) {
                _searchResultIP.value = PresentationModel<Inet>(screenState = ScreenState.DEFAULT)
                _searchResultOrgName.value = PresentationModel<List<Organization>>(screenState = ScreenState.LOADING)
                Log.e("Connection", "connect = ${connectionManager.isConnected()}")
                if (connectionManager.isConnected()) {
                    searchByCompanyNameUseCase.searchByOrganization(companyName).collect {
                        _searchResultOrgName.value = it
                        getSearchHistory()
                    }
                } else {
                    searchByCompanyNameUseCase.searchByOrganizationFromDB(companyName).collect {
                        _searchResultOrgName.value = it
                        getSearchHistory()
                    }
                }
            }
        }

        fun cleanSearchHistory() {
            viewModelScope.launch(Dispatchers.IO) {
                _searchHistory.value = emptyList()
                cleanSearchHistoryUseCase.cleanSearchHistory()
            }
        }

        fun getSearchHistory() {
            viewModelScope.launch(Dispatchers.IO) {
                getSearchHistoryUseCase.getSearchHistory().collect {
                    _searchHistory.value = it
                }
            }
        }
    }
