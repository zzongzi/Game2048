import java.util.Random;

public class Game2048Helper {
    private Random random = new Random();

    public enum GameState {
        WIN, DEFEAT, CONTINUE
    }

    public Game2048Helper() {
    }

    //初始化默认方块 开始游戏时生成 2 个 2 的数字块
    public void initDefaultTile(Tile[][] tiles) {
        int num = 0;
        while (true) {
            int x = random.nextInt(4);
            int y = random.nextInt(4);
            Tile tile = tiles[x][y];
            if (tile.getNum() != 2) {
                tile.setNum(2);
                tile.setColorIndex(1);
                tile.setColor(tile.getColorTable()[tile.getColorIndex()]);
                num++;
            }
            if (num == 2) {
                break;
            }
        }
    }

    //判断是否含有空方块
    public boolean checkNullTile(Tile[][] tiles) {
        for (Tile[] t : tiles
        ) {
            for (Tile tile : t
            ) {
                if (tile.getNum() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //每次移动后更新空方块 在空方块中随机生成一个 2/4 数字块
    public void updateNullTile(Tile[][] tiles) {
        while (true) {
            int x = random.nextInt(4);
            int y = random.nextInt(4);
            Tile tile = tiles[x][y];
            if (tile.getNum() == 0) {
                int num = random.nextInt(2);
                if (num == 0) {
                    tile.setNum(2);
                    tile.setColorIndex(1);
                } else {
                    tile.setNum(4);
                    tile.setColorIndex(2);
                }
                tile.setColor(tile.getColorTable()[tile.getColorIndex()]);
                break;
            }
        }
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
