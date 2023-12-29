import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
/* phase 1 ==> lexical analysis
 * keywords
 * identifier
 * operators
 * numericConstants
 * specialCharacters
 * comments
 * */


/* phase 2 ==> syntax analysis
 * rule 1 ==>  keyword identifier = numericConstant || identifier  ;
 * rule 2 ==> for ( keyword identifier = numericConstant ; identifier operator numericConstant ; identifier ++ ) { }
 * */

public class Main {
    //1
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
   //2
    public static boolean identifierTokens(String token) {
        return token.matches("[a-zA-Z][a-zA-Z0-9_]*");
    }
    //3
    public static boolean operatorTokens(String token) {
        String[] operators = { "+", "-", "*", "/", "++", "--", "==", "!=", "=", "<", ">", "/", "+=", "-=" };
        for (String oper : operators) {
            if (token.equals(oper)) {
                return true;
            }
        }
        return false;
    }
    //4
    public static boolean numericTokens(String token) {
        return token.matches("\\d+(\\.\\d+)?");
    }
   //5
    public static boolean specialChars(String token) {
        String ch[] = { ",", ";", "(", ")", "{", "}", "[", "]" };
        for (String c : ch) {
            if (token.equals(c))
                return true;
        }
        return false;
    }
    //6
    public static boolean comment(String token) {
        return token.matches("//.*|/\\*(.|\\n)*?\\*/"); // single and double comments
    }

    public static List<Pair<String, String>> lexicalAnalayzer(String input) {
        List<Pair<String, String>> result = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(input);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (keywordTokens(token)) {
                result.add(new Pair<>("Keyword", token));
            } else if (identifierTokens(token)) {
                result.add(new Pair<>("Identifier", token));
            } else if (operatorTokens(token)) {
                result.add(new Pair<>("Operator", token));
            } else if (numericTokens(token)) {
                result.add(new Pair<>("Numeric Constant", token));
            } else if (specialChars(token)) {
                result.add(new Pair<>("Special Character", token));
            } else if (comment(token)) {
                result.add(new Pair<>("Comment", token));
            } else {
                result.add(new Pair<>("Invalid Token", token));
            }
        }

