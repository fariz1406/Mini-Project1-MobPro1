package com.fariznst0075.assesment1.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fariznst0075.assesment1.R
import com.fariznst0075.assesment1.navigation.Screen
import com.fariznst0075.assesment1.ui.theme.Assesment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var expandedMenu by remember { mutableStateOf(false) }
    var resetTrigger by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { expandedMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = expandedMenu,
                        onDismissRequest = { expandedMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.about)) },
                            onClick = {
                                expandedMenu = false
                                navController.navigate(Screen.About.route)

                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Reset Input") },
                            onClick = {
                                resetTrigger = true
                                expandedMenu = false
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = stringResource(id = R.string.logo_desc),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(120.dp)
            )

            ScreenContent(resetTrigger = resetTrigger,
                onResetHandled = { resetTrigger = false })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    resetTrigger: Boolean,
    onResetHandled: () -> Unit
) {
    val options = listOf("Celsius", "Fahrenheit", "Reamur", "Kelvin")

    var suhuInput by rememberSaveable { mutableStateOf("") }
    var suhuInputError by rememberSaveable { mutableStateOf(false) }
    var selectedInputUnit by rememberSaveable { mutableStateOf(options[0]) }
    var selectedTargetUnit by rememberSaveable { mutableStateOf(options[1]) }
    var hasilKonversi by rememberSaveable { mutableStateOf<String?>(null) }

    var expandedInput by rememberSaveable { mutableStateOf(false) }
    var expandedTarget by rememberSaveable { mutableStateOf(false) }

    if (resetTrigger) {
        suhuInput = ""
        selectedInputUnit = options[0]
        selectedTargetUnit = options[1]
        hasilKonversi = null
        onResetHandled()
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.suhu_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        // Input & Dropdown
// Input & Dropdown
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 64.dp), // memastikan tinggi sejajar
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = suhuInput,
                onValueChange = { suhuInput = it },
                label = { Text(text = stringResource(id = R.string.input)) },
                trailingIcon = { IconPicker(suhuInputError, "") },
                supportingText = { ErrorHint(suhuInputError) },
                isError = suhuInputError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .weight(0.6f)
                    .heightIn(min = 64.dp) // menyamakan tinggi
            )

            ExposedDropdownMenuBox(
                expanded = expandedInput,
                onExpandedChange = { expandedInput = !expandedInput },
                modifier = Modifier
                    .weight(0.4f)
                    .heightIn(min = 64.dp) // menyamakan tinggi
            ) {
                OutlinedTextField(
                    value = selectedInputUnit,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedInput)
                    },
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedInput,
                    onDismissRequest = { expandedInput = false }
                ) {
                    options.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedInputUnit = unit
                                expandedInput = false
                            }
                        )
                    }
                }
            }
        }


        Text(
            text = stringResource(id = R.string.conversion_to),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 4.dp)
        )

        // Dropdown Tujuan
        ExposedDropdownMenuBox(
            expanded = expandedTarget,
            onExpandedChange = { expandedTarget = !expandedTarget },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedTargetUnit,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTarget)
                },
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedTarget,
                onDismissRequest = { expandedTarget = false }
            ) {
                options.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit) },
                        onClick = {
                            selectedTargetUnit = unit
                            expandedTarget = false
                        }
                    )
                }
            }
        }

// Tombol Konversi
        Button(
            onClick = {
                val input = suhuInput.toDoubleOrNull()

                suhuInputError = (suhuInput == "" || suhuInput == "0")
                if (suhuInputError) return@Button

                hasilKonversi = if (input != null) {
                    val hasil = konversiSuhu(input, selectedInputUnit, selectedTargetUnit)
                    "%.2f °%s = %.2f °%s".format(
                        input, selectedInputUnit, hasil, selectedTargetUnit
                    )
                } else {
                    "Input tidak valid"
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(horizontal=32.dp, vertical=16.dp)


        ) {
            Text(text = stringResource(id = R.string.conversion))
        }

        // Hasil Konversi
        hasilKonversi?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Button(
                    onClick = {
                        shareData(
                            context = context,
                            message = hasilKonversi ?: ""
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    contentPadding = PaddingValues(horizontal=32.dp, vertical=16.dp)
                ) {
                    Text(text = stringResource(R.string.share))
                }
            }
        }

    }



fun konversiSuhu(nilai: Double, dari: String, ke: String): Double {
    val keCelsius = when (dari) {
        "Celsius" -> nilai
        "Fahrenheit" -> (nilai - 32) * 5 / 9
        "Reamur" -> nilai * 5 / 4
        "Kelvin" -> nilai - 273.15
        else -> nilai
    }

    return when (ke) {
        "Celsius" -> keCelsius
        "Fahrenheit" -> keCelsius * 9 / 5 + 32
        "Reamur" -> keCelsius * 4 / 5
        "Kelvin" -> keCelsius + 273.15
        else -> keCelsius
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assesment1Theme {
        MainScreen(rememberNavController())
    }
}