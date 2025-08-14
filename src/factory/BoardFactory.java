package factory;

import java.util.ArrayList;
import java.util.List;

import entities.Board;
import entities.Space;

public final class BoardFactory {
     private BoardFactory() {}

    public static Board create() {
        
        final int[][] solution = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        // Matriz que define quais células são fixas (parte do puzzle inicial).
        // `true` para números fixos, `false` para espaços que o jogador deve preencher.
        final boolean[][] fixedCells = {
            {true,  true,  false, false, true,  false, false, false, false},
            {true,  false, false, true,  true,  true,  false, false, false},
            {false, true,  true,  false, false, false, false, true,  false},
            {true,  false, false, false, true,  false, false, false, true },
            {true,  false, false, true,  false, true,  false, false, true },
            {true,  false, false, false, true,  false, false, false, true },
            {false, true,  false, false, false, false, true,  true,  false},
            {false, false, false, true,  true,  true,  false, false, true },
            {false, false, false, false, true,  false, false, true,  true }
        };

        final List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            final List<Space> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(new Space(solution[i][j], fixedCells[i][j]));
            }
            spaces.add(row);
        }
        return new Board(spaces);
    }
}
