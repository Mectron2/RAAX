package org.mectron.raax.manager;

import org.mectron.raax.api.Toggleable;
import org.mectron.raax.features.Scanner;
import org.mectron.raax.features.XRay;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {
    private static final List<Toggleable> features = new ArrayList<>();

    public static final XRay XRAY = new XRay();
    public static final Scanner SCANNER = new Scanner();

    public static void init() {
        features.add(XRAY);
        features.add(SCANNER);
    }

    public static List<Toggleable> getAll() {
        return features;
    }
}
