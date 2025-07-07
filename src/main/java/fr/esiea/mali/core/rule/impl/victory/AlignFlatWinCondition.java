package fr.esiea.mali.core.rule.impl.victory;

import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.VictoryCondition;

import java.util.*;

public class AlignFlatWinCondition implements VictoryCondition {
    @Override
    public Optional<IPlayer> checkVictory(IGameState state) {
        int n = state.getBoard().getSize();
        Map<IPlayer, Boolean> results = new HashMap<>();
        IPlayer actualPlayer = state.getCurrentPlayer();

        state.getPlayers().forEach(p -> results.put(p, searchIterative(state, p, n, true) || searchIterative(state, p, n, false)));

        long winners = results.values().stream().filter(b -> b).count();


        if(winners == 1) {
            return results.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .findFirst();
        }

        if(winners == results.size()) {
            return Optional.of(actualPlayer);
        }

        return Optional.empty();
    }

    private boolean searchIterative(IGameState state,
                                    IPlayer player,
                                    int n,
                                    boolean vertical)
    {
        for (int i = 0; i < n; i++) {
            Position start = vertical
                    ? new Position(0, i)  // bord nord
                    : new Position(i, 0); // bord ouest

            Deque<Position> stack   = new ArrayDeque<>();
            Set<Position>   visited = new HashSet<>();
            stack.push(start);

            while (!stack.isEmpty()) {
                Position pos = stack.pop();
                if (!state.getBoard().isInside(pos) || !visited.add(pos)) {
                    continue;
                }

                // top-piece doit être flat ou capstone du mover
                var pile = state.getBoard().getStackAt(pos);
                if (pile.isEmpty()) {
                    continue;
                }
                IPiece top = pile.getFirst();
                if (!top.getColor().equals(player.getColor())) {
                    continue;
                }
                PieceKind kind = top.getKind();
                if (kind != PieceKind.FLAT && kind != PieceKind.CAPSTONE) {
                    continue;
                }

                // condition d’arrivée
                if ((vertical  && pos.row() == n - 1)
                        || (!vertical && pos.col() == n - 1)) {
                    return true;
                }

                // empiler les voisins 4-directions
                for (Position nbr : state.getBoard().getNeighbors(pos)) {
                    if (!visited.contains(nbr)) {
                        stack.push(nbr);
                    }
                }
            }
        }
        return false;
    }

    private Position createPosition(int value, boolean vertical) {

        if (vertical) {
            return new Position(0, value);
        }

        return new Position(value, 0);
    }

    private boolean dfs(IGameState state,
                        IPlayer player,
                        Position pos,
                        Set<Position> visited,
                        boolean vertical,
                        int n) {
        if (!state.getBoard().isInside(pos) || visited.contains(pos)) {
            return false;
        }

        visited.add(pos);

        if (!connects(state, pos, player)) {
            return false;
        }

        if ((vertical && pos.row() == n - 1) ||
                (!vertical && pos.col() == n - 1)) {
            return true;
        }

        for (Position nbr : state.getBoard().getNeighbors(pos)) {
            if (dfs(state, player, nbr, visited, vertical, n)) {
                return true;
            }
        }
        return false;
    }

    private boolean connects(IGameState state, Position pos, IPlayer player) {
        var stack = state.getBoard().getStackAt(pos);
        if (stack.isEmpty()) {
            return false;
        }

        IPiece top = stack.peekLast();
        if (!top.getColor().equals(player.getColor())) {
            return false;
        }

        PieceKind k = top.getKind();
        return k == PieceKind.FLAT || k == PieceKind.CAPSTONE;
    }

}
