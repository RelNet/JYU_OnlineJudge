package java.RunWeb.Spring.Data;

import java.util.List;

public interface OJRepository {
    List<OJ> findOJs(long max, long count);
}
