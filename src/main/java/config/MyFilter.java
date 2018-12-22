package config;

import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-12-22
 * @Author ZhengTianle
 * Description:
 */
public class MyFilter extends CharacterEncodingFilter {
    public MyFilter(String encoding, boolean forceRequestEncoding, boolean forceResponseEncoding) {
        super(encoding, forceRequestEncoding, forceResponseEncoding);
    }
}
