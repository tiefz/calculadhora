package br.com.insertkoin.calculadhora.presentation.ui

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.insertkoin.calculadhora.domain.DomainIntervalModel
import br.com.insertkoin.calculadhora.presentation.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val currentDate = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        .format(Date(System.currentTimeMillis())).toLong()
    var start by remember { mutableStateOf("00:00") }
    var breakStart by remember { mutableStateOf("00:00") }
    var breakEnd by remember { mutableStateOf("00:00") }
    var end by remember { mutableStateOf("00:00") }
    var total by remember { mutableStateOf("00:00") }
    val intervals by viewModel.getAllIntervals().collectAsState(initial = emptyList())
    val interval = intervals.firstOrNull() ?: DomainIntervalModel(
        id = currentDate,
        start = "00:00",
        breakStart = "00:00",
        breakEnd = "00:00",
        end = "00:00"
    )
    if (interval.id != currentDate) {
        viewModel.deleteInterval(interval)
    }
    start = interval.start
    breakStart = interval.breakStart
    breakEnd = interval.breakEnd
    end = interval.end
    total = viewModel.calculateTotalTime(intervals)

    val context = LocalContext.current
    var timePickerField by remember { mutableStateOf<String?>(null) }

    fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val cal = Calendar.getInstance()
        val initial =
            String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
        val (h, m) = initial.split(":").map { it.toInt() }
        TimePickerDialog(
            context,
            { _, hour, minute ->
                onTimeSelected(String.format("%02d:%02d", hour, minute))
            },
            h, m, true
        ).show()
    }

    LaunchedEffect(timePickerField) {
        when (timePickerField) {
            "start" -> showTimePicker() {
                start = it
                interval.start = it
                viewModel.insertInterval(interval)
            }

            "breakStart" -> showTimePicker() {
                breakStart = it
                interval.breakStart = it
                viewModel.insertInterval(interval)
            }

            "breakEnd" -> showTimePicker() {
                breakEnd = it
                interval.breakEnd = it
                viewModel.insertInterval(interval)
            }

            "end" -> showTimePicker() {
                end = it
                interval.end = it
                viewModel.insertInterval(interval)
            }
        }
        timePickerField = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calculadhora",
            color = Color(0xFF006400),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        @Composable
        fun TimeRow(label: String, value: String, onClick: () -> Unit) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_edit),
                        contentDescription = "Editar",
                        tint = Color(0xFF006400)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = value,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        TimeRow("Entrada", start) { timePickerField = "start" }
        TimeRow("Saída Almoço", breakStart) { timePickerField = "breakStart" }
        TimeRow("Volta Almoço", breakEnd) { timePickerField = "breakEnd" }
        TimeRow("Saída", end) { timePickerField = "end" }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total de horas",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Text(
                text = total,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}