package com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.dialogs.tricount.addExpense.AddExpenseDialog
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import com.oliva.samuel.tricountclone.ui.widgets.expense.ExpensesList
import com.oliva.samuel.tricountclone.utils.Resource

@Composable
fun TricountDetailScreen(
    tricountDetailViewModel: TricountDetailViewModel,
    navigateToExpenseDetail: (ExpenseId) -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by tricountDetailViewModel.tricount.collectAsState()
    val showAddExpenseDialog by tricountDetailViewModel.showAddExpenseDialog.observeAsState(false)

    when (val tricountsInfo = uiState) {
        is Resource.Error -> {
            Text(
                text = tricountsInfo.message.orEmpty()
            )
        }

        Resource.Loading -> {
            CircularProgressIndicator()
        }

        is Resource.Success -> {
            TricountDetailScreenScaffold(
                tricount = tricountsInfo.data.tricount,
                participants = tricountsInfo.data.participants,
                expenses = tricountsInfo.data.expenses,
                onAddExpense = tricountDetailViewModel::onShowAddExpenseDialogClick,
                onExpenseSelected = { navigateToExpenseDetail(it.id) },
                navigateBack = navigateBack
            )

            AddExpenseDialog(
                show = showAddExpenseDialog,
                tricountId = tricountsInfo.data.tricount.id,
                onDismiss = tricountDetailViewModel::onDismissAddExpenseDialog,
                onExpenseAdded = tricountDetailViewModel::onExpenseAdded
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TricountDetailScreenScaffold(
    tricount: TricountUiModel,
    participants: Map<ParticipantId, ParticipantUiModel>,
    expenses: List<ExpenseUiModel>,
    onAddExpense: () -> Unit,
    onExpenseSelected: (ExpenseUiModel) -> Unit,
    navigateBack: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        TextField(
                            value = "",
                            onValueChange = { },
                            placeholder = { Text("Search expenses...") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isSearching) isSearching = false
                        else navigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    if (!isSearching) {
                        IconButton(
                            onClick = { isSearching = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }

                        IconButton(
                            onClick = { showMenu = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Tricount settings"
                            )

                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Edit") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit"
                                        )
                                    },
                                    onClick = {
                                        showMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Delete") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete tricount"
                                        )
                                    },
                                    onClick = {
                                        showMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpense
            ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add task") }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = tricount.icon,
                fontSize = 30.sp
            )

            Text(
                text = tricount.title
            )

            if (expenses.isNotEmpty()) {
                ExpensesList(
                    expensesList = expenses,
                    participants = participants,
                    onExpenseSelected = onExpenseSelected
                )
            } else {

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nothing here for now. Tap + to add an expense!"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TricountDetailScrenPreview() {
    TricountDetailScreenScaffold(
        tricount = TricountModel.default().copy(
            title = "Tricount Example",
            icon = "🏠"
        ).toUiModel(),
        participants = emptyMap(),
        expenses = emptyList(),
        onAddExpense = {},
        onExpenseSelected = {},
        navigateBack = {}
    )
}
