/************************************************************************
 * @file: GameParser.java
 * @description: Opens an input file based on the first arg, processes each line, operates commands for BST, then writes into results.txt
 * @author: Will S
 * @date: September 24, 2025
 ************************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameParser {

    //Create a BST tree of Game type
    private BST<Game> mybst = new BST<>();
    private PrintWriter writer;
    private FileOutputStream output = null;
    private String currFilePath = null;
    private String csvFile = "steam_games.csv";

    //parses csv file and input file and prints to result.txt
    public GameParser(String filename) throws FileNotFoundException {
        createBST();
        process(new File(filename));
    }

    //inserts a list of steam games into the BST
    private void createBST() throws FileNotFoundException {
        Scanner csvReader = new Scanner(new File(csvFile));
        csvReader.nextLine(); //skip the header line
        ArrayList<String> steam_games = new ArrayList<>();
        while (csvReader.hasNextLine()) {
            String line = csvReader.nextLine();
            if (!line.isEmpty()) {
                steam_games.add(line);
            }
        }
        listToBST(steam_games.toArray(new String[0]));
    }

    //takes the array of Strings, processes them as games, and inserts into the BST
    private void listToBST(String[] steam_games) {
        for (String game : steam_games) {
            String[] data = game.split(",");
            try {
                mybst.insert(new Game(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]),
                        Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]), Integer.parseInt(data[8]), Double.parseDouble(data[9])));
            } catch (NumberFormatException e) {
                System.out.println("Error with parsing line: " + game);
            }
        }
        System.out.println("\nBinary Search Tree populated with " + mybst.size() + " nodes from file " + csvFile);
        writeToFile("Binary Search Tree populated with " + mybst.size() + " nodes from file " + csvFile, "./result.txt");
    }

    // Implement the process method
    //processes input commands from the input file, alters BST if applicable and writes to result.txt if applicable
    public void process(File input) throws FileNotFoundException {
        Scanner reader = new Scanner(input);
        ArrayList<String> command = new ArrayList<>();
        while (reader.hasNextLine()) {
            String line = reader.nextLine().strip();
            if (!line.isEmpty()) {
                command.add(line);
            }
        }

        //call operate_BST method
        operate_BST(command.toArray(new String[0]));

        reader.close();
        if (writer != null) writer.close();
    }

    // Implement the operate_BST method
    // Determine the incoming command and operate on the BST
    public void operate_BST(String[] command) {
        for (String cmd : command) {
            String cmdSub = cmd.toLowerCase();
            String[] data = new String[0];
            if (cmd.indexOf(' ') > 0) {
                cmdSub = cmd.substring(0, cmd.indexOf(' ')).toLowerCase(); //first word, the command
                data = cmd.substring(cmd.indexOf(' ')+1).split(","); //everything else, defines the game
            }
            for (int i = 0; i < data.length; i++) data[i] = data[i].strip(); //strip spaces between each section
            Game game;
            //System.out.println("cmdSub: " + cmdSub + ", data " + Arrays.toString(data)); //debug
            switch (cmdSub) {
                // add your cases here
                // call writeToFile
                case ("insert"):
                    if (data.length < 10) {
                        writeToFile("Invalid Command", "./result.txt");
                        break;
                    }
                    try {
                        game = new Game(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]),
                                Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]), Integer.parseInt(data[8]),
                                Double.parseDouble(data[9]));
                    } catch (NumberFormatException e) {
                        writeToFile("Invalid Command", "./result.txt");
                        break;
                    }
                    mybst.insert(game);
                    writeToFile("insert " + game, "./result.txt");
                    break;
                case ("search"):
                    if (data.length < 10) {
                        writeToFile("Invalid Command", "./result.txt");
                        break;
                    }
                    try {
                        game = new Game(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]),
                                Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]), Integer.parseInt(data[8]),
                                Double.parseDouble(data[9]));
                    } catch (NumberFormatException e) {
                        writeToFile("Invalid Command", "./result.txt");
                        break;
                    }
                    if (mybst.search(game) == null) {
                        writeToFile("search failed", "./result.txt");
                    } else {
                        writeToFile("found " + game, "./result.txt");
                    }
                    break;
                case ("remove"):
                    if (data.length < 10) {
                        writeToFile("Invalid Command", "./result.txt");
                        break;
                    }
                    try {
                        game = new Game(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]),
                                Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]), Integer.parseInt(data[8]),
                                Double.parseDouble(data[9]));
                    } catch (NumberFormatException e) {
                        writeToFile("Invalid Command", "./result.txt");
                        break;
                    }
                    try {
                        Game temp = mybst.remove(game).data;
                        writeToFile("removed " + game, "./result.txt");
                    } catch (NullPointerException e) {
                        writeToFile("remove failed", "./result.txt");
                    }
                    break;
                case ("print"):
                    mybst.print();
                    break;
                case ("isempty"):
                    if (mybst.isEmpty()) writeToFile("empty", "./result.txt");
                    else writeToFile("not empty", "./result.txt");
                    break;
                // default case for Invalid Command
                default:
                    writeToFile("invalid Command", "./result.txt");
            }
        }
    }

    // Implement the writeToFile method
    // Generate the result file
    public void writeToFile(String content, String filePath) {
        //System.out.println(content); //for debugging

        //generate output file
        try {
            output = new FileOutputStream(filePath);
        } catch (IOException e) {
            System.out.println("Output file unable to be created. Program terminated. " + e);
            System.exit(1);
        }
        if (!filePath.equals(currFilePath)) {
            currFilePath = filePath;
            if (writer != null) writer.close();
            writer = new PrintWriter(output);
        }
        writer.println(content);
    }
}