package jest;

import java.util.List;

/**
 * Created by qye on 3/26/2014.
 */
public interface Resource {
  String getName();
  Object getHandler();

  List<Filter> getFilters();
  void addFilter(Filter filter);

  boolean apply(HttpRequest request, HttpResponse response);

}
