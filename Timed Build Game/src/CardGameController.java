import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class CardGameController implements CardGameRound
{
   private CardTableView view;
   private CardGameModel model;

   public CardGameController(CardTableView view, CardGameModel model)
   {
      this.view = view;
      this.model = model;

      //deal
      model.deal();

      //load and show player buttons
      loadPlayerButtons();
      showPlayerButtons();

      //cpu starts
      cpuPlayLowCard();
      loadCpuLabels();

      //show it all
      view.setVisible(true);
   }

   private void loadCpuLabels()
   {
      JLabel[] computerLabels = view.getComputerLabels();
      JPanel pnlComputerHand = view.getPnlComputerHand();

      //grab cpu's hand
      Hand cpuHand = model.getHand(0);

      //Ensure array is empty
      clearLabels(computerLabels);
      //Clear computer hand panel
      pnlComputerHand.removeAll();

      //iterate over all grid columns in JPanel
      for(int i = 0; i < cpuHand.getNumCards(); i++)
      {
         JLabel label = new JLabel(GUICard.getBackCardIcon());
         computerLabels[i] = label;
         pnlComputerHand.add(computerLabels[i]);
      }
   }

   //Clear all labels in array
   private void clearLabels(JLabel[] labelArray)
   {
      for (int i = 0, length = labelArray.length; i < length; i++)
      {
         labelArray[i] = null;
      }
   }

   //creates JButtons for Card
   //passes player Hand to convert Card to JButton
   private void loadPlayerButtons()
   {
      //Get hands to work with
      Hand cpuHand = model.getHand(0);
      Hand playerHand = model.getHand(1);

      //Ensure there are cards to play
      if (cpuHand.getNumCards() > 0 && playerHand.getNumCards() > 0)
      {
         //iterate over all JLabels currently in humanLabels
         for(int i = 0, length = playerHand.getNumCards(); i < length; i++)
         {
            //get playerbuttons list from view
            ArrayList<JButton> playerButtons = view.getPlayerButtons();

            //Create JButton from JLabel
            JButton playerButton = (new JButton(GUICard.getIcon(playerHand.inspectCard(i + 1))));

            //Remove button border and background
            playerButton.setContentAreaFilled(false);
            playerButton.setBorderPainted(false);

            //Add button to list
            playerButtons.add(playerButton);

            //add button to JPanel
            view.getPnlHumanHand().add(playerButton);

            //Add action listeners
            playerButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent e)
               {
                  //get playedcardlabels array from view
                  JLabel[] playedCardLabels = view.getPlayedCardLabels();

                  //get played cards array from model
                  Card[] playedCards = model.getPlayedCards();

                  //get index of player button from list
                  int buttonIndex = playerButtons.indexOf(playerButton);

                  //Add played card label to player's index in played card labels array
                  playedCardLabels[1] = new JLabel(GUICard.getIcon(playerHand.inspectCard(buttonIndex + 1)));

                  //take card from hand and place it into played cards array
                  playedCards[1] = playerHand.playCard(buttonIndex);

                  //get playercard label from view
                  JLabel playerCard = view.getPlayerCard();

                  //Set playerCard to clicked card
                  playerCard.setIcon(playedCardLabels[1].getIcon());

                  //Hide played card from view
                  playerButton.setVisible(false);

                  //Remove clicked button from buttons list
                  playerButtons.remove(playerButton);

                  //Calculate who won
                  int winner = calculateRound(playedCards);

                  //Display round winner in an alert
                  displayRoundWinner(winner);

                  playerCard.setIcon(null);

                  //Cpu is the only one "playing" cards
                  //When cpu hand hits 0, round is over
                  if (cpuHand.getNumCards() > 0)
                  {
                     cpuPlayLowCard();
                     loadCpuLabels();
                  }
                  else
                  {
                     displayGameWinner();
                     System.exit(0);
                  }
               }
            });
         }
      }
   }

   //Shows player's buttons
   private void showPlayerButtons()
   {
      ArrayList<JButton> playerButtons = view.getPlayerButtons();
      JPanel pnlHumanHand = view.getPnlHumanHand();

      //loop to add them to screen
      for (JButton button: playerButtons)
      {
         pnlHumanHand.add(button);
      }
   }

   //Returns index + 1 of lowest card in hand
   private int getlowCard(Hand hand)
   {
      if (hand.getNumCards() > 0)
      {
         //initialize lowcard to first card in hand's index
         int lowCard = 1;

         //iterate from 2nd card to end
         //i holds index of current card
         //lowcard holds index of current low card
         for (int i = 2, length = hand.getNumCards(); i <= length; i++)
         {
            //get value of card at current index
            int currentCardValue = hand.inspectCard(i).getValuRank();
            int lowCardValue = hand.inspectCard(lowCard).getValuRank();
            //compare current card's value to lowcard's value
            if (currentCardValue < lowCardValue)
            {
               //current card is lower than lowcard
               //update lowcard to hold current card's index instead
               lowCard = i;
            }
         }
         //Return lowcard's index + 1 to account for inspectCard
         return lowCard;
      }
      //no cards left
      return 0;
   }

   //Cpu will play the lowest card it has
   private void cpuPlayLowCard()
   {
      Hand cpuHand = model.getHand(0);

      if (cpuHand.getNumCards() > 0)
      {
         //Get played cards and their labels
         JLabel[] playedCardLabels = view.getPlayedCardLabels();
         Card[] playedCards = model.getPlayedCards();

         //cpu's lowest card
         int lowCard = getlowCard(cpuHand);

         //add lowcard's label to played card labels array
         playedCardLabels[0] = new JLabel(GUICard.getIcon(cpuHand.inspectCard(lowCard)));

         //Card to be played
         playedCards[0] = cpuHand.playCard(lowCard - 1);

         //Set the icon of the cpu card
         view.getCpuCard().setIcon(playedCardLabels[0].getIcon());
      }
   }

   //Redefine this based on your card game rules
   //Defined for low card game
   @Override
   public int calculateRound(Card[] playedCards)
   {
      //These are cards, not labels
      //cpu is 0 index
      //player is 1 index
      Card cpuCard = playedCards[0];
      Card playerCard = playedCards[1];

      //cpu is at 0 index
      CardGameModel.CardGamePlayer cpu = model.getPlayers()[0];
      //player is at 1 index
      CardGameModel.CardGamePlayer player = model.getPlayers()[1];


      //Cpu won
      if (cpuCard.getValue() < playerCard.getValue())
      {
         //Retrieve cpuWinnings list
         ArrayList<Card> cpuWinnings = cpu.getWinnings();

         //Retrieve cpu points
         int points = cpu.getPoints();

         //Add both cards to cpu's winnings list
         cpuWinnings.add(cpuCard);
         cpuWinnings.add(playerCard);

         //cpu gets a point
         points++;

         //update cpu's points
         cpu.setPoints(points);

         return 0;
      }
      //Player won
      else if (cpuCard.getValue() > playerCard.getValue())
      {
         //Retrieve playerWinnings list
         ArrayList<Card> playerWinnings = player.getWinnings();

         //Retrieve player points
         int points = player.getPoints();

         //Add both cards to player's winnings list
         playerWinnings.add(cpuCard);
         playerWinnings.add(playerCard);

         //player gets a point
         points++;

         //update player's points
         player.setPoints(points);

         return 1;
      }
      //Draw
      //Each player wins their played card
      else
      {
         //player and cpu get their own cards
         //no points added
         cpu.getWinnings().add(cpuCard);
         player.getWinnings().add(playerCard);

         return -1;
      }
   }

   @Override
   public void displayRoundWinner(int winner)
   {
      //Computer won
      if (winner == 0)
      {
         showMessageDialog(view, "Computer wins");
      }
      //Player won
      else if (winner == 1)
      {
         showMessageDialog(view, "You win");
      }
      //Draw
      else
      {
         showMessageDialog(view, "Draw");
      }
   }

   @Override
   public void displayGameWinner()
   {
      //cpu is at 0 index
      CardGameModel.CardGamePlayer cpu = model.getPlayers()[0];
      //player is at 1 index
      CardGameModel.CardGamePlayer player = model.getPlayers()[1];

      int playerPoints = player.getPoints();
      int cpuPoints = cpu.getPoints();

      if (playerPoints > cpuPoints)
      {
         showMessageDialog(view, "You won the game");
      }
      else if (playerPoints < cpuPoints)
      {
         showMessageDialog(view, "Computer won the game");
      }
      else
      {
         showMessageDialog(view, "The game ended in a draw.");
      }
   }
}

