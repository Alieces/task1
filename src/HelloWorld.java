import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Map;


public class HelloWorld {
        public static int sumOfBigInteger(BigInteger factorialHundred){

        String value = factorialHundred.toString();
        int sum = 0;
        int i = value.length();
        for(int j = 0; j < (i - 1); j++){
            sum+=Integer.parseInt(value.substring(j,j+1));
        }
        return sum;
        }

        public static BigInteger factorial(int n){
            BigInteger factorial =  BigInteger.ONE;
            for(int i = n; i > 0; i--){
                factorial = factorial.multiply(BigInteger.valueOf(i));
            }
            return factorial;
    }
        public static long factorial(long n){
            if(n == 1 || n == 0)
                return 1;
            return n * factorial(n - 1);
        }

        public static long number_of_RightBracketSequence(long num){
            long number1 =  factorial(2*num);
            long number2 = factorial(num+1);
            long number3 = factorial(num);
            return number1/(number2*number3);
        }

    public static void main(String[] args){
        long i;
        Scanner sc = new Scanner(System.in);
        i = sc.nextLong();                                      //Get number of brackets
        System.out.println(String.format("My number %d",i));

        i = number_of_RightBracketSequence(i);      //Calculate number of "right" sequence
        System.out.println(String.format("Number of correct sequence %d",i));



        System.out.println(String.format("Sum of factorial 100 is %d",sumOfBigInteger(factorial(100)))); //Find factorial and calculate sum of number this factorial

        int test, numberCity;
        test = sc.nextInt();
        numberCity =sc.nextInt(); // Enter number test route and city
        City []city = new City[numberCity];
        for(int k = 0; k < numberCity; k++){
            city[k] = new City();
            city[k] = city[k].createCity();   ///Initializing city mass
        }
        String[] test_Find = new String[test];
        for (int j = 0; j < test; j++){
            test_Find[j] = sc.nextLine(); //Get route for which need find sum
        }
        city[0].pathFinder(test, test_Find,city); //Start of find


    }
}
