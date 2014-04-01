package jest;

import java.util.*;

/**
 * Created by qye on 4/1/2014.
 */
public class ResourceImpl implements Resource {
  String name;
  Object handler;
  List<Filter> filters = new ArrayList<>();
  List<Filter> unmodifiableFilters = Collections.unmodifiableList(filters);

  public ResourceImpl(String name, Object handler) {
    if(name == null || handler == null) {
      throw new IllegalArgumentException("name and handler can't be null.");
    }

    // todo: validate the name if it contains illegal chars, e.g. '/'

    this.name = name;
    this.handler = handler;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Object getHandler() {
    return handler;
  }

  @Override
  public List<Filter> getFilters() {
    return unmodifiableFilters;
  }

  @Override
  public void addFilter(Filter filter) {
    filters.add(filter);
  }

  @Override
  public boolean apply(HttpRequest request, HttpResponse response) {
    if(!beforeFilter(request, response))
      return false;

    return afterFilter(request, response);
  }

  boolean beforeFilter(HttpRequest request, HttpResponse response) {
    for(Filter f : filters) {
      if(!f.before(request, response)) {
        return false;
      }
    }

    return true;
  }


  boolean afterFilter(HttpRequest request, HttpResponse response) {
    for (int i = filters.size(); i >= 0 ; i--) {
      Filter f = filters.get(i);
      if(!f.before(request, response)) {
        return false;
      }
    }

    return true;
  }
}
