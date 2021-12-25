/***

 GUI Low Card Game Program

 By:
 Delaney Nikoofekr
 Christopher Flores
 Fernando Arozqueta

 Program:

 Split into 3 phases:
 Phase 1:
   Import images into an array and display them
 Phase 2:
   Create classes necessary to create an MVC model card game
 Phase 3:
   Import CardGameFramework and Create "Low Card Game"

 ***/

import javax.swing.*;
import java.awt.*;

public class Phase1
{
   // static for the 57 icons and their corresponding labels
   // normally we would not have a separate label for each card, but
   // if we want to display all at once using labels, we need to.

   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

   static void loadCardIcons()
   {
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop.  For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.

      //card values
      String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "9", "J", "Q", "K", "A", "X"};

      //card Suits
      String[] suits = {"C", "D", "H", "S"};

      //starting index
      int index = 0;

      //iterate over values
      for (String value: values)
      {
         //iterate over suits
         for (String suit: suits)
         {
            //create path
            String path = "images/" + value + suit + ".gif";
            //instantiate icon with path
            ImageIcon cardIcon = new ImageIcon(path);
            //add imageicon to array
            icon[index] = cardIcon;
            index++;
         }
         //break if index exceeds number of images
         //this will only happen if card images are tampered with
         if (index > NUM_CARD_IMAGES)
         {
            break;
         }
      }
      //add card back manually
      icon[56] = new ImageIcon("images/BK.gif");
   }

   // a simple main to throw all the JLabels out there for the world to see
   public static void main(String[] args)
   {
      int k;

      // prepare the image icon array
      loadCardIcons();

      // establish main frame in which program will run
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      frmMyWindow.setLayout(layout);

      // prepare the image label array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);

      // place your 3 controls into frame
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         frmMyWindow.add(labels[k]);

      // show everything to the user
      frmMyWindow.setVisible(true);
   }
}
