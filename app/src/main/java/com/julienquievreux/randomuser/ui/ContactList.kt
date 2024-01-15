package com.julienquievreux.randomuser.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.julienquievreux.randomuser.R.*
import com.julienquievreux.randomuser.models.ContactView
import com.julienquievreux.randomuser.viewmodels.UiState
import com.julienquievreux.randomuser.viewmodels.UiState.Error
import com.julienquievreux.randomuser.viewmodels.UiState.FilteredList
import com.julienquievreux.randomuser.viewmodels.UiState.Loading
import com.julienquievreux.randomuser.viewmodels.UiState.RefreshSuccess
import com.julienquievreux.randomuser.viewmodels.UiState.Success

@Composable
fun ContactListTextStatus(messageId: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        textAlign = TextAlign.Center,
        text = stringResource(id = messageId)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListItem(
    contactView: ContactView,
    onClickAction: ((contactView: ContactView) -> Unit)?=null,
) {
    OutlinedCard(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
        onClick = {
            onClickAction?.invoke(contactView)
        }
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onSurface)
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(contactView.mediumPicUrl)
                    .crossfade(true)
                    .build(),
//            placeholder = painterResource(drawable.placeholder),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(64.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = contactView.fullName,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = contactView.email,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Icon(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ContactList(
    uiState: UiState,
    onClickAction: ((contactView: ContactView) -> Unit)?=null,
) {
    when (uiState) {
        is Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ContactListTextStatus(messageId = string.contact_list_error)
                Spacer(modifier = Modifier.size(28.dp))
                Icon(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Rounded.WarningAmber,
                    contentDescription = stringResource(id = string.contact_list_error)
                )
            }
        }

        is FilteredList -> {

        }

//        Empty -> {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                ContactListTextStatus(messageId = string.contact_list_empty)
//                Spacer(modifier = Modifier.size(28.dp))
//                Icon(
//                    modifier = Modifier.size(64.dp),
//                    imageVector = Icons.Rounded.CloudOff,
//                    contentDescription = stringResource(id = string.contact_list_error)
//                )
//            }
//        }

        Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ContactListTextStatus(messageId = string.contact_list_loading)
                Spacer(modifier = Modifier.size(28.dp))
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }

        is RefreshSuccess -> {

        }

        is Success -> {
            uiState.contacts?.let { contacts: List<ContactView> ->
                LazyColumn {
                    itemsIndexed(contacts) { index, value: ContactView ->
                        ContactListItem(
                            contactView = value,
                            onClickAction = onClickAction
                        )
                    }
//                if (pagerData.loadState.append is LoadState.Loading) {
//                    item {
//                        Text(
//                            text = "Loading",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 10.dp),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                } else if (pagerData.loadState.append is LoadState.Error) {
//                    item {
//                        Text(
//                            text = "Loading Error",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 10.dp),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
                }
            }
        }
    }
    /*  if (pagerData.loadState.refresh is LoadState.Error) {
          LazyColumn {
              itemsIndexed(pagerData) { index, value ->
                  Text(
                      text = "Index=$index $value",
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(vertical = 10.dp),
                      textAlign = TextAlign.Center
                  )
              }
              if (pagerData.loadState.append is LoadState.Loading) {
                  item {
                      Text(
                          text = "Loading",
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(vertical = 10.dp),
                          textAlign = TextAlign.Center
                      )
                  }
              } else if (pagerData.loadState.append is LoadState.Error) {
                  item {
                      Text(
                          text = "Loading Error",
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(vertical = 10.dp),
                          textAlign = TextAlign.Center
                      )
                  }
              }
          }
      }*/
}

@Composable
private fun getFakeContactView() = ContactView(
    uuid = "proin",
    username = "Brandi Pittman",
    firstName = "Whitney",
    lastName = "Lloyd",
    email = "calvin.vazquez@example.com",
    gender = "ut",
    registrationDate = "aenean",
    registrationAge = "laoreet",
    phoneNumber = "(972) 784-8551",
    largePicUrl = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
    mediumPicUrl = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
    thumbPicUrl = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
    latitude = "commodo",
    longitude = "graece"
)

@Composable
@Preview
fun ContactListPreview() {
    MaterialTheme {
        ContactList(
            uiState = UiState.Success(
                listOf(
                    getFakeContactView()
                )
            )
        )
    }
}