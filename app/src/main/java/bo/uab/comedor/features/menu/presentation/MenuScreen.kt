package bo.uab.comedor.features.menu.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bo.uab.comedor.features.menu.data.sampleWeek // solo para el Preview
import bo.uab.comedor.features.menu.domain.model.DayMenu
import bo.uab.comedor.features.menu.domain.model.Dish
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/** Punto de entrada: conecta el ViewModel con la pantalla. Úsalo en el NavHost. */
@Composable
fun MenuRoute(viewModel: MenuViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    MenuScreen(state = state, onDaySelected = viewModel::onDaySelected)
}

/** Pantalla "tonta": solo recibe estado y avisa eventos. Por eso es fácil de previsualizar. */
@Composable
fun MenuScreen(
    state: MenuUiState,
    onDaySelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        MenuUiState.Loading -> Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        MenuUiState.NotAvailable -> MessageState(
            title = "Menú no disponible",
            message = "El encargado todavía no publicó el menú de esta semana. Vuelve a revisar más tarde.",
            modifier = modifier,
        )

        is MenuUiState.Error -> MessageState(
            title = "No se pudo cargar el menú",
            message = state.message,
            modifier = modifier,
        )

        is MenuUiState.Success -> MenuContent(state, onDaySelected, modifier)
    }
}

@Composable
private fun MenuContent(
    state: MenuUiState.Success,
    onDaySelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val day = state.selectedDay
    Column(modifier.fillMaxSize()) {
        WeekHeader(state.days)
        DaySelector(state.days, state.selectedDate, onDaySelected)
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { DailyMenuCard(day) }
            // Solo llega con datos si el usuario es pensionado vegetariano
            day.vegetarian?.let { dish -> item { VegetarianCard(dish) } }
        }
    }
}

@Composable
private fun WeekHeader(days: List<DayMenu>) {
    Column(Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Text(
            text = "Menú de la semana",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily.Serif,
        )
        Text(
            text = "${days.first().date.shortLabel()} al ${days.last().date.shortLabel()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun DaySelector(
    days: List<DayMenu>,
    selected: LocalDate,
    onSelect: (LocalDate) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        days.forEach { day ->
            val isSelected = day.date == selected
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurfaceVariant,
            ) {
                Column(
                    modifier = Modifier
                        .selectable(
                            selected = isSelected,
                            onClick = { onSelect(day.date) },
                            role = Role.Tab,
                        )
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(day.date.shortDayName(), style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = day.date.dayOfMonth.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

/** La "carta" del día: una sola tarjeta con los platos separados por líneas, como un menú impreso. */
@Composable
private fun DailyMenuCard(day: DayMenu) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text(
                text = day.date.fullLabel(),
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily.Serif,
            )
            Spacer(Modifier.height(4.dp))

            val courses = buildList {
                add("Sopa" to day.soup)
                add("Plato principal" to day.main)
                add("Segundo" to day.second)
                day.dessert?.let { add("Postre" to it) } // solo jueves
                add("Barra de ensaladas" to Dish("Sírvete a gusto", day.saladBar.joinToString(", ")))
            }
            courses.forEachIndexed { index, (label, dish) ->
                if (index > 0) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                CourseRow(label, dish)
            }
        }
    }
}

@Composable
private fun VegetarianCard(dish: Dish) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
            CourseRow(
                label = "Opción vegetariana",
                dish = dish,
                labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Composable
private fun CourseRow(
    label: String,
    dish: Dish,
    labelColor: Color = MaterialTheme.colorScheme.primary,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = labelColor)
        Spacer(Modifier.height(2.dp))
        Text(
            text = dish.name,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily.Serif,
        )
        dish.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalContentColor.current.copy(alpha = 0.72f),
            )
        }
    }
}

@Composable
private fun MessageState(title: String, message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

// ---------- Fechas en español (Bolivia) ----------

private val EsBo: Locale = Locale.forLanguageTag("es-BO")

private fun String.capitalized() = replaceFirstChar { it.titlecase(EsBo) }

private fun LocalDate.shortDayName() =
    dayOfWeek.getDisplayName(TextStyle.SHORT, EsBo).trimEnd('.').capitalized()

private fun LocalDate.shortLabel(): String {
    val monthName = month.getDisplayName(TextStyle.SHORT, EsBo).trimEnd('.')
    return "$dayOfMonth $monthName"
}

private fun LocalDate.fullLabel(): String {
    val dayName = dayOfWeek.getDisplayName(TextStyle.FULL, EsBo).capitalized()
    val monthName = month.getDisplayName(TextStyle.FULL, EsBo)
    return "$dayName $dayOfMonth de $monthName"
}

// ---------- Preview ----------

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun MenuScreenPreview() {
    val week = sampleWeek(LocalDate.of(2026, 10, 12))
    // Cambia MaterialTheme por el tema de tu app (ComedorUABTheme o como lo hayas llamado)
    MaterialTheme {
        MenuScreen(
            state = MenuUiState.Success(week.days, selectedDate = week.days[3].date), // jueves, con postre
            onDaySelected = {},
        )
    }
}
