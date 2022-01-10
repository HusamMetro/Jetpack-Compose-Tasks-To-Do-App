package com.tuwaiq.husam.taskstodoapp.ui.screens.challenges

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.*
import com.tuwaiq.husam.taskstodoapp.data.models.ToDoTask
import com.tuwaiq.husam.taskstodoapp.ui.theme.*
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Action
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ChallengesScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var snackBoolean by remember { mutableStateOf(false) }
    var firstTime by remember { mutableStateOf(false) }
    var snackMessageTask by remember { mutableStateOf("") }
    if (firstTime) {
        LaunchedEffect(key1 = snackBoolean) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = context.getString(R.string.action_add) + " : " + snackMessageTask,
                    actionLabel = context.getString(R.string.ok)
                )
            }
        }
    }
    val lang: String by sharedViewModel.langState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        sharedViewModel.getMockTasks()
        sharedViewModel.getGoldMockTasks()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ChallengesAppBar()
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            if (sharedViewModel.errorMessage.isEmpty() && sharedViewModel.errorMessageGold.isEmpty()) {
                if (sharedViewModel.mockTasks.isEmpty() && sharedViewModel.goldMockTasks.isEmpty()) {
                    Column(
                        modifier = Modifier.padding(MEDIUM_PADDING),
                        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
                    ) {
                        Text(
                            text = stringResource(R.string.gold_challenges),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = LARGE_PADDING,
                                    horizontal = SMALLEST_PADDING
                                ),
                            color = GoldM,
                            style = MaterialTheme.typography.h5,
                            fontFamily = FontFamily.Serif
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)) {
                            repeat(2) {
                                AnimatedShimmerEffectGold()
                            }
                        }
                        Text(
                            text = stringResource(id = R.string.challenges),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    MEDIUM_PADDING
                                ),
                            style = MaterialTheme.typography.h5,
                        )
                        repeat(5) {
                            AnimatedShimmerEffect()
                        }
                    }
                } else {
                    var itemAppeared by remember { mutableStateOf(false) }
                    LaunchedEffect(key1 = true) {
                        itemAppeared = true
                    }
                    AnimatedVisibility(
                        visible = itemAppeared,
                        enter = expandVertically(
                            animationSpec = tween(
                                durationMillis = 300
                            )
                        ),
                    ) {
                        ChallengesContent(
                            mockTasks = sharedViewModel.mockTasks,
                            goldMockTasks = sharedViewModel.goldMockTasks,
                            lang = lang,
                            onAdd = { mock ->
                                val cal = Calendar.getInstance().timeInMillis
                                val startDate = dateFormatter(cal)
                                val endDate = dateFormatterForMock(cal)
                                sharedViewModel.updateTaskFields(
                                    ToDoTask(
                                        title = getTitleLang(mockToDoTask = mock, lang),
                                        description = getDescriptionLang(mockToDoTask = mock, lang),
                                        priority = mock.priority,
                                        startDate = startDate ?: mock.startDate,
                                        endDate = endDate ?: mock.endDate,
                                        maxTask = mock.maxTask,
                                        taskCounter = "0",
                                        gold = mock.gold
                                    )
                                )
                                sharedViewModel.handleDatabaseAction(Action.ADD)
                                snackMessageTask = getTitleLang(mockToDoTask = mock, lang)
                                firstTime = true
                                snackBoolean = !snackBoolean
                            }
                        )
                    }
                }
            } else {
                EmptyContentNoConnection()
            }
        }
    )
}

@Composable
fun EmptyContentNoConnection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(EMPTY_ICON_SIZE),
            painter = painterResource(id = R.drawable.ic_baseline_wifi_off_24),
            contentDescription = stringResource(
                R.string.outlined_note
            ),
            tint = MediumGray
        )
        Text(
            text = stringResource(R.string.no_network),
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}