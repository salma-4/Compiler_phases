import java.util.StringTokenizer;
import  java.io.* ;

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

    //1
    public static boolean keywordTokens(String tokens){
        String keys[]={"int","long","float","double","boolean","String","if","else",
                       "switch","case","continue","break","for","while","char","do","goto"
                        ,"enum","private","public","void"
        };
        for(String key :keys){
            if(tokens.equals(key)){
                return true;
            }
        }
      return  false;
    }

    //2
    public static boolean identifierTokens(String token){
      if(token.matches("[a-zA-Z][a-zA-Z0-9_]*"))
          return true;
      else
        return false;
    }
    //3
    public static boolean operatorTokens(String token){
        String[] operators={"+","-","*","/","++","--","==","!=","=","<",">","/","+=","-="};
        for(String oper : operators){
            if(token.equals(oper)){
                return true;
            }
        }
        return false;
    }
      //4
    public static boolean numericTokens(String token){
        if(token.matches("\\d+(\\.\\d+)?"))
            return true ;
        else
          return false;
    }
    //5
    public static  boolean specialChars(String token){
        String ch[]={",",";","(",")","{","}","[","]"};
        for(String c: ch) {
            if (token.equals(c))
                return  true;
        }
        return false;
    }
    //6
    public static boolean comment(String token){
        return token.matches("//.*|/\\*(.|\\n)*?\\*/"); // single and double comments
    }
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Desktop\\Lexical analysis\\src\\read.txt"))) {
            String readLine;

            while ((readLine = reader.readLine()) != null) {

                StringTokenizer tokenizer = new StringTokenizer(readLine);

                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();

                    if (keywordTokens(token)) {
                        System.out.println("Keyword: " + token);
                    } else if (identifierTokens(token)) {
                        System.out.println("Identifier: " + token);
                    } else if (operatorTokens(token)) {
                        System.out.println("Operator: " + token);
                    } else if (numericTokens(token)) {
                        System.out.println("Numeric Constant: " + token);
                    } else if (specialChars(token)) {
                        System.out.println("Special Character: " + token);
                    } else if (comment(token)) {
                        System.out.println("Comment: " + token);
                    } else {
                        System.out.println("invalid token: " + token);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}