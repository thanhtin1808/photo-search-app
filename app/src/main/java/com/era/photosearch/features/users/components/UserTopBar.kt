package com.era.photosearch.features.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.era.photosearch.compose.theme.PhotoSearchTheme

@Composable
internal fun UserTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Box(modifier = modifier.weight(1f)) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.wrapContentSize(),
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 40.dp)
            ) {
                Text(
                    text = title,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
internal fun UserTopBarPreview() {
    PhotoSearchTheme {
        Column {
            UserTopBar(title = "Github Users")
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .background(Color.DarkGray)
                    .fillMaxWidth()
            )
            UserTopBar(title = "User Details")
        }
    }

}