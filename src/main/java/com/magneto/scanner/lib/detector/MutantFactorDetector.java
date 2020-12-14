package com.magneto.scanner.lib.detector;


public class MutantFactorDetector {
    private Monitor monitor;

    public MutantFactorDetector() {
        this.monitor = Monitor.buildMutantMonitor();
    }

    public MutantFactorDetector(Monitor monitor) {
        this.monitor = monitor;
    }

    public static boolean isMutant(String[] data) {
        MutantFactorDetector detector = new MutantFactorDetector();

        return detector.scan(data);
    }

    public boolean scan(String[] data) {
        int nSize = data.length;
        for (int i = 0; i < nSize && !this.monitor.hashEnoughFactor(); i++) {
            // Horizontal
            scan(data, 0, k -> k + 1, i, k -> k);

            // Vertical
            scan(data, i, k -> k, 0, k -> k + 1);

            // Oblique Desc
            scan(data, 0, x -> x + 1, nSize - i, y -> y + 1);
            scan(data, i, x -> x + 1, 0, y -> y + 1);

            // Oblique Asc
            scan(data, 0, x -> x + 1, i, y -> y - 1);
            scan(data, i + 1, x -> x + 1, nSize - 1, y -> y - 1);
        }

        return this.monitor.hashEnoughFactor();
    }

    private void scan(String[] data, int prevX, IFactorIndex nextXFunc, int prevY, IFactorIndex nextYFunc) {
        int nSize = data.length;

        if (!isValidIndex(nSize, prevX, prevY)) return;

        String prevFactor = String.valueOf(data[prevY].charAt(prevX));
        int itemsOccurrences = 1;

        int nextX = nextXFunc.next(prevX);
        int nextY = nextYFunc.next(prevY);

        while (isValidIndex(nSize, nextX, nextY) && isFactorReachable(nSize, prevX, prevY, nextX, nextY, itemsOccurrences)) {
            if (this.monitor.hashEnoughFactor()) break;

            String currentFactor = String.valueOf(data[nextY].charAt(nextX));
            if (currentFactor.equals(prevFactor)) {
                itemsOccurrences++;

                if (itemsOccurrences == this.monitor.getFactorSize()) this.monitor.recordFactor(currentFactor);
            } else {
                itemsOccurrences = 1;
            }

            prevFactor = currentFactor;
            prevX = nextX;
            prevY = nextY;
            nextX = nextXFunc.next(prevX);
            nextY = nextYFunc.next(prevY);
        }
    }

    private boolean isValidIndex(int nSize, int nextX, int nextY) {
        return isValidPosition(nSize, nextX) && isValidPosition(nSize, nextY);
    }

    private boolean isValidPosition(int nSize, int position) {
        return position >= 0 && position < nSize;
    }

    private boolean isFactorReachable(int nSize, int prevX, int prevY, int nextX, int nextY, int currentRepeatingStreamSize) {
        int remainingIterations = calcRemainingIterations(nSize, prevX, prevY, nextX, nextY);
        int remainingOccurrences = monitor.getFactorSize() - currentRepeatingStreamSize;

        return remainingIterations >= remainingOccurrences;
    }

    private int calcRemainingIterations(int nSize, int prevX, int prevY, int nextX, int nextY) {
        int remainingCharsSize;
        int rowDiff = nextY - prevY;

        if (rowDiff != 0) {
            int m = (nextX - prevX) / rowDiff;

            if (m > 0) {
                // Diagonal Desc
                if (nextX >= nextY) remainingCharsSize = nSize - nextX;
                else remainingCharsSize = nSize - nextY;
            } else {
                // Diagonal Asc
                if (m < 0) {
                    if (prevX + prevY < nSize) remainingCharsSize = prevY;
                    else remainingCharsSize = nSize - nextX;
                } // Vertical
                else remainingCharsSize = nSize - nextY;
            }
        } // Horizontal
        else remainingCharsSize = nSize - nextX;

        return remainingCharsSize;
    }
}
