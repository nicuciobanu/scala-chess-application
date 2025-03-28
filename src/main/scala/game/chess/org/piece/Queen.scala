package game.chess.org.piece

import game.chess.org.Board
import game.chess.org.util.Constants.QueenSymbol

case class Queen(c: Char) extends Piece { def symbol: Char = QueenSymbol }

object Queen {

  /** The method validates the move to the queen, which can move vertically, horizontally and diagonally. It is validated if the movement
    * rule is correct and no pieces of the same color or opposing pieces are encountered on the route, the exception being when there is a
    * piece of the opposite color in the final position. If the conditions are met, true is returned, or false otherwise.
    *
    * @board
    *   - chessboard with all pieces.
    * @from
    *   - start position from the chessboard where the piece is.
    * @to
    *   - end position from the chessboard where the piece should be moved.
    * @isValidRookMove
    *   - a function which validates rook trajectory.
    * @isValidBishopMove
    *   - a function which validates bishop trajectory.
    */
  def isValidQueenMove(
      board: Board,
      from: (Int, Int),
      to: (Int, Int),
      isValidRookMove: (Board, (Int, Int), (Int, Int)) => Boolean,
      isValidBishopMove: (Board, (Int, Int), (Int, Int)) => Boolean
  ): Boolean =
    // Queen can move like a rook or a bishop
    isValidRookMove.apply(board, from, to) || isValidBishopMove.apply(board, from, to)
}
