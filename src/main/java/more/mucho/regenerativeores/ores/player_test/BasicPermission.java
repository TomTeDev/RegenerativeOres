package more.mucho.regenerativeores.ores.player_test;

import more.mucho.regenerativeores.ores.variants.PermissionTestable;

import java.util.Optional;

public class BasicPermission implements PermissionTestable {
    @Override
    public Optional<PlayerTest> getPermissionTest() {
        return Optional.empty();
    }
}
