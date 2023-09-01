package co.com.uma.mseei.invictus.model;

import static co.com.uma.mseei.invictus.R.drawable.ic_bike_black_24dp;
import static co.com.uma.mseei.invictus.R.drawable.ic_climb_black_24dp;
import static co.com.uma.mseei.invictus.R.drawable.ic_jog_black_24dp;
import static co.com.uma.mseei.invictus.R.drawable.ic_run_black_24dp;
import static co.com.uma.mseei.invictus.R.drawable.ic_skiping_rope_black_24dp;
import static co.com.uma.mseei.invictus.R.drawable.ic_walk_black_24dp;
import static co.com.uma.mseei.invictus.R.string.bike;
import static co.com.uma.mseei.invictus.R.string.climb;
import static co.com.uma.mseei.invictus.R.string.jog;
import static co.com.uma.mseei.invictus.R.string.rope_skipping;
import static co.com.uma.mseei.invictus.R.string.run;
import static co.com.uma.mseei.invictus.R.string.walk;

/**
 * SportType is a model enum class which represents the available sport activities in Invictus App.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public enum SportType {
    WALK(walk, ic_walk_black_24dp, true),
    JOG(jog, ic_jog_black_24dp, false),
    ROPE_SKIPPING(rope_skipping, ic_skiping_rope_black_24dp, false),
    CLIMB(climb, ic_climb_black_24dp,false),
    RUN(run, ic_run_black_24dp, false),
    BIKE(bike, ic_bike_black_24dp, false);

    private final int name;
    private final int icon;
    private final boolean implemented;

    SportType(int name, int icon, boolean implemented) {
        this.name = name;
        this.icon = icon;
        this.implemented = implemented;
    }

    public int getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public boolean isImplemented() {
        return implemented;
    }
}
