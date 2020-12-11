import Plot.java;
import java.io.*;
import java.util.*;
public class NewKirby {

   private String fx = "";
   private String[] polynomial;
   private List<Integer> coefficientList = new ArrayList<>();
   private List<Integer> exponentList = new ArrayList<>();
   private List<Integer> fractionList = new ArrayList<>();
   private List<String> operationList = new ArrayList<>();
   
   public static void main(String[] args) {
      Scanner console = new Scanner(System.in);
      introduction();
      NewKirby one = new NewKirby(console);
   }
   
      public static void introduction() {
      System.out.println("Kirby is a calculator that takes user input.");
      System.out.println("However, it can do more advanced operations that online calculators");
      System.out.println("such as google cannot do such as derivates and integrals.");
   }
   
   public NewKirby (Scanner console) {
      System.out.print("Please enter your expression: ");
      fx = console.nextLine();
      String[] polynomial = fx.split(" ");
      store(polynomial);
      advancedOperation(console, polynomial);
   }
   
   public void advancedOperation(Scanner console, String[] polynomial) {
      System.out.println("Let me simplify your expression for you!");
      simplify(polynomial);
      print(polynomial);
      System.out.println("Would you like to differentiate or integrate: ");
      Scanner operation = new Scanner(System.in);
      String choice = operation.next();
      if (choice.equalsIgnoreCase("differentiate")) {
            differentiate();
            print(polynomial);      
         } else if (choice.equalsIgnoreCase("integrate")) {
            integrate();
         } else {
            System.out.println("Invalid choice");
         }      
    }
    
    public void store(String[] polynomial) {
      char variable = 'x';
      char exponent = '^';
      char space = ' ';
      for (String term : polynomial) {
         if (term.contains("x")) {
            String character = term.substring(0, term.indexOf(variable));
            int a = Integer.parseInt(character);
            coefficientList.add(a);
         }
         if (term.contains("^")) {
            String carrot = term.substring(term.indexOf(exponent) + 1, term.length());
            int b = Integer.parseInt(carrot);
            exponentList.add(b);
         }
         if (term.contains("+") || term.contains("-") || term.contains("*") || term.contains("/")) {
            String c = term;
            operationList.add(c);
         }
      }
   }
   
   public void integrate() {
      for (int i = 0; i < coefficientList.size(); i++) {
         if (coefficientList.get(i) != null) {
            int exponentValue = exponentList.get(i) + 1;
            System.out.print(coefficientList.get(i) + "/");
            System.out.print(exponentList.get(i) + 1);
            System.out.print("x^" + exponentValue);
            System.out.print(operationList.get(i));
            }
      }
   }
   
   public void differentiate() {
      int length = coefficientList.size();
      for (int i = 0; i < length; i++) {
         if (coefficientList.get(i) != null) {
            int coefficientValue = coefficientList.get(i);
            int exponentValue = exponentList.get(i);
            coefficientList.set(i, coefficientValue * exponentValue);
            exponentList.set(i, exponentValue - 1);
         }
      }
   }  
   
   public void simplify(String[] polynomial) {
      for (int i = 1; i <= polynomial.length - 1; i++) {
         if (polynomial[i].equals("*") || polynomial[i].equals("/")) {
            muldiv(i, polynomial);
         }  
      }
      for (int i = 1; i <= polynomial.length - 1; i++) {
         if (polynomial[i].equals("+") || polynomial[i].equals("-")) {
            addsub(i, polynomial);
         }
      }
   }
   
   public void print(String[] polynomial) {
      operationList.add("");
      String s = "";
      int x = 0;
      System.out.println("Your new expression is:");
      for (int i = 0; i < coefficientList.size(); i++) {
         if(coefficientList.get(i) != null) {
            if(!fractionList.isEmpty()) {
               if (i == fractionList.get(x)) {
                  s += coefficientList.get(i) + "/";
                  i++;
                     if (coefficientList.get(i-1) < 0) {
                        s += -1 * coefficientList.get(i) + "x^" + exponentList.get(i);
                     } else {
                     s += coefficientList.get(i) + "x^" + exponentList.get(i);
                     }
                  x++;
               } 
            } else {
            s += coefficientList.get(i) + "x^" + exponentList.get(i);
            }
         }
         s += operationList.get(i);
      }   
      System.out.println(s);
   }
   
   public void addsub(int i, String[] polynomial) {
      int first = (i-1)/2;
      int second = ((i-1)/2)+1;
      if (exponentList.get(first) == exponentList.get(second)) {
         if (polynomial[i].equals("+")) {
            int co = coefficientList.get(first) + coefficientList.get(second);
            coefficientList.set(second, co);
         } else if (polynomial[i].equals("-")) {
            int co = coefficientList.get(first) - coefficientList.get(second);
            coefficientList.set(second, co);
         }
         coefficientList.set(first, null);
         exponentList.set(first, null);
         operationList.set(first, "");
      }
   }
   
   public void muldiv(int i, String[] polynomial) {
      int first = (i-1)/2;
      int second = ((i-1)/2)+1;
      if (polynomial[i].equals("*")) {
         int co = coefficientList.get(first) * coefficientList.get(second);
         int ex = exponentList.get(first) + exponentList.get(second);
         coefficientList.set(first, null);
         coefficientList.set(second, co);
         exponentList.set(first, null);
         exponentList.set(second, ex);
      }  else {
            int ex = exponentList.get(first) - exponentList.get(second);
            frac(i, polynomial, first, second, ex);
      }
         operationList.set(first, "");
   }
   
   public void frac(int i, String[] polynomial, int first, int second, int ex) {
      int numerator = coefficientList.get(first);
      int denominator = coefficientList.get(second);
      while (numerator != denominator && numerator > 0) {
         if (numerator > denominator) {
            numerator = numerator - denominator;
         } else {
            denominator = denominator - numerator;
         }
      }
         int x = coefficientList.get(first) / numerator;
         int y = coefficientList.get(second) / numerator;
         if (x % y == 0) {
            coefficientList.set(first, null);
            coefficientList.set(second, x);
            exponentList.set(first, null);
            exponentList.set(second, ex);
            operationList.set(first, "");
         } else {
            coefficientList.set(first, x);
            coefficientList.set(second, y);
            exponentList.set(first, ex);
            exponentList.set(second, ex);
            fractionList.add(first);
      }
   }
}