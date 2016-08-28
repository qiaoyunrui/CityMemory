package com.juhezi.citymemory.util;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public interface Func<T, E> {

    void onSuccess(T t);

    void onFail(E e);

}
