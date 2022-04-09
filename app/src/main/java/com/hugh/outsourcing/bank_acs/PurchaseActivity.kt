package com.hugh.outsourcing.bank_acs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hugh.outsourcing.bank_acs.ui.theme.MjuBankTheme

class PurchaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MjuBankTheme {
                Scaffold(
                    topBar = {
                        Title("一年存款") { finish() }
                    }
                ) {

                }
                // A surface container using the 'background' color from the theme
            }
        }
    }
}

@Preview
@Composable
fun Title(name: String = "HelloWorld", exit: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = name, fontSize = 42.sp) },
        navigationIcon = {
            IconButton(onClick = exit) { Icon(Icons.Filled.ArrowBack, "backToHome") }
        }
    )
}
@Composable
fun InfoCard(){
    Card(elevation = 20.dp) {
        
    }
}