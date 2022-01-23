package swing.minesweeper;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ConstLevels {

    EASY("1", 10, 10, 20),
    NORMAL("2", 10, 20, 50),
    HARD("3", 15, 30, 70);

    public String level;

    public int height;

    public int width;

    public int mineNum;

    public static Map<String, ConstLevels> constMap;

    static {
        constMap = Arrays.stream(ConstLevels.values())
                .collect(Collectors.toMap(ConstLevels::getLevel, e -> e));
    }

    ConstLevels(String level, int height, int width, int mineNum) {
        this.level = level;
        this.height = height;
        this.width = width;
        this.mineNum = mineNum;
    }

    public String getLevel() {
        return level;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMineNum() {
        return mineNum;
    }

    public static Map<String, ConstLevels> getConstMap() {
        return constMap;
    }
}
