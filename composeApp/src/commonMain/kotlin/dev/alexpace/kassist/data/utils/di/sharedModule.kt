package dev.alexpace.kassist.data.utils.di

import dev.alexpace.kassist.data.repositoriesImpl.NaturalDisasterRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.UserRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.LiveChatRepositoryImpl
import dev.alexpace.kassist.data.network.servicesImpl.FirebaseAuthServiceImpl
import dev.alexpace.kassist.data.repositoriesImpl.AdminPendingDataRepositoryImpl
import dev.alexpace.kassist.data.network.servicesImpl.LiveNewsApiServiceImpl
import dev.alexpace.kassist.data.network.servicesImpl.GeoapifyApiServiceImpl
import dev.alexpace.kassist.data.network.servicesImpl.NaturalDisasterApiServiceImpl
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.alexpace.kassist.domain.services.GeoapifyApiService
import dev.alexpace.kassist.domain.services.LiveNewsApiService
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::NaturalDisasterRepositoryImpl).bind<NaturalDisasterRepository>()
    singleOf(::HelpRequestRepositoryImpl).bind<HelpRequestRepository>()
    singleOf(::HelpProposalRepositoryImpl).bind<HelpProposalRepository>()
    singleOf(::FirebaseAuthServiceImpl).bind<FirebaseAuthService>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::NaturalDisasterApiServiceImpl).bind<NaturalDisasterApiService>()
    singleOf(::LiveChatRepositoryImpl).bind<LiveChatRepository>()
    singleOf(::LiveNewsApiServiceImpl).bind<LiveNewsApiService>()
    singleOf(::AdminPendingDataRepositoryImpl).bind<AdminPendingDataRepository>()
    singleOf(::GeoapifyApiServiceImpl).bind<GeoapifyApiService>()
}