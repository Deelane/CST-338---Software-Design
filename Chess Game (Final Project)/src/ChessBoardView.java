import javax.swing.*;
import java.awt.*;

public class ChessBoardView extends JFrame
{
   private JButton[][] buttonSquares;
   private JPanel board;
   //decides what color starts where
   private int boardstate;

   public ChessBoardView()
   {
      this.setSize(1000, 1000);
      this.setResizable(false);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      setTitle("Chess");

      //Prepare board
      board = new JPanel(new GridLayout(8, 8));
      board.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
      buttonSquares = new JButton[8][8];
   }

   public void addBoard()
   {
      //Add board to jframe
      add(board);
   }

   public void loadButtonSquares()
   {
      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            JButton buttonSquare = new JButton();
            //If row is even, every odd column is black
            //If row is odd, every even column is black
            //row is even
            if (row % 2 == 0)
            {
               //col is even
               if (col % 2 == 0)
               {
                  buttonSquare.setBackground(Color.WHITE);
               }
               //col is odd
               else
               {
                  buttonSquare.setBackground(Color.LIGHT_GRAY);
               }
            }
            //row is odd
            else
            {
               //col is even
               if (col % 2 == 0)
               {
                  buttonSquare.setBackground(Color.LIGHT_GRAY);
               }
               //col is odd
               else
               {
                  buttonSquare.setBackground(Color.WHITE);
               }
            }

            //remove border
            buttonSquare.setBorder(null);

            //Add button to buttonSquares matrix
            buttonSquares[row][col] = buttonSquare;
            //Add button to board
            board.add(buttonSquare);
         }
      }
   }

   //Hardcoded method to load icons to their correct locations
   public void loadIconsToBoard()
   {
      ImageIcon piece;

      //White is on bottom
      if (boardstate == 0)
      {
         /**Set black side**/
         //Place rooks
         piece = GUIChessPiece.getPieceIcon("rook_black");
         buttonSquares[0][0].setIcon(piece);
         buttonSquares[0][7].setIcon(piece);

         //Place Knights
         piece = GUIChessPiece.getPieceIcon("knight_black");
         buttonSquares[0][1].setIcon(piece);
         buttonSquares[0][6].setIcon(piece);

         //Place Bishops
         piece = GUIChessPiece.getPieceIcon("bishop_black");
         buttonSquares[0][2].setIcon(piece);
         buttonSquares[0][5].setIcon(piece);

         //Place Queen
         piece = GUIChessPiece.getPieceIcon("queen_black");
         buttonSquares[0][3].setIcon(piece);

         //Place King
         piece = GUIChessPiece.getPieceIcon("king_black");
         buttonSquares[0][4].setIcon(piece);

         //Place pawns
         piece = GUIChessPiece.getPieceIcon("pawn_black");
         for (int i = 0; i < 8; i++)
         {
            buttonSquares[1][i].setIcon(piece);

            //Set button names for referencing later
            buttonSquares[0][i].setName("black");
            buttonSquares[1][i].setName("black");
         }
         /*************/

         /**Set white side**/
         //Place rooks
         piece = GUIChessPiece.getPieceIcon("rook_white");
         buttonSquares[7][0].setIcon(piece);
         buttonSquares[7][7].setIcon(piece);

         //Place Knights
         piece = GUIChessPiece.getPieceIcon("knight_white");
         buttonSquares[7][1].setIcon(piece);
         buttonSquares[7][6].setIcon(piece);

         //Place Bishops
         piece = GUIChessPiece.getPieceIcon("bishop_white");
         buttonSquares[7][2].setIcon(piece);
         buttonSquares[7][5].setIcon(piece);

         //Place Queen
         piece = GUIChessPiece.getPieceIcon("queen_white");
         buttonSquares[7][4].setIcon(piece);

         //Place King
         piece = GUIChessPiece.getPieceIcon("king_white");
         buttonSquares[7][3].setIcon(piece);

         //Place pawns
         piece = GUIChessPiece.getPieceIcon("pawn_white");
         for (int i = 0; i < 8; i++)
         {
            buttonSquares[6][i].setIcon(piece);

            //Set button names for referencing later
            buttonSquares[6][i].setName("white");
            buttonSquares[7][i].setName("white");
         }
      }

      //White is on top
      else if (boardstate == 1)
      {
         /**Set white side**/
         //Place rooks
         piece = GUIChessPiece.getPieceIcon("rook_white");
         buttonSquares[0][0].setIcon(piece);
         buttonSquares[0][7].setIcon(piece);

         //Place Knights
         piece = GUIChessPiece.getPieceIcon("knight_white");
         buttonSquares[0][1].setIcon(piece);
         buttonSquares[0][6].setIcon(piece);

         //Place Bishops
         piece = GUIChessPiece.getPieceIcon("bishop_white");
         buttonSquares[0][2].setIcon(piece);
         buttonSquares[0][5].setIcon(piece);

         //Place Queen
         piece = GUIChessPiece.getPieceIcon("queen_white");
         buttonSquares[0][3].setIcon(piece);

         //Place King
         piece = GUIChessPiece.getPieceIcon("king_white");
         buttonSquares[0][4].setIcon(piece);

         //Place pawns
         piece = GUIChessPiece.getPieceIcon("pawn_white");
         for (int i = 0; i < 8; i++)
         {
            buttonSquares[1][i].setIcon(piece);

            //Set button names for referencing later
            buttonSquares[0][i].setName("white");
            buttonSquares[1][i].setName("white");
         }
         /*************/

         /**Set black side**/
         //Place rooks
         piece = GUIChessPiece.getPieceIcon("rook_black");
         buttonSquares[7][0].setIcon(piece);
         buttonSquares[7][7].setIcon(piece);

         //Place Knights
         piece = GUIChessPiece.getPieceIcon("knight_black");
         buttonSquares[7][1].setIcon(piece);
         buttonSquares[7][6].setIcon(piece);

         //Place Bishops
         piece = GUIChessPiece.getPieceIcon("bishop_black");
         buttonSquares[7][2].setIcon(piece);
         buttonSquares[7][5].setIcon(piece);

         //Place Queen
         piece = GUIChessPiece.getPieceIcon("queen_black");
         buttonSquares[7][4].setIcon(piece);

         //Place King
         piece = GUIChessPiece.getPieceIcon("king_black");
         buttonSquares[7][3].setIcon(piece);

         //Place pawns
         piece = GUIChessPiece.getPieceIcon("pawn_black");
         for (int i = 0; i < 8; i++)
         {
            buttonSquares[6][i].setIcon(piece);

            //Set button names for referencing later
            buttonSquares[6][i].setName("black");
            buttonSquares[7][i].setName("black");
         }
      }
   }

   public JButton[][] getButtonSquares()
   {
      return buttonSquares;
   }

   //Must clear all button borders every time a new border is set
   public void clearButtonBorders()
   {
      //For each row in buttonsquares
      for (JButton[] buttons: buttonSquares)
      {
         //For each column in row
         for (JButton button: buttons)
         {
            //remove border
            button.setBorder(null);
         }
      }
   }

   public void setBoardState(int state)
   {
      //default to 0 if value is bad
      if (state < 0 || state > 1)
      {
         this.boardstate = 0;
      }
      else
      {
         this.boardstate = state;
      }
   }
}

