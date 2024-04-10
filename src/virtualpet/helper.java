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
class helper {
    private static String generateRandomName() {
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
 
        private static void assignPetStats() {
        Random random = new Random();
        int totalPoints = 20;
        int maxHealth, maxFood, maxEnergy;

        maxHealth = random.nextInt(6) + 3;  
        totalPoints -= maxHealth;
        maxFood = random.nextInt(totalPoints) + 1; 
        totalPoints -= maxFood;
        maxEnergy = totalPoints; 

        System.out.println("Your pet's stats:");
        System.out.println("Health: " + maxHealth);
        System.out.println("Food: " + maxFood);
        System.out.println("Energy: " + maxEnergy);
    }
        

        
        public static void petSelection() {
            Scanner scanner = new Scanner(System.in);
            String petType = "";
            System.out.println("What kind of pet would you like?");
            System.out.println("1. Dog");
            System.out.println("2. Cat");
            System.out.println("3. Bird");

            String petChoice = scanner.next().toLowerCase();

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
            System.out.println("You have chosen " + petType);
            System.out.println("Would you like to: ");
            System.out.println("1. Enter a name");
            System.out.println("2. Generate a random name");
            String namingMethod = scanner.next().toLowerCase();

            if (namingMethod.equals("1") || namingMethod.equals("enter a name")) {
                System.out.print("Enter the name: ");
                String petName = scanner.next();
                System.out.println("Your pet, named " + petName + ", has been born!");
            } else if (namingMethod.equals("2") || namingMethod.equals("generate a random name")) {
                String petName = generateRandomName();
                System.out.println("Your pet, named " + petName + ", has been born!");
            } else {
                System.out.println("Invalid choice");
            }
            assignPetStats();
            }  
        }
    

