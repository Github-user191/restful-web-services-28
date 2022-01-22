package com.app.rest.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    // DRY method
    private MappingJacksonValue filterDetails(String field1, String field2, String filterEntityName, Object BeanEntityName) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(field1, field2);
        FilterProvider filters = new SimpleFilterProvider().addFilter(filterEntityName, filter);
        MappingJacksonValue mapping = new MappingJacksonValue(BeanEntityName);
        mapping.setFilters(filters);

        return mapping;

    }

    // field1, field2
    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");

        return filterDetails("field1", "field2", "SomeBeanFilter", someBean);

        // filters out field 3
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
//        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
//
//        MappingJacksonValue mapping = new MappingJacksonValue(someBean);
//        mapping.setFilters(filters);
//        return mapping;

    }

    // because we added @JsonIgnore on field3
    // all the instances of it will be removed in the response

    // Dynamic filtering
    // field2, field3
    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfSomeBean() {
        List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value2"),
                new SomeBean("value12", "value22", "value33"));

        // filter out all except field 2 and 3
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(list);
        mapping.setFilters(filters);

        return mapping;
    }
}
