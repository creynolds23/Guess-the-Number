import java.util.*;

// Create an interface for the game
interface Game {
    void play();
}

enum Difficulty {
    EASY(1, 50, 10),   // Range: 1-50, Attempts: 10
    MEDIUM(1, 100, 10), // Range: 1-100, Attempts: 10
    HARD(1, 200, 5);   // Range: 1-200, Attempts: 5

    private final int lowerBound;
    private final int upperBound;
    private final int numberOfAttempts;

    Difficulty(int lowerBound, int upperBound, int numberOfAttempts) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.numberOfAttempts = numberOfAttempts;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }
}

class NumberGuessingGame implements Game {
    private int lowerBound;          // Minimum number in the range
    private int upperBound;          // Maximum number in the range
    private int numberOfAttempts;    // Number of attempts allowed
    private int targetNumber;        // The random number to guess
    private int attempts;            // Number of attempts made

    public NumberGuessingGame(Difficulty difficulty) {
        this.lowerBound = difficulty.getLowerBound();
        this.upperBound = difficulty.getUpperBound();
        this.numberOfAttempts = difficulty.getNumberOfAttempts();
        Random random = new Random();
        targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound; // Generate a random number
        attempts = 0;
    }

    @Override
    public void play() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> guesses = new ArrayList<>();
        System.out.println("I have selected a number between " + lowerBound + " and " + upperBound + ".");
        System.out.println("You have " + numberOfAttempts + " attempts to guess it.");
        System.out.println("Additionally, you can type 'quit' at any time to exit the game.");
        System.out.println("------------------------------"); // Separator

        boolean hasGuessedCorrectly = false;

        while (attempts < numberOfAttempts) {
            System.out.print("Attempt #" + (attempts + 1) + ": Enter your guess: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("You quit the game. The correct number was: " + targetNumber);
                return; // Return from the play() method if the user chooses to quit
            }

            int userGuess;
            try {
                userGuess = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'quit' to exit.");
                continue;
            }

            if (userGuess < lowerBound || userGuess > upperBound) {
                System.out.println("Your guess is out of the valid range.");
                continue;
            }

            attempts++;
            guesses.add(userGuess);

            if (userGuess == targetNumber) {
                hasGuessedCorrectly = true;
                break;
            } else if (userGuess < targetNumber) {
                System.out.println("Try a higher number.");
            } else {
                System.out.println("Try a lower number.");
            }
        }

        if (hasGuessedCorrectly) {
            System.out.println("Congratulations! You guessed the correct number: " + targetNumber);
        } else {
            System.out.println("Sorry, you've run out of attempts. The correct number was: " + targetNumber);
        }

        System.out.println("Your guesses: " + guesses.toString());
        System.out.println("------------------------------"); // Separator
    }

    public int getAttempts() {
        return attempts;
    }
}

class HighScoreManager {
    private Map<Difficulty, Integer> highScores = new HashMap<>();

    public void updateHighScore(Difficulty difficulty, int score) {
        int currentHighScore = highScores.getOrDefault(difficulty, Integer.MAX_VALUE);
        if (score < currentHighScore) {
            highScores.put(difficulty, score);
            System.out.println("New high score for " + difficulty + " level: " + score);
        }
    }

    public void displayHighScores() {
        System.out.println("High Scores:");
        for (Map.Entry<Difficulty, Integer> entry : highScores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("------------------------------"); // Separator
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HighScoreManager highScoreManager = new HighScoreManager();

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Play");
            System.out.println("2. View High Scores");
            System.out.println("3. Quit");
            System.out.println("------------------------------"); // Separator
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please select a valid option.");
                continue; // Continue the loop to re-enter a valid choice
            }

            switch (choice) {
                case 1:
                    // Play the game
                    System.out.println("Choose a difficulty level:");
                    System.out.println("1. Easy");
                    System.out.println("2. Medium");
                    System.out.println("3. Hard");
                    System.out.println("------------------------------"); // Separator
                    System.out.print("Enter your choice: ");
                    int difficultyChoice;
                    try {
                        difficultyChoice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid choice. Please select a valid option.");
                        continue; // Continue the loop to re-enter a valid choice
                    }

                    Difficulty selectedDifficulty;
                    switch (difficultyChoice) {
                        case 1:
                            selectedDifficulty = Difficulty.EASY;
                            break;
                        case 2:
                            selectedDifficulty = Difficulty.MEDIUM;
                            break;
                        case 3:
                            selectedDifficulty = Difficulty.HARD;
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            continue; // Continue the loop to re-enter a valid choice
                    }

                    NumberGuessingGame game = new NumberGuessingGame(selectedDifficulty);
                    game.play();

                    // Update and display high scores
                    highScoreManager.updateHighScore(selectedDifficulty, game.getAttempts());
                    break;

                case 2:
                    // View High Scores
                    highScoreManager.displayHighScores();
                    break;

                case 3:
                    // Quit the program
                    System.out.println("Thanks for playing!");
                    scanner.close(); // Close the scanner when the program is finished
                    System.exit(0); // Exit the program
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
