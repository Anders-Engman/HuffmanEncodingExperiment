package ResearchPaper2;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.*;

public class HuffmanCoding {

    public static void newBinaryBuilder(Node leftChild, Node rightChild, ArrayList<Node> nodeArr) {
        String oldBinary = "none";
        String replaceStr = "none";

        for (Node targNode : nodeArr) {
            if (leftChild.getStoredChars().contains(targNode.getStoredChars())) {
                oldBinary = targNode.getNewBinary();
                replaceStr = "0" + oldBinary;
                targNode.setNewBinary(replaceStr);
            } else if (rightChild.getStoredChars().contains(targNode.getStoredChars())) {
                oldBinary = targNode.getNewBinary();
                replaceStr = "1" + oldBinary;
                targNode.setNewBinary(replaceStr);
            }
        }
    }

    public static String readFile(String fileName) {
        String fileData = null;
        String inputStr = null;
        try {
            File targFile = new File("../ResearchPaper2/SampleTexts/" + fileName + ".txt");
            Scanner fileReader = new Scanner(targFile);
            while (fileReader.hasNextLine()) {
                inputStr = fileReader.nextLine();
                fileData = fileData + inputStr;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred in the file read process.");
        }

        return fileData;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String userInput = "none";
        String finalUI = "none";
        String newBinaryStr = "none";
        String cypherStr = "";
        String userNumIn = "";
        int compBitNum = 0;
        int charactersNum = 0;
        int frequencyNum = 0;
        int totalFreq = 0;
        int listNum = 0;
        int comparisonTally = 0;
        char listChoice = 0;
        float comparisonNum = 0;
        float compressionRatio = 0;
        boolean answerCheck = false;
        NumberFormat numberFormat = new DecimalFormat("##.##");
        NumberFormat byteFormat = new DecimalFormat("###,###,###,###.##");
        ArrayList<Node> nodeArr = new ArrayList<>();
        ArrayList<Node> copyNodeArr = new ArrayList<>();
        ArrayList<Character> charArr = new ArrayList<>();
        ArrayList<String> txtTitleArr = new ArrayList<>();
        long startTime = System.nanoTime();

        File folder = new File("../ResearchPaper2/SampleTexts");
        File[] listOfFiles = folder.listFiles();

        do {
            System.out.println("From the options listed below, please input the number of the document you'd like to compress: ");
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println((i + 1) + ". " + listOfFiles[i].getName());
                }
            }
            userNumIn = scanner.nextLine();
            listChoice = userNumIn.charAt(0);
            userNumIn = Character.toString(listChoice);
            listNum = Integer.parseInt(userNumIn);
            listNum = listNum - 1;

            if ((Character.isDigit(listChoice) && (listNum < listOfFiles.length))) {
                answerCheck = true;
            } else {
                System.out.println("Please re-input your choice: It needs to be a number and one of the choices above");
            }
        } while (answerCheck == false);

        userNumIn = listOfFiles[listNum].getName();
        userNumIn = userNumIn.substring(0, userNumIn.length() - 4);

        userInput = readFile(userNumIn);
        // Next line used to test Lowercase
        // userInput = userInput.toLowerCase();

        finalUI = userInput;

        charactersNum = userInput.length();

        char[] allChars = userInput.toCharArray();

        for (char char1 : allChars) {
            if (charArr.contains(char1) == false) {
                charArr.add(char1);
            }
        }

        for (int i = 0; i < charArr.size(); i++) {
            for (int a = 0; a < allChars.length; a++) {
                char targChar = charArr.get(i);

                if (targChar == allChars[a]) {
                    frequencyNum++;
                }

                if (a == allChars.length - 1) {
                    String transStr = String.valueOf(targChar);
                    Node newNode = new Node(transStr, frequencyNum);
                    nodeArr.add(newNode);
                }
            }
            frequencyNum = 0;
        }

        // Sort Nodes by frequency
        Collections.sort(nodeArr, new NodeComparator());

