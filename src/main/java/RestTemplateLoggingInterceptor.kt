class RestTemplateLoggingInterceptor : org.springframework.http.client.ClientHttpRequestInterceptor {
    private val log = mu.KotlinLogging.logger {}

    @Throws(java.io.IOException::class)
    override fun intercept(req: org.springframework.http.HttpRequest, reqBody: ByteArray, ex: org.springframework.http.client.ClientHttpRequestExecution): org.springframework.http.client.ClientHttpResponse {
        log.debug("Request body:\n {}", String(reqBody, java.nio.charset.StandardCharsets.UTF_8))
        val response = ex.execute(req, reqBody)
        if (log.isDebugEnabled) {
            val isr = java.io.InputStreamReader(response.body, java.nio.charset.StandardCharsets.UTF_8)
            val body = java.io.BufferedReader(isr).lines()
                .collect(java.util.stream.Collectors.joining("\n"))
            log.debug("Response body: {}", body)
        }
        return response
    }
}
