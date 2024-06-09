package com.springbackend.webservice.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class DozerMapper {
    private static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        return origin.stream()
                     .map(entity -> mapper.map(entity, destination))
                     .collect(Collectors.toList());
    }
}
