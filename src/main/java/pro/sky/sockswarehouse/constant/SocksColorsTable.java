package pro.sky.sockswarehouse.constant;

import java.util.HashMap;
import java.util.Map;

public enum SocksColorsTable {

    RED("Red"),
    ORANGE("Orange"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BLUE("Blue"),
    VIOLET("Violet");

    public final String colorName;

    SocksColorsTable(String colorName){
        this.colorName = colorName;
    }

    private static final Map<String, SocksColorsTable> SOCKS_COLOR_BY_COLOR = new HashMap<>();

    static {
        for(SocksColorsTable socksColorsTable: values()){
            SOCKS_COLOR_BY_COLOR.put(socksColorsTable.colorName, socksColorsTable);
        }
    }

    /**
     * Method to get socks color by String with color name description
     * @param color - String with color name
     * @return SocksColorsTable constant with defined color name or null if element with defined color name was not found
     */
    public static SocksColorsTable getSocksColorByColor(String color){
        return SOCKS_COLOR_BY_COLOR.get(color);
    }


}
