package de.dafuqs.revelationary.mixin;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import de.dafuqs.revelationary.revelationary.RevelationRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
	
	@Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/loot/context/LootContext$Builder;)Ljava/util/List;", at = @At("HEAD"), cancellable = true)
	public void revelationary$getDroppedStacks(BlockState state, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
		BlockState cloak = RevelationRegistry.getCloak(state);
		if(cloak != null) {
			PlayerEntity lootPlayerEntity = RevelationAware.getLootPlayerEntity(builder);
			if (!RevelationRegistry.isVisibleTo(state, lootPlayerEntity)) {
				cir.setReturnValue(getLootForSwappedState(builder, cloak));
			}
		}
	}
	
	private List<ItemStack> getLootForSwappedState(LootContext.Builder builder, BlockState cloakedState) {
		Identifier lootTableId = cloakedState.getBlock().getLootTableId();
		
		if (lootTableId == LootTables.EMPTY) {
			return Collections.emptyList();
		} else {
			LootContext lootContext;
			lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, cloakedState).build(LootContextTypes.BLOCK);
			ServerWorld serverWorld = lootContext.getWorld();
			LootTable lootTable = serverWorld.getServer().getLootManager().getTable(lootTableId);
			return lootTable.generateLoot(lootContext);
		}
	}
	
}
