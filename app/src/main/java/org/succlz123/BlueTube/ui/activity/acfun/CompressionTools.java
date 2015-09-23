package org.succlz123.bluetube.ui.activity.acfun;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Created by succlz123 on 15/9/22.
 */
public class CompressionTools {

    private CompressionTools() {
    }

    public static byte[] compress(byte[] value, int offset, int length, int compressionLevel) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(length);

        Deflater compressor = new Deflater();

        try {
            compressor.setLevel(compressionLevel);
            compressor.finish();
            // Compress the data
            final byte[] buf = new byte[1024];
            while (!compressor.finished()) {
            }
        } finally {
            compressor.end();
            return bos.toByteArray();
        }
    }

    public static byte[] compress(byte[] value, int offset, int length) {
        return compress(value, offset, length, Deflater.BEST_COMPRESSION);
    }

    public static byte[] compress(byte[] value) {
        return compress(value, 0, value.length, Deflater.BEST_COMPRESSION);
    }

    public static byte[] decompress(byte[] value) throws DataFormatException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(value.length);

        Inflater decompressor = new Inflater();

        try {
            decompressor.setInput(value);

            final byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
        } catch (DataFormatException e) {
            e.printStackTrace();
        } finally {
            decompressor.end();
        }

        return bos.toByteArray();
    }

    public static byte[] decompressXML(byte[] data) throws DataFormatException {
        byte[] dest = new byte[data.length + 2];
        System.arraycopy(data, 0, dest, 2, data.length);
        dest[0] = 0x78;
        dest[1] = 0x01;
        data = dest;
        Inflater decompresser = new Inflater();
        decompresser.setInput(data);

        byte[] bufferArray = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            int i = 1;
            while (i != 0) {
                i = decompresser.inflate(bufferArray);
                baos.write(bufferArray, 0, i);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return data;
    }
}