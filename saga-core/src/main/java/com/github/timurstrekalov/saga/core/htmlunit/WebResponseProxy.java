package com.github.timurstrekalov.saga.core.htmlunit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import java.nio.charset.Charset;
import org.apache.http.HttpStatus;

class WebResponseProxy extends WebResponse {

    private final WebResponse delegate;
    private int statusCode = -1;

    public WebResponseProxy(final WebResponse delegate) {
        super(null, null, -1);
        this.delegate = delegate;

        try {
            delegate.getContentAsStream();
        } catch (final Exception e) {
            if (e.getCause() instanceof FileNotFoundException) {
                if (getStatusCode() == HttpStatus.SC_OK) {
                    statusCode = HttpStatus.SC_NOT_FOUND;
                }
            } else {
                if (getStatusCode() == HttpStatus.SC_OK) {
                    statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
                }
            }
        }
    }

    @Override
    public WebRequest getWebRequest() {
        return delegate.getWebRequest();
    }

    @Override
    public List<NameValuePair> getResponseHeaders() {
        return delegate.getResponseHeaders();
    }

    @Override
    public String getResponseHeaderValue(String headerName) {
        return delegate.getResponseHeaderValue(headerName);
    }

    @Override
    public int getStatusCode() {
        if (statusCode != -1) {
            return statusCode;
        }

        return delegate.getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return delegate.getStatusMessage();
    }

    @Override
    public String getContentType() {
        return delegate.getContentType();
    }

    @Override
    public Charset getContentCharsetOrNull() {
        return delegate.getContentCharsetOrNull();
    }

    @Override
    public Charset getContentCharset() {
        return delegate.getContentCharset();
    }

    @Override
    public String getContentAsString() {
        return delegate.getContentAsString();
    }

    @Override
    public String getContentAsString(Charset encoding) {
        return delegate.getContentAsString(encoding);
    }

    @Override
    public InputStream getContentAsStream() throws IOException {
        return delegate.getContentAsStream();
    }


    @Override
    public long getLoadTime() {
        return delegate.getLoadTime();
    }

}
