package muramasa.gti.data;

import muramasa.antimatter.structure.PatternBuilder;
import muramasa.antimatter.structure.StructureElement;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.text.TranslationTextComponent;

import static muramasa.gti.data.GregTechData.*;
import static muramasa.gti.data.Machines.*;
import static muramasa.gti.data.Machines.HATCH_ENERGY;

public class StructureInfo {

    public static void init() {
        // pattern demo
        PatternBuilder builder = new PatternBuilder()
                .of("CCO", "ECM", "CCI").of("BBB", "BAB", "BBB").of(1).of("CCC", "CFC", "CCC")
                .at("F", HATCH_MUFFLER, HATCH_MUFFLER.getFirstTier(), Direction.UP)
                .at("M", BLAST_FURNACE, BLAST_FURNACE.getFirstTier(), Direction.SOUTH)
                .at("C", CASING_HEAT_PROOF.getDefaultState())
                .at("I", HATCH_ITEM_I, HATCH_ITEM_I.getFirstTier(), Direction.SOUTH)
                .at("O", HATCH_ITEM_O, HATCH_ITEM_O.getFirstTier(), Direction.SOUTH)
                .at("E", HATCH_ENERGY, HATCH_ENERGY.getFirstTier(), Direction.NORTH);
        BLAST_FURNACE.setStructurePattern(
                // reuse the builder for page COIL_CUPRONICKEL
                builder.at("B", COIL_CUPRONICKEL.getDefaultState()).description(COIL_CUPRONICKEL.getTranslationKey()).build(),
                // reuse the builder for page COIL_HSSG
                builder.at("B", COIL_HSSG.getDefaultState()).description(COIL_HSSG.getTranslationKey()).build(),
                // reuse the builder for page COIL_KANTHAL
                builder.at("B", COIL_KANTHAL.getDefaultState()).description(COIL_KANTHAL.getTranslationKey()).build(),
                // reuse the builder for page COIL_NAQUADAH and replace one casing with(C) the fluid hatch (K).
                builder.of(3, "CCC", "CFC", "CCK")
                        .at("K", HATCH_FLUID_I, HATCH_FLUID_I.getFirstTier(), Direction.EAST)
                        .at("B", COIL_NAQUADAH.getDefaultState())
                        .description(COIL_NAQUADAH.getTranslationKey())
                        .build());
        builder = new PatternBuilder()
                .of("CCC", "CCC", "CCC").of("CCC, CAM", "CCC").of(0)
                .at("M", COKE_OVEN, COKE_OVEN.getFirstTier(), Direction.SOUTH)
                .at("C",CASING_FIRE_BRICK.getDefaultState(), Structures.FAKE_CASING)
                .at("A", Blocks.AIR.getDefaultState())
                .description(COKE_OVEN.getDisplayName(COKE_OVEN.getFirstTier()));
        COKE_OVEN.setStructurePattern(builder.build());
    }
}
