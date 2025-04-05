package org.juan.dragonblockbr.attributes;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerAttributesImpl implements PlayerAttributes, ICapabilityProvider {
    private final LazyOptional<PlayerAttributes> holder = LazyOptional.of(() -> this);

    private float maxHealth;
    private int stamina;
    private int unspentPoints = 0;
    private int ki = 2000;
    private int kiatk;
    private int atk;
    private int spatk;


    public PlayerAttributesImpl(){}


    @Override
    public float getMaxHealth() {
        return this.maxHealth;
    }
    @Override
    public void setMaxHealth(float value) {
        this.maxHealth=value;
    }



    @Override
    public int getStamina() {
        return  this.stamina;
    }
    @Override
    public void setStamina(int value) {
        this.stamina=value;
    }

    @Override
    public int getKi() {
        return this.ki;
    }
    @Override
    public void setKi(int value) {
        this.ki=value;
    }



    @Override
    public int getSpatk() {
        return this.spatk;
    }
    @Override
    public void setSpatk(int value) {
        this.spatk=value;
    }




    @Override
    public int getAtk() {
        return this.atk;
    }
    @Override
    public void setAtk(int value) {
        this.atk = value;
    }



    @Override
    public int getKiatk() {
        return this.kiatk;
    }
    @Override
    public void setKiatk(int value) {
        this.kiatk = value;
    }


    @Override
    public int getUnspentPoints() {
        return this.unspentPoints;
    }
    @Override
    public void setUnspentPoints(int value) {
        this.unspentPoints = value;
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == AttributesCapabilities.PLAYER_ATTRIBUTES_CAP) {
            return holder.cast();
        }
        return LazyOptional.empty();
    }
}
