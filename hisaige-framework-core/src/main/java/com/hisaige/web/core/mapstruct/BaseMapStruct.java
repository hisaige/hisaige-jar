package com.hisaige.web.core.mapstruct;

import java.util.List;

public interface BaseMapStruct<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List <E> toEntity(List<D> dtoList);

    List <D> toDto(List<E> entityList);
}
