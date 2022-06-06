import java.awt.*;

public class Tile {
    //颜色
    final Color[] colorTable = {
            new Color(0xCDC1B4), new Color(0xFFE4C3), new Color(0xfff4d3),
            new Color(0xffdac3), new Color(0xe7b08e), new Color(0xe7bf8e),
            new Color(0xffc4c3), new Color(0xE7948e), new Color(0xbe7e56),
            new Color(0xbe5e56), new Color(0x9c3931), new Color(0x701710)};

    private Color color = colorTable[0];
    private int colorIndex = 0;
    private int num = 0;

    public Tile() {
    }

    public Tile(int colorIndex, int num) {
        this.colorIndex = colorIndex;
        this.color = colorTable[colorIndex];
        this.num = num;
    }

    public void move(Tile preTile) {
        if (preTile.num == 0) {
            //前一位置为空 直接前移
            preTile.num = num;
            preTile.color = color;
            preTile.colorIndex = colorIndex;
            //当前位置设为空
            num = 0;
            colorIndex = 0;
            color = colorTable[colorIndex];
        } else if (preTile.num == num) {
            //数值相同则合并
            preTile.num = preTile.num * 2;
            preTile.color = colorTable[++preTile.colorIndex];
            //当前位置设为空
            num = 0;
            colorIndex = 0;
            color = colorTable[colorIndex];
        }
    }

    public Color[] getColorTable() {
        return colorTable;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
