package net.ramixin.nethersmelting.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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

    @ModifyReturnValue(method = "getCookTime(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;)I", at = @At("RETURN"))
    private static int divideCookTimeIfInNether(int original, ServerWorld world, AbstractFurnaceBlockEntity entity) {
        if(world == null) return original;
        Optional<RegistryKey<DimensionType>> optionalKey = world.getDimensionEntry().getKey();
        if(optionalKey.isEmpty()) return original;
        if(optionalKey.get() == DimensionTypes.THE_NETHER) return original / 2;
        return original;
    }

}
