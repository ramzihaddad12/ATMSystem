import java.util.Date;

public class Transaction {
    private double amount;
    private Date timeStamp; //time of Transaction
    private String memo; // memo of Transaction
    private Account inAccount; //Account which belongs to the Transaction

    public Transaction(double amount, Account inAccount){//constructor without a memo
        this.amount = amount;
        this.inAccount = inAccount;
        this.timeStamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, Account inAccount, String memo){//constructor with a memo
        this(amount, inAccount); //making use of 1st constructor
        this.memo = memo;
    }

    public double getAmount(){
        return this.amount;
    }

    public String getSummaryLine(){
        if (this.amount >= 0){
            return String.format("%s : $%.02f : %s", this.timeStamp.toString(), this.amount, this.memo);
        }
        else{//withdraw
            return String.format("%s : -$%.02f : %s", this.timeStamp.toString(), this.amount, this.memo);
        }
    }

}
