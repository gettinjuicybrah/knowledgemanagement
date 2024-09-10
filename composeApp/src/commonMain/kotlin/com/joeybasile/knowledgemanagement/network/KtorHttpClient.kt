package com.joeybasile.knowledgemanagement.network

/*
Will be able to call this function from each
individual platform's entry point.

From Android activity with the android engine
From desktop main with the htpp engine

This is the http client instance where all of the magic happens
where we actually make network requests.
 */
class KtorHttpClient(private val engine: HttpClientEngine) : KoinComponent {
    private val tokenRepository: TokenRepository by inject()
    fun create(): HttpClient {

        return HttpClient(engine) {
            /*
        Here, we can configure the http client.
        When it comes to configuring ktor clients,
        it usually means that we want to install 'features'
        LIKE: Content negotation (parse JSON files automatically),
        Logging (log everything automatically),
        Authentication: automatically refresh local access token when
        you have some sort of OAuth authentication system,
        and so on.
         */
            install(Logging) {
                //configure here.
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                //for JSON parsing. will using kotlinx serialization.
                json(
                    json = Json {
                        //If the server responds with JSON fields that the client not know about,
                        //then normally the app would crash.
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Auth) {
                //impl. here. would use Bear, refresh tokens, access tokens.
                bearer {
                    /*
                    This is called automatically by Ktor before every request that requires authentication.
                    Every time your code makes a request to a protected endpoint, Ktor will call loadTokens to get the current tokens.
                     */
                    loadTokens {
                        runBlocking {
                            //?: ""
                            val accessToken = tokenRepository.getAccessToken()
                            val refreshToken = tokenRepository.getRefreshToken()
                            if (accessToken != null && refreshToken != null) {
                                BearerTokens(accessToken, refreshToken)
                            } else {
                                null
                            }
                        }
                    }
                    /*
                    This is called automatically by Ktor when a request fails with a 401 Unauthorized status.
                    The process typically looks like this:

                    Ktor makes a request using the token from loadTokens.
                    If the server responds with 401 Unauthorized, Ktor automatically calls refreshTokens.
                    If refreshTokens succeeds, Ktor retries the original request with the new token.
                     */

                }
            }
            defaultRequest {
                url("http://198.199.122.146:8080")
            }
        }
    }
}