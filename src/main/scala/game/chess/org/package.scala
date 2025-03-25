package game.chess

import game.chess.org.piece.Piece

package object org {
  // Define the board as a 2D array
  type Board = Array[Array[Option[Piece]]]
}
