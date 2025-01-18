package com.gravity.billeasy.ui_layer.app_screens.base_screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gravity.billeasy.R
import com.gravity.billeasy.ui_layer.BillEasyBottomSheet
import com.gravity.billeasy.ui_layer.EditableFields

const val SHOP_NAME = "Shop name"
const val SHOP_ADDRESS = "Shop address"
const val SHOP_MOBILE_NUMBER = "Shop mobile number"
const val SHOP_EMAIL_ADDRESS = "Shop email address"
const val GST_NUMBER = "GST number"
const val TIN_NUMBER = "TIN number"
const val OWNER_NAME = "Owner name"
const val OWNER_ADDRESS = "Owner address"
const val OWNER_MOBILE_NUMBER = "Owner mobile number"

@Composable
fun Home() {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.white))
    ) {
        HomeQuickAccessible(screenHeight)
        SalesChart(screenHeight)
    }
}

@Composable
fun HomeQuickAccessible(screenHeight: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight.dp / 2)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShopDetailsEditable()
            QuickStockAndCreditDetails()
        }
    }
}

@Composable
fun ShopDetailsEditable() {
    val isNeedToLaunchShopDetailsEditScreen = remember { mutableStateOf(false) }
    val shopDetailsMapper: HashMap<String, EditableFields> = hashMapOf<String, EditableFields>()
    val shopName = remember { mutableStateOf("") }
    val shopAddress = remember { mutableStateOf("") }
    val shopEmailId = remember { mutableStateOf("") }
    val shopMobileNumber = remember { mutableStateOf("") }
    val gstNumber = remember { mutableStateOf("") }
    val tinNumber = remember { mutableStateOf("") }
    val ownerName = remember { mutableStateOf("") }
    val ownerMobileNumber = remember { mutableStateOf("") }
    val ownerAddress = remember { mutableStateOf("") }

    shopDetailsMapper.apply {
        put(SHOP_NAME, EditableFields(shopName, remember { mutableStateOf(false) }))
        put(SHOP_ADDRESS, EditableFields(shopAddress, remember { mutableStateOf(false) }))
        put(SHOP_EMAIL_ADDRESS, EditableFields(shopEmailId, remember { mutableStateOf(false) }))
        put(SHOP_MOBILE_NUMBER, EditableFields(shopMobileNumber, remember { mutableStateOf(false) }))
        put(GST_NUMBER, EditableFields(gstNumber, remember { mutableStateOf(false) }))
        put(TIN_NUMBER, EditableFields(tinNumber, remember { mutableStateOf(false) }))
        put(OWNER_NAME, EditableFields(ownerName, remember { mutableStateOf(false) }))
        put(OWNER_ADDRESS, EditableFields(ownerAddress, remember { mutableStateOf(false) }))
        put(OWNER_MOBILE_NUMBER, EditableFields(ownerMobileNumber, remember { mutableStateOf(false) }))
    }

    Row(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
    ) {
        /* TODO When cliked shop name need to show shop description by expanding the view */
        Text(
            text = "SK Stores",
            color = colorResource(R.color.black),
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Box(modifier = Modifier
            .padding(start = 10.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, radius = 100.dp)
            ) { isNeedToLaunchShopDetailsEditScreen.value = true }) {

            Image(
                painter = painterResource(R.drawable.edit), contentDescription = "Edit shop name"
            )

            if(isNeedToLaunchShopDetailsEditScreen.value) {
                BillEasyBottomSheet(
                    sheetHeader = "Edit shop",
                    onDoneClick = { true },
                    onDismiss = { isNeedToLaunchShopDetailsEditScreen.value = false }
                ) { EditShop(shopDetailsMapper = shopDetailsMapper) }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuickStockAndCreditDetails() {

    // we can implement reorder once the reorder component is completely done
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        stickyHeader { QuickStockAndCreditDetailStickyHeader("Stock Updates") }
        items(20) { QuickStockAndCreditDetailsView("Item $it") }
        stickyHeader { QuickStockAndCreditDetailStickyHeader("Credit Given") }
        items(20) { QuickStockAndCreditDetailsView("Credit $it") }
    }
}

@Composable
fun QuickStockAndCreditDetailsView(viewDetail: String) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = viewDetail,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 12.dp, start = 5.dp)
            )

            Box(
                modifier = Modifier
                    .padding(top = 8.dp, end = 10.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.drag_handle),
                    contentDescription = "drag and drop",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun QuickStockAndCreditDetailStickyHeader(headerText: String) {
    Text(
        text = headerText,
        color = colorResource(R.color.black),
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.white)),
        fontSize = 14.sp,
        fontWeight = FontWeight.W500
    )
}

@Composable
fun SalesChart(screenHeight: Int) {
    val imageSize = (screenHeight/6).dp
    val imagePosY = (screenHeight/24).dp
    val imagePosX = (screenHeight/6).dp
    val textX = (screenHeight/18).dp
    val textY = (screenHeight/24).dp
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight.dp)){
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Sales Chart",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.W500
            )

            Image(
                painter = painterResource(R.drawable.chart),
                contentDescription = "Sales chart",
                modifier = Modifier
                    .size(imageSize)
                    .offset(imagePosX, imagePosY)
                    .alpha(0.5f)
            )

            Text(text = "This feature yet to be implemented", modifier = Modifier.offset(textX, textY))
        }
    }
}