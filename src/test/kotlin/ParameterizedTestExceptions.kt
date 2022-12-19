package kotlin

class ParameterizedTestExceptions {

    @ParameterizedTest
    @MethodSource("getHttpClientExceptionMapping")
    fun `delete and HttpClientErrorException is thrown proper Exception is thrown`(httpStatus: HttpStatus, expectedException: Class<Exception>){
        val ecsCustomerId = Random.nextLong().toString()
        val paymentInstrumentId = UUID.randomUUID().toString()
        whenever(client.delete(ecsCustomerId = eq(ecsCustomerId), paymentInstrumentId = eq(paymentInstrumentId), version = any(), requesterId = any())).thenThrow(HttpClientErrorException(httpStatus))
        assertThrows(expectedException){ service.delete(ecsCustomerId = ecsCustomerId, paymentInstrumentId = paymentInstrumentId)}
    }

    companion object{
        @JvmStatic
        fun getHttpClientExceptionMapping(): Stream<Arguments> =
            Stream.of(
                Arguments.of(NOT_FOUND, NotFoundException::class.java),
                Arguments.of(UNAUTHORIZED, UnauthorizedException::class.java),
                Arguments.of(PRECONDITION_FAILED, PreconditionFailedException::class.java),
                Arguments.of(INTERNAL_SERVER_ERROR, InternalServerErrorException::class.java)
            )
    }
}