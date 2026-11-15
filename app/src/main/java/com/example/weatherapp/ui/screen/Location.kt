package com.example.weatherapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.MainViewModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.weatherapp.data.dao.saveName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val locationText = viewModel.weather?.location?.let { loc ->
        listOf(loc.name, loc.region).filter { it.isNotBlank() }.joinToString(", ")
    } ?: "Getting location..."

    val saved = viewModel.savedLocations
    var query by remember { mutableStateOf("") }
    LaunchedEffect(Unit) { viewModel.clearSearch() }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = { Text("Locations") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        viewModel.searchLocations(it)
                    },
                    placeholder = { Text("Search for a city") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = {
                                query = ""
                                viewModel.clearSearch()
                            }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear search")
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            if (query.isNotBlank()) {
                items(viewModel.searchResults, key = { it.id }) { result ->
                    val name = result.saveName
                    ListItem(
                        headlineContent = { Text(name) },
                        supportingContent = { Text(result.country) },
                        leadingContent = { Icon(Icons.Filled.LocationOn, contentDescription = null) },
                        trailingContent = {
                            if (name !in saved) {
                                IconButton(onClick = { viewModel.saveLocation(name) }) {
                                    Icon(Icons.Filled.BookmarkAdd, contentDescription = "Save $name")
                                }
                            }
                        },
                        modifier = Modifier.clickable {
                            viewModel.getWeather(name)
                            onBack()
                        }
                    )
                }
                item { HorizontalDivider() }
            }
            item {
                Text(
                    "Current location",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(locationText)
                    },
                    leadingContent = { Icon(Icons.Filled.MyLocation, contentDescription = null) },
                    trailingContent = {
                        if (viewModel.weather != null && locationText !in saved) {
                            IconButton(onClick = { viewModel.saveLocation(locationText) }) {
                                Icon(Icons.Filled.BookmarkAdd, contentDescription = "Save location")
                            }
                        }
                    },
                    modifier = Modifier.clickable {
                        viewModel.refreshLocation()
                        onBack()
                    }
                )
                HorizontalDivider()
            }

            item {
                Text(
                    "Saved locations",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            if (saved.isEmpty()) {
                item {
                    Text(
                        "No saved locations yet. Tap the bookmark next to your current location to add it.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            items(saved, key = { it }) { name ->
                ListItem(
                    headlineContent = { Text(name) },
                    leadingContent = { Icon(Icons.Filled.LocationOn, contentDescription = null) },
                    trailingContent = {
                        IconButton(onClick = { viewModel.removeLocation(name) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Remove $name")
                        }
                    },
                    modifier = Modifier.clickable {
                        viewModel.getWeather(name)
                        onBack()
                    }
                )
            }
        }
    }
}