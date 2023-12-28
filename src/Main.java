import java.util.StringTokenizer;
import  java.io.* ;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
* keyword
* identifiers
* operators
* numeric constant
* character constants
* special characters
* comments
* */
public class Main {

    public static boolean keywordTokens(String tokens) {
        String keys[] = { "int", "long", "float", "double", "boolean", "String", "if", "else", "switch", "case",
                "continue", "break", "for", "while", "char", "do", "goto", "enum", "private", "public", "void" };
        for (String key : keys) {
            if (tokens.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static boolean identifierTokens(String token) {
        if (token.matches("[a-zA-Z][a-zA-Z0-9_]*"))
            return true;
        else
            return false;
    }

    public static boolean operatorTokens(String token) {
        String[] operators = { "+", "-", "*", "/", "++", "--", "==", "!=", "=", "<", ">", "/", "+=", "-=" };
        for (String oper : operators) {
            if (token.equals(oper)) {
                return true;
            }
        }
        return false;
    }

    public static boolean numericTokens(String token) {
        if (token.matches("\\d+(\\.\\d+)?"))
            return true;
        else
            return false;
    }

    public static boolean specialChars(String token) {
        String ch[] = { ",", ";", "(", ")", "{", "}", "[", "]" };
        for (String c : ch) {
            if (token.equals(c))
                return true;
        }
        return false;
    }

    public static boolean comment(String token) {
        return token.matches("//.*|/\\*(.|\\n)*?\\*/"); // single and double comments
    }

    public static List<Pair<String, String>> analyzeLexical(String input) {
        List<Pair<String, String>> sympolTable= new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(input);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (keywordTokens(token)) {
                sympolTable.add(new Pair<>("Keyword", token));
            } else if (identifierTokens(token)) {
                sympolTable.add(new Pair<>("Identifier", token));
            } else if (operatorTokens(token)) {
                sympolTable.add(new Pair<>("Operator", token));
            } else if (numericTokens(token)) {
                sympolTable.add(new Pair<>("Numeric Constant", token));
            } else if (specialChars(token)) {
                sympolTable.add(new Pair<>("Special Character", token));
            } else if (comment(token)) {
                sympolTable.add(new Pair<>("Comment", token));
            } else {
                sympolTable.add(new Pair<>("Invalid Token", token));
            }
        }

        return sympolTable;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Desktop\\Lexical analysis\\src\\read.txt"))) {
            String readLine;

            while ((readLine = reader.readLine()) != null) {
                List<Pair<String, String>> lexicalResult = analyzeLexical(readLine);

                for (Pair<String, String> pair : lexicalResult) {
                    System.out.println(pair.getFirst() + ": " + pair.getSecond());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}