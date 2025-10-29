//package com.grandlynn.util;
//
//import java.lang.ref.WeakReference;
//import org.dozer.DozerBeanMapper;
//import org.dozer.loader.api.BeanMappingBuilder;
//import org.dozer.loader.api.TypeMappingOption;
//import org.dozer.loader.api.TypeMappingOptions;
//
//public class BeanPowerHelper {
//   private static DozerBeanMapper completeMapper = new DozerBeanMapper();
//
//   public static <T> T mapPartOverrider(final Object sources, final Class<T> destinationClass) {
//      if (sources == null) {
//         return null;
//      } else {
//         WeakReference weakReference = new WeakReference(new DozerBeanMapper());
//         DozerBeanMapper mapper = (DozerBeanMapper)weakReference.get();
//         mapper.addMapping(new BeanMappingBuilder() {
//            protected void configure() {
//               this.mapping(sources.getClass(), destinationClass, new TypeMappingOption[]{TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(false)});
//            }
//         });
//         T dest = mapper.map(sources, destinationClass);
//         mapper.destroy();
//         weakReference.clear();
//         return dest;
//      }
//   }
//
//   public static void mapPartOverrider(final Object sources, final Object destinations) {
//      WeakReference weakReference = new WeakReference(new DozerBeanMapper());
//      DozerBeanMapper mapper = (DozerBeanMapper)weakReference.get();
//      mapper.addMapping(new BeanMappingBuilder() {
//         protected void configure() {
//            this.mapping(sources.getClass(), destinations.getClass(), new TypeMappingOption[]{TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(false)});
//         }
//      });
//      mapper.map(sources, destinations);
//      mapper.destroy();
//      weakReference.clear();
//   }
//
//   public static synchronized <T> T mapCompleteOverrider(Object sources, Class<T> destinationClass) {
//      return sources == null ? null : completeMapper.map(sources, destinationClass);
//   }
//
//   public static synchronized void mapCompleteOverrider(Object sources, Object destinations) {
//      completeMapper.map(sources, destinations);
//   }
//}
