package com.anafthdev.bentalatumblr.ui.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.data.datasource.local.LocalMissionDataProvider
import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import com.anafthdev.bentalatumblr.foundation.common.mission.Mission
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTheme
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import kotlinx.coroutines.launch

@Composable
@Preview(showSystemUi = true)
private fun MissionScreenPreview() {
    BentalaTumblrTheme {
        MissionScreenContent(
            state = MissionState(
                unlockedAchievements = 2,
                missionProgress = listOf(
                    MissionProgress(
                        id = LocalMissionDataProvider.missions[0].id,
                        progress = 1f
                    ),
                    MissionProgress(
                        id = LocalMissionDataProvider.missions[1].id,
                        progress = 1f
                    )
                )
            ),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun MissionScreen(
    viewModel: MissionViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showMissionInfoBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectedMissionId by rememberSaveable { mutableStateOf<String?>(null) }

    MissionInfoBottomSheet(
        visible = showMissionInfoBottomSheet && selectedMissionId != null,
        selectedMissionId = selectedMissionId ?: "",
        missionProgress = state.missionProgress,
        onDismissRequest = {
            showMissionInfoBottomSheet = false
            selectedMissionId = null
        }
    )

    MissionScreenContent(
        state = state,
        onMissionClicked = { mission ->
            selectedMissionId = mission.id
            showMissionInfoBottomSheet = true
        },
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )


}

@Composable
private fun MissionScreenContent(
    state: MissionState,
    modifier: Modifier = Modifier,
    onMissionClicked: (Mission) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Header(
            unlockedAchievements = state.unlockedAchievements,
            modifier = Modifier
                .fillMaxWidth()
        )

        FlowRow(
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            for (mission in LocalMissionDataProvider.missions) {
                key(mission.id) {
                    val progress = remember(mission, state.missionProgress) {
                        state.missionProgress.find { it.id == mission.id }
                    }

                    MissionItem(
                        unlocked = progress?.isCompleted ?: false,
                        mission = mission,
                        onClick = {
                            onMissionClicked(mission)
                        },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }

        Spacer(Modifier.navigationBarsPadding())
    }
}

@Composable
private fun Header(
    unlockedAchievements: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier
            .background(Color(0xffe2f4ff))
            .padding(vertical = 32.dp, horizontal = 24.dp)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(R.drawable.medal),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
        )

        Text(
            text = stringResource(R.string.mission_achievement),
            style = MaterialTheme.typography.titleLarge,
            color = BentalaTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(
                R.string.mission_achievements_progress,
                unlockedAchievements,
                LocalMissionDataProvider.missions.size
            ),
            style = MaterialTheme.typography.labelSmall,
            color = BentalaTheme.colorScheme.lightTextColor
        )
    }
}

@Composable
private fun MissionItem(
    unlocked: Boolean,
    mission: Mission,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(mission.icon),
            contentDescription = null,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                setToSaturation(if (unlocked) 1f else 0f)
            }),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Text(
            text = stringResource(mission.title),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MissionInfoBottomSheet(
    visible: Boolean,
    selectedMissionId: String,
    missionProgress: List<MissionProgress>,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val selectedMission = remember(selectedMissionId) {
        LocalMissionDataProvider.missions.find { it.id == selectedMissionId }
    }

    val unlocked = remember(selectedMissionId, missionProgress) {
        missionProgress.find { it.id == selectedMissionId }?.isCompleted ?: false
    }

    val hideSheet: () -> Unit = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) onDismissRequest()
        }
    }

    if (visible && selectedMission != null) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            modifier = modifier
        ) {
            Spacer(Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(selectedMission.icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToSaturation(if (unlocked) 1f else 0f)
                    }),
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                ) {
                    Text(
                        text = stringResource(selectedMission.title),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = stringResource(selectedMission.description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                FilledTonalButton(
                    onClick = hideSheet,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = stringResource(R.string.mission_close_sheet)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
