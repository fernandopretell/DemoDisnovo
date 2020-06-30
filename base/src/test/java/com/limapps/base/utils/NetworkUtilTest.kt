package com.limapps.base.utils

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NetworkUtilTest {

    @Mock
    private lateinit var networkInfo: NetworkInfo

    private val wifiType = ConnectivityManager.TYPE_WIFI
    private val mobileType = ConnectivityManager.TYPE_MOBILE
    private val differentType = -1

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    //region NetworkInfo is not connected and is not available
    @Test
    fun `Is NOT connected when Networktype is neither WiFi or Mobile and is not connected and is not available `() {
        whenever(networkInfo.type).thenReturn(differentType)
        whenever(networkInfo.isConnected).thenReturn(false)
        whenever(networkInfo.isAvailable).thenReturn(false)

        Assert.assertFalse(isConnected(networkInfo))
    }

    @Test
    fun `Is NOT connected when Networktype is WiFi and is not connected and is not available `() {
        whenever(networkInfo.type).thenReturn(wifiType)
        whenever(networkInfo.isConnected).thenReturn(false)
        whenever(networkInfo.isAvailable).thenReturn(false)

        Assert.assertFalse(isConnected(networkInfo))
    }

    @Test
    fun `Is NOT connected when Networktype is Mobile and is not connected and is not available `() {
        whenever(networkInfo.type).thenReturn(mobileType)
        whenever(networkInfo.isConnected).thenReturn(false)
        whenever(networkInfo.isAvailable).thenReturn(false)

        Assert.assertFalse(isConnected(networkInfo))
    }
    //endregion

    //region NetworkInfo is connected and is not available
    @Test
    fun `Is NOT connected when Networktype is neither WiFi or Mobile and is connected and is not available `() {
        whenever(networkInfo.type).thenReturn(differentType)
        whenever(networkInfo.isConnected).thenReturn(true)
        whenever(networkInfo.isAvailable).thenReturn(false)

        Assert.assertFalse(isConnected(networkInfo))
    }

    @Test
    fun `Is NOT connected when Networktype is WiFi and is connected and is not available `() {
        whenever(networkInfo.type).thenReturn(wifiType)
        whenever(networkInfo.isConnected).thenReturn(true)
        whenever(networkInfo.isAvailable).thenReturn(false)

        Assert.assertFalse(isConnected(networkInfo))
    }

    @Test
    fun `Is NOT connected when Networktype is Mobile and is connected and is not available `() {
        whenever(networkInfo.type).thenReturn(mobileType)
        whenever(networkInfo.isConnected).thenReturn(true)
        whenever(networkInfo.isAvailable).thenReturn(false)

        Assert.assertFalse(isConnected(networkInfo))
    }
    //endregion

    //region NetworkInfo is not connected and is available
    @Test
    fun `Is NOT connected when Networktype is neither WiFi or Mobile and is not connected and is available `() {
        whenever(networkInfo.type).thenReturn(differentType)
        whenever(networkInfo.isConnected).thenReturn(false)
        whenever(networkInfo.isAvailable).thenReturn(true)

        Assert.assertFalse(isConnected(networkInfo))
    }

    @Test
    fun `Is NOT connected when Networktype is WiFi and is not connected and is available `() {
        whenever(networkInfo.type).thenReturn(wifiType)
        whenever(networkInfo.isConnected).thenReturn(false)
        whenever(networkInfo.isAvailable).thenReturn(true)

        Assert.assertFalse(isConnected(networkInfo))
    }

    @Test
    fun `Is NOT connected when Networktype is Mobile and is not connected and is available `() {
        whenever(networkInfo.type).thenReturn(mobileType)
        whenever(networkInfo.isConnected).thenReturn(false)
        whenever(networkInfo.isAvailable).thenReturn(true)

        Assert.assertFalse(isConnected(networkInfo))
    }
    //endregion

    //region NetworkInfo is connected and is available
    @Test
    fun `Is NOT connected when Networktype is neither WiFi or Mobile and is connected and is available `() {
        whenever(networkInfo.type).thenReturn(differentType)
        whenever(networkInfo.isConnected).thenReturn(true)
        whenever(networkInfo.isAvailable).thenReturn(true)

        Assert.assertFalse(isConnected(networkInfo))
    }

    @Test
    fun `Is connected when Networktype is WiFi and is connected and is available `() {
        whenever(networkInfo.type).thenReturn(wifiType)
        whenever(networkInfo.isConnected).thenReturn(true)
        whenever(networkInfo.isAvailable).thenReturn(true)

        Assert.assertTrue(isConnected(networkInfo))
    }

    @Test
    fun `Is connected when Networktype is Mobile and is connected and is available `() {
        whenever(networkInfo.type).thenReturn(mobileType)
        whenever(networkInfo.isConnected).thenReturn(true)
        whenever(networkInfo.isAvailable).thenReturn(true)

        Assert.assertTrue(isConnected(networkInfo))
    }
    //endregion

}