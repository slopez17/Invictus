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

public enum SportType {
    WALK(walk, ic_walk_black_24dp),
    JOG(jog, ic_jog_black_24dp),
    ROPE_SKIPPING(rope_skipping, ic_skiping_rope_black_24dp),
    CLIMB(climb, ic_climb_black_24dp),
    RUN(run, ic_run_black_24dp),
    BIKE(bike, ic_bike_black_24dp);

    private final int sportName;
    private final int sportIcon;

    SportType(int sportName, int sportIcon) {
        this.sportName = sportName;
        this.sportIcon = sportIcon;
    }

    public int getSportName() {
        return sportName;
    }

    public int getSportIcon() {
        return sportIcon;
    }
}
