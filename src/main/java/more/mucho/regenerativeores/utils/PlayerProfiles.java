package more.mucho.regenerativeores.utils;

import org.bukkit.Bukkit;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class PlayerProfiles {
    private final static HashMap<String,PlayerProfile> profiles = new HashMap<>();
    private static int profileCounter = 100;
    public static PlayerProfile get(String texture)throws Exception{
        URL url = valueToUrl(texture);
        return getOrCreate(url);
    }
    private static PlayerProfile getOrCreate(URL url) {
        PlayerProfile profile = profiles.getOrDefault(url.toString(), null);
        if (profile == null) {
            profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID(),String.valueOf(profileCounter++));
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(url);
            profile.setTextures(textures);
            profiles.put(url.toString(), profile);
        }
        return profile;
    }

    private static URL getUrlFromBase64(String base64) throws MalformedURLException {
        String decoded = new String(Base64.getDecoder().decode(base64));
        // We simply remove the "beginning" and "ending" part of the JSON, so we're left with only the URL. You could use a proper
        // JSON parser for this, but that's not worth it. The String will always start exactly with this stuff anyway
        return new URL(decoded.substring("{\"textures\":{\"SKIN\":{\"url\":\"".length(), decoded.length() - "\"}}}".length()));
    }

    public static URL valueToUrl(String value) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {
        if (value.startsWith("http")) {
            //direct link
            //ex https://textures.minecraft.net/texture/afb779e54779afb2c6fd0619ab4e8056f6d91403e8f42c2ec45c7f6211702edf
            return new URI(value).toURL();
        }
        if (value.length() < 65) {
            //skin url
            //ex 8d0b1199d0a10ddff65df209befbe0ecbb67d9f2e0d2377b1226da24a65ae061
            return new URI("https://textures.minecraft.net/texture/" + value).toURL();
        }
        return getUrlFromBase64(value);
    }

}
