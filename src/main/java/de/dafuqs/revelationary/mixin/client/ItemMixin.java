package de.dafuqs.revelationary.mixin.client;

import de.dafuqs.revelationary.ClientRevelationHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Item.class)
public abstract class ItemMixin {
	
	@Inject(at = @At("HEAD"), method = "getName(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/text/Text;", cancellable = true)
	public void getCloakedName(ItemStack stack, CallbackInfoReturnable<Text> callbackInfoReturnable) {
		Item thisItem = (Item) (Object) this;
		if (ClientRevelationHolder.isCloaked(thisItem)) {
			// Get the localized name of the item and scatter it using §k to make it unreadable
			Language language = Language.getInstance();
			MutableText newText = Text.literal("§k" + language.get(thisItem.getTranslationKey()));
			callbackInfoReturnable.setReturnValue(newText);
		}
	}
	
}
