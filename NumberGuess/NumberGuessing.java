

import java.util.Scanner;
import java.util.Random;

public class NumberGuessing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String playAgain = null;
        int totalGames = 0;
        int totalWins = 0;

        System.out.println("Welcome to the Advanced Number Guessing Game!");

        do {
            // User sets the range
            System.out.println("Enter the lower bound of the guessing range:");
            int lowerBound = getValidInput(scanner);

            System.out.println("Enter the upper bound of the guessing range:");
            int upperBound = getValidInput(scanner);

            if (lowerBound >= upperBound) {
                System.out.println("Invalid range! Upper bound must be greater than lower bound. Try again.");
                continue;
            }

            int number = random.nextInt(upperBound - lowerBound + 1) + lowerBound;  // Random number in user-defined range
            int attempts = getDifficultyLevel(scanner);  // Get attempts based on chosen difficulty
            boolean win = false;

            totalGames++;

            System.out.println("Guess the number (between " + lowerBound + " and " + upperBound + "):");

            while (attempts > 0) {
                System.out.println("You have " + attempts + " attempts remaining. Enter your guess:");
                int guess = getValidInput(scanner);

                if (guess > number) {
                    System.out.println("Too high!");
                } else if (guess < number) {
                    System.out.println("Too low!");
                } else {
                    System.out.println("Congratulations! You guessed the number!");
                    win = true;
                    totalWins++;
                    break;
                }
                attempts--;
            }

            if (!win) {
                System.out.println("You've run out of attempts! The number was: " + number);
            }

            System.out.println("Do you want to play again? (yes/no)");
            playAgain = scanner.next();
        } while (playAgain.equalsIgnoreCase("yes"));

        // Display final score
        System.out.println("Thank you for playing!");
        System.out.println("Games played: " + totalGames + ", Games won: " + totalWins);
        System.out.printf("Win percentage: %.2f%%\n", (totalWins * 100.0 / totalGames));

        scanner.close();
    }

    // Get a valid integer input from the user
    private static int getValidInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid integer:");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // Get difficulty level and return number of attempts based on choice
    private static int getDifficultyLevel(Scanner scanner) {
        System.out.println("Choose a difficulty level:");
        System.out.println("1. Easy (10 attempts)");
        System.out.println("2. Medium (7 attempts)");
        System.out.println("3. Hard (5 attempts)");

        int difficulty = getValidInput(scanner);

        switch (difficulty) {
            case 1:
                return 10;
            case 2:
                return 7;
            case 3:
                return 5;
            default:
                System.out.println("Invalid choice! Defaulting to 'Medium' difficulty.");
                return 7;
        }
    }
}