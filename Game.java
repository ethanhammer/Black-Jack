//Ethan Hammer

import java.util.*;

public class Game {

    Scanner scan = new Scanner(System.in);

    ArrayList < Player > players = new ArrayList < Player > ();
    Deck gameDeck = new Deck();
    Dealer gameDealer = new Dealer();


    public void startGame() {

        System.out.println("\033[0;1m" + "WELCOME TO THE TABLE!!\n");
        int numPlayers = 0;

        do {
            try {

                System.out.println("How many players? (1-10)!");

                numPlayers = scan.nextInt();

                resetScreen();

            } catch (InputMismatchException e) {
                scan.next();
                System.out.println("Please type a number");
            }
        } while (numPlayers < 1 || numPlayers > 10);

        createPlayers(numPlayers);
        playGame();
    }

    public void playGame() {
        do {
            getBets();
            gameDeck.resetDeck();
            gameDeck.shuffleDeck();
            assignCards();
            getInput();
            dealerDraw();
            decideWinners();
        } while (keepGameGoing());
    }
  
    public void deleteLosersAndResetCards() {
        resetScreen();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).resetCards();
            if (players.get(i).getBal() == 0) {
                System.out.println("Player " + (i + 1) + " Got removed for being a broke bitch");
                players.remove(i);
                promptToContinue();
            }
        }
    }

    public boolean keepGameGoing() {
        System.out.println("Would you like to continue with the same game? (y/n)");
        String input = scan.next();
        if (input.equals("y")) {
            deleteLosersAndResetCards();
            return true;
        } else {
            return false;
        }
    }

    public void getBets() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + (i + 1) + " How much would you like to bet? Bal: " + players.get(i).getBal());

            try {
                int bet = scan.nextInt();
                if (players.get(i).getBal() < bet) {
                    resetScreen();
                    System.out.println("YOURE TOO BROKE\n");
                    i -= 1;
                    continue;
                } else {
                    players.get(i).setBet(bet);
                    players.get(i).setBal(players.get(i).getBal() - bet);
                    resetScreen();
                    promptToContinue();
                }
            } catch (InputMismatchException e) {
                scan.next();
                System.out.println("Not a valid number");
                i -= 1;
                continue;
            }
        }
    }

    public void createPlayers(int num) {
        for (int i = 0; i < num; i++) {
            players.add(new Player(i));
        }
    }

    public void getInput() {
        for (int i = 0; i < players.size(); i++) {
            showOptions(i);
            String input = scan.next().toLowerCase();
            if (players.get(i).checkBusted()) {
                displayBusted(i + 1);
                players.get(i).setBusted(true);
                input = "busted";
            }
            if (input.equals("hit")) {
                while (input.equals("hit")) {
                    players.get(i).addCard(gameDeck.randomCard());
                    resetScreen();
                    if (players.get(i).checkBusted()) {
                        displayBusted(i + i);
                        input = "busted";
                        break;
                    }
                    showOptions(i);
                    input = scan.next().toLowerCase();
                }
            } else if (input.equals("stand")) {
            } else if (!input.equals("stand") && !input.equals("busted")) {
                i -= 1;
                resetScreen();
                System.out.println("PLEASE ENTER A VALID RESPONSE!\n");
                continue;
            } else {
                resetScreen();
                System.out.println("PLEASE INSERT A VALID WORD");
                i -= 1;
                continue;
            }
            resetScreen();
            promptToContinue();
        }
    }

    public void dealerDraw() {
        if (gameDealer.getValueOfCards() < 17) {
            System.out.println("The dealer's hand:\n");
            gameDealer.printCards();
            System.out.println("\n\nThe dealer's hand does not total to 17, so he must draw");
            promptToContinue();
            resetScreen();
            gameDealer.addCard(gameDeck.randomCard());
            System.out.println("The dealer's hand:\n");
            gameDealer.printCards();
            dealerDraw();
        } else if (gameDealer.getValueOfCards() > 17) {
            resetScreen();
            System.out.println("The dealer's hand:\n");
            gameDealer.printCards();
            System.out.println("\n\nThe dealer's hand totals to 17, so he does not need to draw");
            promptToContinue();
        }
    }

    public void decideWinners() {
        System.out.println("THE DEALERS FINAL CARDS WERE: ");
        gameDealer.printCards();
        System.out.println("\n");
        for (int i = 0; i < players.size(); i++) {
            if (gameDealer.getValueOfCards() > 21) {
                if (players.get(i).checkBusted()) {
                    players.get(i).printCards();
                    System.out.println("Player " + (i + 1) + " BUSTED and now has " + players.get(i).getBal());
                    players.get(i).setBet(0);
                } else {
                    players.get(i).printCards();
                    players.get(i).updateWinnings();
                    players.get(i).setBet(0);
                    System.out.println("Player " + (i + 1) + " Beat the dealer, who busted, and now has " + players.get(i).getBal());
                }
                players.get(i).updateWinnings();
            } else if (players.get(i).getValueOfCards() > 21) {
                players.get(i).printCards();
                players.get(i).setBet(0);
                System.out.println("Player " + (i + 1) + " BUSTED and now has " + players.get(i).getBal());
            } else if (players.get(i).getValueOfCards() > gameDealer.getValueOfCards()) {
                players.get(i).printCards();
                players.get(i).updateWinnings();
                players.get(i).setBet(0);
                System.out.println("Player " + (i + 1) + " BEAT THE DEALER and now has " + players.get(i).getBal());
            } else {
                players.get(i).printCards();
                players.get(i).setBet(0);
                System.out.println("Player " + (i + 1) + " LOST TO THE DEALER and now has " + players.get(i).getBal());
            }
        }
        promptToContinue();
    }


    public void showOptions(int num) {
        gameDealer.showFirst();
        System.out.println("\nPlayer " + (num + 1) + " Would you like to hit or stand");
        System.out.println("\nYour cards are:");
        players.get(num).printCards();
        System.out.println("");
    }
  
    public void assignCards() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).addCard(gameDeck.randomCard());
            players.get(i).addCard(gameDeck.randomCard());
        }
        gameDealer.addCard(gameDeck.randomCard());
        gameDealer.addCard(gameDeck.randomCard());
    }

    public void promptToContinue() {
        System.out.println("Press y to continue");
        if (scan.next().equals("y")) {
            resetScreen();
        } else {
            System.out.println("NOT AN ANSWER");
            promptToContinue();
        }
    }

    public void displayBusted(int player) {
        System.out.println("Player " + (player + 1) + " busted!! cards:");
        players.get(player).printCards();
    }
  
    public void resetScreen() {
        System.out.print("\033[H\033[2J");
    }

}