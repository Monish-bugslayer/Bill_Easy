package com.gravity.billeasy.ui_layer

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource


class ScrollState {
    var isNeedToShowFab = mutableStateOf(true)
    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (available.y < 0 && isNeedToShowFab.value) {
                isNeedToShowFab.value = false
            }
            else if (available.y > 0 && !isNeedToShowFab.value) { isNeedToShowFab.value = true }
            return Offset.Zero
        }
    }
}