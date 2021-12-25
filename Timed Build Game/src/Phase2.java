import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Random;


/**
   TODO
      Finish main phase 2
      Start Phase 3
      Finish UML (Card, Hand, Deck)
**/
public class Phase2
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   public static void main(String[] args)
   {
      int k;
      Icon tempIcon;

      // establish main frame in which program will run
      CardTable myCardTable
              = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // CREATE LABELS ----------------------------------------------------
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("Player 1", JLabel.CENTER);

      for(int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         Card playerCard = randomCardGenerator();

         computerLabels[i] = (new JLabel(GUICard.getBackCardIcon()));
         humanLabels[i] = (new JLabel(GUICard.getIcon(playerCard)));
         
         // ADD LABELS TO PANELS -----------------------------------------
         myCardTable.pnlComputerHand.add(computerLabels[i]);
         myCardTable.pnlHumanHand.add(humanLabels[i]);
      }

      // and two random cards in the play region (simulating a computer/hum ply)
      Card playerCard = randomCardGenerator();
      Card cpuCard = randomCardGenerator();
      myCardTable.pnlPlayArea.add(new JLabel(GUICard.getIcon(cpuCard)));
      myCardTable.pnlPlayArea.add(new JLabel(GUICard.getIcon(playerCard)));
      myCardTable.pnlPlayArea.add(playLabelText[0]);             
      myCardTable.pnlPlayArea.add(playLabelText[1]);

      // show everything to the user
      myCardTable.setVisible(true);


      /**TEST
      GUICard.loadCardIcons();
      Card[] cards =
              {new Card('Q', Card.Suit.SPADES),
              new Card('A', Card.Suit.SPADES),
              new Card('5', Card.Suit.SPADES),
             new Card('T', Card.Suit.SPADES),
             new Card('X', Card.Suit.SPADES),
             new Card('3', Card.Suit.SPADES),
             new Card('K', Card.Suit.SPADES),
             new Card('2', Card.Suit.SPADES),};
      Card.arraySort(cards, cards.length);
      ImageIcon imageIcon = (ImageIcon) GUICard.getBackCardIcon();
      Deck deck = new Deck(1);
      deck.shuffle();
      Hand hand = new Hand();
      for (int i = 0; i < 30; i++)
      {
         hand.takeCard(deck.dealCard());
      }
      hand.sort();
      boolean addCardTest = deck.addCard(new Card('Z', Card.Suit.SPADES));
      boolean removeCardTest = deck.removeCard(deck.inspectCard(30));
      deck.sort();**/
      Deck deck = new Deck(6);
      Card card = deck.dealCard();

   }
 //TODO: remove, this is test code
   static Card randomCardGenerator()
   {
      Random random = new Random();
      char value;
      Card.Suit suit;
      value = Card.valuRanks[random.nextInt(13)];
      suit = Card.Suit.values()[random.nextInt(4)];
      return new Card(value, suit);
   }
}

class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand;
   public JPanel pnlHumanHand;
   public JPanel pnlPlayArea;

   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      //TODO
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
   }

   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   public int getNumPlayers()
   {
      return numPlayers;
   }
}

//TODO GUICard Class
class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   static void loadCardIcons()
   {
      if (!iconsLoaded)
      {
         // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
         // in a SHORT loop.  For each file name, read it in and use it to
         // instantiate each of the 57 Icons in the icon[] array.

         //card values
         String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A", "X"};

         //card Suits
         String[] suits = {"C", "D", "H", "S"};

         //starting indexes
         int row = 0;
         int column = 0;

         //iterate over values
         for (String value: values)
         {
            //iterate over suits
            //adds all 4 suits of current value to current row of iconCards matrix
            for (String suit: suits)
            {
               //Ensure we don't go out of bounds
               if (column < iconCards[0].length)
               {
                  //create path
                  String path = "images/" + value + suit + ".gif";
                  //instantiate icon with path
                  ImageIcon cardIcon = new ImageIcon(path);
                  //add imageicon to matrix
                  iconCards[row][column] = cardIcon;
                  //next column
                  column++;
               }
               //break if we exceed number of columns
               else
               {
                  break;
               }
            }
            //reset column to 0
            column = 0;

            row++;
            //break if we exceed number of rows
            //this shouldn't happen
            if (row >= iconCards.length)
            {
               break;
            }
         }
         //add card back manually
         iconBack = new ImageIcon("images/BK.gif");

         //icons are now loaded
         iconsLoaded = true;
      }
   }
   
   //returns the Icon of the card in iconCards[][]
   static public Icon getIcon(Card card)
   {
      //loads icons into suits and returns iconCards array as int
      loadCardIcons();
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   //Returns integer value (index in valuRank array) of card
   //Returns Joker if card is bad
   private static int valueAsInt(Card card)
   {
      if (!card.getErrorFlag())
      {
         //iterate over valuranks
         for (int i = 0, length = Card.valuRanks.length; i < length; i++)
         {
            //if we find our character
            if (card.getValue() == Card.valuRanks[i])
            {
               //return index of the character
               return i;
            }
         }
      }
      //If we didn't find our value or card is invalid, return Joker so we don't go out of bounds
      return 13;
   }
   
   //Returns suit value (index in Card.Suit enum) of card
   //Returns SPADES if card is bad
   private static int suitAsInt(Card card)
   {
      if (!card.getErrorFlag())
      {
         //iterate over each suit in Card.suit
         for (Card.Suit suit: Card.Suit.values())
         {
            //if current suit equals the suit we passed in
            if (card.getSuit().equals(suit))
            {
               //return the index of the suit
               return suit.ordinal();
            }
         }
      }
      //if we didn't find the suit or card is invalid, return SPADES
      return 3;
   }
   
   
   //Returns card back
   static public Icon getBackCardIcon()
   {
      loadCardIcons();
      return iconBack;
   }
}


