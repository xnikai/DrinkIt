package dev.xnikai.drink_it.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.ui.graphics.vector.ImageVector

data class AlertDialogState(
    val isShow: Boolean = false,
    val isEditDialog: Boolean = true,
    val title: String = "Edit",
    val text: String = "Edit need to drink glass count?",
    val onConfirm: (Int) -> Unit = {},
    val onDismiss: () -> Unit = {},
    val confirmButtonText: String? = null,
    val dismissButtonText: String? = null,
    val icon: ImageVector = Icons.Rounded.Edit
)
