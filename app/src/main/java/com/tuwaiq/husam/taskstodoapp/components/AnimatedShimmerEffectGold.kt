package com.tuwaiq.husam.taskstodoapp.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuwaiq.husam.taskstodoapp.ui.theme.*

@Composable
fun AnimatedShimmerEffectGold() {
    val shimmerColors = listOf(
        MaterialTheme.colors.cardFirstColorGold.copy(alpha = 0.6f),
        MaterialTheme.colors.cardFirstColorGold.copy(alpha = 0.2f),
        MaterialTheme.colors.cardFirstColorGold.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
    ShimmerGridItemGold(brush = brush)
}

@Composable
fun ShimmerGridItemGold(brush: Brush) {
    val spacing = 15.dp
    val width = LocalConfiguration.current.screenWidthDp.dp.minus(LARGEST_PADDING)
    Row(
        modifier = Modifier
            .size(height = GOLD_MOCK_CARD_HEIGHT, width = width / 2)
            .clip(RoundedCornerShape(LARGE_PADDING))
            .background(brush = brush),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(spacing))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.95f)
                    .background(brush)
            )
            Spacer(modifier = Modifier.size(spacing))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(spacing))
                    .fillMaxWidth(0.90f)
                    .background(brush)
            )
            Spacer(modifier = Modifier.size(spacing))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(spacing))
                    .fillMaxWidth(0.92f)
                    .background(brush)
            )
            Spacer(modifier = Modifier.size(spacing))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.90f)
                    .background(brush)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(0.95f)
                .offset((-5).dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(
                modifier = Modifier
                    .size(PRIORITY_INDICATOR_SIZE)
                    .clip(CircleShape)
                    .background(brush)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ShimmerGridItemPreview() {
    AnimatedShimmerEffectGold()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ShimmerGridItemDarkPreview() {
    ShimmerGridItemGold(
        Brush.linearGradient(
            listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f)
            )
        )
    )
}