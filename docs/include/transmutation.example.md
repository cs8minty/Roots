### Examples

```zenscript
import mods.roots.Transmutation;

// Removes the default pumpkin-over-water-to-melon recipe
Transmutation.removeRecipe("pumpkin_melon");

// Adds a recipe which converts end stone into bone blocks
Transmutation.addBlockToBlockRecipe("end_stone_to_bone", <blockstate:minecraft:end_stone>, <blockstate:minecraft:bone_block:axis=y>);

// Adds a recipe which converts default tallgrass into snowballs
Transmutation.addBlockToItemRecipe("tallgrass_to_snowball", <blockstate:minecraft:tallgrass:type=tall_grass>, <minecraft:snowball>*3);
```

### Notes

**Note: complex state functions are not currently acessible through CraftTweaker (i.e., checking surroundings).**

It is possible to find the information about the block and its variants and states by using the F3 debug functionality and targeting it. On the right side of the screen it will display the block's registry name, and then any states below that.

For example, `bone_block` has the following:

```axis: y```

This can be converted into a blockstate by replacing the `:` with `=` like so: `axis=y`, meaning that the final blockstate (for an upwards facing bone block) would be `<blockstate:minecraft:bone_block:axis=y>`.
