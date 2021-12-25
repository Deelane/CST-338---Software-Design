import java.util.HashMap;
import java.util.Map;

public class ChessBoardModel
{
   private ChessPiece[][] chessBoard;
   private HashMap<String, ChessPiece> piecesMap;
   private int boardstate;
   private boolean boardstateSet;
   private boolean[][] boardBooleans;

   public ChessBoardModel(int boardstate)
   {
      this.boardstate = boardstate;
      this.boardstateSet = true;
   }

   public ChessBoardModel()
   {
      //No params
   }

   public ChessPiece[][] getChessBoard()
   {
      return chessBoard;
   }

   public void newGame()
   {
      this.chessBoard = new ChessPiece[8][8];
      createPieces();

      //if user forgets to set board state
      if (!boardstateSet)
      {
         setBoardstate(0);
      }
      //add pieces to board
      populateBoard();
      //set starting rows of pieces
      setStartingRows();
      //set next rows
      setPawnNextRows();
      updateBoardBooleans();
      setChessBoardForAll();
   }

   //Create ChessPiece objects and add them to map
   private void createPieces()
   {
      //Instantiate our map
      piecesMap = new HashMap<>();

      /**Create Pawns**/
      for (int i = 0; i < 8; i++)
      {
         Pawn whitePawn = new Pawn();
         whitePawn.setColor("white");
         whitePawn.setAlive(true);
         whitePawn.setPieceName("pawn_white" + (i + 1));

         Pawn blackPawn = new Pawn();
         blackPawn.setColor("black");
         blackPawn.setAlive(true);
         blackPawn.setPieceName("pawn_black" + (i + 1));

         piecesMap.put(whitePawn.getPieceName(), whitePawn);
         piecesMap.put(blackPawn.getPieceName(), blackPawn);

      }
      /***************/


      /**Create rooks, knights, and bishops**/
      for (int i = 0; i < 2; i++)
      {
         //Add rooks
         Rook whiteRook = new Rook();
         whiteRook.setColor("white");
         whiteRook.setAlive(true);
         whiteRook.setPieceName("rook_white" + (i + 1));

         Rook blackRook = new Rook();
         blackRook.setColor("black");
         blackRook.setAlive(true);
         blackRook.setPieceName("rook_black" + (i + 1));


         piecesMap.put(whiteRook.getPieceName(), whiteRook);
         piecesMap.put(blackRook.getPieceName(), blackRook);

         //Add knights
         Knight whiteKnight = new Knight();
         whiteKnight.setColor("white");
         whiteKnight.setAlive(true);
         whiteKnight.setPieceName("knight_white" + (i + 1));

         Knight blackKnight = new Knight();
         blackKnight.setColor("black");
         blackKnight.setAlive(true);
         blackKnight.setPieceName("knight_black" + (i + 1));

         piecesMap.put(whiteKnight.getPieceName(), whiteKnight);
         piecesMap.put(blackKnight.getPieceName(), blackKnight);

         //Add bishops
         Bishop whiteBishop = new Bishop();
         whiteBishop.setColor("white");
         whiteBishop.setAlive(true);
         whiteBishop.setPieceName("bishop_white" + (i + 1));


         Bishop blackBishop = new Bishop();
         blackBishop.setColor("black");
         blackBishop.setAlive(true);
         blackBishop.setPieceName("bishop_black" + (i + 1));

         piecesMap.put(whiteBishop.getPieceName(), whiteBishop);
         piecesMap.put(blackBishop.getPieceName(), blackBishop);
      }
      /***********************************/

      /**Create Kings and Queens**/
      Queen whiteQueen = new Queen();
      whiteQueen.setColor("white");
      whiteQueen.setAlive(true);
      whiteQueen.setPieceName("queen_white");

      Queen blackQueen = new Queen();
      blackQueen.setColor("black");
      blackQueen.setAlive(true);
      blackQueen.setPieceName("queen_black");

      piecesMap.put(whiteQueen.getPieceName(), whiteQueen);
      piecesMap.put(blackQueen.getPieceName(), blackQueen);

      King whiteKing = new King();
      whiteKing.setColor("white");
      whiteKing.setAlive(true);
      whiteKing.setPieceName("king_white");

      King blackKing = new King();
      blackKing.setColor("black");
      blackKing.setAlive(true);
      blackKing.setPieceName("king_black");

      piecesMap.put(whiteKing.getPieceName(), whiteKing);
      piecesMap.put(blackKing.getPieceName(), blackKing);
      /*************************/
   }

   public int getBoardstate()
   {
      return boardstate;
   }

   //Decide which color is on which side
   public void setBoardstate(int boardstate)
   {
      //bad board state defaults to 0
      if (boardstate < 0 || boardstate > 1)
      {
         this.boardstate = 0;
         boardstateSet = true;
      }
      else
      {
         this.boardstate = boardstate;
         boardstateSet = true;
      }
   }

