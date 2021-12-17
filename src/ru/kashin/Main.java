package ru.kashin;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int puzzleSize = 4;
        System.out.println("Puzzle size: " + puzzleSize);
        PuzzleSolver solver = new PuzzleSolver(puzzleSize, 3, 1);
        if (solver.solve(generatePuzzle(puzzleSize))) {
            solver.showSolutionPath();
        }

//        System.out.println("Metric: a * Manhattan + b * movesCount");
//        System.out.println("Average number of steps for solving the puzzle depending on 'a' and 'b' coeffs:");
//        System.out.print("a\\b| ");
//        for (int b = 1; b <= 15; b++)
//            System.out.printf("   %d   ", b);
//        System.out.println();
//        for (int a = 1; a <= 15; a++) {
//            System.out.printf("%2d | ", a);
//            for (int b = 1; b <= 15; b++) {
//                boolean testPassed = test(puzzleSize, a, b, 30, false);
//                if (!testPassed)
//                    System.out.println("FAIL");
//            }
//            System.out.println();
//        }
//        System.out.println("Average time for solving the puzzle depending on 'a' and 'b' coeffs:");
//        System.out.print("a\\b| ");
//        for (int b = 1; b <= 15; b++)
//            System.out.printf("   %d   ", b);
//        System.out.println();
//        for (int a = 1; a <= 15; a++) {
//            System.out.printf("%2d | ", a);
//            for (int b = 1; b <= 15; b++) {
//                boolean testPassed = test(puzzleSize, a, b, 30, true);
//                if (!testPassed)
//                    System.out.println("FAIL");
//            }
//            System.out.println();
//        }
    }

    private static boolean test (int puzzleSize, int a, int b, int testSize, boolean printTime) {
        PuzzleSolver solver = new PuzzleSolver(puzzleSize, a, b);
        double[] times = new double[testSize];
        int[] moves = new int[testSize];
        for (int testIdx = 0; testIdx < testSize; testIdx++) {
            long startTime = System.currentTimeMillis();
            solver.solve(generatePuzzle(puzzleSize));
            long stopTime = System.currentTimeMillis();
            times[testIdx] = (stopTime - startTime) / 1000.0;
            if (!solver.isSolved())
                return false;
            moves[testIdx] = solver.getMovesCount();
        }
//        System.out.printf("(t = %e, m = %.1f)  ",
//                Arrays.stream(times).average().getAsDouble(),
//                Arrays.stream(moves).average().getAsDouble());
        if (printTime)
            System.out.printf("%.3e  ", Arrays.stream(times).average().getAsDouble());
        else
            System.out.printf("%3.2f  ", Arrays.stream(moves).average().getAsDouble());
        return true;
    }

    private static PuzzleState generatePuzzle(int size) {
        PuzzleState puzzle = PuzzleState.Solution (size);
        for (int i = 0; i < 1000; i++) {
            puzzle = puzzle.randomMove();
        }
        return puzzle;
    }
}
