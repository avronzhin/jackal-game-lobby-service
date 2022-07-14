package ru.rsreu.jackal

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret}") private val secretKeyStringRepresentation: String
) {
    private val secretKey: SecretKey =
        Keys.hmacShaKeyFor(secretKeyStringRepresentation.toByteArray(StandardCharsets.UTF_8))

    private val jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()

    private val lobbyIdJwtKey = "lobby_id"

    fun createAccessToken(lobbyId: Long, userId: Long): String = Jwts.builder()
        .setClaims(formAccessClaims(lobbyId, userId))
        .signWith(secretKey)
        .setHeaderParam("typ", "JWT")
        .compact()

    private fun formAccessClaims(lobbyId: Long, userId: Long): Claims {
        val claims = Jwts.claims().setSubject(userId.toString())
        claims[lobbyIdJwtKey] = lobbyId
        return claims
    }
}