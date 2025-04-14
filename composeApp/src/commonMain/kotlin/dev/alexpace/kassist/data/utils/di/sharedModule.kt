package dev.alexpace.kassist.data.utils.di

import dev.alexpace.kassist.data.repositoriesImpl.EmergencyPlanRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.servicesImpl.FirebaseAuthServiceImpl
import dev.alexpace.kassist.domain.repositories.EmergencyPlanRepository
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::EmergencyPlanRepositoryImpl).bind<EmergencyPlanRepository>()
    singleOf(::HelpRequestRepositoryImpl).bind<HelpRequestRepository>()
    singleOf(::HelpProposalRepositoryImpl).bind<HelpProposalRepository>()
    singleOf(::FirebaseAuthServiceImpl).bind<FirebaseAuthService>()
}