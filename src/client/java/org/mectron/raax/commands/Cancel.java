package org.mectron.raax.commands;

import org.mectron.raax.ReAntiAntiXray;
import org.mectron.raax.util.RefreshingJob;

public class Cancel extends Base {
    public Cancel() {
        super("Cancel", new String[]{"c", "cancel", "abort", "s", "stop"}, "Aborts all current jobs");
    }

    @Override
    public void run(String[] args) {
        ReAntiAntiXray.jobs.forEach(RefreshingJob::cancel);
        super.run(args);
    }
}
