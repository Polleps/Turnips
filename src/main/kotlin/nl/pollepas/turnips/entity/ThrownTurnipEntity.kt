package nl.pollepas.turnips.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.Item
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World
import nl.pollepas.turnips.Turnips

class ThrownTurnipEntity : ThrownItemEntity {

    constructor(entityType: EntityType<ThrownTurnipEntity>, world: World): super(entityType, world)

    constructor(world: World?, owner: LivingEntity) : super(Turnips.THROWN_TURNIP, owner, world)

    constructor(world: World?, x: Double, y: Double, z: Double) : super(EntityType.EGG, x, y, z, world)


    override fun onEntityHit(entityHitResult: EntityHitResult?) {
        super.onEntityHit(entityHitResult)
        entityHitResult?.entity?.damage(this.damageSources.thrown(this, this.owner), 0.0f)
    }

    override fun onCollision(hitResult: HitResult?) {
        super.onCollision(hitResult)

        if (!this.world.isClient) {
            this.discard()

            // TODO: Spawn a turnip warrior
        }
    }

    override fun getDefaultItem(): Item {
        return Turnips.TURNIP_ITEM
    }
}