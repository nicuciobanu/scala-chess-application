package game.chess.org.util

object Utils {
  // Helper function to parse a square (e.g., "e2" -> (1, 4))
  def parseSquare(square: String): Option[(Int, Int)] = {
    val x = 8 - square(1).asDigit   // array line position
    val y = square(0).toLower - 'a' // array column position

    if (square.length != 2) None
    else if (x >= 0 && x < 8 && y >= 0 && y < 8) Some((x, y))
    else None
  }
}
