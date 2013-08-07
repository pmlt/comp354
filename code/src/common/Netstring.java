package common;

import java.io.UnsupportedEncodingException;

public class Netstring {
	public static class DecodeResult {
		public String data = null;
		public int end_pos = 0;
	}
	
	public static byte[] slice(byte[] buf, int start, int len) {
		byte[] tmp = new byte[len];
		System.arraycopy(buf, start, tmp, 0, len);
		return tmp;
	}
	public static byte[] combine(byte[] buf1, byte[] buf2) {
		byte[] tmp = new byte[buf1.length + buf2.length];
		System.arraycopy(buf1, 0, tmp, 0, buf1.length);
		System.arraycopy(buf2, 0, tmp, buf1.length, buf2.length);
		return tmp;
	}
	public static DecodeResult decode(byte[] buffer, int start, String encoding) throws UnsupportedEncodingException{
		DecodeResult ret = new DecodeResult();
		if (start >= buffer.length) return ret;
        int pos = start;
        byte c;
    	//Read a new netstring
    	//System.out.println("Read a new netstring (next characters at pos " + pos + ": " + new String(_Remaining, pos, _Remaining.length - pos, encoding) + ")");
    	do {
            c = buffer[pos];
            if (c == 58) {
                break; 
            } else if (c < 48 || c > 57) {
            	//Prologue is invalid; return empty result with the same buffer.
            	ret.data = null;
            	ret.end_pos = 0;
            	return ret;
            }
            pos ++;
        } while (pos < buffer.length);
    	if (pos == start) {
    		//Empty prologue
        	ret.data = null;
        	ret.end_pos = 0;
        	return ret;
    	}
        int len = Integer.parseInt(new String(buffer, start, pos - start));
        pos++; //Skip colon
        if (buffer.length < pos + len + 1) {
        	//Not enough information in buffer for the length of string
        	ret.data = null;
        	ret.end_pos = 0;
        	return ret;
        }
        ret.data = new String(buffer, pos, len, encoding);
        //System.out.println("Parsed a netstring: " + ns);
        ret.end_pos = pos + len + 1; //Skip string and comma
        return ret;
	}
	
	public static byte[] encode(String message) {
		return (message.length() + ":" + message + ",").getBytes();
	}
}
