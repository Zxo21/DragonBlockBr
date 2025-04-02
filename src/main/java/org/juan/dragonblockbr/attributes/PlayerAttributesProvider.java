package org.juan.dragonblockbr.attributes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerAttributesProvider implements ICapabilityProvider {
    private final LazyOptional<PlayerAttributes> instance;

    public PlayerAttributesProvider(PlayerEntity player) {
        this.instance = LazyOptional.of(() -> {
            PlayerAttributesImpl attrs = new PlayerAttributesImpl();
            attrs.setPlayer(player); // Injeta o jogador na capability
            return attrs;
        });
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return AttributesCapabilities.PLAYER_ATTRIBUTES_CAP.orEmpty(cap, instance);
    }
}
