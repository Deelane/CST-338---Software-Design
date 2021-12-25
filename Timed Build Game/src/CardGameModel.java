import java.util.ArrayList;

public class CardGameModel
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

   //current cards in play
   private Card[] playedCards;

   //holds our players
   private CardGamePlayer[] players;

   public CardGameModel(int numPacks, int numJokersPerPack,
                        int numUnusedCardsPerPack, Card[] unusedCardsPerPack,
                        int numPlayers, int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
      {
         numPacks = 1;
      }
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
      {
         numJokersPerPack = 0;
      }
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
      {
         numUnusedCardsPerPack = 0;
      }
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
      {
         numPlayers = 4;
      }
      // one of many ways to assure at least one full deal to all players
      if  (numCardsPerHand < 1 ||
              numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
                      / numPlayers )
      {
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;
      }
      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];

      for (k = 0; k < numPlayers; k++)
      {
         this.hand[k] = new Hand();
      }
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
      {
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];
      }

      //initialize playedcards array
      this.playedCards = new Card[numPlayers];

      //initialize cardgameplayers array
      this.players = new CardGamePlayer[numPlayers];

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameModel()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
      {
         return new Hand();
      }
      return hand[k];
   }

   public Card getCardFromDeck() { return deck.dealCard(); }

   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

   public void newGame()
   {
      int k, j;

      // clear the hands
      //populate players array
      for (k = 0; k < numPlayers; k++)
      {
         hand[k].resetHand();
         players[k] = new CardGamePlayer();
      }
      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
      {
         deck.removeCard(unusedCardsPerPack[k]);
      }

      // add jokers
      for (k = 0; k < numPacks; k++)
      {
         for ( j = 0; j < numJokersPerPack; j++)
         {
            deck.addCard(new Card('X', Card.Suit.values()[j]));
         }
      }

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
      {
         hand[j].resetHand();
      }

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards ; k++)
      {
         for (j = 0; j < numPlayers; j++)
         {
            if (deck.getNumCards() > 0)
            {
               hand[j].takeCard(deck.dealCard());
            }
            else
            {
               enoughCards = false;
               break;
            }
         }
      }
      return enoughCards;
   }

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
      {
         hand[k].sort();
      }
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
      {
         return false;
      }

      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
      {
         return false;
      }
      return hand[playerIndex].takeCard(deck.dealCard());
   }

   public Card[] getPlayedCards()
   {
      return playedCards;
   }

   public CardGamePlayer[] getPlayers()
   {
      return players;
   }

   //Simple class for tracking points and winnings of a player
   //In reality, these player should have hands, etc
   //For now, make sure to keep track of which player is at which array index
   //Indexes in cardgameplayer array should line up with playedcards array
   static class CardGamePlayer
   {
      //Holds player's points
      private int points = 0;

      //Holds players winnings
      //Must have enough space to account for player winning the whole deck
      private ArrayList<Card> winnings = new ArrayList<>();

      public int getPoints()
      {
         return points;
      }

      public void setPoints(int points)
      {
         //dont allow for negative points
         if (points >= 0)
         {
            this.points = points;
         }
      }

      public ArrayList<Card> getWinnings()
      {
         return winnings;
      }
   }
}


