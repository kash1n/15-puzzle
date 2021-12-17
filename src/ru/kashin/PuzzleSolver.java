package ru.kashin;

import java.util.PriorityQueue;
import java.util.Stack;

public class PuzzleSolver {
    private static int _a;
    private static int _b;
    private final PriorityQueue<SolverState> pq;
    private PuzzleState initial;
    private final PuzzleState goal;

    // priority = a * Manhattan + b * moves
    public PuzzleSolver(int size, int a, int b) {
        _a = a;
        _b = b;
        pq = new PriorityQueue<>();
        goal = PuzzleState.Solution(size);
    }

    public boolean solve(PuzzleState initialState) {
        initial = initialState;
        pq.clear();
        pq.add(new SolverState(initial, 0, null));
        while (!pq.isEmpty() && !pq.peek().board.equals(goal)) {
            SolverState minState = pq.poll();
            var neighbors = minState.board.neighbors();
            for (PuzzleState neighbor : neighbors) {
                if (minState.moves == 0) {
                    pq.add(new SolverState(neighbor, minState.moves + 1, minState));
                } else if (!neighbor.equals(minState.previousState.board)) {
                    pq.add(new SolverState(neighbor, minState.moves + 1, minState));
                }
            }
        }
        return isSolved();
    }

    public boolean isSolved() {
        return !pq.isEmpty() && pq.peek().board.equals(goal);
    }

    public int getMovesCount() {
        if (!isSolved ())
            return -1;
        assert pq.peek() != null;
        return pq.peek().moves;
    }

    public void showSolutionPath() {
        if (isSolved()) {
            Stack<PuzzleState> solutionStack = getPathToSolution();
            while (!solutionStack.empty()) {
                solutionStack.pop().print();
            }
            System.out.println("Number of steps: " + getMovesCount());
        }
        else {
            System.out.println("No solution!");
        }
    }

    public Stack<PuzzleState> getPathToSolution() {
        if (!isSolved() || pq.isEmpty())
            return null;

        Stack<PuzzleState> stackSolution = new Stack<>();
        SolverState current = pq.peek();
        while (current.previousState != null) {
            stackSolution.push(current.board);
            current = current.previousState;
        }
        stackSolution.push(initial);
        return stackSolution;
    }

    private static class SolverState implements Comparable<SolverState> {
        private final PuzzleState board;
        private final int moves;
        private final int priority;
        private final SolverState previousState;

        public SolverState(PuzzleState puzzleState, int moves, SolverState previousState) {
            board = puzzleState;
            this.moves = moves;
            priority = _a * board.manhattan() + _b * moves;
            this.previousState = previousState;
        }

        public int compareTo(SolverState otherState) {
            return (this.priority - otherState.priority);
        }
    }
}
