package jest;

import java.util.List;

/**
 * Created by qye on 3/26/2014.
 */
public interface Service {
  String getName();

  List<Service> getSubServices();
  Service addSubService(String name);

  List<Filter> getFilters();
  void addFilter(Filter filter);

  List<Resource> getResources();
  Resource addResource(String name, Object handler);

  boolean apply(HttpRequest request, HttpResponse response, String[] dirs, int depth, String query);

  boolean validate(String[] dirs, int depth);
  }
