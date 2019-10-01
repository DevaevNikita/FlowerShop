package Application.PersonAndCard;

public class Card {
    private int numberCard;
    private int money = 7000;

    public Card(int numberCard) {
        this.numberCard = numberCard;
    }

    public int getNumberCard() {
        return numberCard;
    }

    public int getMoney() {
        return money;
    }

    public boolean transaction(int money){
        if(this.money >= money){
            this.money -= money;
            return true;
        }else{
            return false;
        }
    }
}
