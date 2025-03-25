package game.chess.org

import game.chess.org.board.Chessboard._
import game.chess.org.Constants.{Black, White}
import game.chess.org.Utils.parseSquare
import game.chess.org.logic.ChessLogic._
import game.chess.org.piece.King

object ChessApp extends App {

  val board                              = initBoard()
  private var currentPlayer              = White
  private var isCurrentPlayerUnderAttack = false

  while (true) {
    println(renderBoard(board))

    val player = if (currentPlayer == White) "1 (White)" else "2 (Black)"

    if (isCurrentPlayerUnderAttack)
      println(s"In check!!! \nPlayer, $player enter your move (e.g., 'e2 e4'):")
    else
      println(s"Player, $player enter your move (e.g., 'e2 e4'):")

    val movementCoordinates = scala.io.StdIn.readLine().split(" ")

    if (movementCoordinates.length != 2) println("Invalid input. Please enter two squares separated by a space.")
    else {
      val from = parseSquare(movementCoordinates(0))
      val to   = parseSquare(movementCoordinates(1))

      (from, to) match {
        case (Some((x1, y1)), Some((x2, y2))) =>
          if (validateMove(board, (x1, y1), (x2, y2))) {
            // Update the board
            movePiece(board, (x1, y1), (x2, y2))

            // Identify opponent's color
            val opponentPlayerColor = if (currentPlayer == White) Black else White

            // Find opponent king
            findPiece(board, King(opponentPlayerColor)) match {
              case Some((x, y)) =>
                val isOpponentColorUnderAttack = isPieceUnderAttack(board, (x, y), opponentPlayerColor)
                println(s"isOpponentColorUnderAttack: $isOpponentColorUnderAttack")
                if (isOpponentColorUnderAttack) {
                  println("Check")
                  // Switch players
                  currentPlayer = opponentPlayerColor
                  // After switching the player update the `isCurrentPlayerUnderAttack` flag
                  isCurrentPlayerUnderAttack = isOpponentColorUnderAttack
                } else {
                  // Switch players
                  currentPlayer = opponentPlayerColor
                }

              case _ =>
                // Switch players
                currentPlayer = opponentPlayerColor
            }
          } else println("Invalid move. Please try again.")

        case _ =>
          println("Invalid square. Please enter squares in the format 'e2', with valid positions from 'a' to 'h' and from '1' to '8'.")
      }
    }
  }
}
