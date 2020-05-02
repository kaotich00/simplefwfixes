package me.kaotich00.simplefwfixes.api.config;

import java.util.concurrent.CompletableFuture;

public interface ConfigService {

    GlobalConfig getGlobal();

    CompletableFuture<Void> reload();

    CompletableFuture<Void> save();

}
