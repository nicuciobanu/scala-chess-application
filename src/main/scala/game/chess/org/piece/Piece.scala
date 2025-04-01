package game.chess.org.piece

trait Piece {
  def symbol: Char

  /** The method identifies the color of the piece, which can be white or black.
    */
  def color: Char = this match {
    case r: Rook   => r.c
    case k: Knight => k.c
    case b: Bishop => b.c
    case q: Queen  => q.c
    case k: King   => k.c
    case p: Pawn   => p.c
  }
}
