package com.example.initi_test_project.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.models.Inet
import com.example.domain.models.Organization
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.initi_test_project.R
import com.example.initi_test_project.ui.theme.INITI_test_projectTheme

@Composable
fun MainScreen(navController: NavHostController){
    val viewModel: MainScreenViewModel = hiltViewModel()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val searchResultIP by viewModel.searchResultIP.collectAsState()
    val searchResultOrgName by viewModel.searchResultOrgName.collectAsState()
    MainScreenContent(
        search = viewModel::search,
    searchHistory = searchHistory,
    cleanSearchHistory = viewModel::cleanSearchHistory,
        searchResultIP = searchResultIP,
        searchResultOrgName =searchResultOrgName,
        navController = navController
    )

}

@Composable
fun MainScreenContent(
    search: (String) -> Unit,
    searchHistory: List<String>,
    cleanSearchHistory: () -> Unit,
    searchResultIP: PresentationModel<Inet>,
    searchResultOrgName: PresentationModel<List<Organization>>,
    navController: NavHostController,
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBur(
                search = search,
                searchHistory = searchHistory,
                cleanSearchHistory = cleanSearchHistory,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)) {
            if (searchResultIP.screenState != ScreenState.DEFAULT){
                SearchResultIP(searchResultIP= searchResultIP, navController=navController)
            }
            if (searchResultOrgName.screenState != ScreenState.DEFAULT){
                SearchResultOrgName(searchResultOrgName = searchResultOrgName, navController=navController)
            }

        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBur(
    search: (String) -> Unit,
    searchHistory: List<String>,
    cleanSearchHistory: () -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            search(text)
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(stringResource(id = R.string.search_placeholder))
        },
        trailingIcon = {
            if (active) {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        text = ""
                    } else {
                        active = false
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon",
                    )
                }

            }
        },
        leadingIcon = {
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    active = false
                    search(text)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                )
            }
        },
    ) {
        if (searchHistory.isNotEmpty()) {
            LazyColumn {
                items(searchHistory){
                    Row(modifier = Modifier.padding(all = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            active = false
                            text = it
                            search(it)
                        }) {
                            Icon(imageVector = Icons.Default.History, contentDescription = null)
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = it)
                    }
                }
            }

            HorizontalDivider(
                modifier =
                Modifier
                    .height(2.dp)
                    .fillMaxWidth(),
            )
            Text(
                modifier =
                Modifier
                    .padding(all = 14.dp)
                    .fillMaxWidth()
                    .clickable {
                        cleanSearchHistory()
                    },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.search_clean_history),
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun TopBurPreview() {
    INITI_test_projectTheme {
        TopBur(
            search = {},
            searchHistory = emptyList(),
            cleanSearchHistory = {},
        )
    }
}
