/*******************
String Manipulation Program
Delaney Nikoofekr

 Program:
 Part 1: Asks user for first name and last name, then concatenates and prints them.
 Part 2: Prints out how many hours a week they should spend on this class, asks how many they spent this week and prints their input.
 *******************/


package com.company;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main
{
    //min and max hours spent on class each week
    static final int MIN_HOURS = 12;
    static final int MAX_HOURS = 20;

    public static void main(String[] args)
    {

        /**PART ONE**/
        //Takes in user's first and last name
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your first name, capitalize the first letter:");
        String firstName = input.nextLine();
        System.out.println("Enter your last name, capitalize the first letter:");
        String lastName = input.nextLine();

        //Concatenates user's first and last name and prints it
        String fullName = firstName + " " + lastName;
        System.out.println("Your name is: " + fullName + "\n" + "Your name is " + fullName.length() + " characters long.");
        /**END PART ONE**/


        /**PART TWO**/
        //Prints how many hours they should spend on this class each week
        System.out.println("You should spend between " + MIN_HOURS + " and " + MAX_HOURS + " hours on CST338 each week.");

        //Asks how many hours they spent on class this week and stores it
        System.out.println("Enter how many hours you have spent on class this week to 3 decimal places:");
        double studentHours = input.nextDouble();

        //Formats user input to 1 decimal place and prints it
        DecimalFormat decimalFormat = new DecimalFormat("##.#");
        System.out.println("You spent " + decimalFormat.format(studentHours) + " hours on class this week.");
        /**END PART TWO**/



        /***OUTPUT***

         RUN 1:

             Enter your first name, capitalize the first letter:
             Delaney
             Enter your last name, capitalize the first letter:
             Nikoofekr
             Your name is: Delaney Nikoofekr
             Your name is 17 characters long.
             You should spend between 12 and 20 hours on CST338 each week.
             Enter how many hours you have spent on class this week to 3 decimal places:
             12.234
             You spent 12.2 hours on class this week.

             Process finished with exit code 0

         RUN 2:

             Enter your first name, capitalize the first letter:
             Captain
             Enter your last name, capitalize the first letter:
             Falcon
             Your name is: Captain Falcon
             Your name is 14 characters long.
             You should spend between 12 and 20 hours on CST338 each week.
             Enter how many hours you have spent on class this week to 3 decimal places:
             17.789
             You spent 17.8 hours on class this week.

             Process finished with exit code 0

         ************/
    }
}
