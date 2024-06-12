package virtualpet;

import java.util.*;

public class Helper {

    // Generates a random pet name based on alternating vowels and consonants
    public static String generateRandomName() {
        Random random = new Random();
        String vowels = "aeiou";
        String consonants = "bcdfghjklmnpqrstvwxyz";
        StringBuilder name = new StringBuilder();

        int nameLength = random.nextInt(5) + 4;
        boolean previousWasVowel = false;

        for (int i = 0; i < nameLength; i++) {
            if (previousWasVowel) {
                name.append(consonants.charAt(random.nextInt(consonants.length())));
                previousWasVowel = false;
            } else {
                name.append(vowels.charAt(random.nextInt(vowels.length())));
                previousWasVowel = true;
            }

            if (random.nextInt(10) == 0) {
                name.append(name.charAt(i));
            }
        }

        return name.toString();
    }

    // Assigns random stats to the pet
    public static int[] assignPetStats() {
        Random random = new Random();
        int totalPoints = 23;
        int maxHealth, maxFood, maxEnergy;

        maxHealth = random.nextInt(6) + 5; // Ensuring max is at least 5
        totalPoints -= maxHealth;
        maxFood = random.nextInt(totalPoints - 2) + 3; // Ensuring at least 3
        totalPoints -= maxFood;
        maxEnergy = totalPoints > 0 ? totalPoints : 1;

        int[] stats = { maxHealth, maxFood, maxEnergy };

        System.out.println("Your pet's stats:");
        System.out.println("Health: " + stats[0]);
        System.out.println("Food: " + stats[1]);
        System.out.println("Energy: " + stats[2]);

        return stats;
    }

    // Allows the user to select and name a pet
    public static String[] petSelection() {
        Scanner scanner = new Scanner(System.in);
        String petType = "";
        String[] petInfo = new String[4];
        System.out.println("What kind of pet would you like?");
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        System.out.println("3. Bird");

        String petChoice = scanner.next().toLowerCase();

        switch (petChoice) {
            case "1":
            case "dog":
                petType = "Dog";
                break;
            case "2":
            case "cat":
                petType = "Cat";
                break;
            case "3":
            case "bird":
                petType = "Bird";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to a dog.");
                petType = "Dog";
        }

        System.out.println("You have chosen " + petType);
        System.out.println("Would you like to: ");
        System.out.println("1. Enter a name");
        System.out.println("2. Generate a random name");
        String namingMethod = scanner.next().toLowerCase();

        String petName = "";
        if (namingMethod.equals("1") || namingMethod.equals("enter a name")) {
            System.out.print("Enter the name: ");
            petName = scanner.next();
        } else if (namingMethod.equals("2") || namingMethod.equals("generate a random name")) {
            petName = generateRandomName();
        } else {
            System.out.println("Invalid choice. Generating a random name.");
            petName = generateRandomName();
        }
        System.out.println("Your pet, named " + petName + ", has been born!");

        int[] stats = assignPetStats();
        petInfo[0] = petType;
        petInfo[1] = petName;
        petInfo[2] = Arrays.toString(stats);
        petInfo[3] = Arrays.toString(stats);

        return petInfo;
    }

    // Implements the number guessing game
    public static int playNumberGuessingGame() {
        int coins = 0;
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int secretNumber = random.nextInt(100) + 1;
        int guesses = 0;
        int maxGuesses = random.nextInt(5) + 6;

        System.out.println("Guess a number between 1 and 100. You have " + maxGuesses + " guesses.");

        while (guesses < maxGuesses) {
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();
            guesses++;

            if (guess == secretNumber) {
                int coinsEarned = (maxGuesses - guesses) * 5;
                if (maxGuesses - guesses == 0) {
                    coinsEarned = 5;
                }
                coins += coinsEarned;
                System.out.println("Correct! You earned " + coinsEarned + " coins.");
                return coins;
            } else if (guess < secretNumber) {
                System.out.println("Too low.");
            } else {
                System.out.println("Too high.");
            }
        }

        if (guesses == maxGuesses) {
            System.out.println("You ran out of guesses. The number was " + secretNumber);
        }
        return coins;
    }

    // Implements the matching game
    public static int playMatchingGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String hiddenString = generateHiddenString(random);
        String displayedString = hiddenString.replaceAll(".", "X");
        System.out.println(displayedString);

        int coins = 0;
        int guesses = 0;
        int maxGuesses = random.nextInt(10) + 15;

        while (guesses < maxGuesses && displayedString.contains("X")) {
            System.out.print("Enter first position to reveal (0-" + (hiddenString.length() - 1) + "): ");
            int pos1 = scanner.nextInt();
            System.out.print("Enter second position to reveal (0-" + (hiddenString.length() - 1) + "): ");
            int pos2 = scanner.nextInt();

            if (pos1 < 0 || pos1 >= hiddenString.length() || pos2 < 0 || pos2 >= hiddenString.length()) {
                System.out.println("Invalid positions!");
                continue;
            }

            if (displayedString.charAt(pos1) != 'X' || displayedString.charAt(pos2) != 'X') {
                System.out.println("One or both positions are already revealed!");
                continue;
            }

            if (hiddenString.charAt(pos1) == hiddenString.charAt(pos2)) {
                System.out.println("Match!");
                displayedString = displayedString.substring(0, pos1) + hiddenString.charAt(pos1)
                        + displayedString.substring(pos1 + 1);
                displayedString = displayedString.substring(0, pos2) + hiddenString.charAt(pos2)
                        + displayedString.substring(pos2 + 1);

                System.out.println(displayedString);
                coins += 5;
            } else {
                System.out.println("No match!");
                coins -= 1;
            }

            guesses++;
        }

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

    // Generates the hidden string for the matching game
    private static String generateHiddenString(Random random) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            char letter = letters.charAt(random.nextInt(letters.length()));
            builder.append(letter);
            builder.append(letter);
        }

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

    // Increases the pet's energy when playing with it
    public static void playWithPet(int[] currentStats, int maxEnergy) {
        if (currentStats[2] < maxEnergy) {
            currentStats[2]++;
            System.out.println("You played with your pet. Energy increased by 1.");
        } else {
            System.out.println("Your pet is already at maximum energy.");
        }
    }

    // Increases the pet's food when feeding it
    public static void feedPet(int[] currentStats, int maxFood) {
        if (currentStats[1] < maxFood) {
            currentStats[1]++;
            System.out.println("You fed your pet. Food increased by 1.");
        } else {
            System.out.println("Your pet is already full.");
        }
    }

    // Increases the pet's health when grooming it
    public static void groomPet(int[] currentStats, int maxHealth) {
        if (currentStats[0] < maxHealth) {
            currentStats[0]++;
            System.out.println("You groomed your pet. Health increased by 1.");
        } else {
            System.out.println("Your pet is already at maximum health.");
        }
    }
}
