package game.chess.org.piece

import game.chess.org.util.Constants.RookSymbol
import game.chess.org.Board

import scala.annotation.tailrec

case class Rook(c: Char) extends Piece { def symbol: Char = RookSymbol }

object Rook {

  /** The method validates the move to the rook, which can move vertically or horizontally. It is validated if the movement rule is correct
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
  // Implement move validation for each piece type, we check up to the second last position
  def isValidRookMove(board: Board, from: (Int, Int), to: (Int, Int)): Boolean = {
    val (x1, y1) = from
    val (x2, y2) = to

    // Rook moves horizontally or vertically
    if (x1 == x2) {
      // Horizontal move
      val step = if (y2 > y1) 1 else -1

      checkHorizontalRookMove(start = y1 + step, stop = y2, step = step, board = board, x = x2)

    } else if (y1 == y2) {
      // Vertical move
      val step = if (x2 > x1) 1 else -1

      checkVerticalRookMove(start = x1 + step, stop = x2, step = step, board = board, y = y2)
    } else
      false
  }

  /** The function traverses the matrix horizontally and checks if there is no obstacle, piece of the same color or opposing piece on the
    * path.
    *
    * @board
    *   - chessboard with all pieces.
    * @start
    *   - starting position on the x-axis.
    * @stop
    *   - starting position on the y-axis.
    * @step
    *   - the step with which the x-axis is traversed.
    */
  @tailrec
  private def checkHorizontalRookMove(start: Int, stop: Int, step: Int, board: Board, x: Int): Boolean =
    if (start == stop) true
    else if (board(x)(start).isDefined) false
    else checkHorizontalRookMove(start + step, stop, step, board, x)

  /** The function traverses the matrix vertically and checks if there is no obstacle, piece of the same color or opposing piece on the
    * path.
    *
    * @board
    *   - chessboard with all pieces.
    * @start
    *   - starting position on the x-axis.
    * @stop
    *   - starting position on the y-axis.
    * @step
    *   - the step with which the x-axis is traversed.
    */
  @tailrec
  private def checkVerticalRookMove(start: Int, stop: Int, step: Int, board: Board, y: Int): Boolean =
    if (start == stop) true
    else if (board(start)(y).isDefined) false
    else checkVerticalRookMove(start + step, stop, step, board, y)
}
