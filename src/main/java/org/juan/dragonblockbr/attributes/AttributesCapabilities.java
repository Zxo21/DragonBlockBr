package org.juan.dragonblockbr.attributes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.juan.dragonblockbr.DragonBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = DragonBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttributesCapabilities {
    public static final ResourceLocation PLAYER_ATTRIBUTES_ID =
            new ResourceLocation("dragonblockbr", "player_attributes");

    @CapabilityInject(PlayerAttributes.class)
    public static Capability<PlayerAttributes> PLAYER_ATTRIBUTES_CAP;

    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(
                    PlayerAttributes.class,
                    new Storage(),
                    PlayerAttributesImpl::new
            );
            System.out.println("[DragonBlockBR] Capability registrada!");
        });
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(
                    PLAYER_ATTRIBUTES_ID,
                    new PlayerAttributesImpl()
            );
        }
    }

    public static class Storage implements Capability.IStorage<PlayerAttributes> {
        @Override
        public INBT writeNBT(Capability<PlayerAttributes> capability, PlayerAttributes instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("ki", instance.getKi());
            nbt.putInt("atk", instance.getAtk());
            nbt.putInt("kiatk", instance.getKiatk());
            nbt.putInt("spatk", instance.getSpatk());
            nbt.putInt("unspent_points", instance.getUnspentPoints());
            return nbt;
        }

        @Override
        public void readNBT(Capability<PlayerAttributes> capability, PlayerAttributes instance, Direction side, INBT nbt) {
            CompoundNBT compound = (CompoundNBT) nbt;
            instance.setKi(compound.getInt("ki"));
            instance.setAtk(compound.getInt("atk"));
            instance.setSpatk(compound.getInt("spatk"));
            instance.setKiatk(compound.getInt("kiatk"));
            instance.setUnspentPoints(compound.getInt("unspent_points"));
        }
    }
}
