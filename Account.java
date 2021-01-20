import java.util.ArrayList;

public class Account {
    private String name; //checking or saving or..
    private String uuid; //different from User uuid
    private User holder; //account holder
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank bank){//constructor
        this.name = name;
        this.holder = holder;
        this.transactions = new ArrayList<Transaction>();

        //generate new and unique uuid for the user
        this.uuid = bank.getNewAccountUUID();


    }

    public String getUUID(){
        return this.uuid;
    }

    public String getSummaryLine(){
        //get account balance
        double balance = this.getBalance();

        //format summaryline, depending on whether balance is negative or positive
        if (balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }
        else{
            return String.format("%s : ($%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    public double getBalance(){
        //loop over all transactions and add ammounts
        double balance = 0;

        for (Transaction t: this.transactions){
            balance += t.getAmount();
        }

        return balance;

    }

    public void printTransHistory(){
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size() - 1; t >= 0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo){
        //create new transaction object and add it to list
        Transaction newTransaction = new Transaction(amount, this, memo);
        this.transactions.add(newTransaction);


    }



}
