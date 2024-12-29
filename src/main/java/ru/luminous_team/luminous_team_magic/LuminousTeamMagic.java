package ru.luminous_team.luminous_team_magic;

import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import ru.luminous_team.luminous_team_magic.blocks.LTMBlocks;
import ru.luminous_team.luminous_team_magic.blocks.blocks_entity.ModBlockEntities;
import ru.luminous_team.luminous_team_magic.gui.LTMMenus;
import ru.luminous_team.luminous_team_magic.items.LTMItems;
import ru.luminous_team.luminous_team_magic.recipes.LTMRecipes;
import ru.luminous_team.luminous_team_magic.recipes.TableRecipes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(LuminousTeamMagic.MODID)
public class LuminousTeamMagic {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(LuminousTeamMagic.MODID, LuminousTeamMagic.MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }
    // Define mod id in a common place for everything to reference
    public static final String MODID = "luminous_team_magic";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public LuminousTeamMagic() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        LTMBlocks.BLOCKS.register(modEventBus);
        LTMItems.ITEMS.register(modEventBus);
        LTMMenus.REGISTRY.register(modEventBus);
        LTMRecipes.SERIALIZERS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        TableRecipes.load();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

}
