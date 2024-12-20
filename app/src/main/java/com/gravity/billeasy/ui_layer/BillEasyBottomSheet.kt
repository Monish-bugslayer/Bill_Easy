package com.gravity.billeasy.ui_layer

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gravity.billeasy.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillEasyBottomSheet(
    listState: LazyListState,
    sheetHeader: String,
    onDoneClick: () -> Boolean,
    onDismiss: () -> Unit,
    sheetContent: @Composable () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val bottomSheetScope = rememberCoroutineScope()
    val keyboardCoroutineScope = rememberCoroutineScope()
    var isNeedToDismiss by remember { mutableStateOf(false) }
    val currentView = LocalView.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
        confirmValueChange = { listState.firstVisibleItemIndex == 0 || isNeedToDismiss })
    val isKeyboardVisible by remember {
        mutableStateOf(keyboardAsState(currentView, keyboardCoroutineScope).value)
    }

    ModalBottomSheet(sheetState = sheetState,
        containerColor = colorResource(R.color.white),
        onDismissRequest = {
            bottomSheetScope.launch {
                if (isKeyboardVisible) {
                    keyboardController?.hide()
                    delay(200)
                    onDismiss()
                } else {
                    onDismiss()
                }
            }
        }) {
        Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp * 0.85).dp)) {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 0.7f
                        )
                    }
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = sheetHeader,
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Image(imageVector = Icons.Default.Check,
                        colorFilter = ColorFilter.tint(colorResource(R.color.black)),
                        contentDescription = "",
                        alignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
                                if(onDoneClick()) {
                                    bottomSheetScope
                                        .launch {
                                            isNeedToDismiss = true
                                            if (isKeyboardVisible) {
                                                keyboardController?.hide()
                                                delay(200)
                                                sheetState.hide()
                                            } else {
                                                sheetState.hide()
                                            }
                                        }
                                        .invokeOnCompletion {
                                            if (!sheetState.isVisible) {
                                                onDismiss()
                                            }
                                        }
                                }
                            })
                }
                sheetContent()
            }
        }
    }
}

fun keyboardAsState(currentView: View, coroutineScope: CoroutineScope): State<Boolean> {
    val keyboardState = mutableStateOf(false)
    coroutineScope.launch {
        ViewCompat.setOnApplyWindowInsetsListener(currentView) { _, insets ->
            keyboardState.value = insets.isVisible(WindowInsetsCompat.Type.ime())
            insets
        }
    }
    return keyboardState
}