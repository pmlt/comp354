package tests;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import common.Netstring;
import common.Netstring.DecodeResult;

public class NetstringTest {

	@Test
	public void testSingle() throws UnsupportedEncodingException {
		byte[] data = Netstring.encode("Hello, world!");
		DecodeResult res = Netstring.decode(data, 0, "UTF-8");
		assertEquals(data.length, res.end_pos);
		assertEquals("Hello, world!", res.data);
		
		//Special case: empty string
		data = Netstring.encode("");
		res = Netstring.decode(data, 0, "UTF-8");
		assertEquals(data.length, res.end_pos);
		assertEquals("", res.data);
	}
	
	@Test
	public void testMultiple() throws UnsupportedEncodingException {
		byte[] msg1 = Netstring.encode("Hello, world!");
		byte[] msg2 = Netstring.encode("");
		byte[] msg3 = Netstring.encode("This is a test");
		byte[] combined = Netstring.combine(msg1, msg2);
		combined = Netstring.combine(combined, msg3);
		DecodeResult res = Netstring.decode(combined, 0, "UTF-8");
		assertEquals(msg1.length, res.end_pos);
		assertEquals("Hello, world!", res.data);
		res = Netstring.decode(combined, res.end_pos, "UTF-8");
		assertEquals(msg1.length + msg2.length, res.end_pos);
		assertEquals("", res.data);
		res = Netstring.decode(combined, res.end_pos, "UTF-8");
		assertEquals(combined.length, res.end_pos);
		assertEquals("This is a test", res.data);
	}
	
	@Test
	public void testInvalidPrologue() throws UnsupportedEncodingException {
		byte[] data = "12This is a test".getBytes();
		DecodeResult res = Netstring.decode(data, 0, "UTF-8");
		assertEquals(0, res.end_pos);
		assertNull(res.data);
		
		//Special case: empty prologue
		data = ":This is a test".getBytes();
		res = Netstring.decode(data, 0, "UTF-8");
		assertEquals(0, res.end_pos);
		assertNull(res.data);
		
		//Special case: decode empty buffer
		data = new byte[0];
		res = Netstring.decode(data, 0, "UTF-8");
		assertEquals(0, res.end_pos);
		assertNull(res.data);
	}
	
	@Test
	public void testIncompleteData() throws UnsupportedEncodingException {
		byte[] data= "36:This is a test".getBytes();
		DecodeResult res = Netstring.decode(data, 0, "UTF-8");
		assertEquals(0, res.end_pos);
		assertNull(res.data);
		
		//Special case: only the comma at the end is missing
		data= "0:".getBytes();
		res = Netstring.decode(data, 0, "UTF-8");
		assertEquals(0, res.end_pos);
		assertNull(res.data);
	}

}
