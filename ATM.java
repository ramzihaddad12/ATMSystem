import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank("Chase");

        User boss = bank.addUser("Tony", "Soprano", "1234");
        Account newAccount = new Account("checking", boss, bank);
        boss.addAccount(newAccount);
        bank.addAccount(newAccount);

        //login prompt
        User curUser;
        while(true){
            //stay in the prompt until login is a success
            curUser = ATM.mainMenuPrompt(bank, sc);

            //stay in menu until user quits
            ATM.printUserMenu(curUser, sc);

        }


    }

    public static User mainMenuPrompt(Bank bank, Scanner sc){
        String userID;
        String pin;
        User authUser;

        //prompt the user for userID and pin

        do {
            System.out.printf("\n\n Welcome to %s \n\n", bank.getName());
            System.out.print("Please enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Please enter your pin: ");
            pin = sc.nextLine();

            //get User object from pin & userID
            authUser = bank.userLogin(userID, pin);
            if (authUser == null){
                System.out.println("Sorry, incorrect userID/pin. Please try again");
            }
        }while (authUser == null);

        return authUser;

    }

    public static void printUserMenu(User user, Scanner sc){
        //print user's accounts
        user.printAccountsSummary();

        int choice;
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", user.getFirstName());
            System.out.println("1) Show account transaction history");
            System.out.println("2) Withdraw");
            System.out.println("3) Deposit");
            System.out.println("4) Transfer");
            System.out.println("5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice. Please choose 1-5");
            }
        }while(choice < 1 || choice > 5);

        // process choice
        switch (choice){
            case 1:
                ATM.showTransHistory(user, sc);
                break;

            case 2:
                ATM.withdrawFunds(user, sc);
                break;

            case 3:
                ATM.depositFunds(user, sc);
                break;

            case 4:
                ATM.transferFunds(user, sc);
                break;

            case 5:
                sc.nextLine();
                break;

        }

        //redisplay menu unless user wants to quit (case 5)
        if (choice != 5){
            ATM.printUserMenu(user, sc);
        }

    }

    public static void showTransHistory(User user, Scanner sc){
        int theAcct;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + " whose transactions you want to see: ",user.numAccounts());
            theAcct = sc.nextInt() - 1;

            if (theAcct < 0 || theAcct >= user.numAccounts()){
                System.out.println("Invalid account. Please try again. ");
            }

        }while (theAcct < 0 || theAcct >= user.numAccounts());

        user.printAcctTranHistory(theAcct);
    }

    public static void transferFunds(User user, Scanner sc){
        int fromAccount;
        int toAccount;
        double amount;
        double acctBal; //of fromAccount

        //get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + " to transfer from: ",user.numAccounts());
            fromAccount = sc.nextInt() - 1;

            if (fromAccount < 0 || fromAccount >= user.numAccounts()){
                System.out.println("Invalid account. Please try again. ");
            }

        }while (fromAccount < 0 || fromAccount >= user.numAccounts());

        acctBal = user.getAcctBalance(fromAccount);

        //get account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + " to transfer to: ",user.numAccounts());
            toAccount = sc.nextInt() - 1;

            if (toAccount < 0 || toAccount >= user.numAccounts()){
                System.out.println("Invalid account. Please try again. ");
            }

        }while (toAccount < 0 || toAccount >= user.numAccounts());

        //get amount to transfer
        do {
            System.out.printf("Enter the amount to transfer: (max $%.02f)", acctBal);
            amount = sc.nextDouble();

            if (amount < 0){
                System.out.println("Amount should be greater than 0");
            }
            else if (amount > acctBal){
                System.out.printf("The amount you entered is greater than \n" + "balance of $%.02f. \n", acctBal);
            }
        }while (amount < 0 || amount > acctBal);

        //do the transfer
        user.addAcctTransaction(fromAccount, -1*amount, String.format("Transfer to account %s", user.getAcctUUID(toAccount)));
        user.addAcctTransaction(toAccount, amount, String.format("Transfer from account %s", user.getAcctUUID(fromAccount)));
    }

    public static void withdrawFunds(User user, Scanner sc){

        int fromAccount;
        double amount;
        double acctBal; //of fromAccount
        String memo;

        //get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + " to withdraw from: ",user.numAccounts());
            fromAccount = sc.nextInt() - 1;

            if (fromAccount < 0 || fromAccount >= user.numAccounts()){
                System.out.println("Invalid account. Please try again. ");
            }

        }while (fromAccount < 0 || fromAccount >= user.numAccounts());

        acctBal = user.getAcctBalance(fromAccount);

        //get amount to transfer
        do {
            System.out.printf("Enter the amount to withdraw: (max $%.02f)", acctBal);
            amount = sc.nextDouble();

            if (amount < 0){
                System.out.println("Amount should be greater than 0");
            }
            else if (amount > acctBal){
                System.out.printf("The amount you entered is greater than \n" + "balance of $%.02f. \n", acctBal);
            }
        }while (amount < 0 || amount > acctBal);

        sc.nextLine();

        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdrawal
        user.addAcctTransaction(fromAccount, -1*amount, memo );
    }

    public static void depositFunds(User user, Scanner sc){


        int toAccount;
        double amount;
        double acctBal; //of fromAccount
        String memo;

        //get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + " to deposit in: ",user.numAccounts());
            toAccount = sc.nextInt() - 1;

            if (toAccount < 0 || toAccount >= user.numAccounts()){
                System.out.println("Invalid account. Please try again. ");
            }

        }while (toAccount < 0 || toAccount >= user.numAccounts());

        acctBal = user.getAcctBalance(toAccount);

        //get amount to transfer
        do {
            System.out.printf("Enter the amount to transfer: (max $%.02f)", acctBal);
            amount = sc.nextDouble();

            if (amount < 0){
                System.out.println("Amount should be greater than 0");
            }
        }while (amount < 0);

        sc.nextLine();

        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdrawal
        user.addAcctTransaction(toAccount, amount, memo );

    }
}
