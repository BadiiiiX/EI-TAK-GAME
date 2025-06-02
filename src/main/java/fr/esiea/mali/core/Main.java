package fr.esiea.mali.core;

import fr.esiea.mali.core.model.board.BoardSize;
import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.service.factory.GameFactory;
import fr.esiea.mali.core.service.impl.Game;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Game game = GameFactory.create(BoardSize.MEDIUM, "BadiiiX", "Karim");
        game.start();

        IGameState state = game.getState();
        System.out.println("=== Partie démarrée ===");
        printState(state);

        try {
            Move move1 = Move.placementStone(new Position(0, 0), false);
            System.out.println("\nJoueur " + state.getCurrentPlayer().getColor() + " joue : " + move1);
            game.playMove(move1);
            state = game.getState();
            printState(state);

            Move move2 = Move.placementStone(new Position(1, 0), true);
            System.out.println("\nJoueur " + state.getCurrentPlayer().getColor() + " joue : " + move2);
            game.playMove(move2);
            state = game.getState();
            printState(state);

            Move move3 = Move.placementCapstone(new Position(2, 0));
            System.out.println("\nJoueur " + state.getCurrentPlayer().getColor() + " joue : " + move3);
            game.playMove(move3);
            state = game.getState();
            printState(state);

            Move move4 = Move.slide(
                    new Position(0, 0),
                    new Position(0, 2),
                    1,
                    List.of(1, 0)
            );
            System.out.println("\nJoueur " + state.getCurrentPlayer().getColor() + " joue : " + move4);
            game.playMove(move4);
            state = game.getState();
            printState(state);

            System.out.println("\nAnnulation du dernier coup...");
            game.undo();
            state = game.getState();
            printState(state);

        } catch (Exception ex) {
            System.err.println("Coup invalide : " + ex.getMessage());
        }
    }

    private static void printState(IGameState state) {
        System.out.println("Tour n°" + state.getHistory().size() + " | Joueur courant : "
                + state.getCurrentPlayer().getColor());
        System.out.println("Plateau :");
        System.out.println(state.getBoard()); // suppose que Board.toString() affiche la grille
    }
}
