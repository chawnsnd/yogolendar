package util;

import java.security.MessageDigest;

public class PasswordUtil {
//	public static String passwordMasking() {
//		final String password, message = "Enter password";
//		if (System.console() == null) { // inside IDE like Eclipse or NetBeans
//			final JPasswordField pf = new JPasswordField();
//			password = JOptionPane.showConfirmDialog(null, pf, message, JOptionPane.OK_CANCEL_OPTION,
//					JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(pf.getPassword()) : "";
//		} else
//			password = new String(System.console().readPassword("%s> ", message));
//		return password;
//	}
	public static String encryptSHA256(String str) {
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return hexString.toString();
	}
}