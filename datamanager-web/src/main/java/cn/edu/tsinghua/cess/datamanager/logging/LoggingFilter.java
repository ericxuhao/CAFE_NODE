package cn.edu.tsinghua.cess.datamanager.logging;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by kurt on 2014/10/6.
 */
public class LoggingFilter implements Filter {

    private static Logger log = Logger.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long begin = System.currentTimeMillis();

        StringBuilder builder = this.buildMessage(request);
        log.info("begin processing request: " + builder.toString());

        try {
            chain.doFilter(request, response);
        } finally {
            long elapsed = System.currentTimeMillis() - begin;
            builder.append("[elapsed=").append(elapsed).append("]");
            log.info("finished processing request: " + builder.toString());
        }
    }
    
    private StringBuilder buildMessage(ServletRequest request) {
        HttpServletRequest r = (HttpServletRequest) request;
        
        StringBuilder builder = new StringBuilder();
        builder.append("[method=").append(r.getMethod()).append("]")
                .append("[uri=").append(r.getRequestURI()).append("]");
        this.printMap(r.getParameterMap(), builder, r.getCharacterEncoding());
        builder.append("[remoteAddr=").append(r.getRemoteAddr()).append("]")
                .append("[remoteHost=").append(r.getRemoteHost()).append("]");
        
        return builder;
    }

    private void printMap(Map<?, ?> map, StringBuilder builder, String enc) {

        builder.append("[parameter=");

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            builder.append("[");

            Object key = entry.getKey();
            Object value = entry.getValue();

            builder.append("[")
                    .append("key=").append(key)
                    .append(", value=").append(toString(value, enc))
                    .append("]");
        }

        builder.append("]");
    }

    private String toString(Object obj, String enc) {
        if (obj == null) {
            return "null";
        } else if (obj.getClass().isArray()) {
            return new ArrayList<Object>(Arrays.asList((Object[]) obj)).toString();
        } else {
            return obj.toString();
        }
    }

    @Override
    public void destroy() {

    }

}