        for (Node node : nodeArr) {
            copyNodeArr.add(node);
        }

        while (copyNodeArr.get(0).getFrequency() < charactersNum) {
            int targInd = 0;
            int topIndex = copyNodeArr.size() - 1;
            Node lowest = copyNodeArr.get(topIndex);
            Node secondLowest = copyNodeArr.get(topIndex - 1);

            comparisonTally++;
            if (lowest.getFrequency() > secondLowest.getFrequency()) {
                Node temp = lowest;
                lowest = secondLowest;
                secondLowest = temp;
            }

            for (int e = copyNodeArr.size() - 3; e >= 0; e--) {
                comparisonTally++;
                if (copyNodeArr.get(e).getFrequency() < lowest.getFrequency()) {
                    secondLowest = lowest;
                    lowest = copyNodeArr.get(e);
                } else if (copyNodeArr.get(e).getFrequency() < secondLowest.getFrequency()) {
                    comparisonTally++;
                    secondLowest = copyNodeArr.get(e);
                }
            }

            String combStr = secondLowest.getStoredChars() + lowest.getStoredChars();
            int combVal = secondLowest.getFrequency() + lowest.getFrequency();

            Node parentNode = new Node(combStr, combVal, secondLowest, lowest);
            newBinaryBuilder(secondLowest, lowest, nodeArr);

            targInd = copyNodeArr.indexOf(secondLowest);

            copyNodeArr.set(targInd, parentNode);
            copyNodeArr.remove(lowest);
        }

        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime) / 1000000;
        int uniqueCharNum = nodeArr.size();

        System.out.println();
        newBinaryStr = userInput;
        String[] replaceArray = newBinaryStr.split("");

        for (Node node : nodeArr) {
            compBitNum = compBitNum + (node.getNewBinary().length() * node.getFrequency());
            cypherStr = cypherStr + node.getStoredChars() + " = " + node.getNewBinary() + " // ";
            for (int u = 0; u < replaceArray.length; u++) {
                if (replaceArray[u].equalsIgnoreCase(node.getStoredChars())) {
                    replaceArray[u] = node.getNewBinary();
                }
            }
        }

        System.out.println();

        compressionRatio =(float)(userInput.length() * 8) / compBitNum;
        comparisonNum = (float) (compBitNum * 100) / (userInput.length() * 8);
        comparisonNum = 100 - comparisonNum;
        double newFileSize = 100.0 - comparisonNum;

        System.out.println("Content drawn from " + userNumIn + ", an excerpt which is " + byteFormat.format(finalUI.length()) + " characters in length.");
        System.out.println("At ~8 bits per 8-bit ASCII character, the original string above is approximately " + byteFormat.format(userInput.length()) + " bytes (" + byteFormat.format((userInput.length() * 8)) + " bits) of memory in size, uncompressed.");
        System.out.println();
        System.out.println("However, after compression via Huffman Coding, the new compressed string is only approximately " + byteFormat.format((compBitNum / 8)) + " bytes (" + byteFormat.format(compBitNum) + " bits) in size.");
        System.out.println("This compression offers an approximate improvement of " + numberFormat.format(comparisonNum) + "% in size consumption.");
        System.out.println("The compression ratio is estimated to be approximately " + numberFormat.format(compressionRatio) + ":1, with the new file being " + numberFormat.format(newFileSize) + "% of the original file size.");
        System.out.println();
        System.out.println("Runtime has been calculated as: " + byteFormat.format(totalTime) + " milliseconds.");
        System.out.println();
        System.out.println(comparisonTally + " comparisons were made in the process of creating the Huffman Tree/cypher.");
        System.out.println("Codes were generated for " + uniqueCharNum + " unique characters.");
        System.out.println();
        System.out.println("The 'cypher' that was created during compression is offered on the following line for verification's sake.");
        System.out.println(cypherStr);
    }
}
