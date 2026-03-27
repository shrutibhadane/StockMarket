package com.assignments.stockmarket.help_support

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.help_support.components.SupportItem
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow

@Composable
fun HelpSupportScreen(navController: NavController) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController = navController,
                title = stringResource(R.string.section_help)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            SupportItem(
                icon = Icons.Default.HelpOutline,
                title = stringResource(R.string.label_faqs),
                subtitle = stringResource(R.string.msg_faq_desc),
                onClick = { }
            )

            SupportItem(
                icon = Icons.Default.Email,
                title = stringResource(R.string.action_email_support),
                subtitle = stringResource(R.string.label_support_email),
                onClick = {

                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse(context.getString(R.string.label_support_email))
                        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.label_support_request))
                    }

                    context.startActivity(intent)
                }
            )

            SupportItem(
                icon = Icons.Default.Call,
                title = context.getString(R.string.action_call_support),
                subtitle = context.getString(R.string.label_support_request),
                onClick = {

                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse(context.getString(R.string.label_contact_number))
                    }

                    context.startActivity(intent)
                }
            )

            SupportItem(
                icon = Icons.Default.ReportProblem,
                title = context.getString(R.string.action_report_issue),
                subtitle = context.getString(R.string.msg_report_issue),
                onClick = { }
            )

        }
    }
}