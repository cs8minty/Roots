package epicsquid.roots.spell;

import java.util.List;

import epicsquid.roots.network.PacketHandler;
import epicsquid.roots.network.message.MessageDandelionCastFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

public class SpellDandelionWinds extends SpellBase {

  public SpellDandelionWinds(String name) {
    super(name, TextFormatting.YELLOW, 255f / 255f, 255f / 255f, 32f / 255f, 255f / 255f, 176f / 255f, 32f / 255f);
    this.castType = SpellBase.EnumCastType.INSTANTANEOUS;
    this.cooldown = 20;
  }

  @Override
  public void cast(EntityPlayer player) {
    PacketHandler.INSTANCE.sendToAll(new MessageDandelionCastFX(player.getUniqueID(), player.posX, player.posY + player.getEyeHeight(), player.posZ));
    List<EntityLivingBase> entities = player.world.getEntitiesWithinAABB(EntityLivingBase.class,
        new AxisAlignedBB(player.posX + player.getLookVec().x * 6.0 - 6.0, player.posY + player.getLookVec().y * 6.0 - 6.0,
            player.posZ + player.getLookVec().z * 6.0 - 4.0, player.posX + player.getLookVec().x * 6.0 + 6.0, player.posY + player.getLookVec().y * 6.0 + 6.0,
            player.posZ + player.getLookVec().z * 6.0 + 6.0));
    if (entities.size() > 0) {
      for (EntityLivingBase e : entities) {
        if (e.getUniqueID().compareTo(player.getUniqueID()) != 0) {
          e.motionX += player.getLookVec().x;
          e.motionY += 0.75f;
          e.motionZ += player.getLookVec().z;
          e.velocityChanged = true;
        }
      }
    }
  }

}
