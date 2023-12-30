package nl.pollepas.turnips.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import nl.pollepas.turnips.entity.ThrownTurnipEntity

class TurnipItem(settings: Settings) : Item(settings) {
    public override fun use(world: World, playerEntity: PlayerEntity, hand: Hand): TypedActionResult<ItemStack>? {
        val itemStack = playerEntity.getStackInHand(hand)

        playerEntity.playSound(
            SoundEvents.ENTITY_EGG_THROW,
            1.0F,
            1.0F / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        );

        if (!world.isClient) {
            val thrownTurnip = ThrownTurnipEntity(world, playerEntity)
            thrownTurnip.setItem(itemStack)
            thrownTurnip.setVelocity(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0f, 1.5f, 1.0f)
            world.spawnEntity(thrownTurnip)
        }

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this))

        if (!playerEntity.abilities.creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    public override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>?,
        context: TooltipContext?
    ) {
        tooltip?.add(Text.translatable("item.turnips.turnip.tooltip"))
    }
}