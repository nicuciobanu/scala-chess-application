package game.chess.org

import game.chess.org.board.Chessboard._
import game.chess.org.util.Constants.{Black, White}
import game.chess.org.util.Utils.parseSquare
import game.chess.org.logic.ChessLogic._
import game.chess.org.piece.King

import scala.annotation.tailrec

object ChessApp extends App {

  startGame(board = initBoard(), currentPlayer = White)

  /** The function starts the logic of the chess game.
    * @board
    *   - chessboard with the positions of the chess pieces.
    * @currentPlayer
    *   - the character that represents the player's color, B(Black) or W(White).
    */
  private def startGame(board: Board, currentPlayer: Char): Unit = {
    val whitePlayerStr = "1 (White)"
    val blackPlayerStr = "2 (Black)"

    @tailrec
    def play(board: Board, currentPlayer: Char, isKingUnderAttack: Boolean, player: String): Unit = {
      // Draw the pieces on the chessboard
      println(renderBoard(board))

      val whiteKingPositionOpt = findPiece(board, King(White))
      val blackKingPositionOpt = findPiece(board, King(Black))

      // Game will continue only when both kins are on the chessboard
      if (whiteKingPositionOpt.isEmpty || blackKingPositionOpt.isEmpty) {
        // Checkmate - game is over
        println(s"Checkmate - game is over, user '$player' won.")
      } else {
        // Print message to enter coordinates
        val updatedPlayer = printEnterMovesMsg(
          currentPlayer = currentPlayer,
          isKingUnderAttack = isKingUnderAttack,
          whitePlayerStr = whitePlayerStr,
          blackPlayerStr = blackPlayerStr
        )

        // Add moves from console
        enterMoves() match {
          case (Some((x1, y1)), Some((x2, y2))) =>
            if (validateMove(board, (x1, y1), (x2, y2))) {
              // Identify opponent's color
              val opponentPlayerColor = if (currentPlayer == White) Black else White

              // Update board
              val updatedBoard = movePiece(board, (x1, y1), (x2, y2))

              val isOppKingUnderAttack =
                printAndCheckIfOpponentKingUnderAttack(updatedBoard, whiteKingPositionOpt, blackKingPositionOpt, opponentPlayerColor)

              play(
                board = updatedBoard,
                currentPlayer = opponentPlayerColor,
                isKingUnderAttack = isOppKingUnderAttack,
                player = updatedPlayer
              )
            } else {
              println("Invalid move. Please try again.")
              play(board = board, currentPlayer = currentPlayer, isKingUnderAttack = false, player = player)
            }

          case _ =>
            play(board = board, currentPlayer = currentPlayer, isKingUnderAttack = false, player = player)
        }
      }
    }

    play(board = board, currentPlayer = currentPlayer, isKingUnderAttack = false, player = whitePlayerStr)
  }

  /** The function prints the message 'check' if the opponent's king is in danger of attack, returns true if the king is under attack or
    * false if the king is safe.
    *
    * @board
    *   - chessboard with the positions of the chess pieces.
    * @whiteKingPositionOpt
    *   - possible coordinates of the white king.
    * @blackKingPositionOpt
    *   - possible coordinates of the black king
    * @opponentPlayerColor
    *   - character which can be B(Black) or W(White).
    */
  private def printAndCheckIfOpponentKingUnderAttack(
      board: Board,
      whiteKingPositionOpt: Option[(Int, Int)],
      blackKingPositionOpt: Option[(Int, Int)],
      opponentPlayerColor: Char
  ): Boolean =
    (whiteKingPositionOpt, blackKingPositionOpt) match {
      case (Some((wx1, wy1)), Some((bx1, by2))) =>
        if (isPieceUnderAttack(board, (wx1, wy1), opponentPlayerColor)) {
          println("Check")
          true
        } else if (isPieceUnderAttack(board, (bx1, by2), opponentPlayerColor)) {
          println("Check")
          true
        } else false

      case _ => false
    }

  /** The function prints an individual message for each player to enter their coordinates, and returns the player.
    *
    * @currentPlayer
    *   - the player color who must make the move.
    * @isKingUnderAttack
    *   - the flag indicating whether the opponent's king is in danger.
    * @whitePlayerStr
    *   - the player's white color string.
    * @blackPlayerStr
    *   - the player's black color string.
    */
  private def printEnterMovesMsg(
      currentPlayer: Char,
      isKingUnderAttack: Boolean,
      whitePlayerStr: String,
      blackPlayerStr: String
  ): String = {
    val player = if (currentPlayer == White) whitePlayerStr else blackPlayerStr

    if (isKingUnderAttack) {
      println(s"In check!!! \nPlayer, $player enter your move (e.g., 'e2 e4'):")
      player
    } else {
      println(s"Player, $player enter your move (e.g., 'e2 e4'):")
      player
    }
  }

  /** The function is used to enter coordinates from the console.
    */
  private def enterMoves(): (Option[(Int, Int)], Option[(Int, Int)]) = {
    val movementCoordinates = scala.io.StdIn.readLine().split(" ")

    if (movementCoordinates.length != 2) {
      println("Invalid input. Please enter two squares separated by a space.")
      (None, None)
    } else (parseSquare(movementCoordinates(0)), parseSquare(movementCoordinates(1)))
  }
}
