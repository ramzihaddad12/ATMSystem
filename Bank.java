import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users; //list of customers/users
    private ArrayList<Account> accounts; //list of accounts

    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID(){
        //generate random uuid
        String uuid;
        Random rng = new Random();
        int len = 6; // number of chars

        // keep looping until we get a uniqueID
        boolean nonUnique = false;
        do{
            //generate uuid
            uuid ="";
            for (int c = 0; c < len; c++){
                uuid += ((Integer) rng.nextInt(10)).toString();
            }
            //check if unique
            for (User u : this.users){
                if(uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }
    public String getNewAccountUUID(){
        //generate random uuid
        String uuid;
        Random rng = new Random();
        int len = 10; // number of chars

        // keep looping until we get a uniqueID
        boolean nonUnique = false;
        do{
            //generate uuid
            uuid ="";
            for (int c = 0; c < len; c++){
                uuid += ((Integer) rng.nextInt(10)).toString();
            }
            //check if unique
            for (Account a : this.accounts){
                if(uuid.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    public void addAccount(Account newAccount){
        this.accounts.add(newAccount);
    }

    public User addUser(String firstName, String lastName, String pin){
        //create a new user and add it to the list of users

        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //create an account for the new user
        Account newAccount = new Account("savings", newUser, this);

        //add/connect the created account to the user/holder and to the bank/bank's list of accounts
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin){
        //search through list of users
        for (User u: this.users){
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }

        return null;
    }

    public String getName(){
        return this.name;
    }

}
