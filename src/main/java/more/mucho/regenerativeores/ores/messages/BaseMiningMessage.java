package more.mucho.regenerativeores.ores.messages;

import more.mucho.regenerativeores.ores.MiningMessage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BaseMiningMessage implements MiningMessage {
    private final DISPLAY_ACTION place;
    private final String message;

    public BaseMiningMessage(DISPLAY_ACTION place, String message) {
        this.place = place;
        this.message = message;
    }
    @Override
    public void send(Player target) {
        switch (place) {
            case CHAT:
                target.sendMessage(message);
                break;
            case ACTION_BAR:
                target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(message));
                break;
            case TITLE_BIG:
                target.sendTitle(message,"",10,60,10);
                break;
            case TITLE_SMALL:
                target.sendTitle("",message,0,20,0);
                break;
        }
    }

    @Override
    public void serialize(ConfigurationSection section) {
        if(section == null){
            throw new IllegalArgumentException("Section is null");
        }
        section.set("type","basic");
        section.set("place",place.name());
        section.set("message",message);
    }

    public static BaseMiningMessage deserialize(ConfigurationSection section){
        if(section == null){
            throw new IllegalArgumentException("Section is null");
        }
        String type = section.getString("type");
        if(type==null||!type.equals("basic")){
            throw new IllegalArgumentException("Unknown message type: " + type);
        }
        DISPLAY_ACTION place = DISPLAY_ACTION.valueOf(section.getString("place"));
        String message = section.getString("message");
        return new BaseMiningMessage(place,message);
    }

}