   //
   private void populateBoard()
   {
      //Row that white starts on
      int whiteStart;
      int blackStart;

      //0 board state means white on bottom
      if (boardstate == 0)
      {
         whiteStart = 7;
         blackStart = 0;
      }
      //1 boardstate means white on top
      else if (boardstate == 1)
      {
         whiteStart = 0;
         blackStart = 7;
      }
      //this should never happen
      //default to white on bottom
      else
      {
         whiteStart = 0;
         blackStart = 7;
      }

      //Add rooks
      Rook rookwhite1 = (Rook) piecesMap.get("rook_white1");
      rookwhite1.setPosition(new int[] {whiteStart, 0});
      Rook rookwhite2 = (Rook) piecesMap.get("rook_white2");
      rookwhite2.setPosition(new int[] {whiteStart, 7});
      chessBoard[whiteStart][0] = rookwhite1;
      chessBoard[whiteStart][7] = rookwhite2;

      Rook rookblack1 = (Rook) piecesMap.get("rook_black1");
      rookblack1.setPosition(new int[] {blackStart, 0});
      Rook rookblack2 = (Rook) piecesMap.get("rook_black2");
      rookblack2.setPosition(new int[] {blackStart, 7});
      chessBoard[blackStart][0] = rookblack1;
      chessBoard[blackStart][7] = rookblack2;

      //Add Knights
      Knight knightwhite1 = (Knight) piecesMap.get("knight_white1");
      knightwhite1.setPosition(new int[] {whiteStart, 1});
      Knight knightwhite2 = (Knight) piecesMap.get("knight_white2");
      knightwhite2.setPosition(new int[] {whiteStart, 6});
      chessBoard[whiteStart][1] = knightwhite1;
      chessBoard[whiteStart][6] = knightwhite2;

      Knight knightblack1 = (Knight) piecesMap.get("knight_black1");
      knightblack1.setPosition(new int[] {blackStart, 1});
      Knight knightblack2 = (Knight) piecesMap.get("knight_black2");
      knightblack2.setPosition(new int[] {blackStart, 6});
      chessBoard[blackStart][1] = knightblack1;
      chessBoard[blackStart][6] = knightblack2;

      //Add bishops
      Bishop bishopwhite1 = (Bishop) piecesMap.get("bishop_white1");
      bishopwhite1.setPosition(new int[] {whiteStart, 2});
      Bishop bishopwhite2 = (Bishop) piecesMap.get("bishop_white2");
      bishopwhite2.setPosition(new int[] {whiteStart, 5});
      chessBoard[whiteStart][2] = bishopwhite1;
      chessBoard[whiteStart][5] = bishopwhite2;

      Bishop bishopblack1 = (Bishop) piecesMap.get("bishop_black1");
      bishopblack1.setPosition(new int[] {blackStart, 2});
      Bishop bishopblack2 = (Bishop) piecesMap.get("bishop_black2");
      bishopblack2.setPosition(new int[] {blackStart, 5});
      chessBoard[blackStart][2] = bishopblack1;
      chessBoard[blackStart][5] = bishopblack2;

      //Add Kings and Queens
      //if white is on top

      Queen queenwhite = (Queen) piecesMap.get("queen_white");
      King kingwhite = (King) piecesMap.get("king_white");
      Queen queenblack = (Queen) piecesMap.get("queen_black");
      King kingblack = (King) piecesMap.get("king_black");

      if (whiteStart == 0)
      {
         //white queen goes on index 3, king on index 4
         queenwhite.setPosition(new int[] {whiteStart, 3});
         chessBoard[whiteStart][3] = queenwhite;
         kingwhite.setPosition(new int[] {whiteStart, 4});
         chessBoard[whiteStart][4] = kingwhite;

         //black queen goes on index 4, king on 3
         queenblack.setPosition(new int[] {blackStart, 4});
         chessBoard[blackStart][4] = queenblack;
         kingblack.setPosition(new int[] {blackStart, 3});
         chessBoard[blackStart][3] = kingblack;
      }
      //whitestart is 7
      else
      {
         //white queen goes on index 4, king on index 3
         queenwhite.setPosition(new int[] {whiteStart, 4});
         chessBoard[whiteStart][4] = queenwhite;
         kingwhite.setPosition(new int[] {whiteStart, 3});
         chessBoard[whiteStart][3] = kingwhite;

         //black queen goes on index 3, king on index 4
         queenblack.setPosition(new int[] {blackStart, 3});
         chessBoard[blackStart][3] = queenblack;
         kingblack.setPosition(new int[] {blackStart, 4});
         chessBoard[blackStart][4] = kingblack;
      }

      //Add pawns
      for (int i = 0; i < 8; i++)
      {
         int whitePawnStart;
         int blackPawnStart;

         //white is on top
         if (whiteStart == 0)
         {
            //white pawns are placed 1 index up
            //black pawns are placed 1 index down
            whitePawnStart = whiteStart + 1;
            blackPawnStart = blackStart - 1;
         }
         //white is on bottom
         else
         {
            whitePawnStart = whiteStart - 1;
            blackPawnStart = blackStart + 1;
         }

         Pawn whitepawn = (Pawn) piecesMap.get("pawn_white" + (i + 1));
         whitepawn.setPosition(new int[]{whitePawnStart, i});
         chessBoard[whitePawnStart][i] = whitepawn;

         Pawn blackpawn = (Pawn) piecesMap.get("pawn_black" + (i + 1));
         blackpawn.setPosition(new int[] {blackPawnStart, i});
         chessBoard[blackPawnStart][i] = blackpawn;
      }
      System.out.println("worked");
   }

