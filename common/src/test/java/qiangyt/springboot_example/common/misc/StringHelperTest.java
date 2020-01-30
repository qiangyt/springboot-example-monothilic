package qiangyt.springboot_example.common.misc;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author
 *
 */
public class StringHelperTest {


  @Test
  public void test_toString_null() {
    Assert.assertNull(StringHelper.toString(null));
  }

  
  @Test
  public void test_isBlank_happy() {
    Assert.assertTrue(StringHelper.isBlank(null));
    Assert.assertTrue(StringHelper.isBlank(""));
    Assert.assertTrue(StringHelper.isBlank("   "));
    Assert.assertTrue(StringHelper.isBlank("\t"));
    Assert.assertTrue(StringHelper.isBlank("\n"));
    Assert.assertTrue(StringHelper.isBlank("\r"));
    Assert.assertTrue(StringHelper.isBlank("\n\t"));
    
    Assert.assertFalse(StringHelper.isBlank("a"));
    Assert.assertFalse(StringHelper.isBlank(" a "));
    Assert.assertFalse(StringHelper.isBlank(" a"));
    Assert.assertFalse(StringHelper.isBlank("a "));
    Assert.assertFalse(StringHelper.isBlank("\na\t"));
    Assert.assertFalse(StringHelper.isBlank("\ta"));
    Assert.assertFalse(StringHelper.isBlank("a\n"));
  }
  
  @Test
  public void test_notBlank_happy() {
    Assert.assertFalse(StringHelper.notBlank(null));
    Assert.assertFalse(StringHelper.notBlank(""));
    Assert.assertFalse(StringHelper.notBlank("   "));
    Assert.assertFalse(StringHelper.notBlank("\t"));
    Assert.assertFalse(StringHelper.notBlank("\n"));
    Assert.assertFalse(StringHelper.notBlank("\r"));
    Assert.assertFalse(StringHelper.notBlank("\n\t"));
    
    Assert.assertTrue(StringHelper.notBlank("a"));
    Assert.assertTrue(StringHelper.notBlank(" a "));
    Assert.assertTrue(StringHelper.notBlank(" a"));
    Assert.assertTrue(StringHelper.notBlank("a "));
    Assert.assertTrue(StringHelper.notBlank("\na\t"));
    Assert.assertTrue(StringHelper.notBlank("\ta"));
    Assert.assertTrue(StringHelper.notBlank("a\n"));
  }

  
  @Test
  public void test_hasBlank_happy() {
    Assert.assertEquals("", StringHelper.hasBlank(List.of("a", "")));
    Assert.assertEquals("   ", StringHelper.hasBlank(List.of("b", "   ")));
    Assert.assertNull(StringHelper.hasBlank(List.of("a", "b")));
  }

  
  @Test
  public void test_trim_happy() {
    var expected = List.of("a", "b");
    Assert.assertEquals(expected, StringHelper.trim(List.of(" a", "b   ")));
  }

}
