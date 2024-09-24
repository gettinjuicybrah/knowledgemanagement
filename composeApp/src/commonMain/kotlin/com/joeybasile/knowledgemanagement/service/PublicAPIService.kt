package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.network.api.PublicAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PublicAPIService: KoinComponent {
    private val publicAPI: PublicAPI by inject()

    suspend fun login(username: String, password: String){
        publicAPI.login(username
    }
    suspend fun register(username: String, password: String){


    }

}