   private void setStartingRows()
   {
      for (Map.Entry<String, ChessPiece> entry : piecesMap.entrySet())
      {
         ChessPiece chessPiece = entry.getValue();
         chessPiece.setStartingRow(chessPiece.getPositionRow());
      }
   }

   public boolean movePiece(ChessPiece chessPiece, int[] newPosition)
   {
      //get old positions
      int oldRow = chessPiece.getPositionRow();
      int oldCol = chessPiece.getPositionColumn();

      //if we successfully updated the position
      if (chessPiece.move(newPosition))
      {
         //get new positions
         int newRow = newPosition[0];
         int newCol = newPosition[1];

         //piece moved from old position to new position
         chessBoard[newRow][newCol] = chessPiece;
         chessBoard[oldRow][oldCol] = null;

         if (chessPiece instanceof Pawn)
         {
            Pawn pawn = (Pawn) chessPiece;
            setPawnNextRow(pawn);
         }
         //update board state
         updateBoardBooleans();

         //give pieces updated chess board
         setChessBoardForAll();

         //successfully moved piece
         return true;
      }
      //move was invalid or something else failed
      return false;
   }

   public ChessPiece getPiece(int[] position)
   {
      int row = position[0];
      int col = position[1];

      if (chessBoard[row][col] != null)
      {
         return chessBoard[row][col];
      }
      //no piece at position
      return null;
   }


   //update board state, but in boolean terms
   //if there is a piece on a square, that square is true
   public void updateBoardBooleans()
   {
      //initialize if null
      if (boardBooleans == null)
      {
         boardBooleans = new boolean[8][8];
      }
      //set booleans
      for (int i = 0; i < 8; i++)
      {
         for (int j = 0; j < 8; j++)
         {
            //if there is a piece
            if (chessBoard[i][j] != null)
            {
               boardBooleans[i][j] = true;
            }
            else
            {
               boardBooleans[i][j] = false;
            }
         }
      }
      updatePieceBoardBooleans();
   }

   //update the boolean array for each piece
   private void updatePieceBoardBooleans()
   {
      for (ChessPiece[] chessPieces: chessBoard)
      {
         for (ChessPiece chessPiece: chessPieces)
         {
            if (chessPiece != null)
            {
               chessPiece.setBoardBooleans(boardBooleans);
            }
         }
      }
   }

   //Pawns have a special movement case, so they always need access to the next row in front of them
   private void setPawnNextRow(Pawn pawn)
   {
      int currentRow = pawn.getPositionRow();
      //default to 0
      int nextRow = 0;
      //if the pawn started at the top
      if (pawn.getStartingRow() == 1)
      {
         nextRow = currentRow + 1;
      }
      //if the pawn started at the bottom
      else if (pawn.getStartingRow() == 6)
      {
         nextRow = currentRow - 1;
      }
      //ensure we dont go out of bounds
      if (nextRow >= 0 && nextRow < 8)
      {
         pawn.setNextRow(nextRow);
      }
   }

   private void setPawnNextRows()
   {
      //Set the next row for the pawns
      for (int i = 0; i < 8; i++)
      {
         for (int j = 0; j < 8; j++)
         {
            if (chessBoard[i][j] instanceof Pawn)
            {
               //get pawn
               Pawn pawn = (Pawn) chessBoard[i][j];
               setPawnNextRow(pawn);
            }
         }
      }
   }

   public void setChessBoard(ChessPiece chessPiece)
   {
      chessPiece.setChessBoard(chessBoard);
   }

   public void setChessBoardForAll()
   {
      for (ChessPiece[] chessPieces: chessBoard)
      {
         for (ChessPiece chessPiece: chessPieces)
         {
            if (chessPiece != null)
            {
               chessPiece.setChessBoard(chessBoard);
            }
         }
      }
   }
}
