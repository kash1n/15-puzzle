package ru.kashin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PuzzleState {
    private final Integer _size;
    private final Integer[] _positions;
    private Integer _emptyPos;

    public PuzzleState(Integer[] positions) {
        _positions = positions;
        _size = (int) Math.sqrt(positions.length);
        for (int i = 0; i < _positions.length; i++) {
            if (_positions[i] == 0) {
                _emptyPos = i;
                break;
            }
        }
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        PuzzleState otherPuzzle = (PuzzleState) other;
        return Arrays.equals(this._positions, otherPuzzle._positions);
    }

    public Integer at(int row, int col) {
        return _positions[row * _size + col];
    }

    public int manhattan() {
        int res = 0;
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                Integer value = at(i, j);
                if (value == 0)
                    continue;
                int diffRows = Math.abs(targetRowFor(value) - i);
                int diffCols = Math.abs(targetColumnFor(value) - j);
                res += diffRows + diffCols;
            }
        }
        return res;
    }

    private int targetRowFor(int value) {
        if (value == 0)
            return _size - 1;
        return (value - 1) / _size;
    }

    private int targetColumnFor(int value) {
        if (value % _size == 0)
            return _size - 1;
        return (value % _size) - 1;
    }

    public ArrayList<PuzzleState> neighbors() {
        ArrayList<PuzzleState> list = new ArrayList<>();

        PuzzleState up = moveUp();
        if (up != this)
            list.add(up);

        PuzzleState down = moveDown();
        if (down != this)
            list.add(down);

        PuzzleState left = moveLeft();
        if (left != this)
            list.add(left);

        PuzzleState right = moveRight();
        if (right != this)
            list.add(right);

        return list;
    }

    public PuzzleState moveLeft() {
        if (_emptyPos % _size == 0)
            return this;
        Integer[] newPositions = _positions.clone();
        newPositions[_emptyPos] = newPositions[_emptyPos - 1];
        newPositions[_emptyPos - 1] = 0;
        return new PuzzleState(newPositions);
    }

    public PuzzleState moveRight() {
        if (_emptyPos % _size == _size - 1)
            return this;
        Integer[] newPositions = _positions.clone();
        newPositions[_emptyPos] = newPositions[_emptyPos + 1];
        newPositions[_emptyPos + 1] = 0;
        return new PuzzleState(newPositions);
    }

    public PuzzleState moveUp() {
        if (_emptyPos < _size)
            return this;
        Integer[] newPositions = _positions.clone();
        newPositions[_emptyPos] = newPositions[_emptyPos - _size];
        newPositions[_emptyPos - _size] = 0;
        return new PuzzleState(newPositions);
    }

    public PuzzleState moveDown() {
        if (_emptyPos > _size * (_size - 1) - 1)
            return this;
        Integer[] newPositions = _positions.clone();
        newPositions[_emptyPos] = newPositions[_emptyPos + _size];
        newPositions[_emptyPos + _size] = 0;
        return new PuzzleState(newPositions);
    }

    public PuzzleState randomMove() {
        Random rnd = new Random();
        int direction = rnd.nextInt(4);
        return switch (direction) {
            case 0 -> this.moveLeft();
            case 1 -> this.moveRight();
            case 2 -> this.moveUp();
            case 3 -> this.moveDown();
            default -> this;
        };
    }

    public void print() {
        for (int i = 0; i < _size; i++) {
            System.out.print("|");
            for (int j = 0; j < _size; j++) {
                if (at(i, j) == 0)
                    System.out.print("   ");
                else
                    System.out.printf("%2d ", at(i, j));
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public static PuzzleState Solution(int n) {
        Integer[] positions = new Integer[n * n];
        for (int i = 0; i < n * n - 1; i++)
            positions[i] = i + 1;
        positions[n * n - 1] = 0;
        return new PuzzleState(positions);
    }
}
