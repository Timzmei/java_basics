import java.util.HashMap;
import java.util.Random;

public class Bank implements Runnable
{
    private HashMap<String, Account> accounts;
    private HashMap<String, String> accountsNumber;
    private final Random random = new Random();


    public Bank(){
        accounts = new HashMap<>();
        accountsNumber = new HashMap<>();
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();

    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {


        Account fromAccount = accounts.get(accountsNumber.get(fromAccountNum));
        Account toAccount = accounts.get(accountsNumber.get(toAccountNum));
//        System.out.println(Thread.currentThread().getName() + " хочет начать работу со счетами " + fromAccountNum + " и " + toAccountNum);

        Account firstLock, secondLock;
        if (Integer.valueOf(fromAccount.getAccNumber()) < Integer.valueOf(toAccount.getAccNumber())) {
            firstLock = fromAccount;
            secondLock = toAccount;
        }
        else {
            firstLock = toAccount;
            secondLock = fromAccount;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (fromAccount.getMoney() >= amount && fromAccount.getStatus() == 0 && toAccount.getStatus() == 0) {


                fromAccount.setMoney(fromAccount.getMoney() - amount);
                toAccount.setMoney(toAccount.getMoney() + amount);


                    if (amount > 50000) {
                        if (!isFraud(fromAccountNum, toAccountNum, amount)) {
                            fromAccount.setStatus(1);
                            toAccount.setStatus(1);

                        }
                    }
                }
                else {
                }


            }


        }


    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum)
    {
        long sum;
        if (accountNum.matches("[0-9]+")) {
            sum = accounts.get(accountsNumber.get(accountNum)).getMoney();
        }
        else {
            sum = accounts.get(accountNum).getMoney();
        }

        return sum;
    }

    public long getBalanceAll(){
        long sum = 0;
        for (Account values : accounts.values()){
            sum = sum + values.getMoney();
        }
        return sum;
    }

    public void addBankAccount(String nameClient){
        int number = accountsNumber.size() + 1;

        accounts.put(nameClient, new Account((long) (5000000 * Math.random()), String.valueOf(number)));

        accountsNumber.put(String.valueOf(number), nameClient);
    }


    @Override
    public void run() {
        try {


            int fromAccountNum;
            int toAccountNum;

            int count = accountsNumber.size();
            for (int i = 0; i < 1000; i++){
                fromAccountNum = 1 + (int) (Math.random() * count);

                toAccountNum = 1 + (int) (Math.random() * count);
                while (toAccountNum == fromAccountNum){
                    toAccountNum = 1 + (int) (Math.random() * count);
                }


                try {
                    transfer(String.valueOf(fromAccountNum), String.valueOf(toAccountNum), (long) (Math.random() * 600000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(getBalanceAll());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public HashMap<String, String> getAccountsNumber() {
        return accountsNumber;
    }

}
