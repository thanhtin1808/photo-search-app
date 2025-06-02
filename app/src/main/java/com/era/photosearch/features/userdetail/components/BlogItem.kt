package com.era.photosearch.features.userdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun BlogItem(
    modifier: Modifier,
    blogUrl: String,
) {
    Column(
        modifier = modifier
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Blog",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = blogUrl,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BlogItemPreview() {
    BlogItem(modifier = Modifier.wrapContentHeight(), blogUrl = "https://blog.abc")
}