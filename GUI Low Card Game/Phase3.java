import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;


public class Phase3
{
	static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;

   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];

   //0 index is cpu, 1 index is player
   static Card[] playedCards = new Card[NUM_PLAYERS];

   //Holds player's card buttons
   static ArrayList<JButton> playerButtons = new ArrayList<>();

   //Must be able to hold 336 cards in the case that one player wins every round in a 6 pack deck
   //Holds cards player has won
   static Card[] playerWinnings = new Card[336];
   //Holds points player has won
   static int playerPoints = 0;
   //Holds cards cpu has won
   static Card[] cpuWinnings = new Card[336];
   //Holds points cpu has won
   static int cpuPoints = 0;

   static JLabel playerCard = new JLabel("", JLabel.CENTER);
   static JLabel cpuCard = new JLabel("", JLabel.CENTER);

   public static void main(String[] args)
   {

      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   // CREATE LABELS ----------------------------------------------------
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("Player 1", JLabel.CENTER);


      int numPacksPerDeck = 1;
      int numJokersPerPack = 2;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      CardGameFramework LowCardGame = new CardGameFramework(
              numPacksPerDeck, numJokersPerPack,
              numUnusedCardsPerPack, unusedCardsPerPack,
              NUM_PLAYERS, NUM_CARDS_PER_HAND);

      //Add text labels for "computer" and "player"
      myCardTable.pnlPlayArea.add(playLabelText[0]);
      myCardTable.pnlPlayArea.add(playLabelText[1]);

      myCardTable.pnlPlayArea.add(cpuCard);
      myCardTable.pnlPlayArea.add(playerCard);


      //Deal the cards
      LowCardGame.deal();

      //Track player and cpu hands
      Hand playerHand = LowCardGame.getHand(0);
      Hand cpuHand = LowCardGame.getHand(1);

      //Load card buttons for player
      loadPlayerButtons(playerHand, cpuHand, myCardTable);

      //Add buttons to button array and show them
      showPlayerButtons(playerHand, myCardTable);

      //Cpu plays lowest card
      cpuPlayLowCard(cpuHand, myCardTable);
      loadCpuLabels(cpuHand, myCardTable);

      myCardTable.setVisible(true);

   }

   //Load card back labels for cpu
   public static void loadCpuLabels(Hand hand, CardTable table)
   {
      //Ensure array is empty
      clearLabels(computerLabels);
      //Clear computer hand panel
      table.pnlComputerHand.removeAll();

      //iterate over all grid columns in JPanel
      for(int i = 0; i <= hand.getNumCards(); i++)
      {
         JLabel label = new JLabel(GUICard.getBackCardIcon());
         computerLabels[i] = label;
         table.pnlComputerHand.add(computerLabels[i]);
      }
   }

   //Clear all labels in array
   public static void clearLabels(JLabel[] labelArray)
   {
      for (int i = 0, length = labelArray.length; i < length; i++)
      {
         labelArray[i] = null;
      }
   }

   //creates JButtons for Card
   //passes player Hand to convert Card to JButton
	public static void loadPlayerButtons(Hand playerHand, Hand cpuHand, CardTable table)
	{
	   //Ensure there are cards to play
	   if (cpuHand.getNumCards() > 0 && playerHand.getNumCards() > 0)
      {
         //iterate over all JLabels currently in humanLabels
         for(int i = 0, length = playerHand.getNumCards(); i < length; i++)
         {
            //Create JButton from JLabel
            JButton playerButton = (new JButton(GUICard.getIcon(playerHand.inspectCard(i + 1))));

            //Remove button border and background
            playerButton.setContentAreaFilled(false);
            playerButton.setBorderPainted(false);

            //Add button to list
            playerButtons.add(playerButton);

            //add button to JPanel
            table.pnlHumanHand.add(playerButton);

            //Add action listeners
            playerButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent e)
               {
                  //get index of player button from list
                  int buttonIndex = playerButtons.indexOf(playerButton);

                  //Add played card label to player's index in played card labels array
                  playedCardLabels[1] = new JLabel(GUICard.getIcon(playerHand.inspectCard(buttonIndex + 1)));

                  //take card from hand and place it into played cards array
                  playedCards[1] = playerHand.playCard(buttonIndex);

                  //Set playerCard to clicked card
                  playerCard.setIcon(playedCardLabels[1].getIcon());

                  //Hide played card from view
                  playerButton.setVisible(false);

                  //Remove clicked button from buttons list
                  playerButtons.remove(playerButton);

                  //Calculate who won
                  int winner = calculateRound(playedCards[0], playedCards[1]);

                  //Display round winner in an alert
                  displayRoundWinner(winner, table);

                  playerCard.setIcon(null);

                  //Cpu is the only one "playing" cards
                  //When cpu hand hits 0, round is over
                  if (cpuHand.getNumCards() > 0)
                  {
                     cpuPlayLowCard(cpuHand, table);
                     loadCpuLabels(cpuHand, table);
                  }
                  else
                  {
                     displayGameWinner(table);
                     System.exit(0);
                  }
               }
            });
         }
      }
	}

	//Calculate who won the round
   //Player wins: return 1
   //Cpu wins: return 0
   //Draw: return -1
   public static int calculateRound(Card cpuCard, Card playerCard)
   {
      //Cpu won
      if (cpuCard.getValue() < playerCard.getValue())
      {
         cpuWinnings[cpuPoints] = cpuCard;
         cpuPoints++;
         cpuWinnings[cpuPoints] = playerCard;
         cpuPoints++;
         return 0;
      }
      //Player won
      else if (cpuCard.getValue() > playerCard.getValue())
      {
         playerWinnings[playerPoints] = cpuCard;
         playerPoints++;
         playerWinnings[playerPoints] = playerCard;
         playerPoints++;
         return 1;
      }
      //Draw
      //Each player wins their played card
      else
      {
         cpuWinnings[cpuPoints] = cpuCard;
         cpuPoints++;
         playerWinnings[playerPoints] = playerCard;
         playerPoints++;
         return -1;
      }
   }

   //Display round winner
   public static void displayRoundWinner(int winner, CardTable table)
   {
      //Computer won
      if (winner == 0)
      {
         showMessageDialog(table, "Computer wins");
      }
      //Player won
      else if (winner == 1)
      {
         showMessageDialog(table, "You win");
      }
      //Draw
      else
      {
         showMessageDialog(table, "Draw");
      }
   }

   //Display game winner
   public static void displayGameWinner(CardTable table)
   {
      if (playerPoints > cpuPoints)
      {
         showMessageDialog(table, "You won the game");
      }
      else if (playerPoints < cpuPoints)
      {
         showMessageDialog(table, "Computer won the game");
      }
      else
      {
         showMessageDialog(table, "The game ended in a draw.");
      }
   }

   //Shows player's buttons
	public static void showPlayerButtons(Hand hand, CardTable table)
   {
      //loop to add them to screen
      for(int i = 0, length = hand.getNumCards(); i < length; i++)
      {
         table.pnlHumanHand.add(playerButtons.get(i));
      }
   }

   //Returns index + 1 of lowest card in hand
   public static int getlowCard(Hand hand)
   {
      if (hand.getNumCards() > 0)
      {
         //initialize lowcard to first card in hand's index
         int lowCard = 1;

         //iterate from 2nd card to end
         //i holds index of current card
         //lowcard holds index of current low card
         for (int i = 2, length = hand.getNumCards(); i < length; i++)
         {
            //get value of card at current index
            int cardValue = hand.inspectCard(i).getValue();

            //compare current card's value to lowcard's value
            if (cardValue < hand.inspectCard(lowCard).getValue())
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
	public static void cpuPlayLowCard(Hand hand, CardTable table)
   {
      if (hand.getNumCards() > 0)
      {
         //cpu's lowest card
         int lowCard = getlowCard(hand);

         //add lowcard's label to played card lbels array
         playedCardLabels[0] = new JLabel(GUICard.getIcon(hand.inspectCard(lowCard)));

         //Card to be played
         playedCards[0] = hand.playCard(lowCard - 1);

         //Set the icon of the cpu card
         cpuCard.setIcon(playedCardLabels[0].getIcon());
      }
   }
}

//class CardGameFramework  ----------------------------------------------------
class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;            // # standard 52-card packs per deck
                                  // ignoring jokers or unused cards
   private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack;  // # cards removed from each pack
   private int numCardsPerHand;        // # cards to deal each player
   private Deck deck;               // holds the initial full deck and gets
                                  // smaller (usually) during play
   private Hand[] hand;             // one Hand for each player
   private Card[] unusedCardsPerPack;   // an array holding the cards not used
                                      // in the game.  e.g. pinochle does not
                                      // use cards 2-8 of any suit

   public CardGameFramework( int numPacks, int numJokersPerPack,
       int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
       int numPlayers, int numCardsPerHand)
   {
    int k;

    // filter bad values
    if (numPacks < 1 || numPacks > 6)
       numPacks = 1;
    if (numJokersPerPack < 0 || numJokersPerPack > 4)
       numJokersPerPack = 0;
    if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
       numUnusedCardsPerPack = 0;
    if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
       numPlayers = 4;
    // one of many ways to assure at least one full deal to all players
    if  (numCardsPerHand < 1 ||
          numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
          / numPlayers )
       numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

    // allocate
    this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
    this.hand = new Hand[numPlayers];
    for (k = 0; k < numPlayers; k++)
       this.hand[k] = new Hand();
    deck = new Deck(numPacks);

    // assign to members
    this.numPacks = numPacks;
    this.numJokersPerPack = numJokersPerPack;
    this.numUnusedCardsPerPack = numUnusedCardsPerPack;
    this.numPlayers = numPlayers;
    this.numCardsPerHand = numCardsPerHand;
    for (k = 0; k < numUnusedCardsPerPack; k++)
       this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

    // prepare deck and shuffle
    newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
    this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
    // hands start from 0 like arrays

    // on error return automatic empty hand
    if (k < 0 || k >= numPlayers)
       return new Hand();

    return hand[k];
   }

   public Card getCardFromDeck() { return deck.dealCard(); }

   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

   public void newGame()
   {
    int k, j;

    // clear the hands
    for (k = 0; k < numPlayers; k++)
       hand[k].resetHand();

    // restock the deck
    deck.init(numPacks);

    // remove unused cards
    for (k = 0; k < numUnusedCardsPerPack; k++)
       deck.removeCard( unusedCardsPerPack[k] );

    // add jokers
    for (k = 0; k < numPacks; k++)
       for ( j = 0; j < numJokersPerPack; j++)
          deck.addCard( new Card('X', Card.Suit.values()[j]) );

    // shuffle the cards
    deck.shuffle();
   }

   public boolean deal()
   {
    // returns false if not enough cards, but deals what it can
    int k, j;
    boolean enoughCards;

    // clear all hands
    for (j = 0; j < numPlayers; j++)
       hand[j].resetHand();

    enoughCards = true;
    for (k = 0; k < numCardsPerHand && enoughCards ; k++)
    {
       for (j = 0; j < numPlayers; j++)
          if (deck.getNumCards() > 0)
             hand[j].takeCard( deck.dealCard() );
          else
          {
             enoughCards = false;
             break;
          }
    }

    return enoughCards;
   }

   void sortHands()
   {
    int k;

    for (k = 0; k < numPlayers; k++)
       hand[k].sort();
   }

   Card playCard(int playerIndex, int cardIndex)
   {
    // returns bad card if either argument is bad
    if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
        cardIndex < 0 || cardIndex > numCardsPerHand - 1)
    {
       //Creates a card that does not work
       return new Card('M', Card.Suit.SPADES);
    }

    // return the card played
    return hand[playerIndex].playCard(cardIndex);

   }

   boolean takeCard(int playerIndex)
   {
    // returns false if either argument is bad
    if (playerIndex < 0 || playerIndex > numPlayers - 1)
       return false;

     // Are there enough Cards?
     if (deck.getNumCards() <= 0)
        return false;

     return hand[playerIndex].takeCard(deck.dealCard());
   }
}

