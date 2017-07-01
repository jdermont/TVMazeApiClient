package omg.jd.tvmazeapiclient.utils

import org.junit.Assert
import org.junit.Test

class StringUtilsTest {

    @Test
    fun startPadZeroTest() {
        Assert.assertEquals("01",StringUtils.startPadZero(1))
        Assert.assertEquals("10",StringUtils.startPadZero(10))
        Assert.assertEquals("100",StringUtils.startPadZero(100))
    }
}