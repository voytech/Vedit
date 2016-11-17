package pl.voytech.vedit.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pl.voytech.vedit.core.features.Feature;

/**
 * Created by USER on 2016-11-03.
 */

public class TokenProduction {
    private final UUID id = UUID.randomUUID();
    private final List<Token> tokens = new ArrayList<Token>();
    private final List<Feature> features = new ArrayList<Feature>();

    public UUID getId() {
        return id;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<Feature> getFeatures() {
        return features;
    }
}
