package dev.alexpace.kassist.ui.victim.pages.help

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.UserRepositoryImpl
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.ui.victim.components.requests.HelpRequestForm
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.firstOrNull
import org.koin.compose.koinInject

@Composable
fun VictimHelpPage() {
    val helpRequestRepository = koinInject<HelpRequestRepositoryImpl>()
    val userRepository = koinInject<UserRepositoryImpl>()

    val currentUserId = Firebase.auth.currentUser?.uid
    var currentUser by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(currentUserId) {
        if (currentUserId != null) {
            currentUser = userRepository.getById(currentUserId).firstOrNull()
        }
    }

    val viewModel: VictimHelpPageViewModel =
        viewModel { VictimHelpPageViewModel(helpRequestRepository) }

    currentUser?.let { user ->
        HelpRequestForm(user, onSubmit = { helpRequest ->
            viewModel.submitHelpRequest(helpRequest)
        })
    }
}