package com.fptu.lab.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lab4Screen() {
    val navController = rememberNavController()
    var selectedFood by remember { mutableStateOf("") }
    var selectedDrink by remember { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "main") {

        composable("main") {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Đặt thức ăn và thức uống") }
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { navController.navigate("food") }) {
                        Text("CHỌN THỨC ĂN")
                    }

                    Button(onClick = { navController.navigate("drink") }) {
                        Text("CHỌN ĐỒ UỐNG")
                    }

                    Button(
                        onClick = {
                            selectedFood = ""
                            selectedDrink = ""
                        }
                    ) {
                        Text("THOÁT")
                    }

                    val combinedText = when {
                        selectedFood.isNotEmpty() && selectedDrink.isNotEmpty() ->
                            "$selectedFood - $selectedDrink"
                        selectedFood.isNotEmpty() -> selectedFood
                        selectedDrink.isNotEmpty() -> selectedDrink
                        else -> "Chưa chọn món"
                    }

                    Text(
                        text = combinedText,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }

        composable("food") {
            SelectScreen(
                title = "Chọn thức ăn",
                options = listOf("Phở Hà Nội", "Bún Bò Huế", "Mì Quảng", "Hủ Tíu Sài Gòn"),
                onConfirm = { food ->
                    selectedFood = food
                    navController.popBackStack()
                }
            )
        }

        composable("drink") {
            SelectScreen(
                title = "Chọn đồ uống",
                options = listOf("Pepsi", "Heineken", "Tiger", "Sài gòn Đỏ"),
                onConfirm = { drink ->
                    selectedDrink = drink
                    navController.popBackStack()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectScreen(
    title: String,
    options: List<String>,
    onConfirm: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf("") }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(title) }) },
        bottomBar = {
            Button(
                onClick = { onConfirm(selectedItem) },
                enabled = selectedItem.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, bottom = 200.dp)
            ) {
                Text("Đặt món")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            options.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedItem = item }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedItem == item,
                        onClick = { selectedItem = item }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(item)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyAppPreview() {
    Lab4Screen()
}
