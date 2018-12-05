package com.alimuzaffar.model;

import com.alimuzaffar.util.TextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.internal.Base64;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filter {
    private static final List<String> allowedOperands = Arrays.asList("eq", "lte", "gte");
    private static final ObjectMapper mapper = new ObjectMapper();

    private String attribute;
    private String operator;
    private String value;
    private Range range;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public static Filter fromJsonBase64String(String str) throws Exception {
        String utf = new String(Base64.decode(str));
        Filter f = mapper.readValue(utf, Filter.class);
        f.validateFilter();
        if (f.getAttribute().equals("_id") || f.getAttribute().equals("id")) {
            f.setAttribute("document_id");
        }
        return f;
    }

    public static List<Filter> fromJsonBase64String(List<String> strArray) throws Exception {
        List<Filter> filters = new ArrayList<>();
        int valueCount = 0;
        int rangeCount = 0;
        for (String s : strArray) {
            Filter f = fromJsonBase64String(s);
            if (f.rangeExists()) {
                rangeCount++;
            } else {
                valueCount++;
            }
            filters.add(f);
        }
        if (valueCount > 0 && rangeCount > 0) {
            throw new Exception("You can't mix value and range filters");
        }
        return filters;
    }

    private boolean validateFilter() throws Exception {
        if (TextUtils.isEmpty(attribute)) {
            throw new Exception("Attribute must be specified.");
        }
        if (!TextUtils.isEmpty(value)
                && rangeExists()) {
            throw new Exception("Specifying both range and filter is not allowed.");
        }
        if (TextUtils.isEmpty(operator)) {
            throw new Exception("Operator is required.");
        }
        if (TextUtils.isEmpty(attribute)) {
            throw new Exception("Attribute is required.");
        }
        if (!allowedOperands.contains(operator)) {
            throw new Exception("Unknown operator: " + operator);
        }

        if (!operator.equals("eq") && rangeExists()) {
            throw new Exception("range is not allowed with gte or lte operator");
        }

        return true;
    }

    public boolean rangeExists() {
        return range != null && !TextUtils.isEmpty(range.getFrom()) && !TextUtils.isEmpty(range.getTo());
    }
}
