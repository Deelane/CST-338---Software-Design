import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessBoardController
{
   private ChessBoardView view;
   private ChessBoardModel model;

   //which color is on which side
   private int boardstate;

   //currently selected button
   private JButton selectedButton;

   //tracks if a piece is selected
   private boolean pieceSelected;
   //tracks if a piece is currently being moved
   private boolean pieceMoved;
   //tracks if piece failed to move
   private boolean failedToMove;

   public ChessBoardController(ChessBoardView view, ChessBoardModel model)
   {
      this.view = view;
      this.model = model;
   }

   public void loadView()
   {
      view.setBoardState(boardstate);
      view.loadButtonSquares();
      view.loadIconsToBoard();
      view.addBoard();

      addActionListeners();

      view.setVisible(true);
   }

   public void newGame()
   {
      model.newGame();
   }

   public void setBoardstate(int state)
   {
      this.boardstate = state;
   }

   private void addActionListeners()
   {
      JButton[][] buttonSquares = view.getButtonSquares();
      for (JButton[] buttons : buttonSquares)
      {
         for (JButton button : buttons)
         {
            button.addActionListener(new PieceClickedActionListener());
            button.addActionListener(new PieceMovedActionListener());
         }
      }
   }

   //remove action listeners for buttons for passed in color
   //used for when its not our turn
   private void removeActionListeners(String color)
   {
      JButton[][] buttonSquares = view.getButtonSquares();
      for (JButton[] buttons : buttonSquares)
      {
         for (JButton button : buttons)
         {
            if (button.getName().equalsIgnoreCase(color))
            {
               //remove each actionlistener
               ActionListener[] actionListeners = button.getActionListeners();
               for (ActionListener actionListener : actionListeners)
               {
                  button.removeActionListener(actionListener);
               }
            }
         }
      }
   }

   //Pawns have a special movement case, so they always need access to the next row in front of them
   private void setPawnNextRow(Pawn pawn, int nextRow)
   {
      pawn.setNextRow(nextRow);
   }

   public class PieceClickedActionListener implements ActionListener
   {
      @Override
      public void actionPerformed(ActionEvent actionEvent)
      {
         Object callingObject = actionEvent.getSource();
         if (callingObject instanceof JButton)
         {
            JButton callingButton = (JButton) callingObject;
            //We clicked on a chess piece, not an empty square
            //Only select a piece if there is no piece selected or currently being moved
            if (callingButton.getIcon() != null && !pieceSelected && !pieceMoved && !failedToMove)
            {
               view.clearButtonBorders();
               callingButton.setBorder(BorderFactory.createLineBorder(Color.RED));
               selectedButton = callingButton;
               pieceSelected = true;
            }
            else //we just moved a piece or did not move the piece we selected
            {
               callingButton.setBorder(null);
               pieceSelected = false;
               pieceMoved = false;
               failedToMove = false;
            }
         }
      }
   }

   public class PieceMovedActionListener implements ActionListener
   {
      @Override
      public void actionPerformed(ActionEvent actionEvent)
      {
         Object callingObject = actionEvent.getSource();
         if (callingObject instanceof JButton)
         {
            //Get our calling button
            JButton callingButton = (JButton) callingObject;

               //If we clicked on a square while a piece is selected and we did not click on the already selected button
               if (pieceSelected && selectedButton != callingButton)
               {
                  //Retrieve our buttons matrix
                  JButton[][] buttonSquares = view.getButtonSquares();

                  //Hold the position of the calling button
                  int[] callingButtonPosition = new int[2];
                  int[] selectedButtonPosition = new int[2];
                  boolean callingButtonPositionFound = false;
                  boolean selectedButtonPositionFound = false;

                  //search for our calling and selected button array positions
                  for (int i = 0; i < 8; i++)
                  {
                     for (int j = 0; j < 8; j++)
                     {
                        if (buttonSquares[i][j].equals(callingButton))
                        {
                           //We found our button's position
                           callingButtonPosition[0] = i;
                           callingButtonPosition[1] = j;
                           callingButtonPositionFound = true;
                        }
                        if (buttonSquares[i][j].equals(selectedButton))
                        {
                           selectedButtonPosition[0] = i;
                           selectedButtonPosition[1] = j;
                           selectedButtonPositionFound = true;
                        }
                        if (callingButtonPositionFound && selectedButtonPositionFound)
                        {
                           break;
                        }
                     }
                  }

                  /**TODO this is where the magic happens
                   If we can move there and there is a piece there, we take it
                   If we move, our turn is ended
                   **/


                  //piece is no longer selected
                  pieceSelected = false;
                  //piece is being moved
                  pieceMoved = true;

                  ChessPiece pieceToMove = model.getPiece(selectedButtonPosition);

                  //call to movepiece will move the piece and return true if succeeded
                  if (model.movePiece(pieceToMove, callingButtonPosition))
                  {
                     //update the boolean matrix for each piece
                     model.updateBoardBooleans();

                     //move the icon in the view
                     callingButton.setIcon(selectedButton.getIcon());
                     selectedButton.setIcon(null);
                     view.clearButtonBorders();

                     //delete pointer
                     selectedButton = null;

                     //move success
                     failedToMove = false;
                  }
                  else //could not move piece, unselect all pieces
                  {
                     //TODO test this hard to make sure selectedbutton set to null doesnt mess anything up
                     failedToMove = true;
                     pieceMoved = false;
                     pieceSelected = false;
                     selectedButton.setBorder(null);
                     selectedButton = null;
                  }
            }
         }
      }
   }
}
