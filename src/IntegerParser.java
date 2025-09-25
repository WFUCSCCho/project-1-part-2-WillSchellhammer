/************************************************************************
 * @file: IntegerParser.java
 * @description: Opens an input file based on the first arg, processes each line, operates commands for BST, then writes into results.txt
 * @author: Will S
 * @date: September 20, 2025
 ************************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class IntegerParser {

    //Create a BST tree of Integer type
    private BST<Integer> mybst = new BST<>();
    PrintWriter writer;
    FileOutputStream output = null;
    String currFilePath = null;

    public IntegerParser(String filename) throws FileNotFoundException {
        process(new File(filename));
    }

    // Implement the process method
    // Remove redundant spaces for each input command
    public void process(File input) throws FileNotFoundException {
        Scanner reader = new Scanner(input);
        ArrayList<String> command = new ArrayList<>();
        while (reader.hasNextLine()) {
            String line = reader.nextLine().strip().toLowerCase();
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
            Integer val = null;
            String cmdSub = cmd;
            if (cmd.indexOf(' ') > 0) {
                cmdSub = cmd.substring(0, cmd.indexOf(' '));
            }
            switch (cmdSub) {
                // add your cases here
                // call writeToFile
                case ("insert"):
                    try {
                        val = Integer.parseInt(cmd.substring(cmd.indexOf(' ')).strip());
                    }
                    catch (NumberFormatException e) {
                        writeToFile("Invalid Command", "./result.txt");
                    }
                    mybst.insert(val);
                    writeToFile("insert " + val, "./result.txt");
                    break;
                case ("search"):
                    try {
                        val = Integer.parseInt(cmd.substring(cmd.indexOf(' ')).strip());
                    }
                    catch (NumberFormatException e) {
                        writeToFile("Invalid Command", "./result.txt");
                    }
                    if (mybst.search(val) == null) {
                        writeToFile("search failed", "./result.txt");
                    }
                    else {
                        writeToFile("found " + val, "./result.txt");
                    }
                    break;
                case ("remove"):
                    try {
                        val = Integer.parseInt(cmd.substring(cmd.indexOf(' ')).strip());
                    }
                    catch (NumberFormatException e) {
                        writeToFile("Invalid Command", "./result.txt");
                    }
                    try {
                        mybst.remove(val);
                        writeToFile("removed " + val, "./result.txt");
                    }
                    catch (NullPointerException e) {
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
        //generate output file
        try {
            output = new FileOutputStream(filePath);
        }
        catch (IOException e) {
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
