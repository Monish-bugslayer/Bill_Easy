package com.gravity.billeasy

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gravity.billeasy.navigationsetup.AppNavigationControllerImpl
import com.gravity.billeasy.navigationsetup.NavigationSetup
import com.gravity.billeasy.ui.theme.BillEasyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val window = (context as Activity).window
            window.statusBarColor = context.resources.getColor(R.color.white)
            window.navigationBarColor = context.resources.getColor(R.color.pink)
            val navHostController: NavHostController = rememberNavController()
            val appNavigationImpl = AppNavigationControllerImpl(navHostController)
            BillEasyTheme {
                Scaffold(bottomBar = { BottomNavigationBar() }) { innerPadding ->
                    val navigationSetup = NavigationSetup(navHostController, appNavigationImpl)
                    navigationSetup.SetupNavgation(innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    BottomAppBar(containerColor = Color(LocalContext.current.resources.getColor(R.color.pink ))) {

        val isHomeClicked = remember { mutableStateOf(true) }
        val isMyProductsClicked = remember { mutableStateOf(false) }
        val isSalesClicked = remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavigationIcon(
                onIconClick = {
                    isHomeClicked.value = !isHomeClicked.value
                    isSalesClicked.value = false
                    isMyProductsClicked.value = false
                },
                isNeedToDisplayText = isHomeClicked,
                displayText = "Home",
                bottomTextViewHeight = 20.dp,
                bottomTextViewWidth = 40.dp,
                contentDescription = "Home",
                imageResId = R.drawable.home,
                boxSize = 100.dp
            )
            BottomNavigationIcon(
                onIconClick = {
                    isMyProductsClicked.value = !isMyProductsClicked.value
                    isSalesClicked.value = false
                    isHomeClicked.value = false
                },
                isNeedToDisplayText = isMyProductsClicked,
                displayText = "My products",
                bottomTextViewHeight = 20.dp,
                bottomTextViewWidth = 80.dp,
                contentDescription = "My products",
                imageResId = R.drawable.products,
                boxSize = 120.dp
            )
            BottomNavigationIcon(
                onIconClick = {
                    isSalesClicked.value = !isSalesClicked.value
                    isHomeClicked.value = false
                    isMyProductsClicked.value = false
                },
                isNeedToDisplayText = isSalesClicked,
                displayText = "Sales",
                bottomTextViewHeight = 20.dp,
                bottomTextViewWidth = 40.dp,
                contentDescription = "Sales",
                imageResId = R.drawable.invoice,
                boxSize = 100.dp
            )
        }
    }
}

@Composable
fun BottomNavigationIcon(
    onIconClick: () -> Unit,
    isNeedToDisplayText: MutableState<Boolean>,
    displayText: String,
    imageResId: Int,
    contentDescription: String,
    bottomTextViewWidth: Dp,
    bottomTextViewHeight: Dp,
    boxSize: Dp
) {
    val context = LocalContext.current
    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .size(boxSize)
        .clip(CircleShape)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = true, radius = 100.dp)
        ) { onIconClick() }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = contentDescription,
                modifier = Modifier.size(25.dp)
            )
            Box(
                modifier = Modifier
                    .height(bottomTextViewHeight)
                    .width(bottomTextViewWidth),
                contentAlignment = Alignment.Center
            ) {
                if (isNeedToDisplayText.value) {
                    Text(
                        text = displayText,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.drawBehind {
                            drawLine(
                                strokeWidth = Stroke.DefaultMiter,
                                start = Offset(x = -10f, y = size.height + 5),
                                end = Offset(x = size.width + 10, size.height + 5),
                                color = Color(context.resources.getColor(R.color.black))
                            )
                        }
                    )
                }
            }
        }
    }
}