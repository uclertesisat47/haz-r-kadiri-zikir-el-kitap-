package com.kadiri.elkitabi.core.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.kadiri.elkitabi.core.designsystem.theme.ZikirCounterStyle

@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = ZikirCounterStyle,
    color: Color = MaterialTheme.colorScheme.primary
) {
    AnimatedContent(
        targetState   = count,
        transitionSpec = {
            if (targetState > initialState) {
                slideInVertically { -it } togetherWith slideOutVertically { it }
            } else {
                slideInVertically { it } togetherWith slideOutVertically { -it }
            }
        },
        label = "counter_animation",
        modifier = modifier
    ) { targetCount ->
        Text(
            text  = targetCount.toString(),
            style = style,
            color = color
        )
    }
}
