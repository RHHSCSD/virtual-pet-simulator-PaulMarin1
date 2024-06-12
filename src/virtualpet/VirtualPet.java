package virtualpet;

import java.io.*;
import java.util.*;

public class VirtualPet {

    // Scanner for user input and Random for random number generation
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // Variables for game state
    static int coins = 0;
    static boolean running = true;

    // Arrays for storing max and current stats: 0: Health, 1: Food, 2: Energy
    static int[] maxStats = new int[3];
    static int[] currentStats = new int[3];

    // Arrays for tracking actions and their counts
    static String[] actions = {"Fed", "Played", "Groomed"};
    static int[] actionCount = new int[3];

    // Variables for pet and user details
    static String petType = "";
    static String petName = "";
    static String username = "";
    static String password = "";

    public static void main(String[] args) {
        displaySplashScreen();
        if (!login()) {
            return;
        }
        loadUserData();
        displayMainMenu();
    }

    // Displays the splash screen with the game title
    public static void displaySplashScreen() {
        System.out.println("""
                           .--._.--.
                          ( O     O )
                          /   . .   \\
                         .`._______.'.
                        /(           )\\
                      _/  \\  \\   /  /  \\_
                   .~   `  \\  \\ /  /  '   ~.
                  {    -.   \\  V  /   .-    }
                _ _`.    \\  |  |  |  /    .'_ _
                >_       _} |  |  | {_       _<
                 /. - ~ ,_-'  .^.  `-_, ~ - .\\
                         '-'|/   \\|`-`
                
                             FROG""");
    }

