package com.rfachrur.nqueenproblem.geneticsalgorithm;

import java.util.Comparator;

/**
 * Created by FACHRUR on 10/6/2016.
 *
 */

public class FitComparator implements Comparator<Chromosome> {

    @Override
    public int compare(Chromosome x, Chromosome y) {
        if (x.fitness == y.fitness) {
            return 0;
        } else if (x.fitness < y.fitness) {
            return 1;
        } else {
            return -1;
        }
    }

}
