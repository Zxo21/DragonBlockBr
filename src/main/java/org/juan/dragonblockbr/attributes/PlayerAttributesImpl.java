package org.juan.dragonblockbr.attributes;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerAttributesImpl implements PlayerAttributes{
    private PlayerEntity player;
    int unspentPoints = 0;
    int ki = 2000;
    int kiatk;
    int atk;
    int spatk;

    public PlayerAttributesImpl(){}

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public float getMaxHealth() {
        return (float) player.getAttributeValue(Attributes.MAX_HEALTH);
    }
    @Override
    public void setMaxHealth(float value) {
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(value);
    }



    @Override
    public int getStamina() {
        return (player != null) ? player.getFoodData().getFoodLevel() : 0;
    }
    @Override
    public void setStamina(int value) {
        player.getFoodData().setFoodLevel(value); // Atualiza a fome do jogador
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



}