        return result;
    }
    //----------------------------------------------------------------------------------------------------------------
     // rule 1  ==>
    public static boolean declarationRule(List<Pair<String, String>> listOfTokens, int startPosition) {
        // Rule: <dataType> <identifier> = (<numericConstant> | <identifier>) ;
        // Example: int x = 10 ;

        int remainingTokens = listOfTokens.size() - startPosition;

        if (remainingTokens >= 5) {
            if (keywordTokens(listOfTokens.get(startPosition).getSecond())) {
                startPosition++;
                if (identifierTokens(listOfTokens.get(startPosition).getSecond())) {
                    startPosition++;
                    if (listOfTokens.get(startPosition).getSecond().equals("=")) {
                        startPosition++;
                        if (remainingTokens >= 2 &&
                                (numericTokens(listOfTokens.get(startPosition).getSecond())
                                        || identifierTokens(listOfTokens.get(startPosition).getSecond()))) {
                            startPosition++;
                            if (listOfTokens.get(startPosition).getSecond().equals(";")) {
                                startPosition++;
                                return true;
                            } else {
                                System.out.println("Error: Expected ; at the end of the sentence");
                                return false;
                            }
                        } else {
                            System.out.println("Error: Expected a numeric constant or identifier after =");
                            return false;
                        }
                    } else {
                        System.out.println("Error: Expected = after identifier");
                        return false;
                    }
                } else {
                    System.out.println("Error: Expected an identifier after the data type");
                    return false;
                }
            } else {
                System.out.println("Error: Expected a valid data type");
                return false;
            }
        } else {
            System.out.println("Error: Incomplete declaration");
            return false;
        }
    }


    //rule ==>2 for loop
    public static boolean forLoopRule(List<Pair<String, String>> listOfTokens, int pos) {
        if (listOfTokens.size() - pos >= 15) {
            if ("for".equals(listOfTokens.get(pos).getSecond())) {
                pos++;
                if ("(".equals(listOfTokens.get(pos).getSecond())) {
                    pos++;

                    if (keywordTokens(listOfTokens.get(pos).getSecond())) {
                        pos++;
                        if (identifierTokens(listOfTokens.get(pos).getSecond())) {
                            pos++;
                            if ("=".equals(listOfTokens.get(pos).getSecond())) {
                                pos++;
                                if (numericTokens(listOfTokens.get(pos).getSecond())) {
                                    pos++;
                                } else {
                                    System.out.println("Error: Expected a numeric constant after = in initialization");
                                    return false;
                                }
                            } else {
                                System.out.println("Error: Expected = in initialization");
                                return false;
                            }
                        } else {
                            System.out.println("Error: Expected an identifier in initialization");
                            return false;
                        }
                    } else {
                        System.out.println("Error: Expected a valid data type in initialization");
                        return false;
                    }

                    if (";".equals(listOfTokens.get(pos).getSecond())) {
                        pos++;

                        if (identifierTokens(listOfTokens.get(pos).getSecond())) {
                            pos++;
                            if (operatorTokens(listOfTokens.get(pos).getSecond())) {
                                pos++;
                                if (numericTokens(listOfTokens.get(pos).getSecond())) {
                                    pos++;
                                } else {
                                    System.out.println("Error: Expected a numeric constant after operator in condition");
                                    return false;
                                }
                            } else {
                                System.out.println("Error: Expected an operator in condition");
                                return false;
                            }
                        } else {
                            System.out.println("Error: Expected an identifier in condition");
                            return false;
                        }

                        if (";".equals(listOfTokens.get(pos).getSecond())) {
                            pos++;

                            if (identifierTokens(listOfTokens.get(pos).getSecond())) {
                                pos++;
                                if ("++".equals(listOfTokens.get(pos).getSecond())) {
                                    pos++;
                                } else {
                                    System.out.println("Error: Expected ++ in update");
                                    return false;
                                }
                            } else {
                                System.out.println("Error: Expected an identifier in update");
                                return false;
                            }

                            if (")".equals(listOfTokens.get(pos).getSecond())) {
                                pos++;
                                if ("{".equals(listOfTokens.get(pos).getSecond())) {
                                    pos++;

                                    if ("}".equals(listOfTokens.get(pos).getSecond())) {
                                        pos++;
                                        return true;
                                    } else {
                                        System.out.println("Error: Expected } at the end of the for loop");
                                        return false;
                                    }
                                } else {
                                    System.out.println("Error: Expected { after for loop conditions");
                                    return false;
                                }
                            } else {
                                System.out.println("Error: Expected ) at the end of the for loop");
                                return false;
                            }
                        } else {
                            System.out.println("Error: Expected ; after for loop condition");
                            return false;
                        }
                    } else {
                        System.out.println("Error: Expected ; after for loop initialization");
                        return false;
                    }
                } else {
                    System.out.println("Error: Expected ( after for");
                    return false;
                }
            } else {
                System.out.println("Error: Expected for");
                return false;
            }
        } else {
            System.out.println("Error: Incomplete for loop");
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String readLine;
        List<Pair<String, String>> lexicalResult;

        // Prompt the user to choose the type of analysis
        System.out.print("Choose analysis type (1 for Declaration, 2 for For Loop): ");
        int choice = in.nextInt();

        // Consume the remaining newline character
        in.nextLine();

        // Print the results based on the user's choice
        switch (choice) {
            case 1:
                System.out.println("Enter your statement : ");
                readLine = in.nextLine();
                lexicalResult = lexicalAnalayzer(readLine);
                boolean declarationPassed = declarationRule(lexicalResult, 0);
                System.out.printf("%-50s%-30s%n", readLine, declarationPassed ? "Passed" : "Failed");
                break;
            case 2:
                System.out.println("Enter your statement : ");
                readLine = in.nextLine();
                lexicalResult = lexicalAnalayzer(readLine);
                boolean forLoop = forLoopRule(lexicalResult, 0);
                System.out.printf("%-50s%-30s%n", readLine, forLoop ? "Passed" : "Failed");
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 for Declaration or 2 for For Loop.");
                break;
        }
    }
}