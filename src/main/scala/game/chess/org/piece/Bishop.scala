package game.chess.org.piece

import game.chess.org.Board
import game.chess.org.util.Constants.BishopSymbol

import scala.annotation.tailrec

case class Bishop(c: Char) extends Piece { def symbol: Char = BishopSymbol }

object Bishop {

  /** The method validates the move to the bishop, which can move vertically or diagonally. It is validated if the movement rule is correct
    * and no pieces of the same color or opposing pieces are encountered on the route, the exception being when there is a piece of the
    * opposite color in the final position. If the conditions are met, true is returned, or false otherwise.
    *
    * @board
    *   - chessboard with all pieces.
    * @from
    *   - start position from the chessboard where the piece is.
    * @to
    *   - end position from the chessboard where the piece should be moved.
    */
  // We check up to the second last position
  def isValidBishopMove(board: Board, from: (Int, Int), to: (Int, Int)): Boolean = {
    val (x1, y1) = from
    val (x2, y2) = to

    // Bishop moves diagonally
    if (Math.abs(x2 - x1) == Math.abs(y2 - y1)) {
      val stepX = if (x2 > x1) 1 else -1
      val stepY = if (y2 > y1) 1 else -1

      checkBishopMove(startX = x1 + stepX, startY = y1 + stepY, stopX = x2, stopY = y2, stepX = stepX, stepY = stepY, board = board)
    } else
      false
  }

  /** The function recursively traverses the matrix on the x and y axes and checks if there are no pieces of the same color or opposing
    * pieces in the path.
    *
    * @board
    *   - chessboard with all pieces.
    * @startX
    *   - starting position on the x-axis.
    * @startY
    *   - starting position on the y-axis.
    * @stopX
    *   - stop position on the x-axis.
    * @stopY
    *   - stop position on the y-axis.
    * @stepX
    *   - the step with which the x-axis is traversed.
    * @stepY
    *   - the step with which the y-axis is traversed.
    */
  @tailrec
  private def checkBishopMove(startX: Int, startY: Int, stopX: Int, stopY: Int, stepX: Int, stepY: Int, board: Board): Boolean =
    if (startX == stopX && startY == stopY) true
    else if (board(startX)(startY).isDefined) false
    else checkBishopMove(startX + stepX, startY + stepY, stopX, stopY, stepX, stepY, board)
}
