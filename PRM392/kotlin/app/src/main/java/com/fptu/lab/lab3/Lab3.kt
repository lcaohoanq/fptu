package com.fptu.lab.lab3

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fptu.lab.R
import com.fptu.lab.ui.theme.labTheme

data class Item(val title: String, val description: String? = "", val imageUrl: String? = "")
data class ItemV2(val title: String, val description: String, val imageUrl: Int)
data class ListItem(val items: List<Item>)

val items = List(5) { index -> Item(title = "Item $index") }

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Lab3_Bai1_Preview() {
    var title by remember { mutableStateOf("") }
    val items = remember { mutableStateListOf(
        Item("Item 0"), Item("Item 1"), Item("Item 2"),
        Item("Item 3"), Item("Item 4")
    ) }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDeleteIndex by remember { mutableIntStateOf(-1) }
    val isSaveEnabled = title.isNotBlank()

    labTheme {
        Scaffold(
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
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Enter your item here") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        if (title.isNotEmpty()) {
                            items.add(Item(title))
                            title = ""
                        }
                    }) { Text("Add") }

                    Button(onClick = {
                        if (selectedIndex != -1 && title.isNotEmpty()) {
                            items[selectedIndex] = items[selectedIndex].copy(title = title)
                            title = ""
                            selectedIndex = -1
                        }
                    }) { Text("Update") }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    itemsIndexed(items) { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    title = item.title
                                    selectedIndex = index
                                }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(item.title)
                            IconButton(onClick = {
                                showDeleteDialog = true
                                itemToDeleteIndex = index
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red
                                )
                            }
                        }
                        HorizontalDivider()
                    }
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Confirm Delete") },
                        text = { Text("Are you sure you want to delete this item?") },
                        confirmButton = {
                            Button(onClick = {
                                items.removeAt(itemToDeleteIndex)
                                showDeleteDialog = false
                                if (selectedIndex == itemToDeleteIndex) {
                                    title = ""
                                    selectedIndex = -1
                                } else if (selectedIndex > itemToDeleteIndex) {
                                    selectedIndex--
                                }
                            }, enabled = isSaveEnabled) {
                                Text("Delete")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDeleteDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Lab3_Bai2_Preview() {
    val items = remember { mutableStateListOf(
        ItemV2("Apple", "apple... some description goes here", R.drawable.icons_apple),
        ItemV2("Banana", "banana... some description goes here", R.drawable.banana),
        ItemV2("Blueberry", "blueberry... some description goes here", R.drawable.blueberry),
        ItemV2("Corn", "corn... some description goes here", R.drawable.corn),
        ItemV2("Grapes", "grapes... some description goes here", R.drawable.grapes),
    ) }

    var selectedItemIndex by remember { mutableStateOf<Int?>(null) }

    labTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier
                                .clickable {
                                    // Add your action here
                                }
                                .padding(8.dp)
                        )
                    },
                    title = { Text("Custom_ListView") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White
                    ),
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    itemsIndexed(items) { index, item ->
                        ProductCard(
                            imgRes = item.imageUrl,
                            title = item.title,
                            description = item.description,
                            onClick = {
                                selectedItemIndex = index
                            }
                        )
                    }
                }
            }
        }
    }

    // Show dialog if item is selected - MOVED OUTSIDE OF SCAFFOLD
    selectedItemIndex?.let { index ->
        EditItemDialog(
            item = items[index],
            onDismiss = { selectedItemIndex = null },
            onSave = { newTitle, newDesc ->
                items[index] = items[index].copy(
                    title = newTitle,
                    description = newDesc
                )
                selectedItemIndex = null
            }
        )
    }
}

@Composable
fun ProductCard(
    @DrawableRes imgRes: Int,
    title: String,
    description: String? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Product image
            Image(
                painter = painterResource(id = imgRes),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            // Text content
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun EditItemDialog(
    item: ItemV2,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf(item.title) }
    var description by remember { mutableStateOf(item.description) }
    val isSaveEnabled = title.isNotBlank() && description.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Product") },
        text = {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(title, description) },
                enabled = isSaveEnabled) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductCardPreview() {
    ProductCard(
        imgRes = R.drawable.icons_apple,
        title = "Sample Product",
        description = "This is a sample product description."
    )
}