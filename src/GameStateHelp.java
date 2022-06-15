public class GameStateHelp {
    public enum GameState {
        WIN, DEFEAT, CONTINUE
    }

    //检验游戏 胜利/失败/继续
    public GameState checkGameState(Tile[][] tiles) {
        //检验是否胜利
        for (Tile[] t : tiles
        ) {
            for (Tile tile : t
            ) {
                if (tile.getNum() == 2048) {
                    return GameState.WIN;
                }
            }
        }
        //检验是否失败
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = tiles[i][j].getNum();
                //上
                if (i - 1 >= 0 && tiles[i - 1][j].getNum() == num) {
                    return GameState.CONTINUE;
                }
                //下
                if (i + 1 < 4 && tiles[i + 1][j].getNum() == num) {
                    return GameState.CONTINUE;
                }
                //左
                if (j - 1 >= 0 && tiles[i][j - 1].getNum() == num) {
                    return GameState.CONTINUE;
                }
                //右
                if (j + 1 < 4 && tiles[i][j + 1].getNum() == num) {
                    return GameState.CONTINUE;
                }
            }
        }
        return GameState.DEFEAT;
    }
}
