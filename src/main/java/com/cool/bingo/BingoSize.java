package com.cool.bingo;

import com.cool.bingo.number.BingoNumber;
import com.cool.exception.InvalidBingoSizeException;
import com.cool.util.StringUtils;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Supplier;

public class BingoSize {
    private static final int MIN_VALUE = 3;
    private static final int MAX_VALUE = 8;
    private static final Random RANDOM = new Random();

    private final int value;

    private BingoSize(int value) {
        this.value = value;
    }

    public static BingoSize from(String bingoSize) {
        StringUtils.validateNonNullAndNotEmpty(bingoSize);
        validateNumberFormat(bingoSize);
        int value = Integer.parseInt(bingoSize);
        validateNumberRange(value);

        return new BingoSize(value);
    }

    private static void validateNumberFormat(String bingoSize) {
        Supplier<RuntimeException> exceptionSupplier =
                () -> new InvalidBingoSizeException("빙고판의 크기로 숫자가 아닌 값이 들어왔습니다 -> " + bingoSize);
        StringUtils.validateNumberFormat(exceptionSupplier, bingoSize);
    }

    private static void validateNumberRange(int bingoSize) {
        if (bingoSize < MIN_VALUE || MAX_VALUE < bingoSize) {
            StringBuilder st = new StringBuilder();
            st.append("빙고판 크기인 숫자가 정해진 범위를 넘었습니다. -> ")
                    .append(bingoSize)
                    .append(" / 최소값 -> ")
                    .append(MIN_VALUE)
                    .append(", 최대값 -> ")
                    .append(MAX_VALUE);

            throw new InvalidBingoSizeException(st.toString());
        }
    }

    public boolean isBiggerThanMaxNumber(int number) {
        int maxNumber = getSize() * 2;
        return maxNumber < number;
    }

    public boolean isBiggerThan(int size) {
        return size < getSize();
    }

    public boolean isSmallerThan(int size) {
        return getSize() < size;
    }

    public boolean isSmallerAndEqualThan(int countMarkedLine) {
        return this.value <= countMarkedLine;
    }

    public int createRandomNumber() {
        return Math.min(RANDOM.nextInt(getSize() * 2) + 1, getSize() * 2);
    }

    public BingoNumber[][] create2dBingoNumbers(Iterator<BingoNumber> iterator) {
        BingoNumber[][] bingoNumbers = new BingoNumber[this.value][this.value];

        for (int row = 0; row < bingoNumbers.length; row++) {
            for (int col = 0; col < bingoNumbers[row].length; col++) {
                bingoNumbers[row][col] = iterator.next();
            }
        }

        return bingoNumbers;
    }

    public int getSize() {
        return (int) Math.pow(this.value, 2);
    }

    public int getValue() {
        return value;
    }
}
