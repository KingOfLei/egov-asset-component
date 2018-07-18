package com.bosssoft.egov.asset.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.platform.common.utils.Assert;

/**
 *
 * @ClassName 类名：BeanUtilsExt
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年11月11日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2016年11月11日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public class BeanUtilsExt extends BeanUtils {
	
	private static Logger logger = LoggerFactory.getLogger(BeanUtilsExt.class);
    /**
     * 在取得bean属性时,需要排除的属性列表
     */
    private static final Set<String> EXCLUDE_PROP_NAMES = new HashSet<String>(Arrays
            .asList(new String[] { "class", "declaringClass", "cachedSuperClass", "metaClass" }));
    
    	
	
	static {
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);
		ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
		ConvertUtils.register(new SqlTimestampConverter(null), java.sql.Timestamp.class);
		ConvertUtils.register(new SqlTimeConverter(null), java.sql.Time.class);
		
		ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
		ConvertUtils.register(new BigIntegerConverter(null), java.math.BigInteger.class);
		
		ConvertUtils.register(new IntegerConverter(null), java.lang.Integer.class);
		ConvertUtils.register(new LongConverter(null), java.lang.Long.class);
		ConvertUtils.register(new DoubleConverter(null), java.lang.Double.class);
		ConvertUtils.register(new FloatConverter(null), java.lang.Float.class);
		ConvertUtils.register(new BooleanConverter(null), java.lang.Boolean.class);
		ConvertUtils.register(new CharacterConverter(null), java.lang.Character.class);
		ConvertUtils.register(new ByteConverter(null), java.lang.Byte.class);
		ConvertUtils.register(new ShortConverter(null), java.lang.Short.class); 
	}
	
	/**
	 * 
	 * <p>函数名称：   copyBean     </p>
	 * <p>功能说明： 赋值对象属性值
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param src
	 * @param target
	 * @return
	 *
	 * @date   创建时间：2016年11月11日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static Object copyBean(Object src, Object target){
		Assert.notNull(src, "源对象不允许为空!");
		Assert.notNull(target, "目标对象不允许为空!");
		try {
			BeanUtils.copyProperties(target, src);
		} catch (Exception e) {
			logger.debug("拷贝对象出错", e);
			throw new RuntimeException("拷贝对象出错：" + e.getMessage(), e);
		}

		return target;
	}
	
	  /**
     * 将指定对象的属性拷贝到目标类的对象中并返回.如果原对象为null,则返回null.
     * 
     * @param <T> 目标类的类型
     * @param source 原对象,如果原对象为null,则返回null
     * @param targetClass 目标类
     * 
     * @return 返回目标类的实例,如果原对象为null,则返回null
     * 
     * <pre>
	 * 修改日期　　  　修改人　　　修改原因
	 * 2010-05-27　　 　　　新建
	 * </pre>
     */
    public static final <T extends Object> T copyToNewBean(Object source, Class<T> targetClass) {

		if (source == null) {
			return null;
		}

		try {
			T result = targetClass.newInstance();
			copyBean(source, result);
			return result;
		} catch (Exception ex) {
			logger.error("实例化指定类出错:", ex);
			throw new RuntimeException("实例化指定类出错:" + ex.getMessage(), ex);
		}
    }
    
    @SuppressWarnings("unchecked")
	public final static <T extends Object> T cloneObj(T srcObj) {
        return (T) BeanUtilsExt.copyToNewBean(srcObj, srcObj.getClass());
    }    
    
    /**
     * 循环向上转型，获取对象声明的字段。
     * 
     * @param clazz 类
     * @param propertyName 属性名称
     * @throws NoSuchFieldException 没有该字段时抛出
     * @return 字段对象
     * 
     * <pre>
     * 修改日期             修改人                     修改原因
     * 2010-04-07                        新建
     * </pre>
     */
    public static final Field getDeclaredField(Class<?> clazz, String propertyName)
            throws NoSuchFieldException {
        //TODO 是否可对结果进行缓存,Class与propertyName的映射.
        //作为核心底层方法,不需要进行判断,此类问题不会暴露到客户,故忽略以优化.
        //Assert.notNull(clazz);
        //Assert.hasText(propertyName);
        
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义，继续向上转型
                logger.info("当前类[{}]不存在指定属性[{}],继续尝试查询父类", clazz, propertyName);
            }
        }
        throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
    }
    
    /**
     * 循环向上转型，获取对象声明的字段。
     * 
     * @param object 对象
     * @param propertyName 属性名称
     * @throws NoSuchFieldException 没有该字段时抛出
     * @return 字段对象
     * 
     * <pre>
     * 修改日期             修改人                     修改原因
     * 2010-04-07                        新建
     * </pre>
     */
    public static final Field getDeclaredField(Object object, String propertyName)
            throws NoSuchFieldException {
        return getDeclaredField(object.getClass(), propertyName);
    }    
    
    /**
     * 暴力获取对象变量值，忽略private、protected修饰符的限制。
     * 
     * @param object 对象
     * @param propertyName 属性名称
     * @throws NoSuchFieldException 没有该字段时抛出
     * @return 属性的值
     * 
     */
    public static final Object forceGetProperty(Object object, String propertyName)
            throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        Field field = getDeclaredField(object, propertyName);

        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.warn("无法获取值:" + object.getClass().getName() + "." + propertyName + ", 错误消息:"
                    + e.getMessage());
            throw new IllegalAccessError("无法获取值:" + object.getClass().getName() + "."
                    + propertyName);
        }
        field.setAccessible(accessible);
        return result;
    }
    
    /**
     * 
     * 暴力获取属性名和属性值的映射关系.
     * 
     * @param object object
     * @return 映射关系.
     * @throws IllegalArgumentException IllegalArgumentException
     * @throws IllegalAccessException IllegalAccessException
     * @throws NoSuchFieldException NoSuchFieldException
     * 
     */
    public static final Map<String, Object> forceGetPropertyMap(Object object)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        Assert.notNull(object);
        Map<String, Object> propertyMap = new HashMap<String, Object>();
        //向上循环遍历对象 获取属性值 xds 20170629
        for(Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()){
	        Field[] fields = superClass.getDeclaredFields();
	        for (Field field : fields) {
	            // 获取属性名.
	            boolean accessible = field.isAccessible();
	            field.setAccessible(true); //成员变量为private时,须进行此操作 
	            String fieldName = field.getName();
	            Object result = null;
	            try {
	                result = field.get(object);
	            } catch (IllegalAccessException e) {
	                logger
	                        .info("Can't get " + object.getClass().getName() + "." + fieldName
	                                + " value");
	            }
	
	            field.setAccessible(accessible);
	            //存在表名是基类覆盖了父类方法了 略过  20170629 xds
	            if(!propertyMap.containsKey(fieldName)){
	              propertyMap.put(fieldName, result);
	            }
	        }
        }
        return propertyMap;
    }
    
    /**
     * 批量取Bean对象值列表
     * 
     * @param srcObj bean对象
     * @param properties 属性名称列表
     * @return Bean对象值列表
     * @throws IllegalAccessException IllegalAccessException
     * @throws InvocationTargetException IllegalAccessException
     * @throws NoSuchMethodException IllegalAccessException
     * 
     */
    public static final String[] getBeanValues(Object srcObj, String[] properties)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String[] result = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            result[i] = BeanUtils.getProperty(srcObj, properties[i]);
            // TODO [x]有优化空间
        }
        return result;
    }    
    
    /**
     * 
     * 取Bean中指定名称的属性值.
     * 
     * @param srcObj srcObj
     * @param propertyName propertyName
     * @return 指定名称的属性值.
     * @throws NoSuchFieldException NoSuchFieldException
     * 

     */
    public static final Object getBeanValue(Object srcObj, String propertyName)
            throws NoSuchFieldException {
        return BeanUtilsExt.forceGetProperty(srcObj, propertyName);
    }    
    
    /**
     * 
     * 取指定对象的指定属性的值
     * 
     * @param bean 对象
     * @param name 属性名
     * @return 指定属性的值
     * @throws IllegalAccessException IllegalAccessException
     * @throws InvocationTargetException IllegalAccessException
     * @throws NoSuchMethodException NoSuchMethodException
     * 

     */
    public static final Object getProperies(Object bean, String name){
    	try {
			return PropertyUtils.getProperty(bean, name);
		} catch (IllegalAccessException e) {			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
        return null;
    }

   

    /**
     * 取指定类型的属性获取方法列表
     * 
     * @param clazz 类型
     * @return 方法列表
     * 
     * <pre>
     * 修改日期             修改人                     修改原因
     * 2010-04-07                        新建
     * </pre>
     */
    public static final Method[] getBeanPropertyGetMethods(Class<?> clazz) {
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
        List<Method> getMethods = new ArrayList<Method>();
        for (PropertyDescriptor pd : pds) {
            String pName = pd.getName();
            if (pd.getPropertyType() == null) {
                continue;
            }

            if (shouldExcludeProperty(pName)) {
                continue;
            }
            getMethods.add(pd.getReadMethod());
        }
        return getMethods.toArray(new Method[getMethods.size()]);
    }
    
    /**
     * 
     * 检查是否为需要忽略的属性
     * 
     * @param name 属性名
     * @return 是就返回true，否则返回false
     * 
     * <pre>
	 * 修改日期             修改人                     修改原因
	 * 2010-04-07                              新建
	 * </pre>
     */
    public static final boolean shouldExcludeProperty(String name) {
        if (EXCLUDE_PROP_NAMES.contains(name)) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 类说明 :用于自定义Bean对象拷贝的接口。
     * 
     * <pre>
	 * 修改日期　　　修改人　　　修改原因
	 * 2010-06-22           　　　新建
	 * </pre>
     */
    public static interface BeanCopier<K, T> {
        /**
         * 拷贝bean.
         * @param srcObj srcObj
         * @param destObj srcObj
         * 
         * <pre>
         * 修改日期		修改人	修改原因
         * 2012-07-6	zhufu	sonar
         * </pre>
         */
        void copyBean(K srcObj, T destObj);
    }
    
    /**
     * 对象列表拷贝
     * 
     * @param <K> 原始对象类型
     * @param <E> 目标对象类型
     * @param srcList 源对象列表
     * @param destClass 目标对象类型
     * @return 目标对象列表
     * @throws RuntimeException RuntimeException
     * 
     * <pre>
	 * 修改日期             修改人                     修改原因
	 * 2010-04-07                          新建
	 * </pre>
     */
    public static final <K, E> List<E> listCopy(List<K> srcList, Class<E> destClass)
            throws RuntimeException {

        return listCopy(srcList, destClass, new BeanCopier<K, E>() {
            @Override
            public void copyBean(K srcObj, E destObj) {            	
                BeanUtilsExt.copyBean(srcObj, destObj);
            }
        });
    }    
    
    /**
     * 
     * 根据对象拷贝的要求将原始对象转成目标对象列表。
     * 
     * @param <K> 原始对象类型
     * @param <E> 目标对象类型
     * @param srcList 原始对象列表
     * @param destClass 目标对象类
     * @param beanCopier 对象拷贝接口
     * @return 目标对象列表
     * @throws RuntimeException
     * 
     * <pre>
	 * 修改日期　　  　修改人　　　修改原因
	 * 2010-6-22　　    　　　新建
	 * </pre>
     * 
     */
    public static final <K, E> List<E> listCopy(List<K> srcList, Class<E> destClass,
            BeanCopier<K, E> beanCopier) throws RuntimeException {
        if (srcList == null) {
            return Collections.emptyList();
        }

      

        // 转换结果值
        List<E> result = new ArrayList<E>(srcList.size());

        for (K srcObj : srcList) {
            E destObj = null;
            try {
                destObj = destClass.newInstance();
                beanCopier.copyBean(srcObj, destObj);
            } catch (Exception ex) {
                throw new RuntimeException(StringUtilsExt
                        .join(new Object[] { "对象内容拷贝出错,目标类:", destClass.getName(), ", 错误消息",
                                ex.getMessage() }), ex);
            }
            result.add(destObj);
        }
        return result;
    }
    
    /**
     * 
     * <p>函数名称： convertMap2Bean       </p>
     * <p>功能说明：Map转指定实体
     *
     * </p>
     *<p>参数说明：</p>
     * @param map
     * @param beanClass
     *
     * @date   创建时间：2017年2月24日
     * @author 作者：xds
     */
    public static void convertMap2Bean(Map<String,Object> map,Object beanClass){
    	if(map == null || beanClass == null){
    		return;
    	}
    	try {
			populate(beanClass, map);
		} catch (IllegalAccessException e) {
			logger.error("Map转指定类出错:", e);
			throw new RuntimeException("Map转指定类出错:",e);
		} catch (InvocationTargetException e) {
			logger.error("Map转指定类出错:", e);
			throw new RuntimeException("Map转指定类出错:",e);			
		}
    }
    
    /**
     * 
     * <p>函数名称：  converBean2Map      </p>
     * <p>功能说明：对象转map
     *
     * </p>
     *<p>参数说明：</p>
     * @param obj
     * @return
     *
     * @date   创建时间：2017年2月24日
     * @author 作者：xds
     */
	public static Map<String,Object> converBean2Map(Object obj){
    	Class<?> type = obj.getClass();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(type);
			 PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
		        for (int i = 0; i< propertyDescriptors.length; i++) {
		            PropertyDescriptor descriptor = propertyDescriptors[i];
		            String propertyName = descriptor.getName();
		            Method readMethod = descriptor.getReadMethod();
	                Object result = readMethod.invoke(obj, new Object[0]);
		            if (!propertyName.equals("class")) {		             
		                if (result != null) {
		                	 returnMap.put(propertyName, result);		                	
		                } else {	
		                	if(result instanceof BigDecimal){
		                	   returnMap.put(propertyName, 0);
		                	} else {
		                	   returnMap.put(propertyName, "");
		                	}
		                }
		            } else { //bean
		            //	returnMap.put(propertyName, converBean2Map(result));
		            }
		        }			
		} catch (IntrospectionException e) {
			logger.error("对象转Map出错:", e);
			throw new RuntimeException("对象转Map出错:",e);
		} catch (InvocationTargetException e) {
			logger.error("对象转Map出错:", e);
			throw new RuntimeException("对象转Map出错:",e);
		} catch (IllegalAccessException e) {
			logger.error("对象转Map出错:", e);
			throw new RuntimeException("对象转Map出错:",e);
		}       
        return returnMap;
    }
	
	/**
	 * 
	 * <p>函数名称：converBean2List        </p>
	 * <p>功能说明：列表对象转列表map
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param listObj
	 * @return
	 *
	 * @date   创建时间：2018年1月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 * @param <T>
	 */
	public static <T> List<Map<String,Object>> converBean2List(List<T> listObj){
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		if( listObj == null) return resultList;
		for(Object obj : listObj){
			if(obj == null) continue;
			resultList.add(converBean2Map(obj));
		}
		return resultList;
	}

	
	
	/**

	 * 判断是否为Bean对象

	 * @param clazz 待测试类

	 * @return 是否为Bean对象

	 */
	public static boolean isBean(Class<?> clazz){
		if(isNormalClass(clazz)){
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if(method.getParameterTypes().length == 1 && method.getName().startsWith("set")){
					//检测包含标准的setXXX方法即视为标准的JavaBean
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isNormalClass(Class<?> clazz) {
		return null != clazz && false == clazz.isInterface() && false == isAbstract(clazz) && false == clazz.isEnum() && false == clazz.isArray() && false == clazz.isAnnotation() && false == clazz
				.isSynthetic() && false == clazz.isPrimitive();
	}
	
	public static boolean isAbstract(Class<?> clazz) {
		return Modifier.isAbstract(clazz.getModifiers());
	}
	
	/**
	 * 
	 * <p>函数名称：objectKeyNullToEmpty        </p>
	 * <p>功能说明：对象转map null值字段会过滤
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param obj
	 * @return
	 *
	 * @date   创建时间：2017年3月23日
	 * @author 作者：xds
	 */
	public static Map<String,Object> objectKeyNullToEmpty(Object obj){
		//暂时先用gson转json 然后转成Map对象 xds20170323
		if(SpringObjectUtilsExt.isEmpty(obj)) return new HashMap<String,Object>();
		return JsonUtilsExt.json2Map(JsonUtilsExt.toJson(obj));
	}
	
	public static Map<String,Object> objectKeyNullToEmptyByCamel(Object obj){
		//暂时先用gson转json 然后转成Map对象 xds20170323
		if(SpringObjectUtilsExt.isEmpty(obj)) return new HashMap<String,Object>();
		return JsonUtilsExt.json2Map(JsonUtilsExt.toJson(obj),true);
	}
}
