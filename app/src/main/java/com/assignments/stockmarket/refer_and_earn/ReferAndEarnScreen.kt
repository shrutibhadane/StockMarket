package com.assignments.stockmarket.refer_and_earn

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.refer_and_earn.components.HowItWorksItem
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun ReferAndEarnScreen(navController: NavController) {

    val context = LocalContext.current
    val referralCode = stringResource(R.string.shruti123)

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController,
                title = stringResource(R.string.action_refer)
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(20.dp)
                .padding(innerPadding),
        ) {

            // 🎁 Banner
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.text_accent_blue)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Text(
                        text = stringResource(R.string.refer_earn_500),
                        style = AppTextStyles.bold(18)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.invite_your_friends_and_earn_rewards_when_they_start_investing),
                        style = AppTextStyles.regular(12)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 💰 Earnings
            Text(
                text = stringResource(R.string.your_earnings),
                style = AppTextStyles.bold(14)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string._1200_earned),
                style = AppTextStyles.bold(22,
                    colorResource(R.color.text_success_light)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔗 Referral Code Box
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.bg_mpin)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = referralCode,
                        style = AppTextStyles.bold(16)
                    )

                    Text(
                        text = stringResource(R.string.copy),
                        style = AppTextStyles.regular(12,
                            colorResource(R.color.bg_mpin)
                        ),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            val clipboard =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText(context.getString(R.string.referral_code), referralCode)
                            clipboard.setPrimaryClip(clip)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 📤 Share Button
            Button(
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Join this app using my code $referralCode and earn rewards!"
                        )
                    }
                    context.startActivity(Intent.createChooser(shareIntent,
                        context.getString(R.string.share_via)))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.bg_mpin),
                    contentColor = Color.White
                )
            ) {
                Text(
                    stringResource(R.string.invite_friends),
                    style = AppTextStyles.bold(14,
                        colorResource(R.color.bg_primary)
                    ),
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // How it works
            Text(
                text = stringResource(R.string.how_it_works),
                style = AppTextStyles.bold(14
                ),
            )

            Spacer(modifier = Modifier.height(10.dp))

            HowItWorksItem(stringResource(R.string.share_your_referral_code_with_friends))
            HowItWorksItem(stringResource(R.string.friend_signs_up_using_your_code))
            HowItWorksItem(stringResource(R.string.you_both_earn_rewards))

        }
    }
}