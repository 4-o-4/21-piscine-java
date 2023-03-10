package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int n) {
        int i = 2;
        if (n <= 1) {
            throw new IllegalArgumentException();
        } else if (n == 2 || n == 3) {
            return true;
        } else {
            while (i * i <= n && i != 0) {
                if (n % i == 0)
                    i = 0;
                else
                    i++;
            }
            return i != 0;
        }
    }

    public int digitsSum(int number) {
        return number % 10
                + number / 10 % 10
                + number / 100 % 10
                + number / 1000 % 10
                + number / 10000 % 10
                + number / 100000 % 10;
    }
}
