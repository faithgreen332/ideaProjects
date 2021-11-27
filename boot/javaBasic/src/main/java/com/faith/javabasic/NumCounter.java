package com.faith.javabasic;

public class NumCounter {
    private int num;
    private int sum;

    private boolean isWholeNum;

    public int getNum() {
        return num;
    }

    public int getSum() {
        return sum + num;
    }

    public boolean isWholeNum() {
        return isWholeNum;
    }

    public NumCounter(int num, int sum, boolean isWholeNum) {
        this.num = num;
        this.sum = sum;
        this.isWholeNum = isWholeNum;
    }

    public NumCounter accumulate(Character c) {
        if (Character.isDigit(c)) {
            return isWholeNum ? new NumCounter(Integer.parseInt("" + c), sum + num, false)
                    : new NumCounter(Integer.parseInt("" + num + c), sum, false);
        } else {
            return new NumCounter(0, sum + num, true);
        }
    }

    public NumCounter combine(NumCounter numCounter) {
        return new NumCounter(numCounter.num, this.sum + numCounter.getSum(), numCounter.isWholeNum());
    }

}
