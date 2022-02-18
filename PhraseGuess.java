// Martin Rudzis
// CS 141
// HW Core Topics: practice with file input, strings, and arrays.
//
// This program reads in a series of phrases from a text file, and randomly picks one to use for a game of phrase guess.
// The user may get a maximum number of incorrect guesses, depending on the value of the class constant, MAX_WRONG
// If the user cannot guess the phrase before using up their wrong guesses, the answer given
// If the user guesses the puzzle, then a special message is printed

import java.io.*;
import java.util.*;

public class PhraseGuess {
   
   public static final int MAX_WRONG = 5;
      
   public static void main(String[] args) throws FileNotFoundException {
      int tries = MAX_WRONG;
      String[] phrases = loadInput();
      String phrase = pickPuzzle(phrases);
      boolean[] checker = new boolean[phrase.length()];
      
      System.out.println("Let's play a word guess game. \nYou can guess wrong up to " + MAX_WRONG + " times.");
      System.out.println();
      
      while (tries > 0 && checkIfSolved(checker) == false) {
         tries = playGame(phrase, checker, tries);
         System.out.println();
      }
      
      System.out.println();
      if (checkIfSolved(checker) == true) {
         System.out.println("You solved the puzzle! Good job!");
      }
      else {
         System.out.println("You have reached the max guesses. The puzzle was " + phrase);
      }
   }
   
   // This method puts each line from the file "phrases.txt" into an array and returns an array that has a number of elements equal to the number at the top of the file
   public static String[] loadInput() throws FileNotFoundException {
      Scanner in = new Scanner (new File("phrases.txt"));
      int length = Integer.parseInt(in.nextLine());
      String[] phrases = new String[length];
    
      int index = 0;
      while (in.hasNextLine()) {
         String phrase = in.nextLine();
         phrases[index] = phrase;
         index++;
      }
      return phrases;
   }
   
   // This method uses a random number generator to choose an index from within an array parameter and return whatever string is at that index
   public static String pickPuzzle(String[] phrase) {
      Random rand = new Random();
      int r = rand.nextInt(phrase.length);
      return phrase[r];
   }
   
   // This method takes a String parameter that contains the phrase to be guessed, and the boolean array "checker" which comes from the main.
   // If any characters in the string are punctuation and the corresponding index in checker is false, then the boolean is changed to true
   // Then, for every true element in the boolean array, whatever is at the corresponding index of in the phrase get printed out via the printPuzzle method
   // For every false element in the boolean array, "_ " gets printed out
   // The user is then prompted to guess a letter, which is returned to the main
   // Finally, the user's guess is checked via the checkGuess method, and the number of remaining incorrect tries is returned to the main
   public static int playGame(String phrase, boolean[] checker, int tries) {
      for (int i = 0; i < phrase.length(); i++) {
         if (phrase.charAt(i) == ' ' || !Character.isLetter(phrase.charAt(i))) {
            checker[i] = true;
         }
      }
      
      printPuzzle(phrase, checker);

      System.out.println();
      System.out.print("Guess a letter > ");
      Scanner console = new Scanner(System.in);
      String guess = console.next().toLowerCase();
      
      tries = checkGuess(guess, phrase, checker, tries);
      return tries;
   }
   
   // This method checks for the event in which the user wins by guessing all letters in the phrase
   // The loop returns whether the number of elements that are "true" in the boolean array equals to the length of the array
   public static boolean checkIfSolved(boolean[] checker) {
      int count = 0;
      for (int i = 0; i < checker.length; i++) {
         if (checker[i] == true) {
            count++;
         }
      }
      return count == checker.length;
   }
   
   // This method takes four parameters: the user's guess, the phrase, the boolean array, and the number of current incorrect tries remaining
   // It then compares the user's guess to the phrase to determine if the guess matches any letters. Depending on the result, a message is printed
   public static int checkGuess(String guess, String phrase, boolean[] checker, int tries) {
      int count = 0;
      for (int i = 0; i < phrase.length(); i++) {
         if (guess.equals(phrase.substring(i, i+1).toLowerCase())) {
            count++;
            checker[i] = true;
         }
      }
      if (count > 0) {
            System.out.println(guess + " was found in the puzzle " + count + " times");
      }
       else {
            tries--;
            System.out.println("You have " + tries + " more incorrect tries");
      }
      System.out.println();
      return tries;
   }
   
   // Thie method looks at which elements in the boolean array are true or false
   // If any element is true, then the letter at the corresponding index of phrase is revealed
   public static void printPuzzle(String phrase, boolean[] checker) {
      for (int i = 0; i < checker.length; i++) {
         if (checker[i] == true) {
            System.out.print(phrase.substring(i, i + 1));
         }
         else {
            System.out.print("_ ");
         }  
      }
   }
}

