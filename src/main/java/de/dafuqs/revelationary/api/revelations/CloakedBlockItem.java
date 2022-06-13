package de.dafuqs.revelationary.api.revelations;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.Hashtable;

public class CloakedBlockItem extends BlockItem implements RevelationAware {
	
	Identifier cloakAdvancementIdentifier;
	Item cloakItem;
	
	public CloakedBlockItem(Block block, Settings settings, Identifier cloakAdvancementIdentifier, Item cloakItem) {
		super(block, settings);
		this.cloakAdvancementIdentifier = cloakAdvancementIdentifier;
		this.cloakItem = cloakItem;
		
		registerCloak();
	}
	
	@Override
	public Identifier getCloakAdvancementIdentifier() {
		return cloakAdvancementIdentifier;
	}
	
	@Override
	public Hashtable<BlockState, BlockState> getBlockStateCloaks() {
		return new Hashtable<>();
	}
	
	@Override
	public Pair<Item, Item> getItemCloak() {
		return new Pair<>(this, cloakItem);
	}
	
}
