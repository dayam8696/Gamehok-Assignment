package com.example.gamehok.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamehok.R
import com.example.gamehok.model.Tournament
import com.example.gamehok.repository.TournamentRepository
import com.example.gamehok.viewmodel.TournamentViewModel
import com.example.gamehok.viewmodel.TournamentViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun GameTournamentScreen(viewModel: TournamentViewModel = viewModel(factory = TournamentViewModelFactory(TournamentRepository.getInstance()))) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001208)) // Fixed background color
    ) {
        TopBar()
        PremiumBanner()
        GameCategories()
        TournamentsSection(viewModel) // Pass ViewModel
        PeopleToFollow()
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
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {
        Box(modifier = Modifier.background(Color(0xFFFFD700))) {
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
fun GameCategories() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Play Tournament by Games", color = Color.White, fontSize = 18.sp)
        Row(modifier = Modifier.fillMaxWidth()) {
            GameCategoryItem("PUBG")
            GameCategoryItem("Call of Duty")
            GameCategoryItem("Counter Strike")
        }
    }
}

@Composable
fun GameCategoryItem(gameName: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = gameName, modifier = Modifier.padding(16.dp))
    }
}


@Composable
fun TournamentsSection(viewModel: TournamentViewModel) {
    val repository = TournamentRepository.getInstance()
    val viewModel: TournamentViewModel = viewModel(factory = TournamentViewModelFactory(repository))

    val tournaments by viewModel.tournaments.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Compete in Battles", color = Color.White, fontSize = 18.sp)

        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text("Error: $error", color = Color.Red)
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tournaments) { tournament ->
                    Log.d("TournamentData", tournament.toString()) // Logging full tournament details

                    TournamentCard(tournament)
                }
            }
        }
    }
}

@Composable
fun TournamentCard(tournament: Tournament) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .padding(8.dp)
            .shadow(6.dp, shape = RoundedCornerShape(12.dp)), // Use shadow instead of elevation
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = tournament.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Text(text = "Game: ${tournament.gameName}")
            Text(text = "Entry Fees: ${tournament.entryFees}")
            Text(text = "Prize: ${tournament.prizeCoins}")
            Text(text = "Status: ${tournament.status}")
            Text(text = "Start: ${formatTime(tournament.tournamentStartTime)}")

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "By ${tournament.organizerDetails.name}")
        }
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
