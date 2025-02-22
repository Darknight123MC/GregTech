package muramasa.gregtech;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterDynamics;
import muramasa.antimatter.AntimatterMod;
import muramasa.antimatter.datagen.providers.*;
import muramasa.antimatter.event.AntimatterCraftingEvent;
import muramasa.antimatter.event.AntimatterLoaderEvent;
import muramasa.antimatter.event.AntimatterProvidersEvent;
import muramasa.antimatter.recipe.loader.IRecipeRegistrate;
import muramasa.antimatter.registration.RegistrationEvent;
import muramasa.gregtech.data.*;
import muramasa.gregtech.data.Machines;
import muramasa.gregtech.datagen.GregTechBlockTagProvider;
import muramasa.gregtech.datagen.GregtechBlockLootProvider;
import muramasa.gregtech.datagen.ProgressionAdvancements;
import muramasa.gregtech.events.RemappingEvents;
import muramasa.gregtech.loader.WorldGenLoader;
import muramasa.gregtech.loader.crafting.*;
import muramasa.gregtech.loader.items.Circuitry;
import muramasa.gregtech.loader.machines.*;
import muramasa.gregtech.loader.machines.generator.CoalBoilerHandler;
import muramasa.gregtech.loader.machines.generator.Fuels;
import muramasa.gregtech.loader.multi.Blasting;
import muramasa.gregtech.loader.multi.Coking;
import muramasa.gregtech.loader.multi.DistillationTower;
import muramasa.gregtech.loader.multi.VacFreezer;
import muramasa.gregtech.proxy.ClientHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.BiConsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Ref.ID)
public class GregTech extends AntimatterMod {

    public static GregTech INSTANCE;
    public static Logger LOGGER = LogManager.getLogger(Ref.ID);

    public GregTech() {
        super();
        LOGGER.info("Loading GregTech");
        INSTANCE = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(RemappingEvents.class);
        MinecraftForge.EVENT_BUS.addListener(GregTech::registerRecipeLoaders);
        MinecraftForge.EVENT_BUS.addListener(GregTech::registerCraftingLoaders);
        MinecraftForge.EVENT_BUS.addListener(GregTech::onProviders);
        MinecraftForge.EVENT_BUS.addListener(WorldGenLoader::init);

        AntimatterDynamics.addProvider(Ref.ID,
                g -> new AntimatterBlockStateProvider(Ref.ID, Ref.NAME + " BlockStates", g));
        AntimatterDynamics.addProvider(Ref.ID,
                g -> new AntimatterItemModelProvider(Ref.ID, Ref.NAME + " Item Models", g));
        AntimatterDynamics.addProvider(Ref.ID, GregTechLocalizations.en_US::new);
    }

    private static void onProviders(AntimatterProvidersEvent ev) {
        if (ev.getSide() == Dist.CLIENT) {

        } else {
            final AntimatterBlockTagProvider[] p = new AntimatterBlockTagProvider[1];
            ev.addProvider(Ref.ID, g -> {
                p[0] = new GregTechBlockTagProvider(Ref.ID, Ref.NAME.concat(" Block Tags"), false, g);
                return p[0];
            });
            ev.addProvider(Ref.ID, g -> new AntimatterItemTagProvider(Ref.ID, Ref.NAME.concat(" Item Tags"),
                    false, g, p[0]));
            ev.addProvider(Ref.ID, g -> new AntimatterFluidTagProvider(Ref.ID,
                    Ref.NAME.concat(" Fluid Tags"), false, g));
            ev.addProvider(Ref.ID, g -> new AntimatterAdvancementProvider(Ref.ID,
                    Ref.NAME.concat(" Advancements"), g, new ProgressionAdvancements()));
            ev.addProvider(Ref.ID,
                    g -> new GregtechBlockLootProvider(Ref.ID, Ref.NAME.concat(" Loot generator"), g));
        }
    }
    
    private static void registerCraftingLoaders(AntimatterCraftingEvent event) {
        event.addLoader(Parts::loadRecipes);
        event.addLoader(Smelting::loadRecipes);
        event.addLoader(WireCablesPlates::loadRecipes);
        event.addLoader(VanillaExtensions::loadRecipes);
        event.addLoader(muramasa.gregtech.loader.crafting.Machines::loadRecipes);
        event.addLoader(SteamMachines::loadRecipes);
        event.addLoader(BlockParts::loadRecipes);
    }

    private static void registerRecipeLoaders(AntimatterLoaderEvent event) {
        BiConsumer<String, IRecipeRegistrate.IRecipeLoader> loader = (a, b) -> event.registrat.add(Ref.ID, a, b);
        loader.accept("wiremill", WiremillLoader::init);
        loader.accept("washer", WasherLoader::init);
        loader.accept("blasting", Blasting::init);
        loader.accept("coking", Coking::init);
        loader.accept("bending", BendingLoader::init);
        loader.accept("assembling", AssemblyLoader::init);
        loader.accept("cracking", CrackingUnit::init);
        loader.accept("fluid_solidify", FluidSolidifier::init);
        loader.accept("circuitry", Circuitry::init);
        loader.accept("chem_reacting", ChemicalReactorLoader::init);
        loader.accept("canning", CanningLoader::init);
        loader.accept("fuels", Fuels::init);
        loader.accept("coal_boiler", CoalBoilerHandler::init);
        loader.accept("fluid_extracting", FluidExtractor::init);
        loader.accept("alloy_loading", AlloyLoader::init);
        loader.accept("distillation_tower", DistillationTower::init);
        loader.accept("mixing", MixerLoader::init);
        loader.accept("hammering", HammerLoader::init);
        loader.accept("lathing", LatheLoader::init);
        loader.accept("electrolyzing", ElectrolyzerLoader::init);
        loader.accept("fluid_canning", FluidCanningLoader::init);
        loader.accept("centrifuging", CentrifugingLoader::init);
        loader.accept("extracting", ExtractorLoader::init);
        loader.accept("compressing", CompressorLoader::init);
        loader.accept("vac_freezing", VacFreezer::init);
        loader.accept("ore_byproducts", OreByproducts::init);
        loader.accept("pulverizing", PulverizerLoader::init);
        loader.accept("sifting", SiftingLoader::init);
        loader.accept("thermal_centrifuging", ThermalCentrifuge::init);
        loader.accept("cutting", CuttingLoader::init);
        loader.accept("fermenting", Fermenter::init);
        loader.accept("pressing", FormingPress::init);
        loader.accept("chemical_bathing", ChemicalBath::init);
    }

    private void clientSetup(final FMLClientSetupEvent e) {
        ClientHandler.setup(e);
    }

    private void setup(final FMLCommonSetupEvent e) {
    }

    public static <T> T get(Class<? extends T> clazz, String id) {
        return AntimatterAPI.get(clazz, id, Ref.ID);
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        switch (event) {
            case DATA_INIT -> {
                Materials.init();
                TierMaps.init();
                GregTechData.init(side);
                Machines.init();
                Guis.init(side);
                Models.init();
            }
            case DATA_READY -> {
                Structures.init();
                //if (side == Dist.CLIENT) StructureInfo.init();
                TierMaps.providerInit();
            }
            default -> {
            }
        }
    }

    @Override
    public String getId() {
        return Ref.ID;
    }
}
