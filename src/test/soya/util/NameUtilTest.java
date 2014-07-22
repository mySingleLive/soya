package soya.util;

import org.soya.util.NameUtil;

/**
 * @author: Jun Gong
 */
public class NameUtilTest extends SoyaBaseTestCase {

    public void testIsGetterName() {
        assertTrue(NameUtil.isGetterName("getValue"));
        assertTrue(NameUtil.isGetterName("getFirstValue"));
        assertFalse(NameUtil.isGetterName("setValue"));
        assertFalse(NameUtil.isGetterName("value"));
        assertFalse(NameUtil.isGetterName("get"));
    }

    public void testIsSetterName() {
        assertTrue(NameUtil.isSetterName("setValue"));
        assertTrue(NameUtil.isSetterName("setFirstValue"));
        assertFalse(NameUtil.isSetterName("getValue"));
        assertFalse(NameUtil.isSetterName("value"));
        assertFalse(NameUtil.isSetterName("set"));
    }

    public void testIsBooleanGetterName() {
        assertTrue(NameUtil.isBooleanGetterrName("isValue"));
        assertTrue(NameUtil.isBooleanGetterrName("isNameChecked"));
        assertFalse(NameUtil.isBooleanGetterrName("value"));
        assertFalse(NameUtil.isBooleanGetterrName("is"));
    }

    public void testToGetterName() {
        assertEquals("getAge", NameUtil.toGetterName("age"));
        assertEquals("getValue", NameUtil.toGetterName("Value"));
        assertEquals("getFirstValue", NameUtil.toGetterName("firstValue"));
    }

    public void testToSetterName() {
        assertEquals("setAge", NameUtil.toSetterName("age"));
        assertEquals("setValue", NameUtil.toSetterName("Value"));
        assertEquals("setFirstValue", NameUtil.toSetterName("firstValue"));
    }

    public void testToBooleanGetterName() {
        assertEquals("isFirst", NameUtil.toBooleanGetterName("first"));
        assertEquals("isValid", NameUtil.toBooleanGetterName("Valid"));
        assertEquals("isNameChecked", NameUtil.toBooleanGetterName("nameChecked"));
    }

    public void testGetNameFromGetter() {
        assertEquals("age", NameUtil.getNameFromGetter("getAge"));
        assertEquals("value", NameUtil.getNameFromGetter("getValue"));
        assertEquals("firstValue", NameUtil.getNameFromGetter("getFirstValue"));
    }

    public void testGetNameFromSetter() {
        assertEquals("age", NameUtil.getNameFromSetter("setAge"));
        assertEquals("value", NameUtil.getNameFromSetter("setValue"));
        assertEquals("firstValue", NameUtil.getNameFromSetter("setFirstValue"));
    }

    public void testGetNameFromBooleanGetter() {
        assertEquals("first", NameUtil.getNameFromBooleanGetter("isFirst"));
        assertEquals("valid", NameUtil.getNameFromBooleanGetter("isValid"));
        assertEquals("nameChecked", NameUtil.getNameFromBooleanGetter("isNameChecked"));
    }
}
