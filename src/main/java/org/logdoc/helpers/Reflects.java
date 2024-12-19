package org.logdoc.helpers;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 15:24
 * code-helpers ☭ sweat and blood
 */
public class Reflects {
    @SuppressWarnings("unchecked")
    public static <T> T instantiateClass(final Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Constructor<?> ctr = clazz.getDeclaredConstructor((Class<?>[]) null);
        makeAccessible(ctr);

        return (T) ctr.newInstance();
    }

    public static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()))
            field.setAccessible(true);
    }

    public static void makeAccessible(final Method method) {
        if (!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
            method.setAccessible(true);
    }

    public static void makeAccessible(final Constructor<?> ctor) {
        if (!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
            ctor.setAccessible(true);
    }

    public static boolean isServiceMethod(final Method m) {
        final int mods = m.getModifiers();

        return m.isSynthetic() || (mods & Modifier.PRIVATE) > 0 || (mods & Modifier.STATIC) > 0;
    }

    public static Field[] getFields(final Object o) {
        return getFields(o.getClass());
    }

    public static Field[] getFields(final Class<?> cls) {
        final Set<Field> list = new HashSet<>(0);
        final Set<String> uniqueNames = new HashSet<>(0);

        list.addAll(Arrays.asList(cls.getDeclaredFields()));

        if (cls.getSuperclass() != null)
            Collections.addAll(list, getFields(cls.getSuperclass()));

        final List<Field> toRemove = new ArrayList<>(0);

        toRemove.addAll(list.parallelStream().filter(f -> !uniqueNames.add(f.getName())).collect(Collectors.toList()));

        if (!toRemove.isEmpty())
            list.removeAll(toRemove);

        return list.toArray(new Field[0]);
    }

    public static Field[] getAllFields(final Class<?> cls) {
        final List<Field> list = new ArrayList<>(0);

        list.addAll(Arrays.asList(cls.getDeclaredFields()));

        if (cls.getSuperclass() != null)
            Collections.addAll(list, getFields(cls.getSuperclass()));

        return list.toArray(new Field[0]);
    }

    public static Field findField(final Object o, final String name) throws NoSuchFieldException {
        return findField(o.getClass(), name);
    }

    public static Field findField(final Class<?> cls, final String name) throws NoSuchFieldException {
        try {
            return cls.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            if (cls.getSuperclass() == null)
                throw e;

            return findField(cls.getSuperclass(), name);
        }
    }

    public static Method findMethod(final Object o, final String name, final Class<?>... params) throws NoSuchMethodException {
        return findMethod(o.getClass(), name, params);
    }

    public static Method findMethod(final Class<?> cls, final String name, final Class<?>... params) throws NoSuchMethodException {
        try {
            return cls.getDeclaredMethod(name, params);
        } catch (NoSuchMethodException e) {
            if (cls.getSuperclass() == null)
                throw e;

            return findMethod(cls.getSuperclass(), name, params);
        }
    }

    public static Set<Method> findMethods(Class<?> cls) {
        final Set<Method> methods = new HashSet<>(0);

        final Method[] mine = cls.getDeclaredMethods();
        methods.addAll(Arrays.asList(mine));

        if (cls.getSuperclass() != null)
            methods.addAll(findMethods(cls.getSuperclass()));

        return methods;
    }

    public static boolean classIsSimple(final Class<?> cls) {
        return cls.isPrimitive() || cls.isEnum() || Void.class.isAssignableFrom(cls) || CharSequence.class.isAssignableFrom(cls) || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls) || Character.class.isAssignableFrom(cls) || Byte.class.isAssignableFrom(cls);
    }

    public static boolean isServiceField(final Field f) {
        final int mods = f.getModifiers();

        return f.isSynthetic() || f.isEnumConstant() || (mods & Modifier.STATIC) > 0 || (mods & Modifier.TRANSIENT) > 0 || (mods & Modifier.FINAL) > 0;
    }

    public static Object getData(final Object o, final String name) throws IllegalAccessException, NoSuchFieldException {
        final Field f = findField(o, name);
        makeAccessible(f);

        return f.get(o);
    }

    public static void setData(final Object o, final String fieldName, final Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field f = findField(o, fieldName);
        makeAccessible(f);
        f.set(o, value);
    }

    public static Set<Method> getAllMethods(Class<?> cls) {
        final Set<Method> methods = new HashSet<>(0);

        final Method[] mine = cls.getDeclaredMethods();
        methods.addAll(Arrays.asList(mine));

        if (cls.getSuperclass() != null)
            methods.addAll(getAllMethods(cls.getSuperclass()));

        return methods;
    }
}
