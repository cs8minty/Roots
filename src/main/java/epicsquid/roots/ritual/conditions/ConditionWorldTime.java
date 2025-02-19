package epicsquid.roots.ritual.conditions;

import epicsquid.roots.tileentity.TileEntityBonfire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

// Time-based rituals aren't fun in the slightest.
@Deprecated
public class ConditionWorldTime implements Condition {

  private final int first, second;

  public ConditionWorldTime(int first, int second) {
    this.first = first;
    this.second = second;
  }


  @Override
  public boolean checkCondition(TileEntityBonfire tile, EntityPlayer player) {
    return tile.getWorld().getWorldTime() % 24000 >= first && tile.getWorld().getWorldTime() % 24000 <= second;
  }

  @Nullable
  @Override
  public ITextComponent failMessage() {
    return null;
  }
}
