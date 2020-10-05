package com.r.immoscoutpuller.immoscout

import okhttp3.Request
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 03.10.20
 */

data class ImmoFakeHeaders(
    val headers: HashMap<String, String>
): Serializable {

    fun apply(request: Request.Builder) {
        for(key in headers.keys) {
            val entry = headers[key]
            if(entry != null) request.addHeader(key, entry)
        }
    }


    companion object {

        fun dummy(): ImmoFakeHeaders {
            val map = HashMap<String, String>()
            map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            map.put("accept-encoding", "utf-8")
            map.put("accept-language", "de")
            map.put("sec-fetch-dest", "document")
            map.put("sec-fetch-mode", "navigate")
            map.put("sec-fetch-site", "none")
            map.put("sec-fetch-user", "?1")
            map.put("upgrade-insecure-requests", "1")
            map.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
            //map.put("Cookie", "reese84=3:7vSJTalSi4TMqUNSK0hCwg==:NKkUCj01QWYg+PQQHeuzq/NFz7TkZuCBnd17bt7hZSdJXI0MMuCab4QiHOickH8d+slb4pYp8nQVw8eOpLpTxja36aN8dLFp19o3R3vKnDzydRw/uzZF1Vfg2yG28gILZ8lz8pmTuNj3cTZnl0nSCYfr1yw8JoFkejminS98VAl09ukTOJfhnfWRNZada5+vf97hRKYx4099GL+enMviVhsbUYjVaVgfUwJHEVyTjFpPGVJ+HR9ZHn4LbRHq0lflR1kiLR7T//HLSFG1xHzlO3z0eJYOW/ZQ2AKoZT+gp3VkhKuN/R31jPS3ju8l9PvdG4bBoXkBZdr9AFOn/jIty/9dpscXONGDWT8eYI8CKRozmPwg8uaDKBhFDJLCQ4kJ1tM58FSUzyZ1Gk2Jfb+v6q0CkkRGxiS4NURqKlz6tvg=:1KKSXEnQjt+xL75wO3v6JcyB1Stm6D+xsax/o81fuY8=;")
            map.put("Connection", "keep-alive")
            map.put("Cache-Control", "no-cache")
            map.put("host", "www.immobilienscout24.de")
            return ImmoFakeHeaders(map)
        }
    }

}
