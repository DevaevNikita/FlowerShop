package Application;

import Application.Flowers.Flower;
import Application.Flowers.KindsOfFlowers.Chrysanthemum;
import Application.Flowers.KindsOfFlowers.Lily;
import Application.Flowers.KindsOfFlowers.Rose;
import Application.Flowers.KindsOfFlowers.Tulip;
import Application.PersonAndCard.Card;
import Application.PersonAndCard.Person;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Application {

    private static ReentrantLock lock = new ReentrantLock();

    private static List<Person> person = new LinkedList<>();
    private static List<Flower> flowers = new ArrayList<>();

    public static void main(String[] args) {


        Shop.create_shop();
        create_people();

        Connect connect = Connect.getConnection();

        ExecutorService es = Executors.newFixedThreadPool(person.size());
        for(int i = 0; i < person.size(); i++){
            es.submit(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        Person man = person.get(0);
                        person.remove(0);
                        int value = Shop.take_flowers(man);
                        man.setToPay(value);
                        try {
                            connect.payment(man);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        man.getInfo();
                        person.add(man);
                    }finally {
                        lock.unlock();
                    }

                }
            });
        }
        es.shutdown();

        try {
            es.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int result = 0;

        for(Person person: person){
            result+=person.getMoney();
        }
        result = (7000 * person.size()) - result;

        System.out.println("People spent: " + result);
        System.out.println("The Store has earned: " + Shop.getShopMoney());

    }

    public static List<Flower> getFlowers() {
        return flowers;
    }

    public static void AddFlower(Flower flower){
        flowers.add(flower);
    }

    public static void create_people(){
        Person Nikita = new Person("Nikita", "Devaev", 24,  new Card(123456));
        Person Tom = new Person("Tom", "Henks", 34, new Card(1324567));
        Person Robert = new Person("Robert", "Evol", 25,  new Card(1322356));
        Person Lyi = new Person("Lyi", "Prommer", 64, new Card(432253134));
        Person Rob = new Person("Rob", "Hopper", 21,  new Card(5623456));
        Person Tay = new Person("Tay", "Resson", 31, new Card(647352923));
        Person Bob = new Person("Bob", "Rastor", 24,  new Card(543634627));
        Person Ray = new Person("Ray", "Pestor", 35, new Card(34621321));


        person.add(Nikita);
        person.add(Tom);
        person.add(Rob);
        person.add(Robert);
        person.add(Lyi);
        person.add(Tay);
        person.add(Bob);
        person.add(Ray);
    }
}

class Shop{

    private static int ShopMoney;

    private Shop(){}

    public static int take_flowers(Person person){
        int result = 0;
        Random random = new Random();

        List<Flower> flowers = Application.getFlowers();

        for(Flower flower: flowers){
            result += flower.getPrice(random.nextInt(25));
        }

        return result;
    }

    public static void create_shop(){
        Rose rose = new Rose("Rose", 170);
        Lily lily = new Lily("Lily", 320);
        Tulip tulip = new Tulip("Tulip", 75);
        Chrysanthemum chrysanthemum = new Chrysanthemum("Chrysanthemum", 120);

        Application.AddFlower(rose);
        Application.AddFlower(lily);
        Application.AddFlower(tulip);
        Application.AddFlower(chrysanthemum);

    }

    public static int getShopMoney() {
        return ShopMoney;
    }

    public static void setShopMoney(int shopMoney) {
        ShopMoney += shopMoney;
    }
}