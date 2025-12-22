package controller.verification;

import java.util.ArrayList;
import java.util.List;

public class VerificationResult {
    private final GameState state;
    private final List<Cell> duplicateCells;

    public VerificationResult(GameState state, List<Cell> duplicateCells) {
        this.state = state;
        this.duplicateCells = (duplicateCells != null) ? duplicateCells : new ArrayList<>();
    }

    public static VerificationResult valid() {
        return new VerificationResult(GameState.VALID, new ArrayList<>());
    }

    public static VerificationResult incomplete() {
        return new VerificationResult(GameState.INCOMPLETE, new ArrayList<>());
    }

    public static VerificationResult invalid(List<Cell> duplicates) {
        return new VerificationResult(GameState.INVALID, duplicates);
    }

    public GameState getState() { return state; }
    public List<Cell> getDuplicateCells() { return duplicateCells; }
}