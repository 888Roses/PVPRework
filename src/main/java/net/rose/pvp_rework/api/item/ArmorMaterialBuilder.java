package net.rose.pvp_rework.api.item;

import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

import java.util.function.Supplier;

public class ArmorMaterialBuilder {
    private final String name;
    private int durabilityMultiplier;
    private int[] protectionAmounts;
    private int enchantability;
    private SoundEvent equipSound;
    private float toughness;
    private float knockbackResistance;
    private Supplier<Ingredient> repairIngredient;

    protected ArmorMaterialBuilder(String name) {
        this.name = name;
    }

    public ArmourMaterial build() {
        return new ArmourMaterial(
                name,
                durabilityMultiplier,
                toughness,
                knockbackResistance,
                protectionAmounts,
                enchantability,
                equipSound,
                repairIngredient
        );
    }

    public ArmorMaterialBuilder durabilityMultiplier(int durabilityMultiplier) {
        this.durabilityMultiplier = durabilityMultiplier;
        return this;
    }

    public ArmorMaterialBuilder protectionAmounts(int[] protectionAmounts) {
        this.protectionAmounts = protectionAmounts;
        return this;
    }

    public ArmorMaterialBuilder headProtectionAmount(int protectionAmount) {
        if (this.protectionAmounts == null) {
            this.protectionAmounts = new int[4];
        }

        this.protectionAmounts[0] = protectionAmount;
        return this;
    }

    public ArmorMaterialBuilder chestProtectionAmount(int protectionAmount) {
        if (this.protectionAmounts == null) {
            this.protectionAmounts = new int[4];
        }

        this.protectionAmounts[1] = protectionAmount;
        return this;
    }

    public ArmorMaterialBuilder legsProtectionAmount(int protectionAmount) {
        if (this.protectionAmounts == null) {
            this.protectionAmounts = new int[4];
        }

        this.protectionAmounts[2] = protectionAmount;
        return this;
    }

    public ArmorMaterialBuilder feetProtectionAmount(int protectionAmount) {
        if (this.protectionAmounts == null) {
            this.protectionAmounts = new int[4];
        }

        this.protectionAmounts[3] = protectionAmount;
        return this;
    }

    public ArmorMaterialBuilder enchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public ArmorMaterialBuilder equipSound(SoundEvent equipSound) {
        this.equipSound = equipSound;
        return this;
    }

    public ArmorMaterialBuilder toughness(float toughness) {
        this.toughness = toughness;
        return this;
    }

    public ArmorMaterialBuilder knockbackResistance(float knockbackResistance) {
        this.knockbackResistance = knockbackResistance;
        return this;
    }

    public ArmorMaterialBuilder repairIngredient(Supplier<Ingredient> repairIngredient) {
        this.repairIngredient = repairIngredient;
        return this;
    }
}