/*
--- SAMPLE 1 ---
 Let's play a word guess game. 
 You can guess wrong up to 5 times.
 
 _ _ '_  _ _ _ _  _  _ _ _ _  _ _ _ '_  _ _ _ _ _ 
 Guess a letter > w
 You have 4 more incorrect tries
 
 
 _ _ '_  _ _ _ _  _  _ _ _ _  _ _ _ '_  _ _ _ _ _ 
 Guess a letter > e
 e was found in the puzzle 2 times
 
 
 _ _ '_  _ ee_  _  _ _ _ _  _ _ _ '_  _ _ _ _ _ 
 Guess a letter > i
 i was found in the puzzle 2 times
 
 
 I_ '_  _ ee_  _  _ _ _ _  _ _ _ '_  _ i_ _ _ 
 Guess a letter > c
 You have 3 more incorrect tries
 
 
 I_ '_  _ ee_  _  _ _ _ _  _ _ _ '_  _ i_ _ _ 
 Guess a letter > l
 You have 2 more incorrect tries
 
 
 I_ '_  _ ee_  _  _ _ _ _  _ _ _ '_  _ i_ _ _ 
 Guess a letter > g
 g was found in the puzzle 1 times
 
 
 I_ '_  _ ee_  _  _ _ _ _  _ _ _ '_  _ ig_ _ 
 Guess a letter > a
 a was found in the puzzle 3 times
 
 
 I_ '_  _ ee_  a _ a_ _  _ a_ '_  _ ig_ _ 
 Guess a letter > m
 You have 1 more incorrect tries
 
 
 I_ '_  _ ee_  a _ a_ _  _ a_ '_  _ ig_ _ 
 Guess a letter > n
 n was found in the puzzle 2 times
 
 
 I_ '_  _ een a _ a_ _  _ a_ '_  nig_ _ 
 Guess a letter > u
 You have 0 more incorrect tries
 
 
 
 You have reached the max guesses. The puzzle was It's been a hard day's night
 
 
--- SAMPLE 2 ---
 Let's play a word guess game. 
 You can guess wrong up to 5 times.
 
 _ _ _ _ _ _ _ _ _  _ _ _ _ _ _ _  _ _  _ _ _  _ _ _ _  _ _  _ _ _ _ _ 
 Guess a letter > A
 a was found in the puzzle 2 times
 
 
 _ _ a_ _ _ _ _ _  _ _ _ _ _ _ _  _ _  _ _ _  _ _ a_  _ _  _ _ _ _ _ 
 Guess a letter > E
 e was found in the puzzle 2 times
 
 
 _ _ a_ _ _ _ _ _  _ _ _ _ _ _ _  _ _  _ _ e _ ea_  _ _  _ _ _ _ _ 
 Guess a letter > I
 i was found in the puzzle 5 times
 
 
 _ _ a_ _ _ i_ _  _ i_ _ i_ _  i_  _ _ e _ ea_  _ _  _ i_ _ _ 
 Guess a letter > O
 o was found in the puzzle 1 times
 
 
 _ _ a_ _ _ i_ _  _ i_ _ i_ _  i_  _ _ e _ ea_  o_  _ i_ _ _ 
 Guess a letter > U
 You have 4 more incorrect tries
 
 
 _ _ a_ _ _ i_ _  _ i_ _ i_ _  i_  _ _ e _ ea_  o_  _ i_ _ _ 
 Guess a letter > Y
 You have 3 more incorrect tries
 
 
 _ _ a_ _ _ i_ _  _ i_ _ i_ _  i_  _ _ e _ ea_  o_  _ i_ _ _ 
 Guess a letter > C
 c was found in the puzzle 1 times
 
 
 _ _ ac_ _ i_ _  _ i_ _ i_ _  i_  _ _ e _ ea_  o_  _ i_ _ _ 
 Guess a letter > N
 n was found in the puzzle 4 times
 
 
 _ _ ac_ _ i_ _  _ in_ in_  in _ _ e _ ea_  o_  ni_ _ _ 
 Guess a letter > G
 g was found in the puzzle 3 times
 
 
 _ _ ac_ _ i_ _  _ inging in _ _ e _ ea_  o_  nig_ _ 
 Guess a letter > T
 t was found in the puzzle 2 times
 
 
 _ _ ac_ _ i_ _  _ inging in t_ e _ ea_  o_  nig_ t
 Guess a letter > B
 b was found in the puzzle 2 times
 
 
 B_ ac_ bi_ _  _ inging in t_ e _ ea_  o_  nig_ t
 Guess a letter > L
 l was found in the puzzle 1 times
 
 
 Blac_ bi_ _  _ inging in t_ e _ ea_  o_  nig_ t
 Guess a letter > K
 k was found in the puzzle 1 times
 
 
 Blackbi_ _  _ inging in t_ e _ ea_  o_  nig_ t
 Guess a letter > R
 r was found in the puzzle 1 times
 
 
 Blackbir_  _ inging in t_ e _ ea_  o_  nig_ t
 Guess a letter > D
 d was found in the puzzle 3 times
 
 
 Blackbird _ inging in t_ e dead o_  nig_ t
 Guess a letter > S
 s was found in the puzzle 1 times
 
 
 Blackbird singing in t_ e dead o_  nig_ t
 Guess a letter > H
 h was found in the puzzle 2 times
 
 
 Blackbird singing in the dead o_  night
 Guess a letter > F
 f was found in the puzzle 1 times
 
 
 
 You solved the puzzle! Good job!
*/