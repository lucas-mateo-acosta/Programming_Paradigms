package namedEntities.heuristics;

import java.io.Serializable;
import java.util.List;

public interface Heuristics extends Serializable {

    public List<String> extractCandidates(String text);

}
