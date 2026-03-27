package com.assignments.stockmarket.funds_payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.funds_payments.components.BalanceCard
import com.assignments.stockmarket.funds_payments.components.BankAccountItem
import com.assignments.stockmarket.funds_payments.components.PaymentActionButton
import com.assignments.stockmarket.funds_payments.components.TransactionItem
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun FundsPaymentsScreen(navController: NavController) {

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController = navController,
                title = stringResource(R.string.section_funds)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            BalanceCard()

            Spacer(modifier = Modifier.height(24.dp))

            PaymentActionButton(
                icon = Icons.Default.Add,
                title = "Add Money"
            )

            PaymentActionButton(
                icon = Icons.Default.Remove,
                title = "Withdraw Money"
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.label_linked_bank_accounts),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colorResource(R.color.white)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BankAccountItem(
                bankName = stringResource(R.string.label_hdfc_bank),
                accountNumber = stringResource(R.string.label__4321)
            )

            BankAccountItem(
                bankName = stringResource(R.string.label_icici_bank),
                accountNumber = stringResource(R.string.label__1298)
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.label_recent_transactions),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colorResource(R.color.white)
            )

            TransactionItem(
                stringResource(R.string.transaction_added), stringResource(R.string.rs_5_000),
                stringResource(R.string.label_today)
            )
            TransactionItem(
                stringResource(R.string.transaction_withdrawn),
                stringResource(R.string.rs_2_000), stringResource(R.string.label_yesterday)
            )
        }
    }
}
