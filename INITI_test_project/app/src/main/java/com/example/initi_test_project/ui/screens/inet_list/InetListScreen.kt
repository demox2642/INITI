package com.example.initi_test_project.ui.screens.inet_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.models.Inet
import com.example.domain.models.OrganizationInetList
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.initi_test_project.R
import com.example.initi_test_project.ui.screens.base.FlagImageLoader
import com.example.initi_test_project.ui.screens.base.ScreenError
import com.example.initi_test_project.ui.screens.base.ScreenLoading

@Composable
fun InetListScreen(
    navController: NavHostController,
    orgId: String,
) {
    val viewModel: InetListViewModel = hiltViewModel()
    Log.e("OrgID", "orgId=$orgId")
    viewModel.getData(orgId)

    val data by viewModel.data.collectAsState()
    InetListScreenContent(
        back = { navController.popBackStack() },
        data = data,
    )
}

@Composable
fun InetListScreenContent(
    back: () -> Unit,
    data: PresentationModel<OrganizationInetList>,
) {
    Scaffold(
        topBar = {
            Column {
                Spacer(modifier = Modifier.height(35.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { back() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "back")
                    }

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.inet_list_title),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
        ) {
            when (data.screenState) {
                ScreenState.ERROR -> {
                    ScreenError(data.errorText.toString())
                }
                ScreenState.LOADING -> {
                    ScreenLoading()
                }
                ScreenState.RESULT -> {
                    InetListScreenData(data)
                }
                ScreenState.DEFAULT -> {}
            }
        }
    }
}

@Composable
fun InetListScreenData(data: PresentationModel<OrganizationInetList>) {
    Row {
        Card(
            onClick = {
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(120.dp)
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
                        Text(text = data.data!!.organization.orgName)
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = stringResource(id = R.string.org_id), style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = data.data!!.organization.orgId)
                    }
                    data.data!!.organization.address?.let { address ->
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = stringResource(id = R.string.org_address), style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = address)
                        }
                    }
                }
                Column(modifier = Modifier.fillMaxWidth(0.6f).height(100.dp), verticalArrangement = Arrangement.Top) {
                    FlagImageLoader(country = data.data!!.organization.orgCountry)
                }
            }
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.org_inet_list),
            style = MaterialTheme.typography.titleLarge,
        )
    }
    LazyColumn {
        items(data.data!!.inetList) { inet ->
            InetItemList(inet)
        }
    }
}

@Composable
fun InetItemList(inet: Inet) {
    Card(
        onClick = {
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
