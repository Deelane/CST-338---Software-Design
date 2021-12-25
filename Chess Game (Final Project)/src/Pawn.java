import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends ChessPiece
{
   //pawns can move 2 spaces forward on their first move
   //initialize this to true
   private boolean firstMove;
   //side the pawn started on
   private String startingSide;
   //next row from current row
   private int nextRow;

   public Pawn()
   {
      super();
      this.firstMove = true;
   }

   @Override
   public boolean move(int[] newPosition)
   {
      //Pawns can only move forward
      //started at top
      if (getStartingRow() == 1)
      {
         startingSide = "top";
      }
      //started at bottom
      else if (getStartingRow() == 6)
      {
         startingSide = "bottom";
      }
      else
      {
         startingSide = "";
      }

      //Current positions
      int[] currentPosition = getPosition();
      int currentRow = currentPosition[0];
      int currentCol = currentPosition[1];

      /**Valid row moves**/
      //Number of valid new row positions
      int validNewRowsSize = 1;

      //If its our first move, we can move up to 2 spaces
      if (firstMove)
      {
         //can move an extra space
         validNewRowsSize += 1;
      }

      int[] validNewRows = new int[validNewRowsSize];

      //if we are on top we can only move down
      if (startingSide.equalsIgnoreCase("top"))
      {
         //can move down at most 2
         for (int i = 0, length = validNewRows.length; i < length; i++)
         {
            int validRow = currentRow + (i + 1);
            if (validRow >= 0 && validRow < 8)
            {
               //Possible moves are either 1 or 2 moves down a row
               validNewRows[i] = validRow;
            }
         }
      }
      else if (startingSide.equalsIgnoreCase("bottom"))
      {
         //can move up at most 2
         for (int i = 0, length = validNewRows.length; i < length; i++)
         {
            int validRow = currentRow - (i + 1);
            if (validRow >= 0 && validRow < 8)
            {
               //Possible moves are either 1 or 2 moves down a row
               validNewRows[i] = validRow;
            }
         }
      }
      /***********/

      /***Valid new columns***/
      //hold our valid new columns
      //cannot be sure on size, so use list
      ArrayList<Integer> validNewCols = new ArrayList<>();

      //can always move in same column if not blocked
      validNewCols.add(getPositionColumn());

      //can only move to same col on first move
      if (!firstMove)
      {
         validNewCols.add(getPositionColumn() - 1);
         validNewCols.add(getPositionColumn() + 1);
      }
      /**********************/

      //calculate valid move positions (before checking if blocked)
      ArrayList<int[]> validMovePositions = new ArrayList<>();
      for (int row: validNewRows)
      {
         for (int col: validNewCols)
         {
            //if row and col are not out of bounds
            if (row >= 0 && row < 8)
            {
               if (col >= 0 && col < 8)
               {
                  int[] movePosition = new int[]{row, col};
                  validMovePositions.add(movePosition);
               }
            }
         }
      }

      //check if we are blocked
      boolean blocked = false;
      //roundabout way to get the int value of our next row
      //if there is a piece in our column in the next row
      if (getBoardBooleans()[nextRow][getPositionColumn()])
      {
         blocked = true;
      }

      //Move the piece
      for (int[] validMovePosition: validMovePositions)
      {
         boolean validMove = true;

         if (Arrays.equals(validMovePosition, newPosition))
         {
            //if we are forward blocked and trying to move to that space
            if (blocked && Arrays.equals(new int[] {nextRow, currentCol}, newPosition))
            {
               validMove = false;
            }


            //get the row column we are trying to move to
            int validMoveRow = validMovePosition[0];
            int validMoveCol = validMovePosition[1];

            //we are trying to move diagonally
            if (validMoveCol != currentCol)
            {
               //there is a piece where we are trying to move
               boolean hasPiece = getBoardBooleans()[validMoveRow][validMoveCol];
               if (hasPiece)
               {
                  //retrieve the chess board
                  ChessPiece[][] chessBoard = getChessBoard();
                  //retrieve our color
                  String myColor = getColor();

                  //if the piece we are trying to take is our color
                  if (chessBoard[validMoveRow][validMoveCol].getColor().equalsIgnoreCase(myColor))
                  {
                     //we cannot take our own piece
                     validMove = false;
                  }
               }

               //there is no piece to take, so we cannot move diagonally
               if (!getBoardBooleans()[validMoveRow][validMoveCol])
               {
                  validMove = false;
               }
            }

            //It is a valid move
            if (validMove)
            {
               //set our new position
               setPosition(newPosition);

               //if we made it to the end
               if (getStartingRow() == 1 && getPositionRow() == 7)
               {
                  promote();
               }
               else if (getStartingRow() == 6 && getPositionRow() == 0)
               {
                  promote();
               }

               //if it was our first move, set first move to false
               if (firstMove)
               {
                  firstMove = false;
               }

               //piece was moved
               return true;
            }
         }
      }

      //move was invalid or we were blocked
      return false;
   }

   public int getNextRow()
   {
      return nextRow;
   }

   public void setNextRow(int nextRow)
   {
      this.nextRow = nextRow;
   }

   public void promote()
   {

   }
}
