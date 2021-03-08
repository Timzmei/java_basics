public class Transfer implements Runnable {


    private final Bank bank;

    public Transfer(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        try {


            int fromAccountNum;
            int toAccountNum;

            int count = bank.getAccountsNumber().size();
            for (int i = 1; i <= 1000; i++){

                fromAccountNum = 1 + (int) (Math.random() * count);

                toAccountNum = 1 + (int) (Math.random() * count);
                while (toAccountNum == fromAccountNum){
                    toAccountNum = 1 + (int) (Math.random() * count);
                }

                try {

                    bank.transfer(String.valueOf(fromAccountNum), String.valueOf(toAccountNum), (long) (Math.random() * 60000));


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(bank.getBalanceAll());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
