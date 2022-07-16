package ru.rsreu.jackal.websocket.leaving.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserLeavingErrorResponse(
    val type: WebSocketResponseType = WebSocketResponseType.LEAVING_ERROR,
    val userLeavingErrorType: UserLeavingErrorType
)

enum class UserLeavingErrorType {
    ALREADY_NOT_IN_LOBBY, USER_IN_GAME, INVALID_TOKEN, LOBBY_NOT_EXISTS
}