    // login or registration
    public static boolean login() {
        System.out.print("Enter your username: ");
        username = scanner.nextLine();
        File userFile = new File(username + ".txt");

        if (userFile.exists()) {
            // If user exists, prompt for password
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter your password: ");
                password = scanner.nextLine();
                if (checkPassword(userFile, password)) {
                    return true;
                } else {
                    System.out.println("Incorrect password. You have " + (2 - i) + " tries left.");
                }
            }
            System.out.println("Too many incorrect attempts. Goodbye");
            return false;
        } else {
            // If new user, prompt for new password
            System.out.print("Enter a new password: ");
            password = scanner.nextLine();
            return true;
        }
    }

    // Verifies if the entered password matches the stored password
    public static boolean checkPassword(File userFile, String password) {
        try (Scanner fileScanner = new Scanner(userFile)) {
            String storedUsername = fileScanner.nextLine();
            String storedPassword = fileScanner.nextLine();
            return storedPassword.equals(password);
        } catch (FileNotFoundException e) {
            System.out.println("User data not found.");
            return false;
        }
    }

    // Displays the main menu and handles user choices
    public static void displayMainMenu() {
        while (true) {
            System.out.println("\nMENU: \n1. Start \n2. Instructions \n3. Exit");
            System.out.print("Enter the corresponding number to choose an option: ");
            String menuInput = scanner.nextLine().toLowerCase();
            switch (menuInput) {
                case "1":
                case "start":
                    startGame();
                    break;
                case "2":
                case "instructions":
                    System.out.println("Insert instructions here");
                    break;
                case "3":
                case "exit":
                    displayActionSummary();
                    saveUserData();
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    // Starts the game, initializing a new pet if necessary
    public static void startGame() {
        if (!new File(username + ".txt").exists()) {
            String[] petInfo = Helper.petSelection();
            petType = petInfo[0];
            petName = petInfo[1];
            String[] statsStr = petInfo[2].replace("[", "").replace("]", "").split(", ");
            for (int i = 0; i < statsStr.length; i++) {
                maxStats[i] = Integer.parseInt(statsStr[i]);
                currentStats[i] = maxStats[i] - 2; // Set current stats to max stats minus 2
            }
        }

        while (running) {
            displayPetMenu();
        }
    }

    // Displays the pet menu 
    public static void displayPetMenu() {
        System.out.println("\nPET MENU: \n1. Play/Interact \n2. Coin Balance \n3. Exit");
        System.out.print("Enter the corresponding number to choose an option: ");
        int petMenuChoice = scanner.nextInt();
        switch (petMenuChoice) {
            case 1:
                displayGameMenu();
                break;
            case 2:
                System.out.println("You have " + coins + " coins!");
                break;
            case 3:
                displayActionSummary();
                saveUserData();
                System.out.println("Goodbye! See you soon!");
                System.exit(0);
            default:
                System.out.println("Please enter a valid input");
        }
    }

    // Displays the game menu 
    public static void displayGameMenu() {
        System.out.print("\nGAME MENU: \n1. Number Guessing Game \n2. Matching Game\n3. Play with Pet\n4. Feed Pet\n5. Groom Pet\nEnter the corresponding number to choose an option: ");
        int gameMenuChoice = scanner.nextInt();
        switch (gameMenuChoice) {
            case 1:
                coins += Helper.playNumberGuessingGame();
                reducePetStats();
                break;
            case 2:
                coins += Helper.playMatchingGame();
                reducePetStats();
                break;
            case 3:
                if (currentStats[2] < maxStats[2]) {
                    Helper.playWithPet(currentStats, maxStats[2]);
                    actionCount[1]++;
                } else {
                    System.out.println("Your pet's energy is already at maximum.");
                }
                displayCurrentStats();
                break;
            case 4:
                if (currentStats[1] < maxStats[1]) {
                    Helper.feedPet(currentStats, maxStats[1]);
                    actionCount[0]++;
                } else {
                    System.out.println("Your pet's food is already at maximum.");
                }
                displayCurrentStats();
                break;
            case 5:
                if (currentStats[0] < maxStats[0]) {
                    Helper.groomPet(currentStats, maxStats[0]);
                    actionCount[2]++;
                } else {
                    System.out.println("Your pet's health is already at maximum.");
                }
                displayCurrentStats();
                break;
            default:
                System.out.println("Invalid choice. Returning to pet menu.");
        }
    }

    // Reduces pet stats after each game
    public static void reducePetStats() {
        currentStats[0] = Math.max(0, currentStats[0] - 1); // Decrease health
        currentStats[1] = Math.max(0, currentStats[1] - 1); // Decrease food
        currentStats[2] = Math.max(0, currentStats[2] - 1); // Decrease energy

        System.out.println("Your pet's current stats after the game:");
        System.out.println("Health: " + currentStats[0]);
        System.out.println("Food: " + currentStats[1]);
        System.out.println("Energy: " + currentStats[2]);
    }

    // Displays the current stats of the pet
    public static void displayCurrentStats() {
        System.out.println("Current stats of your pet:");
        System.out.println("Health: " + currentStats[0] + "/" + maxStats[0]);
        System.out.println("Food: " + currentStats[1] + "/" + maxStats[1]);
        System.out.println("Energy: " + currentStats[2] + "/" + maxStats[2]);
    }

    // Displays a summary of actions performed during the game session
    public static void displayActionSummary() {
        System.out.println("\nYou have:");
        for (int i = 0; i < actions.length; i++) {
            System.out.println(actions[i] + " your pet " + actionCount[i] + " times");
        }
        if (actionCount[0] >= 20) {
            System.out.println("Congratulations you have earned Avid Eater!!");
        }
        if (actionCount[1] >= 20) {
            System.out.println("Congratulations you have earned Play Master!!");
        }
        if (actionCount[2] >= 20) {
            System.out.println("Congratulations you have earned Grooming Expert!!");
        }
    }

    // Saves user data to a file
    public static void saveUserData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(username + ".txt"))) {
            writer.println(username);
            writer.println(password);
            writer.println(petName);
            writer.println(petType);
            for (int stat : maxStats) {
                writer.println(stat);
            }
            for (int stat : currentStats) {
                writer.println(stat);
            }
            writer.println(coins);
            for (int count : actionCount) {
                writer.println(count);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving user data.");
        }
    }

    // Load data from a file
    public static void loadUserData() {
        File file = new File(username + ".txt");
        if (!file.exists()) {
            System.out.println("No existing user data found. Starting a new game.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            username = fileScanner.nextLine();
            password = fileScanner.nextLine();
            petName = fileScanner.nextLine();
            petType = fileScanner.nextLine();
            for (int i = 0; i < maxStats.length; i++) {
                maxStats[i] = fileScanner.nextInt();
            }
            for (int i = 0; i < currentStats.length; i++) {
                currentStats[i] = fileScanner.nextInt();
            }
            coins = fileScanner.nextInt();
            for (int i = 0; i < actionCount.length; i++) {
                actionCount[i] = fileScanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.out.println("User data not found. Starting a new game.");
        } catch (Exception e) {
            System.out.println("An error occurred while loading user data.");
        }
    }
}
