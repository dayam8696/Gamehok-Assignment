import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.gamehok.R
import com.example.gamehok.model.Tournament
import com.example.gamehok.repository.TournamentRepository
import com.example.gamehok.ui.screens.TournamentsSection
import com.example.gamehok.viewmodel.TournamentViewModel
import com.example.gamehok.viewmodel.TournamentViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TournamentDetailScreen(viewModel: TournamentViewModel = viewModel(factory = TournamentViewModelFactory(
    TournamentRepository.getInstance())
)) {
    val tournament by viewModel.tournaments.collectAsState()

    if (tournament.isNotEmpty()) {
        val tournamentDetail = tournament.first()
        var selectedTabIndex by remember { mutableStateOf(0) }
        val tabs = listOf("Overview", "Players", "Rules")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Tournament Banner
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = if (tournamentDetail.thumbnailPath.isNullOrEmpty()) {
                        painterResource(id = R.drawable.tournament_icon)
                    } else {
                        rememberImagePainter(tournamentDetail.thumbnailPath)
                    },
                    contentDescription = "Tournament Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tournament Name & Organizer Details
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = tournamentDetail.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "By ${tournamentDetail.organizerDetails.name}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${tournamentDetail.gameName} | Entry-${tournamentDetail.entryFees}",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${tournamentDetail.registeredCount}/${tournamentDetail.totalCount}",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, color = Color.White) }
                    )
                }
            }

            // Tab Content
            when (selectedTabIndex) {
                0 -> OverviewSection(tournamentDetail)
                1 -> PlayersSection()
                2 -> RulesSection()
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}
@Composable
fun OverviewSection(tournamentDetail: Tournament) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            // Details Section
            Text(
                text = "Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0B0F0D), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                DetailItem(R.drawable.teams_icon, "Team Size", tournamentDetail.teamSize.capitalize())
                DetailItem(R.drawable.format_icon, "Format", "Single Elimination")
                DetailItem(R.drawable.calender_icon, "Tournament Starts", formatTimestamp(tournamentDetail.tournamentStartTime))
                DetailItem(R.drawable.check_icon, "Check-In", "10 mins before the match starts")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Prize Pool Section
        item {
            Text(
                text = "Total Tournament Prize",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0B0F0D), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                PrizeItem(R.drawable.trophy_icon, "1st Prize", "1000")
                PrizeItem(R.drawable.trophy_icon, "2nd Prize", "500")
                PrizeItem(R.drawable.trophy_icon, "3rd Prize", "200")
                PrizeItem(R.drawable.trophy_icon, "4th Prize", "100")
                PrizeItem(R.drawable.trophy_icon, "5th Prize", "100")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Rounds and Schedule Section
        item {
            Text(
                text = "Rounds and Schedule",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0B0F0D), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                ScheduleItem("Qualifiers (Round 1)", "Single Elimination", "3rd Aug, 10:00 PM")
                ScheduleItem("Qualifiers (Round 1)", "Single Elimination", "3rd Aug, 10:00 PM")
                ScheduleItem("Qualifiers (Round 1)", "Single Elimination", "3rd Aug, 10:00 PM")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // How to Join Section
        item {
            Text(
                text = "How to Join a Match",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0B0F0D), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = "• Have your game open already and on the latest version", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "• Once the match is configured you will receive an invite in-game to join the lobby.", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "• Join the match and wait for the game to start.", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "• When eliminated, return to the match room page to be ready to join the next map in the round.", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
// Contact Section
        item {
            Text(
                text = "Organiser’s Details and Contact",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0B0F0D), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Gamehok Esports",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "This is the in-house organiser of this platform.\nYou can follow our page on this platform for regular updates.",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { /* Follow button action */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF002E14)) // Green Follow Button
                ) {
                    Text(text = "Follow", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Phone Number and Email in the same row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.phone_icon),
                        contentDescription = "Phone Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "9890987754", color = Color.White)

                    Spacer(modifier = Modifier.width(16.dp)) // Space between phone and email

                    Icon(
                        painter = painterResource(id = R.drawable.mail_icon),
                        contentDescription = "Mail Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Support@gamehok.com", color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))

                // WhatsApp Number
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.whatsapp_icon),
                        contentDescription = "WhatsApp Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "9890987754", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        // More Tournaments Section
        item {
            Text(
                text = "More tournaments for you",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            TournamentsSection(viewModel = TournamentViewModel(TournamentRepository.getInstance()))
        }
        item {
            Button(
                onClick = { /* Handle join tournament action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), // Adjust height as needed
                colors = ButtonDefaults.buttonColors(Color(0xFF01B752)), // Green color
                shape = RoundedCornerShape(8.dp) // Rounded corners
            ) {
                Text(
                    text = "JOIN TOURNAMENT",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}


@Composable
fun PrizeItem(iconRes: Int, title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value, color = Color.White)
    }
}
@Composable
fun ScheduleItem(round: String, format: String, time: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Row for Round Title & Format Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = round, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.weight(1f)) // Pushes the format badge to the right
            Box(
                modifier = Modifier
                    .background(Color(0xFF311A61), shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = format, color = Color.White, fontSize = 12.sp)
            }
        }

        // Row for "Top 4 to next round" and Date/Time
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Top 4 to next round", color = Color.Gray)
            Text(text = time, color = Color.White)
        }
    }
}





@Composable
fun PlayersSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Leaderboard", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        val dummyLeaderboard = listOf("Player 1", "Player 2", "Player 3", "Player 4")

        dummyLeaderboard.forEachIndexed { index, player ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "#${index + 1}", color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = player, color = Color.White)
            }
        }
    }
}

@Composable
fun RulesSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tournament Rules", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("1. Rule 1: Follow the guidelines.", color = Color.Gray)
        Text("2. Rule 2: No cheating.", color = Color.Gray)
        Text("3. Rule 3: Be respectful.", color = Color.Gray)
    }
}

@Composable
fun DetailItem(@DrawableRes iconRes: Int, title: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            tint = Color(0xFF00FF99), // Green accent color
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title.uppercase(), fontSize = 14.sp, color = Color(0xFF7D7D7D)) // Grayish text
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}


fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEE d MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}