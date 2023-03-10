package edu.school21.numbers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    NumberWorker numberWorker = new NumberWorker();

    @DisplayName("Простое число")
    @ParameterizedTest(name = "Число {0} простое")
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29})
    void isPrimeForPrimes(int argument) {
        assertTrue(numberWorker.isPrime(argument), "Составное число");
    }

    @DisplayName("Составное число")
    @ParameterizedTest(name = "Число {0} составное")
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18})
    void isPrimeForNotPrimes(int argument) {
        assertFalse(numberWorker.isPrime(argument), "Простое число");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1})
    void isPrimeForIncorrectNumbers(int argument) {
        assertThrows(IllegalArgumentException.class, () -> numberWorker.isPrime(argument));
    }

    @ParameterizedTest(name = "{index}: {0} = {1}")
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int number, int sum) {
        assertEquals(sum, numberWorker.digitsSum(number));
    }
}
