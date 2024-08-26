package com.uvg.lab7anggelie

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

enum class NotificationType(val displayName: String) {
    GENERAL("General"),
    CHARLADELVAS("Charla Delvas"),
    INFORMATIVAS("Informativas")
}

data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val sendAt: String,
    val type: NotificationType
)

fun NotificationType.Icon(): ImageVector {
    return when (this) {
        NotificationType.GENERAL -> Icons.Default.Favorite
        NotificationType.CHARLADELVAS -> Icons.Default.DateRange
        NotificationType.INFORMATIVAS -> Icons.Default.Notifications
    }
}

fun NotificationType.getBackgroundColor(): Color {
    return when (this) {
        NotificationType.GENERAL -> Color.Blue
        NotificationType.CHARLADELVAS -> Color.Green
        NotificationType.INFORMATIVAS -> Color.Red
    }
}

fun NotificationType.getIconColor(): Color {
    return when (this) {
        NotificationType.GENERAL -> Color.White
        NotificationType.CHARLADELVAS -> Color.Black
        NotificationType.INFORMATIVAS -> Color.Yellow
    }
}

fun generateFakeNotifications(): List<Notification> {
    val notifications = mutableListOf<Notification>()
    val titles = listOf(
        "Nueva versión disponible",
        "Nueva Charla Delvas",
        "Mensaje de Anggelie",
        "Mañana hay charla Delvas UVG"
    )
    val bodies = listOf(
        "La aplicación ha sido actualizada a v1.0.4. Ve a la PlayStore y actualízala!",
        "No te olvides de asistir a la Charla Delvas mañana, a las 13:30pm, en el Edificio H.",
        "Te ha llegado un mensaje de Anggelie!",
        "No te olvides de asistir a la Charla Delvas el viernes, a las 15:30pm, en el Edificio J."
    )
    val types = NotificationType.values()

    for (i in 1..50) {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        notifications.add(
            Notification(
                id = i,
                title = titles.random(),
                body = bodies.random(),
                sendAt = dateFormat.format(calendar.time),
                type = types.random()
            )
        )
    }
    return notifications
}
