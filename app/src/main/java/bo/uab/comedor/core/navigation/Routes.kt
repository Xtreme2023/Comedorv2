package bo.uab.comedor.core.navigation

import bo.uab.comedor.shared.model.Role

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val HOME_EMPLEADO = "home_empleado"
    const val HOME_ENCARGADO = "home_encargado"
    const val HOME_INSTITUCION = "home_institucion"
    const val HOME_ADMIN = "home_admin"

    fun homeFor(role: Role): String = when (role) {
        Role.EMPLEADO -> HOME_EMPLEADO
        Role.ENCARGADO -> HOME_ENCARGADO
        Role.INSTITUCION -> HOME_INSTITUCION
        Role.ADMIN -> HOME_ADMIN
    }
}