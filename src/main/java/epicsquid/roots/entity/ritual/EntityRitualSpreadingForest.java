package epicsquid.roots.entity.ritual;

import com.google.common.collect.Lists;
import epicsquid.mysticallib.util.Util;
import epicsquid.roots.particle.ParticleUtil;
import epicsquid.roots.ritual.RitualRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("deprecation")
public class EntityRitualSpreadingForest extends EntityRitualBase {
  private Set<Block> saplingBlocks = new HashSet<>();

  protected static final DataParameter<Integer> lifetime = EntityDataManager.createKey(EntityRitualSpreadingForest.class, DataSerializers.VARINT);

  public EntityRitualSpreadingForest(World worldIn) {
    super(worldIn);
    getDataManager().register(lifetime, RitualRegistry.ritual_regrowth.getDuration() + 20);
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    float alpha = (float) Math.min(40, (RitualRegistry.ritual_regrowth.getDuration() + 20) - getDataManager().get(lifetime)) / 40.0f;
    getDataManager().set(lifetime, getDataManager().get(lifetime) - 1);
    getDataManager().setDirty(lifetime);
    if (getDataManager().get(lifetime) < 0) {
      setDead();
    }
    if (world.isRemote && getDataManager().get(lifetime) > 0) {
      ParticleUtil.spawnParticleStar(world, (float) posX, (float) posY, (float) posZ, 0, 0, 0, 150, 255, 100, 0.5f * alpha, 20.0f, 40);
      if (rand.nextInt(5) == 0) {
        ParticleUtil.spawnParticleSpark(world, (float) posX, (float) posY, (float) posZ, 0.125f * (rand.nextFloat() - 0.5f), 0.0625f * (rand.nextFloat()),
            0.125f * (rand.nextFloat() - 0.5f), 100, 255, 50, 1.0f * alpha, 1.0f + rand.nextFloat(), 160);
      }
      if (rand.nextInt(2) == 0) {
        for (float i = 0; i < 360; i += rand.nextFloat() * 120.0f) {
          float tx = (float) posX + 2.0f * (float) Math.sin(Math.toRadians(i));
          float ty = (float) posY;
          float tz = (float) posZ + 2.0f * (float) Math.cos(Math.toRadians(i));
          for (int j = 0; j < 4; j++) {
            ParticleUtil.spawnParticleStar(world, tx, ty + 0.2f, tz, 0, rand.nextFloat() * 0.125f, 0, 100, 255, 50, 0.5f * alpha, 5.0f + rand.nextFloat() * 5.0f, 90);
          }
        }
      }
    }
    if (saplingBlocks.isEmpty()) {
      List<BlockPos> positions = Util.getBlocksWithinRadius(world, getPosition(), 25, 20, 25, (BlockPos pos) -> world.getBlockState(pos).getBlock() instanceof BlockLeaves);
      if (!positions.isEmpty()) {
        for (BlockPos pos : positions) {
          IBlockState state = world.getBlockState(pos);
          Block block = state.getBlock();
          List<ItemStack> drops = block.getDrops(world, pos, state, 0);
          for (ItemStack s : drops) {
            if (s.getItem() instanceof ItemBlock && ((ItemBlock) s.getItem()).getBlock() instanceof BlockSapling) {
              saplingBlocks.add(((ItemBlock) s.getItem()).getBlock());
            }
          }
        }
      }
    }

    if (ticksExisted % 140 == 0 && !saplingBlocks.isEmpty()) {
      List<BlockPos> positions = Util.getBlocksWithinRadius(world, getPosition(), 25, 20, 25, (BlockPos pos) -> world.isAirBlock(pos.up()) && world.getBlockState(pos).getBlock() == Blocks.GRASS);
      if (!positions.isEmpty()) {
        Block sapling = Lists.newArrayList(saplingBlocks).get(rand.nextInt(saplingBlocks.size()));
        IBlockState state = sapling.getDefaultState();
        BlockPos position = positions.get(rand.nextInt(positions.size()));
        world.setBlockState(position.up(), state);
      }
    }
    if (ticksExisted % 80 == 0) {
      List<BlockPos> positions = Util.getBlocksWithinRadius(world, getPosition(), 25, 20, 25, (BlockPos pos) -> world.getBlockState(pos).getBlock() instanceof BlockSapling);
      if (!positions.isEmpty()) {
        BlockPos pos = positions.get(rand.nextInt(positions.size()));
        IBlockState state = world.getBlockState(pos);
        ((BlockSapling)state.getBlock()).grow(world, pos, state, rand);
      }
    }
  }

  @Override
  public DataParameter<Integer> getLifetime() {
    return lifetime;
  }

}