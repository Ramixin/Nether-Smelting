package net.ramixin.nethersmelting.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {

    @ModifyReturnValue(method = "getCookTime", at = @At("RETURN"))
    private static int divideCookTimeIfInNether(int original, @Local(argsOnly = true) ServerWorld world, @Local(argsOnly = true) AbstractFurnaceBlockEntity entity) {
        if(entity.getWorld() == null) return original;
        Optional<RegistryKey<DimensionType>> optionalKey = entity.getWorld().getDimensionEntry().getKey();
        if(optionalKey.isEmpty()) return original;
        if(optionalKey.get() == DimensionTypes.THE_NETHER) return original / 2;
        return original;
    }

}
