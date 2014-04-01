package jest;

/**
 * Created by qye on 3/26/2014.
 */
public interface Filter {
  boolean before(HttpRequest request, HttpResponse response);
  boolean after(HttpRequest request, HttpResponse response);
}
