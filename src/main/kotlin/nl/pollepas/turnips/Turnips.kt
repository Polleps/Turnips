package nl.pollepas.turnips

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.client.render.entity.FlyingItemEntityRenderer
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.item.ItemGroups
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import nl.pollepas.turnips.entity.ThrownTurnipEntity
import nl.pollepas.turnips.item.TurnipItem
import org.slf4j.LoggerFactory

object Turnips : ModInitializer {
    private val logger = LoggerFactory.getLogger("turnips")

	val TURNIP_ITEM = Registry.register(
		Registries.ITEM,
		Identifier("turnips", "turnip"),
		TurnipItem( FabricItemSettings().maxCount(5))
	)


	val THROWN_TURNIP : EntityType<ThrownTurnipEntity> = Registry.register(
		Registries.ENTITY_TYPE,
		Identifier("turnips", "thrown_turnip"),
		FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ThrownTurnipEntity)
			.dimensions(EntityDimensions.fixed(0.75f, 0.75f))
			.trackRangeChunks(4)
			.trackedUpdateRate(10)
			.build()
	)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Initializing Turnips!")

		FuelRegistry.INSTANCE.add(TURNIP_ITEM, 100)

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register {
			it.addAfter(Items.BEETROOT, TURNIP_ITEM)
		}

		EntityRendererRegistry.register(THROWN_TURNIP) {
			FlyingItemEntityRenderer(it)
		}
	}
}