package com.era.photosearch.features.users.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.era.photosearch.domain.models.users.UserDetailModel
import com.era.photosearch.domain.models.users.UserModel
import com.era.photosearch.R
import com.era.photosearch.compose.theme.PhotoSearchTheme
import java.util.Locale

@Composable
internal fun UserItem(
    userModel: UserModel,
    userDetailModel: UserDetailModel = UserDetailModel(),
    isVisibleLocation: Boolean = false,
    modifier: Modifier = Modifier,
    onUserLandingPageClick: () -> Unit = {},
    onUserClick: () -> Unit = {},
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
            Row(modifier = Modifier
                .fillMaxSize()
                .clickable { onUserClick() }
                .padding(vertical = 12.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(
                            color = colorResource(R.color.light_gray),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = userModel.avatar,
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.ic_user_foreground),
                        error = painterResource(id = R.drawable.ic_user_foreground),
                    )

                }

                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 2.dp),
                    ) {
                        Text(
                            text = userModel.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        if (isVisibleLocation) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.wrapContentSize(),  // Fill the available space
                                horizontalArrangement = Arrangement.Start  // Center horizontally if needed
                            ) {
                                Image(
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.size(15.dp),
                                    painter = painterResource(R.drawable.ic_location),
                                    contentDescription = null
                                )
                                Text(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    text = userDetailModel.location,
                                    color = colorResource(R.color.medium_gray),
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        } else {
                            Text(
                                text = userModel.landingPageUrl,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = colorResource(R.color.light_blue),
                                    textDecoration = TextDecoration.Underline,
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.clickable { onUserLandingPageClick() },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun UserItemPreview() {
    PhotoSearchTheme {
        UserItem(
            modifier = Modifier.height(130.dp), userModel = UserModel(
                id = 0, avatar = "", name = "David", landingPageUrl = "https://www.linkedin.com/"
            )
        )
    }

}