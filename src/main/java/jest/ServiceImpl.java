package jest;

import java.util.*;

/**
 * Created by qye on 4/1/2014.
 */
public class ServiceImpl implements Service {
  List<Service> services = new ArrayList<>();
  List<Service> unmodifiableServices = Collections.unmodifiableList(services);

  List<Filter> filters = new ArrayList<>();
  List<Filter> unmodifiableFilters = Collections.unmodifiableList(filters);

  List<Resource> resources = new ArrayList<>();
  List<Resource> unmodifiableResources = Collections.unmodifiableList(resources);

  String name;

  public ServiceImpl(String name) {
    if(name == null) {
      throw new IllegalArgumentException("name can't be null.");
    }

    // todo: validate the name if it contains illegal chars, e.g. '/'

    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<Service> getSubServices() {
    return unmodifiableServices;
  }

  @Override
  public Service addSubService(String name) {
    Service subService = new ServiceImpl(name);
    services.add(subService);
    return subService;
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
  public List<Resource> getResources() {
    return unmodifiableResources;
  }

  @Override
  public Resource addResource(String name, Object handler) {
    Resource resource = new ResourceImpl(name, handler);
    resources.add(resource);
    return resource;
  }

  @Override
  public boolean apply(HttpRequest request, HttpResponse response, String[] dirs, int depth, String query) {

    if(!beforeFilter(request, response))
      return false;

    if(dirs.length > 1) { // it is a service
      for(Service s : services) {
        if(s.getName().equals(dirs[depth])) {
          boolean ret = s.apply(request, response, dirs, depth + 1, query);
          if(!ret)
            return false;

          break;
        }
      }
    } else { // it is a resource
      for(Resource r : resources) {
        if(r.getName().equals(dirs[depth])) {
          boolean ret = r.apply(request, response);
          if(!ret)
            return false;

          break;
        }
      }
    }

    return afterFilter(request, response);
  }

  @Override
  public boolean validate(String[] dirs, int depth) {
    if(dirs.length > 1) { // it is a service

      for(Service s : services) {
        if(s.getName().equals(dirs[depth])) {
          return s.validate(dirs, depth + 1);
        }
      }
      return false;
    } else { // it is a resource
      for(Resource r : resources) {
        if(r.getName().equals(dirs[depth])) {
          return true;
        }
      }
      return false;
    }
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
