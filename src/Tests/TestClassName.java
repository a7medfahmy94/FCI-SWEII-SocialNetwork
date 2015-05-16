package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClassName {
  @Test
  public void f() {
	  String actual = "Mohamed";
	  String expected = "Mohamed";
	  Assert.assertEquals(actual, expected);
  }
}
