package com.rfachrur.ratu.geneticsalgorithm;

import java.util.Comparator;



class FitComparator implements Comparator<Chromosome> {

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
