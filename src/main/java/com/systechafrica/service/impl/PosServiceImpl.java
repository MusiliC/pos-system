package com.systechafrica.service.impl;

import com.systechafrica.exception.InsufficientAmountException;
import com.systechafrica.exception.OrderNotFoundException;
import com.systechafrica.model.Order;
import com.systechafrica.model.Payment;
import com.systechafrica.model.Product;
import com.systechafrica.service.AuthenticationService;
import com.systechafrica.service.PaymentService;
import com.systechafrica.service.PosService;
import com.systechafrica.util.Config;
import com.systechafrica.util.InputSanitizer;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

public class PosServiceImpl implements PosService {

    private Logger logger;

    private AuthenticationService authenticationService;
    private PaymentService paymentService;

    Scanner scanner;

    private Order order;

    private double totalCost=0.0;
    private double change=0.0;

    public PosServiceImpl(Logger logger) {
        scanner = new Scanner(System.in);
        this.logger = logger;
        authenticationService = new AuthenticationServiceImpl();
        order = new Order();
        paymentService = new PaymentServiceImpl(logger);
    }

    @Override
    public void startApplication() {
        logger.info("In the PosServiceImpl ");

        try {
            //? authenticate use
            boolean isLoggedIn = authenticateUser();
            logger.info("User authenticated: " + isLoggedIn);
            System.out.println();
            //? show menu
            boolean keepShowingMenu = true;
            while (keepShowingMenu) {
                showMenu();
                //? prompt the user for the option
                System.out.print("Enter your option: ");
                String input = scanner.nextLine();
                int option = InputSanitizer.sanitizeStringToInt(input, logger);

                // handle the option
                switch (option) {
                    case 1:
                        addItems();
                        break;

                    case 2:
                        makePayment();
                        break;

                    case 3:
                        printReceipt();
                        break;

                    case 4:
                        keepShowingMenu = false;
                        break;

                    default:
                        logger.severe("Please enter a valid option: (1-4)\n");
                        break;
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
        }


    }

    private void printReceipt() {
        if (order.getOrderId() != null) {
            logger.info("Display receipt: ");
            displayItems();
            System.out.println("Total Cost: " + totalCost);
            System.out.println("Change: " + totalCost);
            System.out.println("Thank you for shopping with us: " + totalCost);

        }else {
            throw new OrderNotFoundException("Oops!! You don't have any paid order at the moment");
        }
    }

    private void makePayment() {
        //? calculate the order total

        List<Product> products = order.getProducts();

        for (Product product : products) {
            totalCost += product.getPrice() * product.getQuantity();
        }
        displayItems();
        System.out.println("---------------------------------------------");
        System.out.println(" Total Cost: " + totalCost);
        System.out.print("Enter you value: ");
        double amountFromUser = InputSanitizer.sanitizeStringToDouble(scanner.nextLine(), logger);

        //? calculate the change
        if (amountFromUser >= totalCost) {
            // amount is enough for payment
            change = amountFromUser - totalCost;
            //? assign order id
            order.setOrderId(UUID.randomUUID().toString());
        }else{
            throw new InsufficientAmountException("your amount "+amountFromUser+" is not sufficient for clearing the cost of "+totalCost);
        }
        System.out.println("Your change is: "+change);

        //? proceed save the payment
        // orderId, total Amount
        Payment payment = new Payment(order.getOrderId() ,totalCost, LocalDate.now());
        paymentService.savePayment(payment);


    }

    private void displayItems() {
        System.out.println("Item              quantity    price     Sub Total");
        order.getProducts()
                .forEach(product -> {
                    double subTotal = product.getQuantity() * product.getPrice();
                    System.out.println(product.getProductName() + "              " + product.getQuantity() + "    " + product.getPrice() + "     " + subTotal);
                });
    }

    private void addItems() {
        boolean keepAddingItem = true;
        while (keepAddingItem) {
            //? prompt for the item details
            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();

            System.out.print("Enter product quantity: ");
            int quantity = InputSanitizer.sanitizeStringToInt(scanner.nextLine(), logger);

            System.out.print("Enter product price: ");
            double unitPrice = InputSanitizer.sanitizeStringToDouble(scanner.nextLine(), logger);

            Product product = new Product(productName, quantity, unitPrice);

            order.addItem(product);

            //? prompt do you want to add another item
            System.out.print("Do you want to enter another item(Y/N): ");
            String addOthers = scanner.nextLine();
            if (!addOthers.equalsIgnoreCase("Y")) {
                keepAddingItem = false;
            }
        }

    }

    private void showMenu() {
        System.out.println("---------------");
        System.out.println();
        System.out.println("SYSTECH POS SYSTEM");
        System.out.println();
        System.out.println("---------------");
        System.out.println();
        System.out.println("1. ADD ITEM");
        System.out.println("2. MAKE PAYMENT");
        System.out.println("3. DISPLAY RECIEPT");
        System.out.println("4. QUIT");
        System.out.println();
    }

    private boolean authenticateUser() throws SQLException, ClassNotFoundException {
        logger.info("Authenticating the user");

        int loginEntries = 1;
        while (loginEntries <= Config.LOGIN_RETRIES_LIMIT) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            if (authenticationService.authenticate(username, password)) {
                return true;
            }
            loginEntries++;
            logger.info("user enetered wrong credentials!!!");
            System.out.println();
        }
        return false;


    }
}