// class for creating cards
class Card
{
   public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}
   private char value;
   private Suit suit;
   private boolean errorFlag;
   //order of card values in with the smallest first, included 'X' for a joker
   public static char[] valuRanks = {'2','3','4','5','6','7','8','9','T','J','Q','K','A','X'};

   public Card()
   {
      value = 'A';
      suit = Suit.SPADES;
   }

   public Card(char value, Suit suit)
   {
      set(value,suit);
   }

   public String toString()
   {
      if(errorFlag)
      {
         return "not valid";
      }
      else
      {
         return getValue() + " of " + getSuit();
      }
   }

   // this function initializes a card based on the values passed
   public boolean set(char value, Suit suit)
   {
      if(isValid(value,suit))
      {
         this.value = value;
         this.suit = suit;
         return true;
      }
      else
      {
         errorFlag = true;
         return false;
      }
   }

   // function checks if two cards equal each other
   public boolean equals(Card card)
   {
      //Ensures cards are valid
      if (!errorFlag && !card.getErrorFlag())
      {
         return (value == card.value) && (suit == card.suit);
      }
      //One or both cards were not valid
      return false;
   }


   //this function uses an array as a reference to check if the value is valid
   //accVal == accepted values
   // boolean created inside function to check if the array contains the value
   private boolean isValid(char value, Suit suit)
   {
      //Adjusted for joker (X)
      char[] accVal = {'2','3','4','5','6','7','8','9','T','A','J','Q','K','X'};

      //immediately returns true if valid, returns false otherwise
      for(int i = 0; i <accVal.length; i++)
      {
         if(value == accVal[i])
         {
            return true;
         }
      }
      return false;
   }

   // accessors for value,suit and errorflag
   public char getValue()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   //bubble-sort
   //Very inefficient, sure wish I could use maps... (':
   static void arraySort(Card[] cardArray, int arraySize)
   {
      //If array size is less than or equal to 1, then it is sorted or invalid
      if (arraySize > 1)
      {
         boolean sorted;
         do
         {
            //if sorted is not set to false in for loop, then array is sorted
            sorted = true;

            for (int i = 0; i < arraySize - 1; i++)
            {
               //store value rank of card at index i and i + 1
               int card1Value = -1;
               int card2Value = -1;

               //iterate over valuRanks array
               //store value rank of card i and i + 1 in respective ints
               for (int j = 0, length = valuRanks.length; j < length; j++)
               {
                  if (cardArray[i].getValue() == valuRanks[j])
                  {
                     card1Value = j;
                  }
                  if (cardArray[i + 1].getValue() == valuRanks[j])
                  {
                     card2Value = j;
                  }
                  //If we found our card values
                  if (card1Value != -1 && card2Value != -1)
                  {
                     break;
                  }
               }
               if (card1Value > card2Value)
               {
                  //not sorted
                  sorted = false;
                  //Temp card holding card at array index i
                  Card tempCard = new Card(cardArray[i].getValue(), cardArray[i].getSuit());
                  //Swap the cards
                  cardArray[i] = cardArray[i + 1];
                  cardArray[i + 1] = tempCard;
               }
            }
         }while (!sorted);
      }
   }

   //Return value ranking of card
   public int getValuRank()
   {
      if (!errorFlag)
      {
         for (int i = 0, length = valuRanks.length; i < length; i++)
         {
            if (value == valuRanks[i])
            {
               return i;
            }
         }
      }
      //Default to 0 if card is invalid
      return 0;
   }
}

/*****************FERNANDO PHASE2*************/

