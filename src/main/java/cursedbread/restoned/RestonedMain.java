package cursedbread.restoned;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.IOException;
import java.net.URISyntaxException;


public class RestonedMain implements ModInitializer, GameStartEntrypoint, ClientModInitializer {
    public static final String MOD_ID = "restoned";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Toml TOML = new Toml();
	public static final TomlConfigHandler CFG;

	static {
		TOML.addCategory("IDs")
			.addEntry("Starting_block_id", 11000)
			.addEntry("Starting_item_id", 19000);

		CFG = new TomlConfigHandler(MOD_ID, TOML);

		RestonedBlocks.blockId = CFG.getInt("IDs.Starting_block_id");
		RestonedItems.itemId = CFG.getInt("IDs.Starting_item_id");
	}

    @Override
    public void onInitialize() {
        LOGGER.info("Re Stoning");
		new RestonedBlocks().initBlocks();
		new RestonedItems().initItems();
		new RestonedEntities().initilizeEntities();
    }

	@Override
	public void beforeGameStart() {	}

	@Override
	public void afterGameStart() { }

	@Override
	public void onInitializeClient() {
		new RestonedModels();
		try {
			TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.blockAtlas, true);
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
