package com.jack.lib_common.network;

import java.util.Map;

/**
 * @fileName: NetworkOption
 * @author: susy
 * @date: 2021/5/28 20:44
 * @description: 网络请求参数
 */
public class NetworkOption {
    private String mUrl;
    private String mTag;
    private Map<String, String> mHeaders;

    public NetworkOption(String mTag) {
        this.mTag = mTag;
    }

    public String getTag() {
        return mTag;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public static final class Builder {
        public String mTag;
        public Map<String, String> mHeaders;
        public String mUrl;

        public Builder setTag(String mTag) {
            this.mTag = mTag;
            return this;
        }

        public Builder setHeaders(Map<String, String> mHeaders) {
            this.mHeaders = mHeaders;
            return this;
        }

        public Builder setUrl(String mUrl) {
            this.mUrl = mUrl;
            return this;
        }

        public NetworkOption build() {
            NetworkOption option = new NetworkOption(mTag);
            option.mHeaders = mHeaders;
            option.mUrl = mUrl;
            return option;
        }
    }
}
