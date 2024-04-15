/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet;
import java.util.*;

/**
 *
 * @author paul
 */
class Helper {
    private static String generateRandomName() {
        // intilise random, StringBuilder and other variables
        Random random = new Random();
        String vowels = "aeiou";
        String consonants = "bcdfghjklmnpqrstvwxyz";
        StringBuilder name = new StringBuilder();
        
        // geenrate random length 4 to 8 characters
        int nameLength = random.nextInt(5) + 4;
        
        // Intilize for vowel alternation
        boolean previousWasVowel = false;
        // Generate the string based off the vowel alteration
        for (int i = 0; i < nameLength; i++) {
            if (previousWasVowel) {
                name.append(consonants.charAt(random.nextInt(consonants.length())));
                previousWasVowel = false;
            } else {
                name.append(vowels.charAt(random.nextInt(vowels.length())));
                previousWasVowel = true;
            }
            // Have a 10% to have the double name
            if (random.nextInt(10) == 0) {
                name.append(name.charAt(i));
            }
        }

        return name.toString();
    }

      // Create max pet stats function
    private static int[] assignPetStats() {
        Random random = new Random();
        int totalPoints = 20;
        int maxHealth, maxFood, maxEnergy;
  

        // Assign the maxes randomly
        maxHealth = random.nextInt(6) + 3;
        totalPoints -= maxHealth;
        maxFood = random.nextInt(totalPoints) + 1;
        totalPoints -= maxFood;
        maxEnergy = totalPoints;

        // Store stats in an array (am I allowed?)
        int[] stats = {maxHealth, maxFood, maxEnergy};

        // Display the maxes (optional)
        System.out.println("Your pet's stats:");
        System.out.println("Health: " + stats[0]);
        System.out.println("Food: " + stats[1]);
        System.out.println("Energy: " + stats[2]);

        return stats;
    }
      
        

        // Make pet selection menu function
        public static int[] petSelection() {
            Scanner scanner = new Scanner(System.in);
            String petType = "";
            int[] stats = {};
            // Display options
            System.out.println("What kind of pet would you like?");
            System.out.println("1. Dog");
            System.out.println("2. Cat");
            System.out.println("3. Bird");
            
            // Take input
            String petChoice = scanner.next().toLowerCase();
            
            // Decide what to do with input
            switch (petChoice) {
                case "1":
                case "dog":
                    petType = "dog";
                    break;
                case "2":
                case "cat":
                    petType = "cat";
                    break;
                case "3":
                case "bird":
                    petType = "bird";
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to a dog.");
                    petType = "dog";
            }
            // Display choice and second menu
            System.out.println("You have chosen " + petType);
            System.out.println("Would you like to: ");
            System.out.println("1. Enter a name");
            System.out.println("2. Generate a random name");
            String namingMethod = scanner.next().toLowerCase();
            
            // Determine what to do with input
            if (namingMethod.equals("1") || namingMethod.equals("enter a name")) {
                System.out.print("Enter the name: ");
                String petName = scanner.next();
                System.out.println("Your pet, named " + petName + ", has been born!");
            // Call radnom name if they choose random name
            } else if (namingMethod.equals("2") || namingMethod.equals("generate a random name")) {
                String petName = generateRandomName();
                System.out.println("Your pet, named " + petName + ", has been born!");
            } else {
                System.out.println("Invalid choice");
            }
            stats = assignPetStats();
            return stats;
            }
        
