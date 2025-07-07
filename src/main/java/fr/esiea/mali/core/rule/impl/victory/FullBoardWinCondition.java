package fr.esiea.mali.core.rule.impl.victory;

import fr.esiea.mali.core.model.board.IBoard;
import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.VictoryCondition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FullBoardWinCondition extends AbstractCountFlatWinCondition implements VictoryCondition {
    @Override
    public Optional<IPlayer> checkVictory(IGameState state) {

        if(!isBoardFull(state.getBoard())) {
            return Optional.empty();
        }

        List<Map.Entry<IPlayer, Long>> sorted = countFlatPlayed(state).entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .toList();
        System.out.println("winner ! 3");

        return Optional.of(sorted.getFirst().getKey());
    }


    private boolean isBoardFull(IBoard board) {

        int n = board.getSize();

        boolean isFull = true;
        for(int r = 0; r < n && isFull; r++) {
            for (int c = 0; c < n; c++) {
                if(board.getStackAt(new Position(r, c)).isEmpty()) {
                    isFull = false;
                    break;
                }
            }
        }

        return isFull;
    }
}
