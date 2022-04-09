package muramasa.gregtech.loader.multi;

import muramasa.antimatter.material.Material;
import muramasa.antimatter.recipe.ingredient.FluidIngredient;
import muramasa.gregtech.data.Materials;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import static muramasa.gregtech.data.RecipeMaps.HEAT_EXCHANGING;

public class HeatExchangerLoader {
    public static void init() {
        HEAT_EXCHANGING.RB().fi(FluidIngredient.of(new FluidStack(Fluids.WATER, 150)))
                .fo(Materials.Steam.getGas(2000))
                .add(60, 30);
        HEAT_EXCHANGING.RB().fi(FluidIngredient.of(Materials.DistilledWater.getLiquid(100)))
                .fo(Materials.Steam.getGas(2000))
                .add(60, 25);
    }
}
