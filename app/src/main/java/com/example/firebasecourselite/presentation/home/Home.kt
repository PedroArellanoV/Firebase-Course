package com.example.firebasecourselite.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.firebasecourselite.R
import com.example.firebasecourselite.presentation.model.Artist
import com.example.firebasecourselite.presentation.model.Player
import com.example.firebasecourselite.presentation.model.Playlist
import com.example.firebasecourselite.ui.theme.Black
import com.example.firebasecourselite.ui.theme.Green
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = HomeViewModel(),
    auth: FirebaseAuth,
    navigateToInitial: () -> Unit
) {

    val artists = viewModel.artist.collectAsState()
    val playlists = viewModel.playlist.collectAsState()
    val player by viewModel.player.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(16.dp)
    ) {
        Text(
            text = "Popular artist.",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        LazyRow {
            items(artists.value) { artist ->
                ArtistItem(artist)
            }
        }
        Text(
            text = "Recomended playlists.",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        LazyRow {
            items(playlists.value) { playlist ->
                PlaylistItem(playlist)

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        player?.let{
            PlayerComponent(
                player = it,
                onPlaySelected = {viewModel.onPlaySelected()}
                )
        }
    }
}

@Composable
fun PlaylistItem(playlist: Playlist) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        AsyncImage(
            model = playlist.image,
            contentDescription = "Playlist Image",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = playlist.name.orEmpty(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ArtistItem(artist: Artist) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        AsyncImage(
            model = artist.image,
            contentDescription = "Artist Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Text(
            text = artist.name.orEmpty(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PlayerComponent(player: Player, onPlaySelected: () -> Unit) {

    val icon = if (player.play == true) R.drawable.ic_pause else R.drawable.ic_play

        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Green),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(player.artist?.name.orEmpty())
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = icon),
                contentDescription = "play/pause",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onPlaySelected() })
        }

}