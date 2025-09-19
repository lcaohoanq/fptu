package com.fptu.lab.lab3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fptu.lab.ui.theme.labTheme

data class Item(val title: String, val description: String? = "", val imageUrl: String? = "")
data class ListItem(val items: List<Item>)

val items = List(5) { index -> Item(title = "Item $index") }


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Lab3_Bai1_Preview (){

    labTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("ListView") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Magenta,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = 30.dp, end = 30.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                items.forEach { item ->
                    Text(text = item.title, modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 20.dp))
                    HorizontalDivider()
                }

            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Lab3_Bai1_2_Preview (){

    var title by remember { mutableStateOf("") }
    val items = remember { mutableStateListOf(
        Item(title = "Item 0"),
        Item(title = "Item 1"),
        Item(title = "Item 2"),
        Item(title = "Item 3"),
        Item(title = "Item 4"),
    ) }
    var selectedIndex by remember { mutableStateOf(-1) }


    labTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("ListView") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Magenta,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(onClick = {
                        if (title.isNotEmpty()) {
                            items.add(Item(title = title))
                            title = ""
                        }
                    }) {
                        Text(text = "Add")
                    }

                    Button(onClick = {
                        if (selectedIndex != -1 && title.isNotEmpty()) {
                            items[selectedIndex] = items[selectedIndex].copy(title = title)
                            title = ""
                            selectedIndex = -1
                        }
                    }) {
                        Text(text = "Update")
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp),
                    horizontalAlignment = Alignment.Start,
                ) {

                    items.forEachIndexed { index, item ->
                        Text(
                            text = item.title,
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp)
                                .clickable {
                                    title = item.title
                                    selectedIndex = index
                                }
                        )
                        HorizontalDivider()
                    }

                }
            }


        }

    }
}