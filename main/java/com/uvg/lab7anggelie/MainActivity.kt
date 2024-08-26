package com.uvg.lab7anggelie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTheme {
                NotificationsApp()
            }
        }
    }
}

@Composable
fun CustomTheme(content: @Composable () -> Unit) {
    val colorScheme = darkColorScheme(
        primary = Color(0xFF8f7193),  //listo
        secondary = Color(0xFF8f7193),  //listo
        tertiary = Color(0xFFffeebc), //listo
        background = Color(0xFFffffeb), //listo
        surface = Color(0xFFdfcae1),
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onTertiary = Color.White,
        onBackground = Color(0xFFa788ab),
        onSurface = Color(0xFF8f7193)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
fun NotificationsApp() {
    val notifications = remember { generateFakeNotifications() }
    var selectedType by remember { mutableStateOf<NotificationType?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        NotificationsScreen(
            notifications = notifications,
            selectedType = selectedType,
            onTypeSelected = { selectedType = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun NotificationsScreen(
    notifications: List<Notification>,
    selectedType: NotificationType?,
    onTypeSelected: (NotificationType?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        NotificationHeader()
        NotificationTypeFilter(
            selectedType = selectedType,
            onTypeSelected = onTypeSelected
        )
        NotificationList(
            notifications = notifications.filter {
                selectedType == null || it.type == selectedType
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationHeader() {
    TopAppBar(
        title = { Text("Notificaciones", color = MaterialTheme.colorScheme.onPrimary) },
        navigationIcon = {
            IconButton(onClick = { /* Handle back navigation */ }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimary)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun NotificationTypeFilter(
    selectedType: NotificationType?,
    onTypeSelected: (NotificationType?) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Tipos de Notificaciones",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NotificationType.entries.forEach { type ->
                FilterChip(
                    selected = selectedType == type,
                    onClick = { onTypeSelected(if (selectedType == type) null else type) },
                    label = { Text(type.displayName) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondary,
                        selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
        }
    }
}

@Composable
fun NotificationList(
    notifications: List<Notification>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(notifications) { notification ->
            NotificationItem(notification)
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            NotificationIcon(notification.type)
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = notification.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = notification.sendAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun NotificationIcon(type: NotificationType) {
    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        shape = CircleShape,
        modifier = Modifier.size(40.dp)
    ) {
        Icon(
            imageVector = type.Icon(),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.padding(8.dp)
        )
    }
}