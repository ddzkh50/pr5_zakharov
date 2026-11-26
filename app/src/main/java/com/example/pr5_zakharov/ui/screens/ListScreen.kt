package com.example.pr5_zakharov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pr5_zakharov.data.Specialty
import com.example.pr5_zakharov.data.SpecialtyRepository
import com.example.pr5_zakharov.ui.theme.CatalogStyle
import com.example.pr5_zakharov.ui.theme.CatalogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    specialties: List<Specialty>,
    darkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onSpecialtyClick: (Int) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Каталог ИТ-специальностей",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (darkTheme) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    titleContentColor = if (darkTheme) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    }
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text(
                    text = if (darkTheme) "Тёмная тема" else "Светлая тема",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = darkTheme,
                    onCheckedChange = { onToggleTheme() }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(CatalogStyle.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(CatalogStyle.CardSpacing)
        ) {
            items(specialties, key = { it.id }) { specialty ->
                SpecialtyCard(
                    specialty = specialty,
                    onClick = { onSpecialtyClick(specialty.id) }
                )
            }
        }
    }
}

@Composable
private fun SpecialtyCard(
    specialty: Specialty,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CatalogStyle.CardCorner),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CatalogStyle.ContentPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = specialty.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Код: ${specialty.code}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${specialty.qualification} · ${specialty.duration}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Список — светлая тема")
@Composable
fun ListScreenPreview() {
    CatalogTheme(darkTheme = false) {
        ListScreen(
            specialties = SpecialtyRepository.specialties,
            darkTheme = false,
            onToggleTheme = {},
            onSpecialtyClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Список — тёмная тема",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ListScreenDarkPreview() {
    CatalogTheme(darkTheme = true) {
        ListScreen(
            specialties = SpecialtyRepository.specialties,
            darkTheme = true,
            onToggleTheme = {},
            onSpecialtyClick = {}
        )
    }
}
