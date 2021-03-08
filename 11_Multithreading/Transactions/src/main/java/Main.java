public class Main {

    public static void main(String[] args) throws InterruptedException {


        Bank bank = new Bank();


        for (int i = 0; i < 10; i++) {
            bank.addBankAccount("Account" + i);
        }

        System.out.println(bank.getBalanceAll());


        for (int i = 1; i <= 10; i++){

            Thread t = new Thread(new Transfer(bank));
            t.setName("Поток " + i);

            t.start();

        }



    }


}
