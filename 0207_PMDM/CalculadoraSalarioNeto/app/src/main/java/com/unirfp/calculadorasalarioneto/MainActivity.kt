package com.unirfp.calculadorasalarioneto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.unirfp.calculadorasalarioneto.ui.theme.CalculadoraSalarioNetoTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraSalarioNetoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "formulario"
                ) {
                    composable("formulario") {
                        MyApp(navController = navController)
                    }
                    composable(
                        route = "resultado/{salarioBruto}/{pagasExtras}/{edad}/{estadoCivil}/{numHijos}",
                        arguments = listOf(
                            navArgument("salarioBruto") { type = NavType.FloatType },
                            navArgument("pagasExtras") { type = NavType.IntType },
                            navArgument("edad") { type = NavType.IntType },
                            navArgument("estadoCivil") { type = NavType.StringType },
                            navArgument("numHijos") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        Resultado(
                            navController = navController,
                            salarioBruto = arguments.getFloat("salarioBruto").toDouble(),
                            pagasExtras = arguments.getInt("pagasExtras"),
                            edad = arguments.getInt("edad"),
                            estadoCivil = arguments.getString("estadoCivil") ?: "Soltero",
                            numHijos = arguments.getInt("numHijos")
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(navController: NavHostController) {
    val focusManager = LocalFocusManager.current

    val salarioBrutoFR = remember { FocusRequester() }
    val pagasExtrasFR = remember { FocusRequester() }
    val edadFR = remember { FocusRequester() }
    val numHijosFR = remember { FocusRequester() }

    var salarioBruto by rememberSaveable { mutableStateOf("") }
    var pagasExtras by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }
    var estadoCivil by rememberSaveable { mutableStateOf("Soltero") }
    var tieneHijos by rememberSaveable { mutableStateOf(false) }
    var numHijos by rememberSaveable { mutableStateOf("") }



    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Calculadora de salario neto",
                        textAlign = TextAlign.Justify
                    )

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),

                )
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            shape = MaterialTheme.shapes.small,
            color = Color(0xFFEEEEEF),
            shadowElevation = 50.dp,

            ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Introduzca sus datos",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                OutlinedTextField(
                    value = salarioBruto,
                    onValueChange = {
                        if(it.matches(Regex("^\\d*$"))) {
                            salarioBruto = it
                        }
                    },
                    label = { Text("Salario bruto anual €") },
                    keyboardActions = KeyboardActions.Default,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .focusRequester(salarioBrutoFR)
                )

                OutlinedTextField(
                    value = pagasExtras,
                    onValueChange = {
                        if (it.matches(Regex("^\\d$"))) {
                            pagasExtras = it
                        }
                    },
                    label = {Text("Número de pagas extras (0-2)")},
                    keyboardActions = KeyboardActions.Default,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .focusRequester(pagasExtrasFR)
                )

                OutlinedTextField(
                    value = edad,
                    onValueChange = {
                        if(it.matches(Regex("^\\d{1,3}$"))) {
                            edad = it
                        }
                    },
                    label = { Text("Edad") },
                    keyboardActions = KeyboardActions.Default,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .focusRequester(edadFR)
                )

                Text(
                    text = "Estado civil:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("Soltero", "Casado").forEach { estado ->
                        FilterChip(
                            selected = estadoCivil == estado,
                            onClick = { estadoCivil = estado },
                            label = { Text(estado) }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = tieneHijos,
                        onCheckedChange = {
                            tieneHijos = it
                            if (!it) {
                                numHijos = "0"
                            }
                        }
                    )
                    Text(
                        "¿Tiene hijos?",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                if (tieneHijos) {
                    OutlinedTextField(
                        value = numHijos,
                        onValueChange = {
                            if ((it.matches(Regex("^\\d{1,2}$")))) {
                                numHijos = it
                            }
                        },
                        label = { Text("Número de Hijos") },
                        keyboardActions = KeyboardActions.Default,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .focusRequester(numHijosFR)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        val salario = salarioBruto.toDoubleOrNull()
                        val pagas = pagasExtras.toIntOrNull() ?: 0
                        val edadNum = edad.toIntOrNull()
                        val hijos = if (tieneHijos) numHijos.toIntOrNull() ?: 0 else 0

                        if (salario != null && salario > 0 && edadNum != null && edadNum >= 16) {
                            navController.navigate(
                                "resultado/$salario/$pagas/$edadNum/$estadoCivil/$hijos"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = salarioBruto.isNotEmpty() &&
                            edad.isNotEmpty() &&
                            (!tieneHijos || numHijos.isNotEmpty()) &&
                            (edad.toIntOrNull() ?: 0) >= 16
                ) {
                    Text(
                        "CALCULAR SALARIO NETO",
                        fontSize = 16.sp
                    )
                }

            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Resultado(
    navController: NavHostController,
    salarioBruto: Double,
    pagasExtras: Int,
    edad: Int,
    estadoCivil: String,
    numHijos: Int
) {
    val resultado = calcularSalarioNeto(salarioBruto, pagasExtras, edad, estadoCivil, numHijos)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resultado del Cálculo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            shape = MaterialTheme.shapes.small,
            color = Color(0xFFEEEEEF),
            shadowElevation = 50.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Salario neto",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Salario bruto",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ResultadoItem(
                            label= "Anual:",
                            valor = resultado.salarioBrutoAnual
                        )

                        ResultadoItem(
                            label = "Mensual:",
                            valor= resultado.salarioBrutoMensual
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = Color(0xFF002AB3)
                        )


                        Text(
                            text = "Deducciones",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ResultadoItem(
                            label = "Seguridad social (${resultado.porcentajeSS}%):",
                            valor = resultado.seguridadSocial
                        )

                        ResultadoItem(
                            "IRPF (${resultado.porcentajeIRPF}%):",
                            resultado.irpf
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ResultadoItem(
                            "Total deducciones:",
                            resultado.totalDeducciones,
                            )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = Color(0xFF002AB3)
                        )

                        Text(
                            text = "Salario neto",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        ResultadoItem(
                            label = "Anual:",
                            valor = resultado.salarioNetoAnual,
                            destacado = true,
                            size = 20.sp
                        )

                        ResultadoItem(
                            label = "Mensual:",
                            valor = resultado.salarioNetoMensual,
                            destacado = true,
                            size = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("CALCULAR OTRO SALARIO", fontSize = 16.sp)
                }
            }
        }

    }
}

@Composable
fun ResultadoItem(
    label: String,
    valor: String,
    destacado: Boolean = false,
    size: TextUnit = 16.sp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = size,
            fontWeight = if (destacado) FontWeight.Bold else FontWeight.Normal,
            color = if (destacado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = valor,
            fontSize = size,
            fontWeight = if (destacado) FontWeight.Bold else FontWeight.Normal,
            color = if (destacado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

data class ResultadoCalculo(
    val salarioBrutoAnual: String,
    val salarioBrutoMensual: String,
    val seguridadSocial: String,
    val porcentajeSS: String,
    val irpf: String,
    val porcentajeIRPF: String,
    val totalDeducciones: String,
    val salarioNetoAnual: String,
    val salarioNetoMensual: String
)

fun calcularSalarioNeto(
    salarioBrutoAnual: Double,
    pagasExtras: Int,
    edad: Int,
    estadoCivil: String,
    numHijos: Int
): ResultadoCalculo {
    val df = DecimalFormat("#,##0.00")

    val porcentajeSS = 6.35
    val seguridadSocial = salarioBrutoAnual * porcentajeSS / 100

    val baseImponible = salarioBrutoAnual - seguridadSocial
    var porcentajeIRPF = when {
        baseImponible <= 12450 -> 19.0
        baseImponible <= 20200 -> 24.0
        baseImponible <= 35200 -> 30.0
        baseImponible <= 60000 -> 37.0
        baseImponible <= 300000 -> 45.0
        else -> 47.0
    }

    if (estadoCivil == "Casado") {
        porcentajeIRPF -= 1.0
    }

    if (numHijos > 0) {
        porcentajeIRPF -= (numHijos * 1.0)
    }

    if (edad < 25) {
        porcentajeIRPF -= 5.0
    }

    val irpf = baseImponible * porcentajeIRPF / 100

    val totalDeducciones = seguridadSocial + irpf
    val salarioNetoAnual = salarioBrutoAnual - totalDeducciones

    val numPagas = 12 + pagasExtras
    val salarioBrutoMensual = salarioBrutoAnual / numPagas
    val salarioNetoMensual = salarioNetoAnual / numPagas

    return ResultadoCalculo(
        salarioBrutoAnual = "${df.format(salarioBrutoAnual)} €",
        salarioBrutoMensual = "${df.format(salarioBrutoMensual)} €",
        seguridadSocial = "${df.format(seguridadSocial)} €",
        porcentajeSS = "$porcentajeSS",
        irpf = "${df.format(irpf)} €",
        porcentajeIRPF = df.format(porcentajeIRPF),
        totalDeducciones = "${df.format(totalDeducciones)} €",
        salarioNetoAnual = "${df.format(salarioNetoAnual)} €",
        salarioNetoMensual = "${df.format(salarioNetoMensual)} €"
    )
}