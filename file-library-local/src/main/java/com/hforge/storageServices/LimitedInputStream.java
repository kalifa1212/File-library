package com.hforge.storageServices;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream extends FilterInputStream {
    private long remaining;

    public LimitedInputStream(
            InputStream inputStream,
            long length
    ) {
        super(inputStream);
        this.remaining = length;
    }

    @Override
    public int read() throws IOException {

        if (remaining <= 0) {
            return -1;
        }

        int result = super.read();

        if (result != -1) {
            remaining--;
        }

        return result;
    }

    @Override
    public int read(
            byte[] buffer,
            int offset,
            int length
    ) throws IOException {

        if (remaining <= 0) {
            return -1;
        }

        int maxRead = (int) Math.min(
                length,
                remaining
        );

        int bytesRead = super.read(
                buffer,
                offset,
                maxRead
        );

        if (bytesRead != -1) {
            remaining -= bytesRead;
        }

        return bytesRead;
    }
}
