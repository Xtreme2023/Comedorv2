package bo.uab.comedor.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bo.uab.comedor.shared.model.Role

@Composable
fun LoginScreen(onFakeLogin: (Role) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Login temporal (HU1 en el paso 3)", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(24.dp))
        Role.entries.forEach { role ->
            Button(onClick = { onFakeLogin(role) }, modifier = Modifier.padding(vertical = 4.dp)) {
                Text("Entrar como ${role.name}")
            }
        }
    }
}