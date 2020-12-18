package delivery;

import java.util.HashSet;
import java.util.Set;

public class WrapperForSelectedData {
    private Set<City> reachable = new HashSet<>();
    private int positionInComboStart = 0;

    public Set<City> getReachable() {
        return reachable;
    }

    public void setReachable(Set<City> reachable) {
        this.reachable = reachable;
    }

    public int getPositionInComboStart() {
        return positionInComboStart;
    }

    public void setPositionInComboStart(int positionInComboStart) {
        this.positionInComboStart = positionInComboStart;
    }
}