class Hand
{
   public static int MAX_CARDS = 50;
   private Card[] myCards;
   private int numCards;

   //DEFAULT CONSTRUCTOR
   public Hand()
   {
      myCards = new Card[0];
      numCards = 0;
   }

   //RESET HAND - REMOVES ALL CARDS FROM THE DECK
   public void resetHand()
   {
      myCards = new Card[0];
      numCards = 0;
   }

   //TAKE CARD - ADDS A CARD TO THE NEXT AVAILABLE POSITION IN THE myCards ARRAY.
   public boolean takeCard(Card card)
   {
      // case for not adding a cards
      if (card.getErrorFlag() || numCards == MAX_CARDS)
      {
         return false;
      }

      // since we haven't learned about enummerated lists or vectors yet,
      // we have to create the array again with an additional space
      int currentLength = myCards.length;
      int newLength = currentLength + 1;
      Card[] currentHand = myCards.clone();
      myCards = new Card[newLength];

      // copy all cards, no references, the list can be deleted
      for (int i = 0; i < currentLength; i++)
      {
         myCards[i] = new Card(currentHand[i].getValue(),
               currentHand[i].getSuit());
      }

      // add the new card
      myCards[newLength - 1] = new Card(card.getValue(), card.getSuit());
      numCards++;

      return true;
   }

   /*public Card playCard(int cardIndex)
   {
      //Holding at least one card
      if (numCards > 0)
      {
         Card card = myCards[cardIndex];
         //decrement numCards
         numCards--;
         return card;
      }
      //Return bad card if we are out of cards
      return new Card('Z', Card.Suit.SPADES);
   }*/

   /**playCard from prompt**/
   //REMOVES THE CARD AT INDEX LOCATION AND SHIFTS CARDS DOWN IN THE ARRAY
   public Card playCard(int cardIndex)
   {
      if ( numCards == 0 ) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);
      }
      //Decreases numCards.
      Card card = myCards[cardIndex];

      numCards--;
      for(int i = cardIndex; i < numCards; i++)
      {
         myCards[i] = myCards[i+1];
      }

      myCards[numCards] = null;

      return card;
    }

   //STRING REPRESENTATION OF HAND CLASS
   public String toString()
   {
      String hand = "Hand = ( ";
      
      if (numCards == 0)
      {
         hand += ")";
      }
      else
      {
         for (int i = 0; i < numCards - 1; i++)
         {
            hand += myCards[i] + ", ";
         }
         hand += myCards[numCards - 1] + " )";
      }
      return hand;
   }

   //RETURNS THE NUMBER OF CARDS
   public int getNumCards()
   {
      return numCards;
   }

   //INSPECT THE CARD IN HAND
   public Card inspectCard (int k)
   {
      if ((k <= numCards) && (k > 0))
      {
         return myCards[k - 1];
      }
      //Returns card with errorflag = true if k is less than 1 or greater than the size of the player's hand
      return new Card('Z', Card.Suit.SPADES);
   }

   //sorts the hand by calling arraySort() from Card class
   void sort() 
   {
      Card.arraySort(myCards, myCards.length);
   }

}

/*** PHASE 3 ***/
class Deck
{
   //6 packs of cards
   public final int MAX_CARDS = 6;
   //Master pack containing all 52 cards + 4 joker
   private static Card[] masterPack;
   //Holds our current deck
   private Card[] cards;
   //Holds the index of the topCard
   private int topCard;

   public Deck()
   {
      allocateMasterPack();
      //Wording in prompt is unclear, so im going to assume prof wants us to call init() here
      init(1);
   }

   public Deck(int numPacks)
   {
      //Initialize to one pack if exceed max packs or num packs is negative
      if (numPacks > MAX_CARDS || numPacks < 1)
      {
         init(1);
      }
      //If we didn't exit...
      allocateMasterPack();
      init(numPacks);
   }

   //Creates numPack copies of masterPack and stores them in cards array
   public void init (int numPacks)
   {
      //Makes space for 4 * numPacks jokers, to be added by CardGameFramework
      /**These empty spaces MUST be added to the end of ALL CARDS, not each pack or
         addCard would need to be refactored and would be inefficient**/
      cards = new Card[56 * numPacks];

      //Hold actual index of the top card
      //Must account for empty spaces for jokers
      this.topCard = cards.length - (4 * numPacks) - 1;

      //iterates from 0 - numPacks * 56
      int cardCounter = 0;
      for (int i = 0; i < numPacks; i++)
      {
         for (Card card: masterPack)
         {
            cards[cardCounter] = card;
            cardCounter++;
         }
      }
   }

