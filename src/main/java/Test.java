/**
 * Created by qye on 3/31/2014.
 */

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * Created by qye on 3/31/2014.
 */
public class Test {
  public int get(java.lang.Integer integer, int i, String s){
    return i;
  }

  public static void main(String[] args) {
    MethodAccess ma = MethodAccess.get(Test.class);
    System.out.println(ma.invoke(new Test(), "get", (Integer)null, (Integer)null, null));
  }
}