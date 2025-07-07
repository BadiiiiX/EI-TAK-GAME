package fr.esiea.mali.core.rule.impl.victory;

import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.VictoryCondition;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCountFlatWinCondition implements VictoryCondition {

    protected Map<IPlayer, Long> countFlatPlayed(IGameState state) {
        return state.getPlayers().stream()
                .collect(Collectors.toMap(
                        p -> p,
                        p -> countVisibleFlats(state, p)
                ));
    }

    private long countVisibleFlats(IGameState state, IPlayer player) {
        int n = state.getBoard().getSize();
        long count = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                var stack = state.getBoard().getStackAt(new Position(r, c));
                if (!stack.isEmpty()) {
                    IPiece top = stack.peekLast();
                    if (top.getColor().equals(player.getColor())
                            && top.getKind() == PieceKind.FLAT) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

}
