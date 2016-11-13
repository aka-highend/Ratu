package com.rfachrur.nqueenproblem.geneticsalgorithm;

/**
 * Created by FACHRUR on 10/6/2016.
 *
 */

public final class Chromosome {

    public int[] genes;
    public int fitness;
    public double cumAvgFitness;

    public Chromosome clone() {
        Chromosome varCopy = new Chromosome();
        varCopy.genes = this.genes;
        varCopy.fitness = this.fitness;
        varCopy.cumAvgFitness = this.cumAvgFitness;
        return varCopy;
    }

}
