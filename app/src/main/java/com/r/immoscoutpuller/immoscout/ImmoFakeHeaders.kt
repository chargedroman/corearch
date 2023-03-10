package com.r.immoscoutpuller.immoscout

import okhttp3.Request
import java.io.Serializable

/**
 * keeping for reference
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
            map["accept"] = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
            map["accept-encoding"] = "gzip, deflate, br"
            map["accept-language"] = "en-GB,en;q=0.9,de-DE;q=0.8,de;q=0.7,fr-FR;q=0.6,fr;q=0.5,es-ES;q=0.4,es;q=0.3,en-US;q=0.2,de-AT;q=0.1,nl-NL;q=0.1,nl;q=0.1,de-CH;q=0.1,ru-RU;q=0.1,ru;q=0.1,uk-UA;q=0.1,uk;q=0.1"
            map["cache-control"] = "no-cache"
            map["cookie"] = ""
            map["pragma"] = "no-cache"
            map["sec-ch-ua"] = "\"Chromium\";v=\"110\", \"Not A(Brand\";v=\"24\", \"Google Chrome\";v=\"110\""
            map["sec-ch-ua-mobile"] = "?0"
            map["sec-ch-ua-platform"] = "macOs"
            map["sec-fetch-dest"] = "document"
            map["sec-fetch-mode"] = "navigate"
            map["sec-fetch-site"] = "none"
            map["sec-fetch-user"] = "?1"
            map["upgrade-insecure-requests"] = "1"
            map["cookie"] = "ABNTEST=1678384602; is24_experiment_visitor_id=6faf3ea1-5c35-4e09-a6ea-b6c95310d8ab; is24_experiment_reporting=sea-3360-new-schaufenster-design=new_design_1; optimizelyUniqueVisitorId=e7f3ccaa-8a78-44d4-ae49-fa5ee3970882; utag_main=v_id:0186c7849f59001eb563c4a53b4205075002106d00942\$_sn:1\$_se:4\$_ss:0\$_st:1678386433154\$ses_id:1678384602970;exp-session\$_pn:1;exp-session\$referrer_to_lp:;exp-session\$psn_usr_login_cookie:false;exp-1709920604208\$cdp_ab_splittest:GroupB; longUnreliableState=\"dWlkcg==:YS0wMTg2Yzc4NDlmNTkwMDFlYjU2M2M0YTUzYjQyMDUwNzUwMDIxMDZkMDA5NDI=\""
            map["user-agent"] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
            return ImmoFakeHeaders(map)
        }
    }

}
