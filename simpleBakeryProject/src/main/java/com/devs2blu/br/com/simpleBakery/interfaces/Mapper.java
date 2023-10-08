package com.devs2blu.br.com.simpleBakery.interfaces;

import java.util.Collection;
import java.util.List;

/**
 * @param <T> Entity
 * @param <U> Request
 * @param <V> Response
 */
public interface Mapper <T, U, V> {
    V toResponse(T t);
    T toEntity(U request);
    List<V> toResponseList(Collection<T> list);
    T updateEntity(T t, U request);
}