        // Make a game function to have everything nice and organizeed
        public static int playGame() {
            int coins = 0;
            boolean running = true;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the mini-games! Earn coins to win prizes.");

        while (running) {
            // Display menu and take input
            System.out.println("\nChoose a game:");
            System.out.println("1. Number Guessing Game");
            System.out.println("2. Matching Game");
            System.out.println("3. Check coins");
            System.out.println("4. Exit");
            String choice = scanner.next().toLowerCase();
            scanner.nextLine();
           
            // Determine what to do with input
            switch (choice) {
                case "1":
                case "number guessing game":
                    coins += playNumberGuessingGame();
                    break;
                case "2":
                case "matching game":
                    coins += playMatchingGame();
                    break;
                case "3":
                    System.out.println("You have " + coins + " coins.");
                    break;
                case "4":
                    System.out.println("Thanks for playing!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        return coins;
        }

        private static int playNumberGuessingGame() {
            // Define variables
            int coins = 0;
            Random random = new Random();
            Scanner scanner = new Scanner(System.in);
            int secretNumber = random.nextInt(100) + 1;
            int guesses = 0;
            int maxGuesses = random.nextInt(5) + 6; // Variable Difficulty

            System.out.println("Guess a number between 1 and 100. You have " + maxGuesses + " guesses.");
            
            //Start Game
            while (guesses < maxGuesses) {
                // Take input
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();
                guesses++;
                
                // If the guess is correct award the suser
                if (guess == secretNumber) {
                    int coinsEarned = (maxGuesses - guesses) * 5;
                    if (maxGuesses - guesses == 0){
                      coinsEarned = 5;  
                    }
                    coins += coinsEarned;
                    System.out.println("Correct! You earned " + coinsEarned + " coins.");
                    return coins;
                // If the guess is incorrect provide guidance
                } else if (guess < secretNumber) {
                    System.out.println("Too low.");
                } else {
                    System.out.println("Too high.");
                }
            }
            
            // If they suck and cant find it tell them what it was and end game
            if (guesses == maxGuesses) {
                System.out.println("You ran out of guesses. The number was " + secretNumber);
            }
            return coins;
        }
        
    public static int playMatchingGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Generate the hidden string
        String hiddenString = generateHiddenString(random);

        // Display the initially hidden string
        String displayedString = hiddenString.replaceAll(".", "X");
        System.out.println(displayedString);
        
        int coins = 0;
        int guesses = 0;
        int maxGuesses = random.nextInt(10) + 15; //Randomize Diffuculty
        int money = 0;

        while (guesses < maxGuesses && displayedString.contains("X")) {
            System.out.print("Enter first position to reveal (0-" + (hiddenString.length() - 1) + "): ");
            int pos1 = scanner.nextInt();
            System.out.print("Enter second position to reveal (0-" + (hiddenString.length() - 1) + "): ");
            int pos2 = scanner.nextInt();

            // Check for valid positions
            if (pos1 < 0 || pos1 >= hiddenString.length() || pos2 < 0 || pos2 >= hiddenString.length()) {
                System.out.println("Invalid positions!");
                continue;
            }

            // Check if already revealed
            if (displayedString.charAt(pos1) != 'X' || displayedString.charAt(pos2) != 'X') {
                System.out.println("One or both positions are already revealed!");
                continue;
            }

            // Check for a match
            if (hiddenString.charAt(pos1) == hiddenString.charAt(pos2)) {
                System.out.println("Match!");

                // "Reveal" the letters (replace X)
                displayedString = displayedString.substring(0, pos1) + hiddenString.charAt(pos1) + displayedString.substring(pos1 + 1);
                displayedString = displayedString.substring(0, pos2) + hiddenString.charAt(pos2) + displayedString.substring(pos2 + 1);

                System.out.println(displayedString);
                coins += 5; // Reward
            } else {
                System.out.println("No match!");
                coins -= 1; // Penalty
            }

            guesses++;
        }

        // Game Over
        if (displayedString.contains("X")) {
            System.out.println("You ran out of guesses!");
            System.out.println("Final coins: " + coins);
            return coins;
        } else {
            System.out.println("You found all the pairs!");
            System.out.println("Final coins: " + coins);
            return coins;
        }
        
    }

    private static String generateHiddenString(Random random) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 10; i++) { // Generate 10 pairs
            char letter = letters.charAt(random.nextInt(letters.length()));
            builder.append(letter);
            builder.append(letter);
        }

        // Shuffle the string
        String result = builder.toString();
        int length = result.length();
        for (int i = 0; i < length; i++) {
            int swapIndex = random.nextInt(length);
            char temp = result.charAt(i);
            result = result.substring(0, i) + result.charAt(swapIndex) + result.substring(i + 1);
            result = result.substring(0, swapIndex) + temp + result.substring(swapIndex + 1);
        }

        return result;
    }  

    }

        
        
        
        
     
    

