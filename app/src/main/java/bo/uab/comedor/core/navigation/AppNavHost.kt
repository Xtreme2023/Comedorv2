package bo.uab.comedor.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bo.uab.comedor.core.designsystem.components.PlaceholderScreen
import bo.uab.comedor.features.auth.presentation.LoginScreen
import bo.uab.comedor.features.menu.presentation.MenuRoute
import bo.uab.comedor.features.welcome.presentation.WelcomeScreen
import bo.uab.comedor.shared.model.Role

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    // TEMPORAL: en el paso 3 el rol vendrá de la sesión de Firebase.
    var currentRole by remember { mutableStateOf<Role?>(null) }

    val backToLogin: () -> Unit = {
        navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
    }

    NavHost(navController = navController, startDestination = Routes.WELCOME) {
        composable(Routes.WELCOME) {
            WelcomeScreen(onStart = {
                navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.WELCOME) { inclusive = true }
                }
            })
        }
        composable(Routes.LOGIN) {
            LoginScreen(onFakeLogin = { role ->
                currentRole = role
                navController.navigate(Routes.homeFor(role)) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            })
        }
        composable(Routes.HOME_EMPLEADO) {
            RoleGuard(currentRole, setOf(Role.EMPLEADO), backToLogin) {
                MenuRoute()
            }
        }
        composable(Routes.HOME_ENCARGADO) {
            RoleGuard(currentRole, setOf(Role.ENCARGADO), backToLogin) {
                PlaceholderScreen("Home Encargado")
            }
        }
        composable(Routes.HOME_INSTITUCION) {
            RoleGuard(currentRole, setOf(Role.INSTITUCION), backToLogin) {
                PlaceholderScreen("Home Institución")
            }
        }
        composable(Routes.HOME_ADMIN) {
            RoleGuard(currentRole, setOf(Role.ADMIN), backToLogin) {
                PlaceholderScreen("Home Administrador")
            }
        }
    }
}