import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class CardTableView extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;

   //Panel for computer cardbacks
   private JPanel pnlComputerHand;
   //Panel for player buttons
   private JPanel pnlHumanHand;
   //Panel for played card icons
   private JPanel pnlPlayArea;

   //Holds cardbacks for computer
   private JLabel[] computerLabels;
   //Holds card icons for player
   private JLabel[] playedCardLabels;
   //Holds board labels for players
   private JLabel[] playLabelText;

   //Holds player's buttons
   private ArrayList<JButton> playerButtons = new ArrayList<>();

   //Holds cpu and player's currently played cards
   private JLabel playerCard = new JLabel("", JLabel.CENTER);
   private JLabel cpuCard = new JLabel("", JLabel.CENTER);

   public CardTableView(String title, int numCardsPerHand, int numPlayers)
   {
      setTitle(title);

      //Ensure cards is not negative nor exceeds max cards
      if (numCardsPerHand > 0 && numCardsPerHand < MAX_CARDS_PER_HAND)
      {
         this.numCardsPerHand = numCardsPerHand;
      }
      //Default to 7 if bad num card value passed
      else
      {
         this.numCardsPerHand = 7;
      }
      //Ensure number of players is 2
      if (numPlayers == MAX_PLAYERS)
      {
         this.numPlayers = numPlayers;
      }
      else
      {
         this.numPlayers = 2;
      }

      //Holds cardbacks for computer
      this.computerLabels = new JLabel[numCardsPerHand];
      //Holds card icons for player
      this.playedCardLabels  = new JLabel[numPlayers];
      //Holds board labels for players
      this.playLabelText  = new JLabel[numPlayers];

      this.setSize(800, 600);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //0 index is always computer
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);

      //iterate from first player to last player
      for (int i = 1; i < numPlayers; i++)
      {
         playLabelText[i] = new JLabel("Player " + i, JLabel.CENTER);
      }

      //Set border layout for calling JFrame
      setLayout(new BorderLayout());

      /*
       * Setup of the Public JPanels, give them a border with a title, then add
       * them to their appropriate boarder location
       */
      // Top Computer Hand
      pnlComputerHand = new JPanel(new GridLayout(1,numCardsPerHand));
      pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
      add(pnlComputerHand, BorderLayout.NORTH);

      // Middle Playing Area
      pnlPlayArea = new JPanel(new GridLayout(2,numPlayers));
      pnlPlayArea.setBorder(new TitledBorder("Playing Area"));
      add(pnlPlayArea, BorderLayout.CENTER);

      // Bottom Human Player Hand
      pnlHumanHand = new JPanel(new GridLayout(1,numCardsPerHand));
      pnlHumanHand.setBorder(new TitledBorder("Your Hand"));
      add(pnlHumanHand, BorderLayout.SOUTH);

      //Add text labels for cpu and players
      for (int i = 0; i < numPlayers; i++)
      {
         pnlPlayArea.add(playLabelText[i]);
      }

      //Add labels in play area for cpu and player cards
      pnlPlayArea.add(cpuCard);
      pnlPlayArea.add(playerCard);
   }

   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   public int getNumPlayers()
   {
      return numPlayers;
   }

   public JPanel getPnlComputerHand()
   {
      return pnlComputerHand;
   }

   public JPanel getPnlHumanHand()
   {
      return pnlHumanHand;
   }

   public JPanel getPnlPlayArea()
   {
      return pnlPlayArea;
   }

   public JLabel[] getComputerLabels()
   {
      return computerLabels;
   }

   public JLabel[] getPlayedCardLabels()
   {
      return playedCardLabels;
   }

   public JLabel[] getPlayLabelText()
   {
      return playLabelText;
   }

   public ArrayList<JButton> getPlayerButtons()
   {
      return playerButtons;
   }

   public JLabel getPlayerCard()
   {
      return playerCard;
   }

   public JLabel getCpuCard()
   {
      return cpuCard;
   }
}
