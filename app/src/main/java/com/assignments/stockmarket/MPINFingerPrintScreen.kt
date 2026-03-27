package com.assignments.stockmarket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun MPINFingerPrintScreen(
    navController: NavController,
    onMPINFingerPrintClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.bg_primary))
            .padding(horizontal = 24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔹 Logo
            Image(
                painter = painterResource(id = R.drawable.ic_stock_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(top = 100.dp)
            )

            // 🔹 MPIN
            Text(
                text = stringResource(R.string.mpin),
                color = colorResource(R.color.white),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(60.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(R.color.bg_mpin))
                .align(Alignment.BottomCenter)
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                // Logo + App Name
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(R.drawable.ic_stock_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(50.dp)
                    )

                    Text(
                        text = "Trade Sphere",
                        color = colorResource(R.color.white),
                        fontSize = 12.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Fingerprint Verification",
                    color = colorResource(R.color.text_accent_blue),
                    fontSize = 24.sp,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "Sign in using fingerprint",
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Fingerprint Icon
                Icon(
                    painter = painterResource(R.drawable.ic_biometric),
                    contentDescription = "Fingerprint",
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(120.dp)
                )

                Text(
                    text = "Scan your fingerprint",
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Enter TradeSphere PIN",
                    color = colorResource(R.color.text_accent_blue_light),
                    fontSize = 20.sp,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}