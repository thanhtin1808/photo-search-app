package com.era.photosearch.features.photos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.era.photosearch.R
import com.era.photosearch.compose.theme.PhotoSearchTheme
import com.era.photosearch.domain.models.photos.PhotoModel

@Composable
internal fun PhotoItem(
    photoModel: PhotoModel,
    modifier: Modifier = Modifier,
    onPhotoClick: () -> Unit = {},
) {
    val roundedCornerShape = RoundedCornerShape(12.dp)
    Surface(
        modifier = modifier.fillMaxSize(),
        shadowElevation = 7.dp,
        shape = roundedCornerShape,
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = roundedCornerShape,
            colors = CardDefaults.cardColors(
                containerColor = White,
            ),
        ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = colorResource(R.color.light_gray),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = photoModel.originalSourceImage,
                        contentDescription = "Photo Original",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        error = painterResource(id = R.drawable.ic_launcher_foreground),
                    )
            }
        }
    }
}

@Preview
@Composable
internal fun PhotoItemPreview() {
    PhotoSearchTheme {
        PhotoItem(
            modifier = Modifier.fillMaxSize(), photoModel = PhotoModel(
                id = 0,
                originalSourceImage = "https://images.pexels.com/photos/32370562/pexels-photo-32370562.jpeg"
            )
        )
    }

}