package com.assignments.stockmarket.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.assignments.stockmarket.R

object AppTextStyles {

    // -------- Regular --------
    @Composable
    fun regular(size: Int, color: Color = colorResource(R.color.white)): TextStyle {
        return TextStyle(
            fontSize = size.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            color = color,
            lineHeight = size.sp,
        )
    }

    // -------- Semi Bold --------
    @Composable
    fun medium(size: Int, color: Color = colorResource(R.color.white)): TextStyle {
        return TextStyle(
            fontSize = size.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Medium,
            color = color
        )
    }

    // -------- Semi Bold --------
    @Composable
    fun semiBold(size: Int, color: Color = colorResource(R.color.white)): TextStyle {
        return TextStyle(
            fontSize = size.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }

    // -------- Bold --------
    @Composable
    fun bold(size: Int, color: Color = colorResource(R.color.white)): TextStyle {
        return TextStyle(
            fontSize = size.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }

    // -------- Extra Bold --------
    @Composable
    fun extraBold(size: Int, color: Color = colorResource(R.color.white)): TextStyle {
        return TextStyle(
            fontSize = size.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.ExtraBold,
            color = color
        )
    }
}