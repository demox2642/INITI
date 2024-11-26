package com.example.initi_test_project.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.domain.models.Inet
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.initi_test_project.R
import com.example.initi_test_project.ui.screens.HomeScreens
import com.example.initi_test_project.ui.screens.base.FlagImageLoader
import com.example.initi_test_project.ui.screens.base.ScreenError
import com.example.initi_test_project.ui.screens.base.ScreenLoading

@Composable
fun SearchResultIP(
    searchResultIP: PresentationModel<Inet>,
    navController: NavHostController,
) {
    when (searchResultIP.screenState) {
        ScreenState.ERROR -> {
            ScreenError(searchResultIP.errorText.toString())
        }
        ScreenState.LOADING -> {
            ScreenLoading()
        }
        ScreenState.RESULT -> {
            SearchResultIPContent(inet = searchResultIP.data!!, navController = navController)
        }
        ScreenState.DEFAULT -> {}
    }
}

@Composable
fun SearchResultIPContent(
    inet: Inet,
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
            text = stringResource(id = R.string.search_org_inet),
            style = MaterialTheme.typography.titleLarge,
        )
    }
    Card(
        onClick = {
            navController.navigate(HomeScreens.InetListScreen.route + "/${inet.orgId}")
        },
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(text = stringResource(id = R.string.inet_num), style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = inet.num)
                }

                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = stringResource(id = R.string.inet_name), style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = inet.name)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = stringResource(id = R.string.inet_org_id), style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = inet.orgId)
                }
            }
            Column(modifier = Modifier.fillMaxWidth(0.6f).height(100.dp), verticalArrangement = Arrangement.Top) {
                FlagImageLoader(country = inet.country.toString())
            }
        }
    }
}
