package com.example.initi_test_project.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.domain.models.Organization
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.initi_test_project.R
import com.example.initi_test_project.ui.screens.HomeScreens
import com.example.initi_test_project.ui.screens.base.FlagImageLoader
import com.example.initi_test_project.ui.screens.base.ScreenError
import com.example.initi_test_project.ui.screens.base.ScreenLoading

@Composable
fun SearchResultOrgName(
    searchResultOrgName: PresentationModel<List<Organization>>,
    navController: NavHostController,
) {
    when (searchResultOrgName.screenState) {
        ScreenState.ERROR -> {
            ScreenError(searchResultOrgName.errorText.toString())
        }
        ScreenState.LOADING -> {
            ScreenLoading()
        }
        ScreenState.RESULT -> {
            SearchResultOrgNameContent(orgs = searchResultOrgName.data!!, navController = navController)
        }
        ScreenState.DEFAULT -> {}
    }
}

@Composable
fun SearchResultOrgNameContent(
    orgs: List<Organization>,
    navController: NavHostController,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.search_org_title),
            style = MaterialTheme.typography.titleLarge,
        )
    }
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(orgs) {
            OrganizationListItem(org = it, orgId = { orgId -> navController.navigate(HomeScreens.InetListScreen.route + "/$orgId") })
        }
    }
}

@Composable
fun OrganizationListItem(
    org: Organization,
    orgId: (String) -> Unit,
) {
    Card(
        onClick = {
            orgId(org.orgId)
        },
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.75f)) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(text = stringResource(id = R.string.org_name), style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = org.orgName)
                }

                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = stringResource(id = R.string.org_id), style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = org.orgId)
                }
                org.address?.let { address ->
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = stringResource(id = R.string.org_address), style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = address)
                    }
                }
            }
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
            ) {
                FlagImageLoader(country = org.orgCountry)
            }
        }
    }
}
