package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_a
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_b
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_c
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_d
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_e
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room1
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room2
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room3
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room4
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room5
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.model.type
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet

@Composable
fun BookmarkList(
    scrollState: LazyListState,
    bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
    timetableItemMap: PersistentMap<String, List<TimetableItem>>,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkIconClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = scrollState,
        modifier = modifier.padding(end = 16.dp),
    ) {
        timetableItemMap.forEach { (_, values) ->
            itemsIndexed(values) { index, timetableItem ->
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Column(
                        modifier = Modifier.width(58.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        if (index == 0) {
                            Spacer(modifier = Modifier.size(6.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = timetableItem.startsTimeString,
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(text = "|")
                                Text(
                                    text = timetableItem.endsTimeString,
                                )
                            }
                        }
                    }
                    TimetableListItem(
                        timetableItem = timetableItem,
                        isBookmarked = bookmarkedTimetableItemIds.contains(timetableItem.id),
                        chipContent = {
                            val roomChipBackgroundColor = when (timetableItem.room.type) {
                                Room1 -> room_hall_a
                                Room2 -> room_hall_b
                                Room3 -> room_hall_c
                                Room4 -> room_hall_d
                                Room5 -> room_hall_e
                                else -> Color.White
                            }
                            AssistChip(
                                onClick = { /*Do Nothing*/ },
                                label = {
                                    Text(
                                        timetableItem.room.name.currentLangTitle,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp,
                                        color = Color.White,
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = roomChipBackgroundColor,
                                ),
                                border = AssistChipDefaults.assistChipBorder(
                                    borderColor = Color.Transparent,
                                    disabledBorderColor = Color.Transparent,
                                    borderWidth = 0.dp,
                                ),
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            AssistChip(
                                onClick = { /*Do Nothing*/ },
                                label = { Text(timetableItem.day?.name.orEmpty()) },
                            )
                        },
                        onClick = onTimetableItemClick,
                        onBookmarkClick = onBookmarkIconClick,
                    )
                }
            }
        }
    }
}
