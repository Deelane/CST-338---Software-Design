import javax.swing.*;
import java.util.Hashtable;

public class GUIChessPiece
{
   private static Hashtable<String, ImageIcon> piecesMap;
   /*//Store image icons for white
   private static Icon[] whitePieces = new ImageIcon[16];
   //Store image icons for black
   private static Icon[] blackPieces = new ImageIcon[16];*/
   static boolean iconsLoaded = false;

   static void loadPieceIcons()
   {
      if (!iconsLoaded)
      {
         String[] pieces = {"pawn", "rook", "knight", "bishop", "queen", "king"};
         piecesMap = new Hashtable<>();

         //map piece names to imageicons
         for (String piece : pieces)
         {
            piecesMap.put((piece + "_white"), new ImageIcon("ChessPieceImages/" + piece + "_white.png"));
            piecesMap.put((piece + "_black"), new ImageIcon("ChessPieceImages/" + piece + "_black.png"));
         }
         iconsLoaded = true;
      }
   }
   //Return entire pieces map
   static Hashtable<String, ImageIcon> getPiecesMap()
   {
      loadPieceIcons();
      return piecesMap;
   }

   //Return a single piece icon
   static ImageIcon getPieceIcon(String iconName)
   {
      loadPieceIcons();
      if (piecesMap.containsKey(iconName))
      {
         return piecesMap.get(iconName);
      }
      //icon was not there
      return null;
   }
}
