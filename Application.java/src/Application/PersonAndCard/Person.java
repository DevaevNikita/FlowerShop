package Application.PersonAndCard;

public class Person {
    private String name;
    private String surname;
    private int age;

    private int ToPay;

    private Card myCard;

    public Person(String name, String surname, int age, Card myCard) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.myCard = myCard;
    }

    public void getInfo(){
        System.out.println(name + " " + surname);
        System.out.println("Age: " + age);
        System.out.println("------------------------");
        System.out.println("Number card: " + myCard.getNumberCard());
        System.out.println("Money in card: " + myCard.getMoney() + "\n");
    }

    public boolean shopping(int money){
        boolean shop = myCard.transaction(money);
        if(!shop){
            return false;
        }
        return true;
    }

    public void setToPay(int toPay) {
        ToPay = toPay;
    }

    public int getToPay() {
        return ToPay;
    }

    public int getMoney(){
        return myCard.getMoney();
    }
}


