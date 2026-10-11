package bo.uab.comedor.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import bo.uab.comedor.shared.model.Role

@Composable
fun RoleGuard(
    currentRole: Role?,
    allowed: Set<Role>,
    onDenied: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (currentRole != null && currentRole in allowed) {
        content()
    } else {
        LaunchedEffect(Unit) { onDenied() }
    }
}