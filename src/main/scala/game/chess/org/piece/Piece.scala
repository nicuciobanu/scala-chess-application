package game.chess.org.piece

trait Piece {
  def symbol: Char

  def color: Char = this match {
    case r: Rook   => r.c
    case k: Knight => k.c
    case b: Bishop => b.c
    case q: Queen  => q.c
    case k: King   => k.c
    case p: Pawn   => p.c
  }
}
