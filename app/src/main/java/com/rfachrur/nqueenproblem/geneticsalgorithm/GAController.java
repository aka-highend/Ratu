package com.rfachrur.nqueenproblem.geneticsalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by FACHRUR on 10/6/2016.
 *
 */

public class GAController {

    // max collisions can be 28
    public static final int MAX_FIT = 28;

    private Random random;

    public GAController() {
        random = new Random(new Random().nextInt(Integer.MAX_VALUE));
    }

    public final void DoMating(ObjectReference<ArrayList<Chromosome>> initPopulation,
                               int generations, double probCrossver, double probMutation) {
        int totalFitness = 0;
        ObjectReference<Integer> tempRef_totalFitness = new ObjectReference<>(totalFitness);
        CalcFitness(initPopulation, tempRef_totalFitness);
        totalFitness = tempRef_totalFitness.value;
        for (int generation = 0; generation < generations; generation++) {
            PrepareRuletteWheel(initPopulation, totalFitness);
            Crossover(initPopulation, probCrossver);
            Mutate(initPopulation, probMutation);
            ObjectReference<Integer> tempRef_totalFitness2 = new ObjectReference<>(totalFitness);
            CalcFitness(initPopulation, tempRef_totalFitness2);
            totalFitness = tempRef_totalFitness2.value;
            if (initPopulation.value.get(initPopulation.value.size() - 1).fitness == 28) {
                break;
            }
        }
        Collections.sort(initPopulation.value, new FitComparator());
    }

    public final void Crossover(ObjectReference<ArrayList<Chromosome>> parents, double probability) {
        ArrayList<Chromosome> offspring = new ArrayList<>();
        for (int i = 0; i < parents.value.size(); i++) {
            if (Assay(probability)) //if the chance is to crossover
            {
                Chromosome parentX = AssayRuletteWheel(parents.value).clone();
                Chromosome parentY = AssayRuletteWheel(parents.value).clone();
                ArrayList<Integer> child = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    if (Assay(0.5)) //select from parentX
                    {
                        for (int k = 0; k < parentX.genes.length; k++) {
                            //instead of deleting the similar genes from parents select the next non-contained number
                            if (!child.contains(parentX.genes[k])) {
                                child.add(parentX.genes[k]);
                                break;
                            }
                        }
                    } else //select from parentY
                    {
                        for (int k = 0; k < parentY.genes.length; k++) {
                            //instead of deleting the similar genes from parents select the next non-contained number
                            if (!child.contains(parentY.genes[k])) {
                                child.add(parentY.genes[k]);
                                break;
                            }
                        }
                    }
                }
                Chromosome offSpr = new Chromosome();
                offSpr.genes = new int[child.size()];
                for (int m = 0; m < child.size(); m++) {
                    offSpr.genes[m] = child.get(m);
                }
                offspring.add(offSpr.clone());
            } else //else the chance is to clonning
            {
                Chromosome parentX = AssayRuletteWheel(parents.value).clone();
                offspring.add(parentX.clone());
            }
        }
        while (offspring.size() > parents.value.size()) {
            offspring.remove((int) GetRandomVal(0, offspring.size() - 1));
        }
        parents.value = offspring;
    }

    private void PrepareRuletteWheel(ObjectReference<ArrayList<Chromosome>> parents, int total) {
        int currentTotalFitness = 0;
        for (int i = 0; i < parents.value.size(); i++) {
            currentTotalFitness += parents.value.get(i).fitness;
            Chromosome temp = parents.value.get(i).clone();
            temp.cumAvgFitness = currentTotalFitness / (double) total;
            parents.value.set(i, temp.clone());
        }
    }

    private Chromosome AssayRuletteWheel(ArrayList<Chromosome> parents) {
        Chromosome selection = parents.get(0).clone();
        double probability = random.nextDouble();
        for (int i = 0; i < parents.size(); i++) {
            selection = parents.get(i).clone();
            if (parents.get(i).cumAvgFitness > probability) {
                break;
            }

        }
        return selection;
    }

    public final void Mutate(ObjectReference<ArrayList<Chromosome>> parents, double probability) {
        ArrayList<Chromosome> offsprings = new ArrayList<>();

        for (int i = 0; i < parents.value.size(); i++) {
            Chromosome offspring = parents.value.get(i).clone();
            for (int mutatePosition = 0; mutatePosition < 8; mutatePosition++) {
                if (Assay(probability)) //if the chance is to mutate
                {
                    int newGeneIndex = (int) (GetRandomVal(0, 6) + 0.5);
                    if (newGeneIndex >= mutatePosition) {
                        newGeneIndex += 1;
                    }
                    int swapTemp = offspring.genes[mutatePosition];
                    offspring.genes[mutatePosition] = offspring.genes[newGeneIndex];
                    offspring.genes[newGeneIndex] = swapTemp;
                }
            }

            offsprings.add(offspring.clone());
        }

        parents.value = offsprings;
    }

    public final double GetRandomVal(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    private boolean Assay(double probability) {
        return random.nextDouble() < probability;
    }

    public final void CalcFitness(ObjectReference<ArrayList<Chromosome>> chromosome, ObjectReference<Integer> totalFitness) {
        int collisions = 0;
        totalFitness.value = 0;
        for (int k = 0; k < chromosome.value.size(); k++) {
            for (int i = 0; i < chromosome.value.get(k).genes.length - 1; i++) {
                int y = chromosome.value.get(k).genes[i];
                for (int j = i + 1; j < chromosome.value.get(k).genes.length; j++) {
                    if (Math.abs(j - i) == Math.abs(chromosome.value.get(k).genes[j] - y)) {
                        collisions++;
                    }
                }
            }
            Chromosome temp = chromosome.value.get(k).clone();
            temp.fitness = MAX_FIT - collisions;
            chromosome.value.set(k, temp.clone());
            totalFitness.value += chromosome.value.get(k).fitness;
            collisions = 0;
        }
    }

}
