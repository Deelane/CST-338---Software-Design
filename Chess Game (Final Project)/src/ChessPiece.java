public abstract class ChessPiece
{
   private String color;
   private boolean alive;
   private boolean canMove;
   private String pieceName;
   private int[] position;
   private int positionRow;
   private int positionColumn;
   private int startingRow;
   private boolean[][] boardBooleans;
   private ChessPiece[][] chessBoard;

   //Returns true if the move is valid, and also moves
   //Returns false otherwise
   public abstract boolean move(int[] newPosition);

   public boolean isAlive()
   {
      return alive;
   }

   public void setAlive(boolean alive)
   {
      this.alive = alive;
   }

   public String getColor()
   {
      return color;
   }

   public void setColor(String color)
   {
      this.color = color;
   }

   public boolean canMove()
   {
      return canMove;
   }

   public void setCanMove(boolean canMove)
   {
      this.canMove = canMove;
   }

   public String getPieceName()
   {
      return pieceName;
   }

   public void setPieceName(String pieceName)
   {
      this.pieceName = pieceName;
   }

   public int[] getPosition()
   {
      return position;
   }

   public void setPosition(int[] position)
   {
      if (position[0] >= 0 && position[0] < 8 && position[1] >= 0 && position[1] < 8)
      {
         this.position = position;
         this.positionRow = position[0];
         this.positionColumn = position[1];
      }
   }

   public void setPosition(int row, int column)
   {
      if (row >= 0 && row < 8 && column >= 0 && column < 8)
      {
         this.position = new int[] {row, column};
         this.positionRow = row;
         this.positionColumn = column;
      }
   }

   public int getPositionRow()
   {
      return positionRow;
   }

   public int getPositionColumn()
   {
      return positionColumn;
   }

   public int getStartingRow()
   {
      return startingRow;
   }

   public void setStartingRow(int startingRow)
   {
      this.startingRow = startingRow;
   }

   public boolean[][] getBoardBooleans()
   {
      return boardBooleans;
   }

   public void setBoardBooleans(boolean[][] boardBooleans)
   {
      this.boardBooleans = boardBooleans;
   }

   public ChessPiece[][] getChessBoard()
   {
      return chessBoard;
   }

   public void setChessBoard(ChessPiece[][] chessBoard)
   {
      this.chessBoard = chessBoard;
   }
}
