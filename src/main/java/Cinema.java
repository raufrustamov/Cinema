import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    private static final int PRICE_FOR_FIRST_ROWS = 10;
    private static final int PRICE_FOR_OTHER_ROWS = 8;
    private static final int BIG_HALL_CAPACITY = 60;
    private static final int COUNT_OF_THE_FIRST_HALF_ROWS = 5;
    private static final int HALF_ROWS_IN_THE_HALL = 2;

    public static void main(String[] args) {
        // Write your code here
        int purchasedTickets = 0;
        int currentIncome = 0;
        int totalIncome;
        int seat;
        int row;
        int action;

        Scanner console = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        System.out.print("> ");
        int rows = console.nextInt();
        System.out.println("Enter the number of seats in each row:");
        System.out.print("> ");
        int seats = console.nextInt();
        System.out.println();
        totalIncome = totalIncomeCalculation(rows, seats);
        String[][] hall = fillSeatsWithCharacter(rows, seats);


        do {
            printMenu();
            System.out.print("> ");
            action = console.nextInt();
            System.out.println();
            switch (action) {
                case 1:
                    printHallSchema(seats, hall);
                    System.out.println();
                    break;
                case 2:
                    try {
                        do {
                            console = new Scanner(System.in);
                            System.out.println("Enter a row number:");
                            System.out.print("> ");
                            row = console.nextInt();
                            System.out.println("Enter a seat number in that row:");
                            System.out.print("> ");
                            seat = console.nextInt();
                        } while (!seatAvailabilityCheck(hall, row, seat));
                        printTicketPrice(rows, seats, row);
                        updateSchemaAfterPurchase(seat, row, hall);
                        printHallSchema(seats, hall);
                        System.out.println();
                        purchasedTickets++;
                        currentIncome = currentIncome + ticketPriceCalculation(rows, seats, row);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Wrong input!");
                    }
                    break;
                case 3:
                    getStatistics(purchasedTickets, currentIncome, totalIncome, rows, seats);
            }
        } while (action != 0);

    }

    private static void updateSchemaAfterPurchase(int seat, int row, String[][] hall) {
        hall[row - 1][seat - 1] = "B";
    }


    private static void getStatistics(int purchasedTickets, int currentIncome, int totalIncome, int rows, int seats) {
        float purchasedTicketsPercent;
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        float totalPlace = seats * rows;
        purchasedTicketsPercent = purchasedTickets / totalPlace * 100;
        System.out.println("Percentage: " + String.format("%.2f", purchasedTicketsPercent) + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
        System.out.println();
    }


    private static int totalIncomeCalculation(int rows, int seats) {
        int totalIncome = 0;
        if (seats * rows <= BIG_HALL_CAPACITY) {
            totalIncome = seats * rows * PRICE_FOR_FIRST_ROWS;
        } else if (rows % HALF_ROWS_IN_THE_HALL == 0 && seats * rows > BIG_HALL_CAPACITY) {
            totalIncome = (seats * (rows / HALF_ROWS_IN_THE_HALL) * PRICE_FOR_OTHER_ROWS) +
                    (seats * (rows / HALF_ROWS_IN_THE_HALL) * PRICE_FOR_FIRST_ROWS);
        } else if (rows % HALF_ROWS_IN_THE_HALL != 0 && seats * rows > BIG_HALL_CAPACITY) {
            int newRows = rows % HALF_ROWS_IN_THE_HALL;
            totalIncome = (seats * ((rows - newRows) / HALF_ROWS_IN_THE_HALL) * PRICE_FOR_FIRST_ROWS) +
                    (seats * (rows - ((rows - newRows) / HALF_ROWS_IN_THE_HALL)) * PRICE_FOR_OTHER_ROWS);
        }
        return totalIncome;
    }


    private static int ticketPriceCalculation(int rows, int seats, int row) {
        int price = 0;
        if (seats * rows <= BIG_HALL_CAPACITY) {
            price = PRICE_FOR_FIRST_ROWS;
        } else if (rows % HALF_ROWS_IN_THE_HALL == 0 && seats * rows > BIG_HALL_CAPACITY
                && row < COUNT_OF_THE_FIRST_HALF_ROWS) {
            price = PRICE_FOR_FIRST_ROWS;
        } else if (rows % HALF_ROWS_IN_THE_HALL == 0 && seats * rows > BIG_HALL_CAPACITY
                && row > COUNT_OF_THE_FIRST_HALF_ROWS) {
            price = PRICE_FOR_OTHER_ROWS;
        } else if (rows % HALF_ROWS_IN_THE_HALL != 0 && seats * rows > BIG_HALL_CAPACITY) {
            int newRows = rows % HALF_ROWS_IN_THE_HALL;
            if (row <= (rows - newRows) / HALF_ROWS_IN_THE_HALL) {
                price = PRICE_FOR_FIRST_ROWS;
            } else {
                price = PRICE_FOR_OTHER_ROWS;
            }
        }
        return price;
    }

    private static void printTicketPrice(int rows, int seats, int row) {
        if (seats * rows <= BIG_HALL_CAPACITY) {
            System.out.println("Ticket price: " + "$10\n");
        } else if (rows % HALF_ROWS_IN_THE_HALL == 0 && seats * rows > BIG_HALL_CAPACITY
                && row < COUNT_OF_THE_FIRST_HALF_ROWS) {
            System.out.println("Ticket price: " + "$10\n");
        } else if (rows % HALF_ROWS_IN_THE_HALL == 0 && seats * rows > BIG_HALL_CAPACITY
                && row > COUNT_OF_THE_FIRST_HALF_ROWS) {
            System.out.println("Ticket price: " + "$8\n");
        } else if (rows % HALF_ROWS_IN_THE_HALL != 0 && seats * rows > BIG_HALL_CAPACITY) {
            int newRows = rows % HALF_ROWS_IN_THE_HALL;
            if (row <= (rows - newRows) / HALF_ROWS_IN_THE_HALL) {
                System.out.println("Ticket price: " + "$10\n");
            } else {
                System.out.println("Ticket price: " + "$8\n");
            }
        }
    }


    private static String[][] fillSeatsWithCharacter(int rows, int seats) {
        String[][] seatsSchema = new String[rows][seats];
        for (String[] strings : seatsSchema) {
            Arrays.fill(strings, "S");
        }
        return seatsSchema;
    }


    private static void printHallSchema(int seats, String[][] seatsSchema) {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 1; i <= seats; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < seatsSchema.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < seatsSchema[i].length; j++) {
                System.out.print(seatsSchema[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean seatAvailabilityCheck(String[][] seatsSchema, int row, int seat) {
        boolean available = true;
        if (seatsSchema[row - 1][seat - 1].contains("B")) {
            System.out.println("That ticket has already been purchased!\n");
            available = false;
        }
        return available;
    }

    private static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

}



