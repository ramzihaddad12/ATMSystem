import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String uuid;//id
    private byte pinHash[];// MD5 hash of the user pin instead of the actual pin for security reasons
    private ArrayList<Account> accounts;// user accounts

    public User(String firstName, String lastName, String pin, Bank bank){
        this.firstName = firstName;
        this.lastName = lastName;

        //MD5 hashing of pin
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error caught: NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //generate new and unique uuid for the user
        this.uuid = bank.getNewUserUUID();

        //accounts
        this.accounts = new ArrayList<Account>();

        //printing
        System.out.printf("New user %s, %s with ID %s is created. \n", lastName, firstName, this.uuid);
    }

    public void addAccount(Account newAccount){
        this.accounts.add(newAccount);
    }

    public String getUUID(){
        return this.uuid;
    }

    public boolean validatePin(String aPin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error caught: NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountsSummary(){
        System.out.printf("\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++){
            System.out.printf("%d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public String getFirstName(){
        return this.firstName;
    }

    public int numAccounts(){
        return this.accounts.size();
    }

    public void printAcctTranHistory(int aactIndex){
        this.accounts.get(aactIndex).printTransHistory();
    }

    public double getAcctBalance(int aactIndex){
        return this.accounts.get(aactIndex).getBalance();
    }

    public String getAcctUUID(int aactIndex){
        return this.accounts.get(aactIndex).getUUID();
    }

    public void addAcctTransaction(int aactIndex, double amount, String memo){
        this.accounts.get(aactIndex).addTransaction(amount, memo);
    }




}
