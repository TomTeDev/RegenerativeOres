package more.mucho.regenerativeores.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colors {
    private Colors(){}
    public static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])",Pattern.CASE_INSENSITIVE);
    public static String addColor(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static ChatColor GOLD = ChatColor.of("#FFD700");
    public static ChatColor SILVER = ChatColor.of("#C0C0C0");
    public static ChatColor BRONZE = ChatColor.of("#964B00");
    public static ChatColor PHTALO = ChatColor.of("#3E885B");
    public static ChatColor SALMON = ChatColor.of("#ED6A5A");
    public static ChatColor IDK_1 = ChatColor.of("#F6F5AE");
    public static ChatColor IDK_2 = ChatColor.of("#DEC5E3");
    public static ChatColor RED_INFO = ChatColor.of("#E05263");
    public static ChatColor YELLOW_INFO = ChatColor.of("#FFE548");
    public static ChatColor GREEN_INFO = ChatColor.of("#7EE081");
    public static ChatColor INDIGO = ChatColor.of("#005377");
    public static ChatColor GREEN_JUNGLE = ChatColor.of("#06A77D");
    public static ChatColor CITRON = ChatColor.of("#D5C67A");
    public static ChatColor ADMIN_RED = ChatColor.of("#710000");
    public static ChatColor LIGHT_PINK_GRAY = ChatColor.of("#D3C4D1");
    public static ChatColor RANK_GRAY = ChatColor.of("#a2a1a4");
    public static ChatColor CREAMY = ChatColor.of("#FBE8DA");
    public static ChatColor DARK_AQUA = ChatColor.DARK_AQUA;
    public static ChatColor BOLD = ChatColor.BOLD;
    public static ChatColor SUNSET = ChatColor.of("#F2C57C");
    public static ChatColor CAMBRIDGE_BLUE = ChatColor.of("#7FB685");
    public static ChatColor LAVENDER = ChatColor.of("#E5EAFA");
    public static ChatColor ATOMIC_TANGERINE = ChatColor.of("#E39774");

}
