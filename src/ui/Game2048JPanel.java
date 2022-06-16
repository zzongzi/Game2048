package ui;

import entity.Tile;
import util.GameStateHelper;
import util.TileHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game2048JPanel extends JPanel {
    private Tile[][] tiles = new Tile[4][4];
    private TileHelper tileHelper = new TileHelper();
    private GameStateHelper gameStateHelper = new GameStateHelper();
    private Color backgroundColor = new Color(0xBBADA0);
    private boolean isStart = false;
    private GameStateHelper.GameState gameState = GameStateHelper.GameState.CONTINUE;

    public Game2048JPanel() {
        tileHelper.initNullTile(tiles);
        initJPanel();
    }

    //初始化JPanel相关属性
    public void initJPanel() {
        setPreferredSize(new Dimension(900, 700));
        setBackground(new Color(0xFAF8EF));
        setFont(new Font("SansSerif", Font.BOLD, 48));
        setFocusable(true);

        //设置鼠标点击监听
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //当胜利/失败后 点击重新开始游戏
                if (gameState == GameStateHelper.GameState.WIN || gameState == GameStateHelper.GameState.DEFEAT) {
                    //重新初始化
                    gameState = GameStateHelper.GameState.CONTINUE;
                    isStart = false;
                    tileHelper.initNullTile(tiles);
                    //重绘
                    repaint();
                }
            }
        });

        //设置键盘监听事件
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRight();
                        break;
                }

                //若含有空方块
                if (tileHelper.checkNullTile(tiles)) {
                    //移动后随机将空方块赋为2/4的数值
                    tileHelper.updateNullTile(tiles);
                }

                //移动后检测游戏是否胜利/失败/继续
                switch (gameStateHelper.checkGameState(tiles)
                ) {
                    case WIN -> {
                        gameState = GameStateHelper.GameState.WIN;
                        break;
                    }
                    case DEFEAT -> {
                        //再次检验是否含有空方块 若无空方块则失败
                        if (!tileHelper.checkNullTile(tiles)) {
                            gameState = GameStateHelper.GameState.DEFEAT;
                        }
                        break;
                    }
                }

                //重绘
                repaint();
            }
        });
    }

    private void moveRight() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j >= 0; j--) {
                tiles[i][j].move(tiles[i][j + 1]);
            }
        }
    }

    private void moveLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                tiles[i][j].move(tiles[i][j - 1]);
            }
        }
    }

    private void moveDown() {
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j].move(tiles[i + 1][j]);
            }
        }
    }

    private void moveUp() {
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j].move(tiles[i - 1][j]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (!isStart) {
            //初始化默认方块值
            tileHelper.initDefaultTile(tiles);
            isStart = true;
        }
        //绘制背景
        drawBackground(gg);
        //绘制方块
        drawTile(gg);
        //绘制游戏状态
        drawState(gg);
    }

    //绘制背景
    private void drawBackground(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRoundRect(200, 100, 499, 499, 15, 15);
    }

    //绘制方块
    private void drawTile(Graphics2D g) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.setColor(tiles[i][j].getColor());
                g.fillRoundRect(215 + j * 121, 115 + i * 121, 106, 106, 7, 7);
                if (tiles[i][j].getNum() != 0) {
                    String s = String.valueOf(tiles[i][j].getNum());
                    g.setColor(backgroundColor.darker());
                    FontMetrics fm = g.getFontMetrics();
                    int asc = fm.getAscent();
                    int dec = fm.getDescent();
                    int x = 215 + j * 121 + (106 - fm.stringWidth(s)) / 2;
                    int y = 115 + i * 121 + (asc + (106 - (asc + dec)) / 2);
                    g.drawString(s, x, y);
                }
            }
        }
    }

    //绘制状态
    private void drawState(Graphics2D g) {
        switch (gameState) {
            case WIN -> {
                g.drawString("   WIN   ", 320, 367);
                g.drawString("Click To Replay", 270, 480);
                break;
            }
            case DEFEAT -> {
                g.drawString("Game Over", 320, 367);
                g.drawString("Click To Replay", 270, 480);
                break;
            }
        }
    }
}