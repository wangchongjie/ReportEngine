package com.baidu.unbiz.devlib;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.util.Assert;

import com.baidu.unbiz.devlib.collection.IdCollector;
import com.baidu.unbiz.devlib.collection.ToKvMapConverter;
import com.baidu.unbiz.devlib.collection.ToMapConverter;
import com.baidu.unbiz.devlib.collection.ToSetMapConverter;
import com.baidu.unbiz.devlib.vo.ViewItem;

public class CollectionTest {

    @Test
    public void testToKvMapConverterTest() {
        List<ViewItem> list = ViewItem.mockList1();
        Map<Integer, String> kvMap = new ToKvMapConverter<ViewItem, Integer, String>() {
            @Override
            public Integer getMapKey(ViewItem obj) {
                return obj.getId();
            }

            @Override
            public String getMapValue(ViewItem obj) {
                return obj.getName();
            }
        }.convert(list);

        Assert.isTrue(kvMap.size() > 1);
        System.out.print(kvMap);
    }

    @Test
    public void testToMapConverterTest() {
        List<ViewItem> list = ViewItem.mockList1();
        Map<Integer, ViewItem> itemMap = new ToMapConverter<Integer, ViewItem>() {
            @Override
            public Integer getMapKey(ViewItem obj) {
                return obj.getId();
            }
        }.convert(list);

        Assert.isTrue(itemMap.size() > 1);
        System.out.print(itemMap);
    }

    @Test
    public void testToSetMapConverter() {
        List<ViewItem> list = ViewItem.mockList1();
        Map<Integer, Set<String>> itemSetMap = new ToSetMapConverter<ViewItem, Integer, String>() {
            @Override
            public Integer getMapKey(ViewItem obj) {
                return obj.getId();
            }

            @Override
            public String getSetValue(ViewItem obj) {
                return obj.getName();
            }
        }.convert(list);

        Assert.isTrue(itemSetMap.size() > 1);
        System.out.print(itemSetMap);
    }

    @Test
    public void testIdCollectorTest() {
        List<ViewItem> list = ViewItem.mockList1();
        IdCollector collector = new IdCollector<ViewItem>() {
        };
        Set<Integer> ids = collector.collectIds(list, "id");
        Set<String> names = collector.collectIds(list, "name");

        Assert.isTrue(ids.size() > 1);
        System.out.print(ids);
        Assert.isTrue(names.size() > 1);
        System.out.print(names);
    }

    @Test
    public void testElseIdCollectorTest() {
        List<ViewItem> list = ViewItem.mockList1();
        Set<Integer> idSet = new HashSet<Integer>();
        idSet.add(1);
        idSet.add(2);

        IdCollector collector = new IdCollector<ViewItem>() {
        };
        Assert.isTrue(collector.containIds(list, "id", idSet));

        collector.removeItemByIds(list, "id", idSet);
        Assert.isTrue(!collector.containIds(list, "id", idSet));
    }

}
