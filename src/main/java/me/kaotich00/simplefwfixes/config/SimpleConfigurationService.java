package me.kaotich00.simplefwfixes.config;

import me.kaotich00.simplefwfixes.Simplefwfixes;
import me.kaotich00.simplefwfixes.api.config.ConfigService;
import me.kaotich00.simplefwfixes.api.config.GlobalConfig;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SimpleConfigurationService implements ConfigService {

    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private final ObjectMapper.BoundInstance mapper;

    public SimpleConfigurationService(Simplefwfixes plugin ) {

        TypeSerializerCollection serializers = TypeSerializers.getDefaultSerializers().newChild();
        ConfigurationOptions options = ConfigurationOptions.defaults().setSerializers(serializers);

        this.loader = HoconConfigurationLoader.builder()
                .setDefaultOptions(options)
                .setPath(ConfigUtil.CONF)
                .build();

        try {
            this.mapper = ObjectMapper.forClass(SimpleGlobalConfig.class).bindToNew();
        } catch (ObjectMappingException e) {
            plugin.getLogger().error("Failed to populate configuration");
            throw new RuntimeException(e);
        }

    }

    @Override
    public GlobalConfig getGlobal() {
        return (SimpleGlobalConfig) this.mapper.getInstance();
    }

    @Override
    public CompletableFuture<Void> reload() {
        if (Files.notExists(ConfigUtil.CONF)) {
            save();
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.runAsync(() -> {
            try {
                CommentedConfigurationNode node = this.loader.load();
                this.mapper.populate(node);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    public void populate() throws ObjectMappingException, IOException {
        if (Files.notExists(ConfigUtil.CONF)) {
            return;
        }
        CommentedConfigurationNode node = this.loader.load();
        this.mapper.populate(node);
    }

    @Override
    public CompletableFuture<Void> save() {
        return CompletableFuture.runAsync(() -> {
            SimpleCommentedConfigurationNode n = SimpleCommentedConfigurationNode.root();
            try {
                if (Files.notExists(ConfigUtil.CONF)) {
                    Files.createFile(ConfigUtil.CONF);
                }
                this.mapper.serialize(n);
                this.loader.save(n);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

}
