package jest;

import java.util.List;

/**
 * Created by qye on 3/26/2014.
 */
public interface Service {
  public List<Service> subServices();
  public List<Filter> filters();
  public List<Object> resources();
}
