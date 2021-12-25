public class main
{
   public static void main(String[] args)
   {
      GUIChessPiece.loadPieceIcons();

      ChessBoardView view = new ChessBoardView();
      ChessBoardModel model = new ChessBoardModel(1);
      ChessBoardController controller = new ChessBoardController(view, model);
      //0 or anything else for white on bottom
      //1 for white on top
      controller.setBoardstate(1);
      //load the view
      controller.loadView();
      controller.newGame();

   }
}
