package com.kuluruvineeth.composefacebook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kuluruvineeth.composefacebook.ui.theme.ButtonGray
import com.kuluruvineeth.composefacebook.ui.theme.ComposeFacebookTheme

@Composable
fun HomeScreen(
    navigateToSignIn: () -> Unit
) {
    val viewModel = viewModel<HomeScreenViewModel>()
    val state by viewModel.state.collectAsState()
    when(state){
        is HomeScreenState.Loaded -> HomeScreenContent()
        HomeScreenState.Loading -> LoadingScreen()
        HomeScreenState.SignInRequired -> LaunchedEffect(Unit){
            navigateToSignIn()
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeScreenContent() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        LazyColumn() {
            item {
                TopAppBar()
            }
            item {
                TabBar()
            }
            item {
                StatusUpdateBar(
                    avatarUrl = "https://kuluruvineeth-portfolio.s3.ap-south-1.amazonaws.com/myPic.JPG",
                    onTextChange = {

                    },
                    onSendClick = {

                    }
                )
            }
        }
    }
}


@Composable
private fun TopAppBar() {
    Surface {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {},
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ButtonGray)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {},
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ButtonGray)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ChatBubble,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        }
    }

}


data class TabItem(
    val icon:ImageVector,
    val contentDescription:String
)

@Composable
fun TabBar() {
    Surface {
        var index by remember{
            mutableStateOf(0)
        }
        TabRow(
            selectedTabIndex = index,
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.primary
        ) {

            val tabs = listOf(
                TabItem(Icons.Rounded.Home, stringResource(id = R.string.home)),
                TabItem(Icons.Rounded.Tv, stringResource(id = R.string.reels)),
                TabItem(Icons.Rounded.Store, stringResource(id = R.string.marketplace)),
                TabItem(Icons.Rounded.Newspaper, stringResource(id = R.string.news)),
                TabItem(Icons.Rounded.Notifications, stringResource(id = R.string.notifications)),
                TabItem(Icons.Rounded.Menu, stringResource(id = R.string.menu))
            )

            tabs.forEachIndexed { i, item ->
                Tab(
                    selected = index==i,
                    onClick = { index=i },
                    modifier = Modifier.heightIn(
                        48.dp
                    )
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription,
                        tint = if(index==i){
                            MaterialTheme.colors.primary
                        }else{
                            MaterialTheme.colors.onSurface.copy(
                                alpha = 0.44f
                            )
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun StatusUpdateBar(
    avatarUrl: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(
                        LocalContext.current
                    ).data(avatarUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.ic_placeholder)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                /*Text(
                    text = stringResource(id = R.string.whats_on_your_mind),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.44f
                    )
                )*/
                var text by remember{
                    mutableStateOf("")
                }
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = {
                        text = it
                        onTextChange(it)
                    },
                    placeholder = {
                        Text(stringResource(id = R.string.whats_on_your_mind))
                    },
                    keyboardActions = KeyboardActions(
                        onSend = {
                            onSendClick()
                            text=""
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    )
                )
            }
            Divider(thickness = Dp.Hairline)
            Row(
                Modifier.fillMaxWidth(),
                //horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatusAction(
                    icon = Icons.Rounded.VideoCall,
                    text = stringResource(id = R.string.live),
                    modifier = Modifier.weight(1f)
                )
                VerticalDivider(
                    Modifier.height(48.dp),
                    thickness = Dp.Hairline
                )
                StatusAction(
                    icon = Icons.Rounded.PhotoAlbum,
                    text = stringResource(id = R.string.photo),
                    modifier = Modifier.weight(1f)
                )
                VerticalDivider(
                    Modifier.height(48.dp),
                    thickness = Dp.Hairline
                )
                StatusAction(
                    icon = Icons.Rounded.ChatBubble,
                    text = stringResource(id = R.string.chat),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    thickness: Dp = 1.dp,
    topIndent: Dp = 0.dp
) {
    val indentMod = if (topIndent.value != 0f) {
        Modifier.padding(top = topIndent)
    } else {
        Modifier
    }
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier
            .then(indentMod)
            .fillMaxHeight()
            .width(targetThickness)
            .background(color = color)
    )
}

@Composable
private fun StatusAction(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String
) {
        TextButton(
            modifier = modifier,
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colors.onSurface
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = text
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text
                )
            }

        }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    ComposeFacebookTheme {
        //HomeScreen()
    }
}