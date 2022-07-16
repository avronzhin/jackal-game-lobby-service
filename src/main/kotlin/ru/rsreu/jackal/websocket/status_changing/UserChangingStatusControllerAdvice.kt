package ru.rsreu.jackal.websocket.status_changing

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.status_changing.dto.UserChangingStatusErrorResponse
import ru.rsreu.jackal.websocket.status_changing.dto.UserChangingStatusErrorType

@ControllerAdvice(basePackageClasses = [UserChangingStatusChangingController::class])
class UserChangingStatusControllerAdvice(private val wsUtil: WebSocketUtil) {
    @MessageExceptionHandler(InvalidForLobbyTokenException::class)
    fun handleInvalidForLobbyTokenException(exception: InvalidForLobbyTokenException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserChangingStatusErrorResponse(
                userChangingStatusErrorType = UserChangingStatusErrorType.INVALID_TOKEN
            )
        )
    }

    @MessageExceptionHandler(LobbyNotFoundException::class)
    fun handleLobbyNotFoundException(exception: LobbyNotFoundException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserChangingStatusErrorResponse(
                userChangingStatusErrorType = UserChangingStatusErrorType.LOBBY_NOT_EXISTS
            )
        )
    }

    @MessageExceptionHandler(UserNotInAnyLobbyException::class, AttemptToChangeStateNotConnectedException::class)
    fun handleUserNotFoundInAnyLobbyException(exception: WebSocketException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserChangingStatusErrorResponse(
                userChangingStatusErrorType = UserChangingStatusErrorType.USER_NOT_CONNECTED
            )
        )
    }

    @MessageExceptionHandler(AttemptToChangeStateInGameException::class)
    fun handleAttemptToChangeStateInGameException(exception: AttemptToChangeStateInGameException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserChangingStatusErrorResponse(
                userChangingStatusErrorType = UserChangingStatusErrorType.USER_IN_GAME
            )
        )
    }
}