package org.example.product_shop.util.xmlmapper;

public interface XMLMapperService {
    <T> void marshal(T object, String filePath) throws Exception;

    <T> T unmarshal(Class<T> clazz, String filePath) throws Exception;
}
