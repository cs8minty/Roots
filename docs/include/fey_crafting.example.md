### Examples

```zenscript
import mods.roots.Fey;

// Adds a recipe for TNT using 4 gunpowder and red wool
Fey.addRecipe("tnt", <minecraft:tnt>, [<minecraft:gunpowder>, <minecraft:gunpowder>, <minecraft:gunpowder>, <minecraft:gunpowder>, <minecraft:wool:14>]);

// Adds the above recipe but also grants the player 6 levels (from 0-6 relatively) every time it is crafted
Fey.addRecipe("tnt", <minecraft:tnt>, [<minecraft:gunpowder>, <minecraft:gunpowder>, <minecraft:gunpowder>, <minecraft:gunpowder>, <minecraft:wool:14>], 6);

// Removes the recipe for the living axe
Fey.removeRecipe(<roots:living_axe>);

// Adds a different recipe for the living axe, using the same name, to ensure that it shows up in Patchouli. By default all Fey crafting recipe names are the same as the item's registry name.
Fey.addRecipe("living_axe", <roots:living_axe>, [<minecraft:sand>, <minecraft:dirt>, <minecraft:stone>, <minecraft:glass>, <minecraft:stone_axe>]);
```

### Notes

It is important for Patchouli continuity that, if you remove a default recipe (say `living_axe`), that you replace it with another recipe and give that recipe the name `"living_axe"` if you wish Patchouli to properly display the new recipe.

