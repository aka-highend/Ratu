package com.rfachrur.ratu.geneticsalgorithm;



public final class Chromosome {

    public int[] genes;
    public int fitness;
    double cumAvgFitness;

    public Chromosome clone() {
        Chromosome varCopy = new Chromosome();
        varCopy.genes = this.genes;
        varCopy.fitness = this.fitness;
        varCopy.cumAvgFitness = this.cumAvgFitness;
        return varCopy;
    }

}
