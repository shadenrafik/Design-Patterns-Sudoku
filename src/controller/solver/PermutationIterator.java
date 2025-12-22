package controller.solver;

import java.util.NoSuchElementException;

public class PermutationIterator {
    private final int lenght;
    private final int[] currentPermutation;
    private boolean hasNext=true;

    public PermutationIterator(int lenght) {
        this.lenght = lenght;
        this.currentPermutation = new int[lenght];
    
        for (int i = 0; i < lenght; i++) {
            currentPermutation[i] = 1;
        }
        
    }
    public boolean hasNext() {
        return hasNext;
    }

    public int[] next() {
        if(!hasNext) {
            throw new NoSuchElementException();
        }
        int [] result = currentPermutation.clone();
        generateNextPermutation();
        return result;

    }

    private void generateNextPermutation() {
        int i = lenght - 1;
        while (i >= 0 ) {
            if(currentPermutation[i] < 9) {
                currentPermutation[i]++;
                return;
            } else {
                currentPermutation[i] = 1;
                i--;
            }
        }
        hasNext = false;
    }
}
