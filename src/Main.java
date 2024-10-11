import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<Bejegyzes> bejegyzesList;
    private static Scanner scanner = new Scanner(System.in);
    private static Random rand = new Random();

    public static void main(String[] args) {

        bejegyzesList = new ArrayList<>();

        bejegyzesList.add(new Bejegyzes("Ember", "Szeretem a tejet!"));
        bejegyzesList.add(new Bejegyzes("Másik Ember", "Ló"));

        askForBejegyzes();
        Beolvas("bejegyzesek.csv");

        randomLike();
        modifyBejegyzes();

        System.out.println(bejegyzesList);

        System.out.println("Legtöbb like: " + mostLikes());
        System.out.println(moreThan35Like()? "Van olyan bejegyzés ami 35-nél több likot kapott": "Nincs olyan bejegyzés ami 35-nél több likot kapott");
        System.out.println("15 liknál kevesebbett kapottak száma: " + hasLessThan15Likes());
        sortBejegyzesek();
    }

    private static  void Beolvas(String fajl){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fajl));

            String sor = reader.readLine();
            while(sor != null){
                String[] adatok = sor.split(";");
                bejegyzesList.add(new Bejegyzes(adatok[0], adatok[1]));
                sor = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void askForBejegyzes(){
        int count = askForInt();
        for (int i = 0; i < count; i++) {
            String szerzo = "";
            String tartalom = "";

            System.out.println("Adja meg a szerzőt: ");
            szerzo = scanner.nextLine();
            System.out.println("Adja meg tartalomat: ");
            tartalom = scanner.nextLine();

            bejegyzesList.add(new Bejegyzes(szerzo,tartalom));
        }
    }

    private static int askForInt(){
        boolean success = false;
        int output = 0;

        do {
            try{
                System.out.println("Kérem adjon meg egy számot: ");
                output = scanner.nextInt();
                success = true;
            }catch (Exception e){
                System.out.println("Kérem egy természetes számot adjon meg!");
            }
            scanner.nextLine();
        }while (!success);

        return output;
    }

    private static void randomLike(){
        for (int i = 0; i < bejegyzesList.size()*20; i++) {
            bejegyzesList.get(rand.nextInt(bejegyzesList.size())).like();
        }
    }

    private static void modifyBejegyzes(){
        System.out.println("Írjon valamilyen szöveget");
        bejegyzesList.get(1).setTartalom(scanner.nextLine());
    }

    private static int mostLikes(){
        int max = 0;
        for (int i = 0; i < bejegyzesList.size(); i++) {
            if(bejegyzesList.get(i).getLikeok() > max){
                max = bejegyzesList.get(i).getLikeok();
            }
        }
        return max;
    }

    private static boolean moreThan35Like(){
        boolean success = false;
        int i = 0;
        while (!success && i < bejegyzesList.size()){
            if(bejegyzesList.get(i).getLikeok() > 35){
                success = true;
            }
            i++;
        }
        return success;
    }

    private static int hasLessThan15Likes(){
        int count = 0;
        for (int i = 0; i < bejegyzesList.size(); i++) {
            if(bejegyzesList.get(i).getLikeok() < 15){
                count++;
            }
        }
        return count;
    }

    private static void sortBejegyzesek(){
        bejegyzesList.sort((o1, o2) -> o1.getLikeok() - o2.getLikeok());
        try {
            FileWriter myWriter = new FileWriter("bejegyzesek_rendezett.txt");
            for (int i = 0; i < bejegyzesList.size(); i++) {
                myWriter.write(bejegyzesList.get(i) + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}