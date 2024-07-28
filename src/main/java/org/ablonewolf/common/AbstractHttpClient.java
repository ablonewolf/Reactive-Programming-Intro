package org.ablonewolf.common;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

/**
 * an abstract http client to consume APIs from foreign services
 */
public abstract class AbstractHttpClient {

    private static final String BASE_URL = "https://localhost:7070/";
    protected final HttpClient httpClient;

    public AbstractHttpClient() {
        var loopResources = LoopResources.create("resource", 1, true);
        this.httpClient = HttpClient.create().runOn(loopResources).baseUrl(BASE_URL);
    }
}
