package game.chess.org.logic

import game.chess.org.Board
import game.chess.org.piece.{Bishop, King, Knight, Pawn, Piece, Queen, Rook}

import scala.annotation.tailrec

object ChessLogic {

  /** The function receives three parameters: the chessboard, the starting position where the piece we want to move is located, and the
    * final position where the piece must be moved. The acceptance conditions are that a piece must not encounter any other piece of the
    * same color or an opposite piece in its path, the exception being when there is a piece of the opposite color in the final position. If
    * the conditions are met, true is returned, or false otherwise.
    *
    * @board
    *   - chessboard with all pieces.
    * @from
    *   - start position from the chessboard where the piece is.
    * @to
    *   - end position from the chessboard where the piece should be moved.
    */
  def validateMove(board: Board, from: (Int, Int), to: (Int, Int)): Boolean = {
    val (x1, y1) = from
    val (x2, y2) = to

    // Check if the starting square has a piece
    board(x1)(y1) match {
      case None        => false
      case Some(piece) =>
        // Check if the destination square is either empty or has an opponent's piece
        val destinationSquare                       = board(x2)(y2)
        val isOpponentPiece                         = destinationSquare.exists(_.color != piece.color)
        val isEmptyDestinationSquare                = destinationSquare.isEmpty
        val isEmptyDestinationSquareOrOpponentPiece = isOpponentPiece || isEmptyDestinationSquare

        // Validate the move based on the piece type
        piece match {
          case Rook(_)   => Rook.isValidRookMove(board, from, to) && isEmptyDestinationSquareOrOpponentPiece
          case Knight(_) => Knight.isValidKnightMove(from, to) && isEmptyDestinationSquareOrOpponentPiece
          case Bishop(_) => Bishop.isValidBishopMove(board, from, to) && isEmptyDestinationSquareOrOpponentPiece
          case Queen(_) =>
            Queen.isValidQueenMove(
              board,
              from,
              to,
              Rook.isValidRookMove,
              Bishop.isValidBishopMove
            ) && isEmptyDestinationSquareOrOpponentPiece
          case King(_)        => King.isValidKingMove(from, to) && isEmptyDestinationSquareOrOpponentPiece
          case pawn @ Pawn(_) => Pawn.isValidPawnMove(board, pawn, from, to) && isEmptyDestinationSquareOrOpponentPiece
        }
    }
  }

  /** The function receives three parameters: the chessboard, the starting position where the piece we want to move is located, and the
    * final position where we will move the piece, which will return the chessboard with the updated piece positions. The initial position
    * is being released, and in the final position, we will have the piece from the initial position.
    *
    * @board
    *   - chessboard with all pieces.
    * @from
    *   - start position from the chessboard where the piece is.
    * @to
    *   - end position from the chessboard where the piece should be moved.
    */
  def movePiece(board: Board, from: (Int, Int), to: (Int, Int)): Board = {
    val (x1, y1) = from
    val (x2, y2) = to

    board(x1)(y1) match {
      case Some(piece) =>
        board(x2)(y2) = Some(piece)
        board(x1)(y1) = None
        board

      case _ => board
    }
  }

  /** The function will receive two parameters: the chessboard and the piece we want to find. If the piece is found, we will return its
    * position. If the piece is not found, we will return nothing.
    *
    * @board
    *   - chessboard with all pieces.
    * @piece
    *   - the piece we are looking for.
    */
  def findPiece(board: Board, piece: Piece): Option[(Int, Int)] = {
    @tailrec
    def find(board: Board, piece: Piece, i: Int, j: Int, n: Int): Option[(Int, Int)] = {
      if (board(i)(j) == Some(piece)) Some((i, j))
      else if (j < n - 1) find(board, piece, i, j + 1, n)
      else if (i < n - 1) find(board, piece, i + 1, 0, n)
      else None
    }

    find(board, piece, 0, 0, board.length)
  }

  /** The function validate whether the piece on the specified position can be attacked by the opponent. It has three parameters, the
    * chessboard, the position of the piece we are checking for, and the color of the opposing pieces. If the piece can be attacked, it will
    * return true and false otherwise.
    *
    * @board
    *   - chessboard with all pieces.
    * @square
    *   - the piece position for which we are performing the check.
    * @opponentColor
    *   - color of the opposing pieces.
    */
  def isPieceUnderAttack(board: Board, square: (Int, Int), opponentColor: Char): Boolean = {
    @tailrec
    def isUnderAttack(i: Int, j: Int, n: Int, board: Board, square: (Int, Int), color: Char): Boolean =
      if (validateMove(board = board, from = (i, j), to = square) && isSamePieceColor(i, j, board, color)) true
      else if (j < n - 1) isUnderAttack(i, j + 1, n, board, square, color)
      else if (i < n - 1) isUnderAttack(i + 1, 0, n, board, square, color)
      else false

    isUnderAttack(i = 0, j = 0, n = board.length, board = board, square = square, color = opponentColor)
  }

  /** The function checks if the color of the piece at position x, y corresponds to the searched color. The function receives 4 parameters,
    * the x and y ax positions of the matrix, and the chessboard with the searched color. Returns true if the piece has the same color from
    * the parameter, or false if the color is not found.
    *
    * @board
    *   - chessboard with all pieces.
    * @x
    *   - start position from the x axis on the chessboard.
    * @y
    *   - start position from the y axis on the chessboard.
    * @color
    *   - color we are looking for.
    */
  private def isSamePieceColor(x: Int, y: Int, board: Board, color: Char): Boolean =
    board(x)(y) match {
      case Some(piece) if piece.color == color => true
      case _                                   => false
    }
}
