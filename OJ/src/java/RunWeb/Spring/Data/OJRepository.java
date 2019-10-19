package java.RunWeb.Spring.Data;

import java.util.List;

public interface OJRepository {
    List<OJ> findOJ(long max, long count);
}
