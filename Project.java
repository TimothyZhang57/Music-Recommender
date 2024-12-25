
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project {
    public static List<String> startsWith(List<String> input, char Start) {
        List<String> output = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).charAt(0) == Start) {
                output.add(input.get(i));
            }
        }
        return output;
    }

    public static int countType(List<Song> songs, String genre, int decade) {
        int count = 0;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getDecade() == decade && songs.get(i).getGenre().equals(genre)) {
                count++;
            }
        }
        return count;
    }

    public static int countGenre(List<Song> songs, String genre) {
        int count = 0;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getGenre().equals(genre)) {
                count++;
            }
        }
        return count;
    }

    public static Song recommend(List<Song> songs, String genre, int decade) {
        Random rand = new Random();
        int num = countType(songs, genre, decade);
        if (num == 0) {
            System.out.println("No song found");
            return null;
        } else {
            int random = rand.nextInt(0, num);
            int numFound = -1;
            Song recommended = new Song();
            for (int i = 0; i < songs.size(); i++) {
                if (songs.get(i).getDecade() == decade && songs.get(i).getGenre().equals(genre)) {
                    recommended = new Song(songs.get(i).getName(), songs.get(i).getGenre(), songs.get(i).getDecade());
                    numFound++;
                    if (numFound == random) {
                        System.out.println("We recommend " + recommended.getName());
                        break;
                    }
                }
            }
            return recommended;
        }
    }

    public static Song recommendGenre(List<Song> songs, String genre) {
        Random rand = new Random();
        int num = countGenre(songs, genre);
        if (num == 0) {
            System.out.println("No song found");
            return null;
        } else {
            int random = rand.nextInt(0, num);
            int numFound = -1;
            Song recommended = new Song();
            for (int i = 0; i < songs.size(); i++) {
                if (songs.get(i).getGenre().equals(genre)) {
                    recommended = new Song(songs.get(i).getName(), songs.get(i).getGenre(), songs.get(i).getDecade());
                    numFound++;
                    if (numFound == random) {
                        System.out.println("We recommend " + recommended.getName());
                        break;
                    }
                }
            }
            return recommended;
        }
    }

    public static Song random(List<Song> songs) {
        Random rand = new Random();
        int random = rand.nextInt(songs.size());
        return songs.get(random);
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<Person> people = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        FileReader fr = new FileReader("Songs.csv");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Song song = new Song(data[0], data[2], Integer.parseInt(data[1]));
                songs.add(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileReader PeopleReader = new FileReader("People.csv");
        BufferedReader PeopledBuffered = new BufferedReader(PeopleReader);
        String personData;
        try {
            while ((personData = PeopledBuffered.readLine()) != null) {
                String[] data = personData.split(",");
                Person person = new Person(data[0], data[2], Integer.parseInt(data[1]));
                people.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Music Recommender");
        Container pane = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Music Recommender");
        JLabel result = new JLabel("");
        label.setFont(new Font("Serif", Font.PLAIN, 50));
        JButton getRecommendation = new JButton(new AbstractAction("Get recommendation") {
            public void actionPerformed(ActionEvent e) {
                String genre = JOptionPane.showInputDialog(frame, "Enter a genre", null);
                int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter a decade", null));
                Song selected = recommend(songs, genre, decade);
                if (selected != null) {
                    result.setText("Listen to " + selected.getName());
                } else {
                    result.setText("No Song found");
                }

            }
        });
        JButton addSong = new JButton(new AbstractAction("Add a song") {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter a name", null);
                String genre = JOptionPane.showInputDialog(frame, "Enter a genre", null);
                int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter a decade", null));
                Song newSong = new Song(name, genre, decade);
                if (songs.contains(newSong) == false) {
                    songs.add(newSong);
                    try {
                        FileWriter Writer = new FileWriter("Songs.csv", true);
                        Writer.write('\n' + name + "," + decade + "," + genre);
                        result.setText("Song successfully added");
                        Writer.close();
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                } else {
                    result.setText("Song already exists");
                }
            }
        });

        JButton addUser = new JButton(new AbstractAction("Add a user") {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter a name", null);
                String genre = JOptionPane.showInputDialog(frame, "Enter a perferred genre", null);
                int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter a perferred decade", null));
                Person newUser = new Person(name, genre, decade);
                if (people.contains(newUser) == false) {
                    people.add(newUser);
                    try {
                        FileWriter Writer = new FileWriter("People.csv", true);
                        Writer.write('\n' + name + "," + decade + "," + genre);
                        result.setText("User successfully added");
                        Writer.close();
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                } else {
                    result.setText("User already exists");
                }
            }
        });
        JButton getRecommendationForUser = new JButton(new AbstractAction("Get a recommendation for a user") {
            public void actionPerformed(ActionEvent e) {
                boolean found = false;
                String name = JOptionPane.showInputDialog(frame, "Enter a name", null);
                for (int i = 0; i < people.size(); i++) {
                    if (people.get(i).getName().equals(name)) {
                        Song selected = recommend(songs, people.get(i).getFavoriteGenre(),
                                people.get(i).getFavoriteDecade());
                        found = true;
                        result.setText("Listen to " + selected.getName());
                    }
                }
                if (found == false) {
                    result.setText("User not found");
                }

            }
        });
        JButton getRandom = new JButton(new AbstractAction("Get a random song") {
            public void actionPerformed(ActionEvent e) {
                Song randomSelected = random(songs);
                result.setText("Listen to " + randomSelected.getName());
            }
        });
        JButton getRandomByGenre = new JButton(new AbstractAction("Get a random song by genre") {
            public void actionPerformed(ActionEvent e) {
                String genre = JOptionPane.showInputDialog(frame,
                        "Enter a genre", null);
                Song recommended = recommendGenre(songs, genre);
                result.setText("Listen to " + recommended.getName());
            }
        });
        JButton modifyUser = new JButton(new AbstractAction("Modify a user") {
            public void actionPerformed(ActionEvent e) {
                boolean found = false;
                String name = JOptionPane.showInputDialog(frame, "Enter a name", null);
                for (int i = 0; i < people.size(); i++) {
                    if (people.get(i).getName().equals(name)) {
                        String genre = JOptionPane.showInputDialog(frame, "Enter a perferred genre", null);
                        int decade = Integer
                                .parseInt(JOptionPane.showInputDialog(frame, "Enter a perferred decade", null));
                        people.get(i).setDecade(decade);
                        people.get(i).setGenre(genre);
                        try {
                            FileWriter Writer = new FileWriter("People.csv");
                            for (int a = 0; a< people.size(); a++) {
                                if (a != 0) {
                                    Writer.write('\n' + people.get(a).getName() + "," + people.get(a).getFavoriteDecade() + "," + people.get(a).getFavoriteGenre());
                                }else{
                                    Writer.write(people.get(a).getName() + "," + people.get(a).getFavoriteDecade() + "," + people.get(a).getFavoriteGenre());
                                }
                            }
                            Writer.close();
                        } catch (IOException error) {
                            error.printStackTrace();
                        }
                        found = true;
                        result.setText("User successful modified");
                    }
                }
                if (found == false) {
                    result.setText("User not found");
                }

            }
        });
        frame.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        result.setFont(new Font("Serif", Font.PLAIN, 25));
        pane.add(Box.createHorizontalGlue());
        pane.add(label, BorderLayout.CENTER);
        pane.add(Box.createRigidArea(new Dimension(250, 0)));
        pane.add(getRecommendation);
        pane.add(addSong);
        pane.add(addUser);
        pane.add(getRecommendationForUser);
        pane.add(getRandom);
        pane.add(getRandomByGenre);
        pane.add(modifyUser);
        pane.add(result);
        frame.setSize(700, 350);
        frame.setVisible(true);
        /*
         * boolean interacting = true;
         * Scanner scanner = new Scanner(System.in);
         * while (interacting == true) {
         * System.out.println("Welcome to Music Recommender");
         * System.out.println("Type 1 to get a recommendation");
         * System.out.println("Type 2 to add a song");
         * System.out.println("Type 3 to add a user");
         * System.out.println("Type 4 to get a recommendation for a user");
         * System.out.println("Type 5 to see all the users");
         * System.out.println("Type 6 to get a random song");
         * System.out.println("Type 7 to get a random song by genre");
         * System.out.println("Type 8 to stop");
         * int response = scanner.nextInt();
         * scanner.nextLine();
         * if (response == 1) {
         * System.out.println("Enter your genre");
         * String selectedGenre = scanner.nextLine();
         * System.out.println("Enter a decade");
         * int selectedDecade = scanner.nextInt();
         * scanner.nextLine();
         * recommend(songs, selectedGenre, selectedDecade);
         * } else if (response == 2) {
         * System.out.println("Enter a name of a song");
         * String name = scanner.nextLine();
         * System.out.println("Enter an genre");
         * String genre = scanner.nextLine();
         * System.out.println("Enter a decade");
         * int decade = scanner.nextInt();
         * Song newSong = new Song(name, genre, decade);
         * if (songs.contains(newSong) == false) {
         * songs.add(newSong);
         * try {
         * FileWriter Writer = new FileWriter("Songs.csv", true);
         * Writer.write('\n' + name + "," + decade + "," + genre);
         * Writer.close();
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         * } else {
         * System.out.println("This song is already in the database");
         * }
         * } else if (response == 3) {
         * System.out.println("Enter a name");
         * String name = scanner.nextLine();
         * System.out.println("Enter a decade");
         * int Decade = scanner.nextInt();
         * scanner.nextLine();
         * System.out.println("Enter a genre");
         * String genre = scanner.nextLine();
         * Person newUser = new Person(name, genre, Decade);
         * people.add(newUser);
         * try {
         * FileWriter Writer = new FileWriter("People.csv", true);
         * Writer.write('\n' + name + "," + Decade + "," + genre);
         * Writer.close();
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         * } else if (response == 4) {
         * System.out.println("Enter a name");
         * String name = scanner.nextLine();
         * boolean found = false;
         * for (int i = 0; i < people.size(); i++) {
         * if (people.get(i).getName().equals(name)) {
         * people.get(i).setRecommended(
         * recommend(songs, people.get(i).getFavoriteGenre(),
         * people.get(i).getFavoriteDecade()));
         * found = true;
         * }
         * }
         * if (found == false) {
         * System.out.println("user not found");
         * }
         * } else if (response == 5) {
         * for (int i = 0; i < people.size(); i++) {
         * System.out.println(people.get(i).toString());
         * }
         * } else if (response == 6) {
         * Song randomSelected = random(songs);
         * System.out.println("Listen to " + randomSelected.getName());
         * } else if (response == 7) {
         * System.out.println("Enter a genre");
         * String genre = scanner.nextLine();
         * recommendGenre(songs, genre);
         * } else {
         * interacting = false;
         * }
         * }
         * scanner.close();
         */
    }
}
