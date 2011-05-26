package de.po.mobile.note;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import android.util.Log;

public class Note {
	private static final String TAG = "Note";
	
	private String id;
	private String content;
	private Date creation;
	private Date modification;

	public Note(String contentIn) {
		this.content = contentIn;
		this.creation = new Date();
		this.modification = new Date();
		this.id = _generateID();
	}

	public Note(String idIn, String contentIn, Date creationIn,
			Date modificationIn) {
		this.id = idIn;
		this.content = contentIn;
		this.creation = creationIn;
		this.modification = modificationIn;
		Log.d(TAG, "Note with id=" + id + ", content=" + content + ", creation=" + creation + ", modification=" + modification + ".");
	}

	public void setContent(String contentIn) {
		if (!content.equals(contentIn)) {
			this.content = contentIn;
			this.modification = new Date();
		}
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return getContent(true);
	}

	public String getContent(boolean withTitle) {
		if (withTitle) {
			return content;
		} else {
			String[] parts = content.split(
					System.getProperty("line.separator"), 2);
			if (parts.length > 1) {
				return parts[1];
			} else {
				return "";
			}
		}
	}

	public String getTitle() {
		String[] parts = content.split(System.getProperty("line.separator"), 2);
		return parts[0];
	}

	public Date getModification() {
		return modification;
	}

	public Date getCreation() {
		return creation;
	}

	/**
	 * todo check if this id already exists in this system
	 * 
	 * @return
	 */
	private String _generateID() {
		String meal = id + content + creation.toString()
				+ modification.toString();
		Random rand = new Random(meal.length());
		meal += rand.nextDouble();

		MessageDigest md;
		BigInteger sum = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] md5sum = md.digest(meal.getBytes("UTF-8"));
			sum = new BigInteger(1, md5sum);
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "MD5 is not supported");
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "UTF-8 is not supported");
		}

		String out = sum.toString(16);
		while (out.length() < 32) {
			out = "0" + out;
		}

		return out;
	}
}
