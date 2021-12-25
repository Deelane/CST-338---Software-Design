public class CardGameMain
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;

   public static void main (String[] args)
   {

      int numPacksPerDeck = 1;
      int numJokersPerPack = 2;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      CardTableView view = new CardTableView("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      CardGameModel model = new CardGameModel(numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack, null, NUM_PLAYERS, NUM_CARDS_PER_HAND);

      CardGameController controller = new CardGameController(view, model);
   }
}

