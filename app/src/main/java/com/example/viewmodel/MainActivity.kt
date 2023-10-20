@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodel.data.DataForm
import com.example.viewmodel.data.DataSource.jenis
import com.example.viewmodel.ui.theme.ViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Kiw()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilLayar(
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            TampilForm()
        }
    }
}
@Composable
fun TampilForm(cobaViewModel: CobaViewModel = viewModel()){
    var textNama by remember{mutableStateOf("")}
    var textTlp by remember{mutableStateOf("")}

    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState

    OutlinedTextField(
        value = textNama, singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nama Lengkap")},
        onValueChange = {
            textNama = it
        }
    )
    OutlinedTextField(
        value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telepon")},
        onValueChange = {
            textTlp = it
        }
    )
    SelectJK(
        options = jenis.map { id -> context.resources.getString(id) },
        onSelectionChanged = {cobaViewModel.setJenisK(it)}
    )
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
        cobaViewModel.insertData(textNama, textTlp, dataForm.sex)
        }
    ) {
        Text(text = stringResource(R.string.submit),
        fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(180.dp))
    TextHasil(namanya = cobaViewModel.namaUsr, telponnya = cobaViewModel.noTlp, jenisnya = cobaViewModel.jenisKl)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectJK(
    options: List<String>,
    onSelectionChanged: (String) -> Unit={}
){
    var selectedValue by rememberSaveable{ mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        options.forEach {item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextHasil(namanya: String,telponnya: String, jenisnya: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Nama : " + namanya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Telepon : " + telponnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Jenis Kelamin : " + jenisnya,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun Kiw(){
    ViewModelTheme {
        TampilLayar()
    }
}