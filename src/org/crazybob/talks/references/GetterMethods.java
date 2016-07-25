package org.crazybob.talks.references;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
public class GetterMethods {
  final static Cache<Class<?>, List<Method>> cache = CacheBuilder.newBuilder()
      .weakKeys()
      .softValues()
      .build(new CacheLoader<Class<?>, List<Method>>() {
        public List<Method> load(Class<?> clazz) {
          List<Method> getters = new ArrayList<Method>();
          for (Method m : clazz.getMethods())
            if (m.getName().startsWith("get"))
               getters.add(m);
          return getters;
        }
      });
  public static List<Method> on(Class<?> clazz) {
    return cache.apply(clazz);
  }
}
