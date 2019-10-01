package Application;

import Application.PersonAndCard.Person;

import java.util.concurrent.Semaphore;

//Singleton
public class Connect {

    private final static Connect connect = new Connect();
    private Semaphore semaphore = new Semaphore(1);

    private Connect() {

    }

    public static Connect getConnection(){
        return connect;
    }
    public void payment(Person person) throws InterruptedException {
        semaphore.acquire();
        try {
            calculate(person);
        }finally {
            semaphore.release();
        }
    }

    private void calculate(Person person){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int value = person.getToPay();
        if(person.shopping(value)){
            Shop.setShopMoney(value);
        }else{
            System.out.println("You don't have that much money");
        }
    }
}
