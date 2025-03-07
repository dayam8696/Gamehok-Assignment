package com.example.gamehok.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gamehok.R
import com.example.gamehok.model.Game
import com.example.gamehok.model.Tournament
import com.example.gamehok.repository.TournamentRepository
import com.example.gamehok.viewmodel.TournamentViewModel
import com.example.gamehok.viewmodel.TournamentViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

@Composable
fun GameTournamentScreen(viewModel: TournamentViewModel = viewModel(factory = TournamentViewModelFactory(TournamentRepository.getInstance()))) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001208)), // Fixed background color
        verticalArrangement = Arrangement.spacedBy(12.dp) // Adds spacing between items
    ) {
        item { TopBar() }
        item { PremiumBanner() }
        item { GameCategories(viewModel , onViewAllClick = { /* Handle view all click */ }  ) }
        item { TournamentsSection(viewModel) } // Pass ViewModel
        item { PeopleToFollow() }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.profile), // Replace with your profile drawable
            contentDescription = "Profile",
            modifier = Modifier.size(40.dp)
        )

        // Right Side (Ticket, Coins & Notification)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            // Ticket & Coins Row
            Row(
                modifier = Modifier
                    .background(Color(0xFF008000), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ticket_icon), // Replace with your ticket icon
                    contentDescription = "Ticket Icon",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "245", color = Color.White, fontSize = 14.sp)

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.coin_icon), // Replace with your coin icon
                    contentDescription = "Coin Icon",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "2456", color = Color.White, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.width(12.dp)) // Space between row & notification

            // Notification Bell Icon
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}





@Composable
fun PremiumBanner() {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFEEBA6), Color.White),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY) // Vertical gradient
                    )
                )
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Gamehok Premium", fontWeight = FontWeight.Bold)
                    Text("Upgrade to premium membership for rewards.")
                }
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA7B4D))
                ) {
                    Text("Get Now", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun GameCategories(gameCategories: List<Game>, onViewAllClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Play Tournament by Games",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "View All",
                color = Color(0xFF00FF00), // Green color
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onViewAllClick() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(gameCategories) { game ->
                GameCategoryItem(game)
            }
        }
    }
}

@Composable
fun GameCategories(viewModel: TournamentViewModel, onViewAllClick: () -> Unit) {
    val gameCategories by viewModel.gameCategories.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Play Tournament by Games",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "View All",
                color = Color(0xFF00FF00), // Green color
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onViewAllClick() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (gameCategories.isEmpty()) {
            // Show a loading indicator while fetching data
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            LazyRow {
                items(gameCategories) { game ->
                    GameCategoryItem(game)
                }
            }
        }
    }
}
@Composable
fun GameCategoryItem(game: Game) {
    val placeholderImages = listOf(
        R.drawable.pubg_image,        // PUBG
        R.drawable.cod_image,         // Call of Duty
        R.drawable.counter_image          // Counter-Strike
    )

    val defaultDrawable = placeholderImages[(game.id % placeholderImages.size).absoluteValue]

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(width = 140.dp, height = 100.dp) // Adjusted size
                .clickable { /* Handle game category click */ },
            colors = CardDefaults.cardColors(containerColor = Color.Transparent) // No background color
        ) {
            Image(
                painter = painterResource(id = defaultDrawable),
                contentDescription = game.gameName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = game.gameName,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TournamentsSection(viewModel: TournamentViewModel) {
    val tournaments by viewModel.tournaments.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Compete in Battles",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "View All",
                color = Color(0xFF00FF00), // Green color
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { }
            )
        }
        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            error != null -> {
                error?.let { errorMessage ->
                    Text(text = "Error: $errorMessage", color = Color.Red)
                }
            }

            tournaments.isNotEmpty() -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(tournaments) { tournament ->
                        Log.d("TournamentData", tournament.toString())
                        TournamentCard(tournament)
                    }
                }
            }
        }
    }
}
@Composable
fun TournamentCard(tournament: Tournament) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .padding(8.dp)
            .shadow(6.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            val imageUrl = tournament.thumbnailPath?.takeIf { it.isNotBlank() } ?: R.drawable.tournament_icon

            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Tournament Thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Gradient Background behind the Text Content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF56E293), Color(0xFF062E17))
                        )
                    )
                    .padding(12.dp) // Padding inside the gradient area
            ) {
                Column {
                    // Tournament Name
                    Text(
                        text = tournament.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Organizer Info
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = tournament.organizerDetails.profileImagePath
                                ?: R.drawable.tournament_icon, // Fallback profile image
                            contentDescription = "Organizer Profile",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "By ${tournament.organizerDetails.name}",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Tournament Tags (Game Name, Solo/Duo, Entry Fee)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        TournamentTag(text = tournament.gameName)
                        TournamentTag(text = tournament.teamSize)
                        TournamentTag(text = "Entry-${tournament.entryFees} 🪙")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Start Time
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = "Time", tint = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Starts ${formatTime(tournament.tournamentStartTime)}",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Prize Pool
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = "Prize", tint = Color.Yellow)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Prize Pool: ${tournament.prizeCoins} 🪙",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Yellow
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Registration Status + Player Count
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF008000), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(text = tournament.status, color = Color.White, fontSize = 12.sp)
                        }

                        Box(
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(text = "${tournament.registeredCount}/${tournament.totalCount}", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TournamentTag(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFF002E14), shape = RoundedCornerShape(8.dp)) // Dark Green BG color
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, fontSize = 12.sp, color = Color.White)
    }
}

fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}


@Composable
fun PeopleToFollow() {
    val profileImages = listOf(
      com.example.gamehok.R.drawable.peoplepro,
        com.example.gamehok.R.drawable.profile_one,
        com.example.gamehok.R.drawable.profile_two

    )

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("People to follow", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("View More", color = Color(0xFF00C853), fontSize = 14.sp) // Green color for "View More"
        }

        Spacer(modifier = Modifier.height(8.dp))

        profileImages.forEach { imageRes ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Legend Gamer", color = Color.White, fontSize = 16.sp)
                }

                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003E14)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Follow", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}



@Preview
@Composable
fun PreviewGameTournamentScreen() {
    GameTournamentScreen()
}
