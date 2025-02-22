package muramasa.gregtech.loader.machines.generator;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.recipe.ingredient.FluidIngredient;
import muramasa.gregtech.data.Materials;

import static muramasa.antimatter.Data.GAS;
import static muramasa.antimatter.Data.LIQUID;
import static muramasa.gregtech.data.Materials.Steam;
import static muramasa.gregtech.data.RecipeMaps.*;

public class Fuels {
    public static void init() {
        AntimatterAPI.all(Material.class, mat -> {
            if (mat != Materials.Steam && mat.getFuelPower() > 0) {
                if (mat.has(LIQUID))
                    COMBUSTION_FUELS.RB().fi(mat.getLiquid(1)).add(1, mat.getFuelPower());
                if (mat.has(GAS)) {
                    GAS_FUELS.RB().fi(mat.getGas(1)).add(1, mat.getFuelPower());
                }
            }
        });
        STEAM_FUELS.RB().fi(FluidIngredient.of(Steam, 2)).add(1,1);
    }
}