   //Fisher-Yates shuffle
   public void shuffle()
   {
      Random random = new Random();

      //Upper bound of array
      int upperBound = topCard;

      //iterate from topCard down to 1
      for (int i = upperBound; i > 1; i--)
      {
         //random integer between 0 and i inclusive
         int randomInt = random.nextInt(i + 1);

         //Deep copy of card at cards[i]
         Card copyCard = new Card(cards[i].getValue(), cards[i].getSuit());

         //Can now safely update cards[i] with new card (swap it)
         cards[i] = new Card(cards[randomInt].getValue(), cards[randomInt].getSuit());

         //Update cards[randomInt] with cards[i]'s old values
         cards[randomInt] = new Card(copyCard.getValue(), copyCard.getSuit());
      }
   }

   //returns and removes the card in the top occupied position of cards[].
   //returns null if the deck is empty
   public Card dealCard()
   {
      //There is at least one card
      if (topCard >= 0)
      {
         //If there is a valid card, and array index is not empty
         if (cards[topCard] == null)
         {
            //While we are at our null indexes left open as space for jokers, decrement topCard
            while(cards[topCard] == null)
            {
               topCard--;
            }
         }
         //This card should have data
         //Need to create a deep copy so that we can safely delete the top card
         Card card = new Card(cards[topCard].getValue(), cards[topCard].getSuit());

         //Delete topCard and decrement
         cards[topCard] = null;
         topCard--;
         return card;
      }
      //if no cards are available
      return null;
   }

   //returns the index of the top card
   public int getTopCard()
   {
      return topCard;
   }

   /*
   Unclear if prompt is asking for the kth card, or the card at the kth index.
   So I am returning the card at the kth index.
   */
   //Accessor for an individual card.
   //Returns a card with errorFlag = true if k is bad.
   public Card inspectCard(int k)
   {
      if (k >= 0 && k <= topCard)
      {
         //return a deep copy so that the inspector can't change the card.
         return new Card(cards[k].getValue(), cards[k].getSuit());
      }
      //if no cards are available or bad k is passed
      return new Card('Z', Card.Suit.SPADES);
   }

   //Initializes masterPack if it hasn't been done already.
   private static void allocateMasterPack()
   {
      //only initialize masterPack if it doesnt exist already
      if (masterPack == null)
      {
         //Opens 4 extra spaces for possible jokers to be added via CardGameFramework
         masterPack = new Card[52];
         char[] accVal = {'2','3','4','5','6','7','8','9','T','J','Q','K','A'};
         int i = 0;

         //iterates over all 4 suits
         for (Card.Suit suit: Card.Suit.values())
         {
            //iterates over all valid values
            for (char value: accVal)
            {
               //creates new card from current suit and value, adds it to masterpack
               //Doesn't check for i exceeding 52
               masterPack[i] = new Card(value, suit);
               i++;
            }
         }
      }
   }

   boolean addCard(Card card)
   {
      //Ensure card is valid
      if (!card.getErrorFlag())
      {
         //amount of card in each deck
         int instances = 0;

         //maximum allowed instances of a card
         //amount of cards in deck divided by amount of cards in 1 deck
         int max = cards.length / 56;

         //iterates all cards in current deck
         for(int i = 0; i <= topCard; i++)
         {
            //add one instance of card
            if (cards[i].equals(card))
            {
               instances++;
            }
         }

         //if instances of card is less than max allowed instances
         //and if amount of cards left is less than total cards that deck can hold
         if(instances < max && topCard < cards.length)
         {
            //topCard holds index of card at largest index
            //Add card at index after largest
            cards[topCard + 1] = (new Card(card.getValue(), card.getSuit()));
            //topCard is now 1 larger than before
            topCard++;
            return true;
         }

         //too many card instances or deck is full
      }
      return false;
   }


   //Remove a specific card from the deck.
   //Put the current top card into its place. 
   //Be sure the card you need is actually still in the deck, 
   //if not return false.
    boolean removeCard(Card card)
    {
       //Dont bother checking if invalid card is in deck
       if (!card.getErrorFlag())
       {
          //topCard is index and # of cards
          //iterate over all cards in current deck
          for(int i = 0; i <= topCard; i++)
          {
             //if the card is in the deck
             if(cards[i].equals(card))
             {
                //sets specific card to top card
                Card tempCard = new Card(cards[topCard].getValue(), cards[topCard].getSuit());
                cards[i] = tempCard;

                //original topCard set to null
                cards[topCard] = null;

                //decrement top card
                topCard--;
                return true;
             }
          }
       }
   	//card wasn't in the deck or was invalid
   	return false;
   }
   
   //return the number of cards remaining in the deck.
   int getNumCards()
   {
   	return topCard;
   }
   
   //Sort cards according to value
   void sort()
   {
   	Card.arraySort(cards, topCard + 1);
   }
}