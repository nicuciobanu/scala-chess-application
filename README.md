### Chess Game App
- A chess game application written in Scala.

![chessboard with pieces.jpeg](..%2F..%2F..%2FDesktop%2Fchessboard%20with%20pieces.jpeg)

#### Game rules
The move is according to the moves allowed by the piece on the starting position:
- The king can move only 1 square but in any direction;
- The bishop can move any number of squares but only diagonally;
- The rook can move any number of squares but only horizontally or vertically;
- The queen can move any number of squares horizontally, vertically or diagonally;
- The knight can move in an L shape with sides of 2 and 1 squares respectively. That is 8 different possible moves. Unlike other pieces it jumps over other pieces;
- The pawn can move one or two squares forward on its first move (when not taking an opponent piece);
- The pawn can move one square forward on subsequent moves (when not taking an opponent piece);
- The pawn can move one square forward diagonally if taking an opponent piece.

#### Instructions for starting the application
- Go to the "ChessApp" class and press the `Run ChessApp` button, on line 9.

#### Instructions for starting the tests
- To start the tests, type `sbt test` in the terminal;
- Or type `testOnly game.chess.org.board.ChessboardSpec`;
- Or type `testOnly game.chess.org.logic.ChessLogicSpec`.
