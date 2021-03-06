package org.tinyradius.packet;

import static org.junit.Assert.*;
import gnu.crypto.hash.HashFactory;
import gnu.crypto.hash.IMessageDigest;
import net.sf.jradius.util.MSCHAP;
import net.sf.jradius.util.RadiusUtils;

import org.junit.Test;

public class HashTest {
	@Test
	public void it_computes_the_first_digest() {
		String password = "test12345";
		
		byte[] ntResponse = {0x73,0x51,(byte) 0xC0,0x1A,(byte) 0xBB,(byte) 0xA6,0x5D,0x6D,0x7E,(byte) 0xD4,
				0x73,(byte) 0xAA,0x55,0x2E,0x7D,(byte) 0xE9,0x61,(byte) 0xD3,0x12,0x7A,0x3E,(byte) 0xDD,(byte) 0xFF,(byte) 0xD2,};
		byte[] Magic1 = {0x4D, 0x61, 0x67, 0x69, 0x63, 0x20, 0x73, 0x65, 0x72, 0x76, 0x65,
				0x72, 0x20, 0x74, 0x6F, 0x20, 0x63, 0x6C, 0x69, 0x65, 0x6E, 0x74, 0x20, 0x73, 
				0x69, 0x67, 0x6E, 0x69, 0x6E, 0x67, 0x20, 0x63, 0x6F, 0x6E, 0x73, 0x74, 0x61, 0x6E, 0x74 };
		System.err.println("Magic1 (" + RadiusUtils.byteArrayToHexString(Magic1) + ")");
		
		byte[] passwordHash = MSCHAP.NtPasswordHash(password.getBytes());
//		System.err.println("passwordHash (" + RadiusUtils.byteArrayToHexString(passwordHash) + ")");
		byte[] passwordHashHash = MSCHAP.HashNtPasswordHash(passwordHash);
		System.err.println("passwordHashHash (" + RadiusUtils.byteArrayToHexString(passwordHashHash) + ")");
		System.err.println("ntResponse (" + RadiusUtils.byteArrayToHexString(ntResponse) + ")");

		IMessageDigest md = HashFactory.getInstance("SHA-1");
//		md.update(passwordHashHash, 0, 16);
//		md.update(ntResponse, 0, 24);
//		md.update(Magic1, 0, 39);
		md.update(passwordHashHash, 0, 16);
		md.update(ntResponse, 0, 24);
		md.update(Magic1, 0, 39);
		byte[] digest = md.digest();
		System.err.println("digest (" + RadiusUtils.byteArrayToHexString(digest) + ")");
		
		byte[] expectedDigest = new byte[] {0x70, (byte) 0xAD, (byte) 0xB2, (byte) 0xE5, (byte) 0x90, 0x25, (byte) 0x86, (byte) 0xF7, (byte) 0xA9, 
				(byte) 0xFE, (byte) 0xA3, (byte) 0xB4, (byte) 0xE0, (byte) 0x94, (byte) 0x93, 0x1A, (byte) 0xD1, (byte) 0xC5, 0x36, (byte) 0xD5 };
		
		assertEquals(RadiusUtils.byteArrayToHexString(expectedDigest), RadiusUtils.byteArrayToHexString(digest));
		
		
		
		
		
//		digest: {0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 
//			0x7, 0xED, 0x2F, 0xD7, 0x24, 0xD2, 0x33, 0xAA, 0xCE, 0xD0, 0x1, }
//		digest: {0x78, 0xD0, 0xE3, 0x4D, 0x8B, 0xB9, 0xAF, 0xF4, 0x0, 
//			0x7, 0xED, 0x2F, 0xD7, 0x24, 0xD2, 0x33, 0xAA, 0xCE, 0xD0, 0x1, }

	}
}
