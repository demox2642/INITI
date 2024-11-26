package com.example.initi_test_project.ui.screens.inet_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Inet
import com.example.domain.models.Organization
import com.example.domain.models.OrganizationInetList
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.usecases.GetDataForInetListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InetListViewModel
    @Inject
    constructor(
        private val getDataForInetListUseCase: GetDataForInetListUseCase,
    ) : ViewModel() {
        private val _data = MutableStateFlow(PresentationModel<OrganizationInetList>(screenState = ScreenState.DEFAULT))
        val data = _data.asStateFlow()


        fun getData(orgId: String) {
            viewModelScope.launch(Dispatchers.IO) {
                getDataForInetListUseCase.getDataForInetList(orgId).collect{
                    _data.value = it
                }
            }
        }
    }
