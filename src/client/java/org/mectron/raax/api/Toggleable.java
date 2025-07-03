package org.mectron.raax.api;

public interface Toggleable {
    String getName();
    boolean isEnabled();
    void toggle();
}
