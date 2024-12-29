package com.gravity.billeasy.ui_layer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gravity.billeasy.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillEasyBottomSheet(
    sheetHeader: String,
    onDoneClick: () -> Boolean,
    onDismiss: () -> Unit,
    sheetContent: @Composable () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newState ->
            newState != SheetValue.Hidden // avoid the swipe to dismiss functionality of the bottom sheet
        })
    val keyboardController = LocalSoftwareKeyboardController.current
    val bottomSheetScope = rememberCoroutineScope()
    var isNeedToDismiss by remember { mutableStateOf(false) }

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = colorResource(R.color.white),
        onDismissRequest = { onDismiss() },
        dragHandle = { null },
        modifier = Modifier.imePadding()
    ) {
        Box(Modifier.fillMaxSize()) {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .drawBehind {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 0.7f
                        )
                    }
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(bounded = true, radius = 100.dp)
                            ) { onDismiss() })
                    Text(
                        text = sheetHeader,
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Image(imageVector = Icons.Default.Check,
                        colorFilter = ColorFilter.tint(colorResource(R.color.black)),
                        contentDescription = "",
                        alignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clip(CircleShape)
                            .clickable {
                                if (onDoneClick()) {
                                    bottomSheetScope
                                        .launch {
                                            isNeedToDismiss = true
                                            sheetState.hide()
                                        }
                                        .invokeOnCompletion {
                                            if (sheetState.isVisible) {
                                                keyboardController?.hide()
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