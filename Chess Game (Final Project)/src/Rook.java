import java.util.ArrayList;
import java.util.Arrays;
//TODO MAKE ROOKS BLOCKED BY WHITE PIECES TOO
public class Rook extends ChessPiece
{
   public Rook()
   {
      super();
   }

   //Rooks can move along any row OR any column, but not both and not if they are blocked
   @Override
   public boolean move(int[] newPosition)
   {
      //Current positions
      int[] currentPosition = getPosition();
      int currentRow = currentPosition[0];
      int currentCol = currentPosition[1];

      //Number of valid new row positions
      int validNewRowsSize = 1;


      //Size 8, but one index will always be empty
      //The rook can move at most 7 forward or backward
      int[][] validMovesVertical = new int[8][2];
      //The rook can move at most 7 left or right
      int[][] validMovesHorizontal = new int[8][2];

      /**valid vertical moves**/
      //iterate 8 times because we will skip one iteration guaranteed
      for (int validRow = 0; validRow < 8; validRow++)
      {
         //if validrow is our current row, then we are already there
         //we cannot move to the same position we are already in
         if (currentRow != validRow)
         {
            //column will always be the same for vertical moves
            validMovesVertical[validRow] = new int[] {validRow, currentCol};
         }
         //we are already here
         else
         {
            validMovesVertical[validRow] = null;
         }
      }
      /*************/
      /**valid horizontal moves**/
      for (int validCol = 0; validCol < 8; validCol++)
      {
         //if validCol is our current column, then we are already there
         //we cannot move to the same position we are already in
         if (currentCol != validCol)
         {
            //column will always be the same for vertical moves
            validMovesHorizontal[validCol] = new int[] {currentRow, validCol};
         }
         //we are already here
         else
         {
            validMovesHorizontal[validCol] = null;
         }
      }
      /***********/

      //get our board booleans
      ChessPiece[][] chessBoard = getChessBoard();

      //how many spaces from our current space can we move without being blocked
      int limitRight = currentCol;
      int limitLeft = currentCol;
      int limitUp = currentRow;
      int limitDown = currentRow;

      /**Right limit**/
      //iterate from column right of our column to end
      for (int i = currentCol + 1; i < 8; i++)
      {
         //don't go out of bounds
         if (i < 0 || i > 8)
         {
            break;
         }
         //if there is a piece there and it is our color
         if (chessBoard[currentRow][i] != null &&
                 chessBoard[currentRow][i]
                         .getColor()
                         .equalsIgnoreCase(getColor()))
         {
            //then we are blocked
            break;
         }
         //if we don't break, then we are not blocked and our limit increases
         limitRight++;
      }

      /**Left limit**/
      //iterate from column left of our column to end
      for (int i = currentCol - 1; i >= 0; i--)
      {
         //don't go out of bounds
         if (i < 0 || i > 8)
         {
            break;
         }
         //if there is a piece there and it is our color
         if (chessBoard[currentRow][i] != null &&
                 chessBoard[currentRow][i]
                         .getColor()
                         .equalsIgnoreCase(getColor()))
         {
            //then we are blocked
            break;
         }
         //if we don't break, then we are not blocked and our limit increases
         limitLeft--;
      }

      /**Down limit**/
      //iterate from column right of our column to end
      for (int i = currentRow + 1; i < 8; i++)
      {
         //don't go out of bounds
         if (i < 0 || i > 8)
         {
            break;
         }
         //if there is a piece there and it is our color
         if (chessBoard[i][currentCol] != null &&
                 chessBoard[i][currentCol]
                         .getColor()
                         .equalsIgnoreCase(getColor()))
         {
            //then we are blocked
            break;
         }
         //if we don't break, then we are not blocked and our limit increases
         limitDown++;
      }

      /**Up limit**/
      //iterate from column right of our column to end
      for (int i = currentRow - 1; i < 8; i--)
      {
         //don't go out of bounds
         if (i < 0 || i > 8)
         {
            break;
         }
         //if there is a piece there and it is our color
         if (chessBoard[i][currentCol] != null &&
                 chessBoard[i][currentCol]
                         .getColor()
                         .equalsIgnoreCase(getColor()))
         {
            //then we are blocked
            break;
         }
         //if we don't break, then we are not blocked and our limit increases
         limitUp--;
      }

      //remove invalid up moves
      for (int i = limitUp - 1; i >= 0; i--)
      {
         validMovesVertical[i] = null;
      }
      //remove invalid down moves
      for (int i = limitDown + 1; i < 8; i++)
      {
         validMovesVertical[i] = null;
      }
      //remove invalid left moves
      for (int i = limitLeft - 1; i >= 0; i--)
      {
         validMovesHorizontal[i] = null;
      }
      //remove invalid right moves
      for (int i = limitRight + 1; i < 8; i++)
      {
         validMovesHorizontal[i] = null;
      }

      //calculate valid move positions (before checking if blocked)
      ArrayList<int[]> validMovePositions = new ArrayList<>();
      for (int i = 0; i < 8; i++)
      {
         if (validMovesVertical[i] != null)
         {
            validMovePositions.add(validMovesVertical[i]);
         }
         if (validMovesHorizontal[i] != null)
         {
            validMovePositions.add(validMovesHorizontal[i]);
         }
      }

      //Move the piece
      for (int[] validMovePosition: validMovePositions)
      {
         if (Arrays.equals(validMovePosition, newPosition))
         {
            //set our new position
            setPosition(newPosition);

            //piece was moved
            return true;
         }
      }
      //move was invalid or we were blocked*/
      return false;
   }
}
