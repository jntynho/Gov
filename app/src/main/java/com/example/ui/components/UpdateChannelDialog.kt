package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.LocalAccentColor
import com.example.ui.theme.LocalVaultPalette

/**
 * Update channel selection dialog matching the screenshot styling:
 * - Clean title
 * - Radio options with title and descriptive subtext
 * - Full-width filled pill cancel button
 * - Specular top edge rim light
 */
@Composable
fun UpdateChannelDialog(
    selectedChannel: String = "Beta",
    onChannelSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val palette = LocalVaultPalette.current
    val accent = LocalAccentColor.current
    var currentChoice by remember { mutableStateOf(selectedChannel) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .widthIn(max = 420.dp)
                .vaultTopGlow(),
            shape = VaultDialogShape,
            colors = CardDefaults.cardColors(
                containerColor = if (palette.name.equals("light", ignoreCase = true)) palette.cardBg else Color(0xFF1E222A)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Update channel",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Stable Option
                ChannelOptionItem(
                    title = "Stable",
                    subtitle = "Intended for everyday use.",
                    selected = currentChoice == "Stable",
                    accentColor = accent,
                    textColor = palette.textPrimary,
                    mutedColor = palette.textSecondary,
                    onClick = {
                        currentChoice = "Stable"
                        onChannelSelected("Stable")
                        onDismiss()
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Beta Option
                ChannelOptionItem(
                    title = "Beta",
                    subtitle = "Early updates that may contain bugs, break features, or behave unexpectedly.",
                    selected = currentChoice == "Beta",
                    accentColor = accent,
                    textColor = palette.textPrimary,
                    mutedColor = palette.textSecondary,
                    onClick = {
                        currentChoice = "Beta"
                        onChannelSelected("Beta")
                        onDismiss()
                    }
                )

                Spacer(modifier = Modifier.height(26.dp))

                // Full-width pill Cancel button matching the reference screenshot
                Button(
                    onClick = onDismiss,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (palette.name.equals("light", ignoreCase = true)) palette.surface else Color(0xFF282D37),
                        contentColor = palette.textPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Cancel",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ChannelOptionItem(
    title: String,
    subtitle: String,
    selected: Boolean,
    accentColor: Color,
    textColor: Color,
    mutedColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = accentColor,
                unselectedColor = mutedColor
            ),
            modifier = Modifier.padding(top = 2.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subtitle,
                fontSize = 13.5.sp,
                lineHeight = 18.sp,
                color = mutedColor
            )
        }
    }
}
