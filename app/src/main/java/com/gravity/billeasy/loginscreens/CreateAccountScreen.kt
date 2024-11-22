package com.gravity.billeasy.loginscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gravity.billeasy.R
import com.gravity.billeasy.navigationsetup.BillEasyScreens

/* TODO: Need to do field validation */

const val CREATE_YOUR_OWN_ACCOUNT = "Create your own account"
const val OWNER_FIRST_NAME = "Owner first name"
const val OWNER_LAST_NAME = "Owner last name"
const val SHOP_NAME = "Shop name"
const val SHOP_MOBILE_NUMBER = "Shop mobile number"
const val OWNER_MOBILE_NUMBER = "Owner mobile number"
const val SHOP_EMAIL = "Shop email"
const val OWNER_EMAIL = "Owner email"
const val SHOP_ADDRESS = "Shop address"
const val PROCEED = "Proceed"


@Composable
fun CreateAccountScreen(onClickProceed: (String, String) -> Unit) {

    val ownerFirstName = remember { mutableStateOf("") }
    val ownerLastName = remember { mutableStateOf("") }
    val shopName = remember { mutableStateOf("") }
    val shopMobileNumber = remember { mutableStateOf("") }
    val ownerMobileNumber = remember { mutableStateOf("") }
    val shopEmail = remember { mutableStateOf("") }
    val ownerEmail = remember { mutableStateOf("") }
    val shopAddress = remember { mutableStateOf("") }
    val accountFields = mutableListOf<MutableState<String>>()
    accountFields.apply {
        add(shopName)
        add(shopMobileNumber)
        add(shopAddress)
        add(shopEmail)
        add(ownerFirstName)
        add(ownerLastName)
        add(ownerMobileNumber)
        add(ownerEmail)
    }
    val createAccountFieldsMap = mutableMapOf<String, MutableState<String>>()
    createAccountFieldsMap.apply {
        put(SHOP_NAME, shopName)
        put(SHOP_MOBILE_NUMBER, shopMobileNumber)
        put(SHOP_ADDRESS, shopAddress)
        put(SHOP_EMAIL, shopEmail)
        put(OWNER_FIRST_NAME, ownerFirstName)
        put(OWNER_LAST_NAME, ownerLastName)
        put(OWNER_MOBILE_NUMBER, ownerMobileNumber)
        put(OWNER_EMAIL, ownerEmail)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.sandle))
    ) {

        Text(
            text = CREATE_YOUR_OWN_ACCOUNT,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 15.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            Modifier
                .padding(top = 15.dp)
                .align(Alignment.CenterHorizontally)
                .imePadding()
        ) {

            items(createAccountFieldsMap.toList()) { fieldValue ->
                OutlinedTextField(
                    value = fieldValue.second.value,
                    onValueChange = { fieldValue.second.value = it },
                    label = { Text(text = fieldValue.first) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = if (fieldValue.first.contains("number")) KeyboardType.Number
                        else KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, end = 20.dp, start = 20.dp)
                )
            }
        }

        ElevatedButton(
            onClick = { onClickProceed(shopMobileNumber.value, BillEasyScreens.CREATE_ACCOUNT.name) },
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = PROCEED)
        }

    }

}

