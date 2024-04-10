/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package virtualpet;
import java.util.*;
/**
 *
 * 
 */
public class VirtualPet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       
        
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
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter username: ");
            String username = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();

            if (username.equals("snoopy") && password.equals("toto")) {
                System.out.println("Login successful!");
                break;
            } else {
                attempts++;
                System.out.println("Incorrect login. Attempts remaining: " + (3 - attempts));
            }
        }

        if (attempts == 3) {
            System.out.println("Too many failed login attempts. Exiting.");
            System.exit(0);
          }

        
        System.out.println("""
                           1. Start
                           2. Instructions
                           3. Exit""");
        String choice  = scanner.next().toLowerCase();
        switch (choice) {
                case "1":
                case "start":
                    helper.petSelection();
                    break; 
                case "2":
                case "instructions":
                    System.out.println("Instructions displayed here...");
                    break; 
                case "3":
                case "exit": 
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0); 
                default:
                    System.out.println("Invalid choice");
               }

        }
                                        
    }
  
