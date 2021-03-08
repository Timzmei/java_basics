public class Account
{


    private long money;
    private String accNumber;
    private int status;



    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
        status = 0;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        if(this.status == 0) {
            this.money = money;
        }
        else{
            System.out.println("Bank account blocked");
        }
    }

    public String getAccNumber() {
        return accNumber;
    }


    public int getStatus() {

        return this.status;

    }

    public void setStatus(int status) {
        this.status = status;
    }
}
