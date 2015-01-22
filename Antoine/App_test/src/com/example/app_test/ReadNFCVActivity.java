package com.example.app_test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.IsoDep;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcV;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ReadNFCVActivity extends Activity {

	protected NfcV mynfcv;

	protected final int maxtries = 3;

	protected boolean isTainted = true; // Tag info already read?
	protected byte[] mysysinfo = null; // NfcV SystemInformation - or generated
	protected byte[] myuserdata = null; // buffer user content
	protected boolean[] blocktainted; // true when block is to be uploaded to
										// tag
	protected byte[] blocklocked; // 0 means writable

	protected byte afi = 0;
	public byte nBlocks = 0;
	public byte blocksize = 0;
	public byte[] Id;
	public byte[] UID; // becomes valid when a real tag is contacted
	public byte DSFID = -1;
	public int maxtrans = 0; // tag dependent max transceive length
	public byte lastErrorFlags = -1; // re-set by each transceive
	public byte lastErrorCode = -1; // re-set by each transceive
	public byte manuByte = 0;

	public static final byte BYTE_IDSTART = (byte) 0xe0;
	public static final byte MANU_TAGSYS = 0x04;
	public static final HashMap<Byte, String> manuMap = new HashMap<Byte, String>();

	public static final String TAG = "App_test";
	public static final String MIME_TEXT_PLAIN = "text/plain";

	private TextView t0 = null;
	private TextView t1 = null;
	private TextView t2 = null;
	private TextView t3 = null;
	private TextView t4 = null;
	private TextView t5 = null;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_activity);
		Log.d(TAG, "Starting app");
		t0 = (TextView) findViewById(R.id.r_nfc_type);
		t1 = (TextView) findViewById(R.id.r_read);
		t2 = (TextView) findViewById(R.id.nfc_message);
		t3 = (TextView) findViewById(R.id.id_nfc_hex);
		t4 = (TextView) findViewById(R.id.id_nfc_dec);
		t5 = (TextView) findViewById(R.id.id_nfc_bytes);
		t1.setText("NONE");

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		try {
			Log.d(TAG, "Try");
			resoudreIntent(getIntent());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mNfcAdapter.disableForegroundDispatch(this);
	}

	private void resoudreIntent(Intent intent)
			throws UnsupportedEncodingException {

		String action = intent.getAction();
		// String message = null;
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
			Log.d(TAG, "TAG discovered");
			t1.setText("OK TAG");
			t0.setText("TAG");
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			Log.d(TAG, "TAG ID = " + getTextData(tag.getId()));
			String Id = bytesToHexString(tag.getId());
			t3.setText(Id);
			Log.d(TAG, Id);
			t4.setText((new BigInteger(Id, 16)).toString());
			t5.setText(tag.getId().toString());
			
			ReadNfcV(tag);
			
			// Log.d(TAG, Integer.toString(Integer.parseInt(Id, 16)));
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage[] messages;
			if (rawMsgs != null) {
				messages = new NdefMessage[rawMsgs.length];
				Log.d(TAG, "l = " + rawMsgs.length);
				for (int i = 0; i < rawMsgs.length; i++) {
					messages[i] = (NdefMessage) rawMsgs[i];
					// To get a NdefRecord and its different properties from a
					// NdefMesssage
					NdefRecord record = messages[i].getRecords()[i];
					byte[] id = record.getId();
					short tnf = record.getTnf();
					byte[] type = record.getType();
					String message = getTextData(record.getPayload());
					Log.d(TAG, "message = " + message);
					t2.setText(message);
					t3.setText(id.toString());
				}
			}
		}
	}

	public void ReadNfcV(Tag t) {
		UID = t.getId(); // sysinfo holds the UID in lsb order - Id will be
							// filled lateron from sysinfo!
		// Log.d(TAG,"getId: "+toHex(t.getId()));
		mynfcv = NfcV.get(t);
		try {
			mynfcv.connect();
			mysysinfo = getSystemInformation();
			// explore Nfcv properties..
			// initfields(); // done by getSys..

			maxtrans = mynfcv.getMaxTransceiveLength();
			DSFID = mynfcv.getDsfId();
			Log.d(TAG, nBlocks + " x " + blocksize + " bytes");
			blocklocked = new byte[nBlocks]; // init the lock shadow
			getMultiSecStatus(0, nBlocks); // and fill from tag

			blocktainted = new boolean[nBlocks];
			taintblock(0, nBlocks);

			// Log.d(TAG,"maxtrans "+maxtrans);
			// init space for userdata ?
			myuserdata = new byte[nBlocks * blocksize];
			
			String message = getTextData(myuserdata);
			Log.d(TAG, "message = " + message);
			t2.setText(message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			lastErrorFlags = -1;
			Log.d(TAG, "MyNfcV failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * recreate NfcV Tag from log
	 * 
	 * @param sysinfo
	 *            : the logged system info only
	 * @return
	 */
	public void ReadNfcV(String sysinfo) {
		int startat = 0;
		sysinfo.toLowerCase(); // ignore case
		if (sysinfo.startsWith("0x")) { // lets believe in HEX
			startat = 2;
		}
		mysysinfo = hexStringToByteArray(sysinfo.substring(startat));
		initfields();
		// init space for userdata TODO limit size?
		// myuserdata= new byte[nBlocks*blocksize];
		isTainted = false;
		// TODO fake Tag? mytag = Tag.CREATOR.createFromParcel(???);
	}

	/**
	 * recreate NfcV Tag from log
	 * 
	 * @param sysinfo
	 *            : the logged system info
	 * @param userdata
	 *            : the logged userdata
	 * @return
	 */
	public void ReadNfcV(String sysinfo, String userdata) {
		// this.(sysinfo);
		// TODO fake userdata
		int startat = 0;
		userdata.toLowerCase(); // ignore case
		if (userdata.startsWith("0x")) { // lets believe in HEX
			startat = 2;
		}
		myuserdata = hexStringToByteArray(userdata.substring(startat));
	}

	/**
	 * parse system information byte array into attributes with respect to the
	 * flags found DSFID AFI memsize values (block count and length)
	 */
	private void initfields() {
		byte[] read = mysysinfo;
		if ((null != read) && (12 < read.length) && (0 == read[0])) {// no error
			char flags = (char) read[1]; // s.charAt(1);

			// String s=new String(read);
			// s.substring(2, 9).compareTo(Id.toString()) // the same?
			// set the Id from mysysinfo
			int pos = 2;
			boolean forwardId = false; // the Id field is in lsb order
			if (BYTE_IDSTART == read[pos]) {
				forwardId = true;
				manuByte = read[pos + 1];
			} else if (BYTE_IDSTART == read[pos + 7]) {
				manuByte = read[pos + 6];
				forwardId = false;
			} else
				Log.e(TAG, "Id start byte not found where expected");
			if (null == Id) { // dont overwrite, if given
				Id = new byte[8];
				for (int i = 0; i < 8; i++)
					// TODO decide if Id to be reversed (Zebra needs msb order,
					// that is Id[7] changes between tags)
					Id[i] = (forwardId ? read[pos + i] : read[pos + 7 - i]); // reverse?!
				Log.d(TAG, "Id from sysinfo (reversed): " + toHex(Id));
			}

			pos = 10; // start after flags, Infoflags and Id TODO: change if
						// transceive should eat up the error byte
			if (0 < (flags & 0x1)) { // DSFID valid
				pos++; // already implemented
			}
			if (0 < (flags & 0x2)) { // AFI valid
				afi = (byte) read[pos++];// s.charAt(pos++);
			}
			if (0 < (flags & 0x4)) { // memsize valid
				nBlocks = (byte) (read[pos++] + 1);// (s.charAt(pos++)+1);
				blocksize = (byte) (read[pos++] + 1); // ((s.charAt(pos++)&0x1f)+1);
			}
		}
	}

	/**
	 * @return the stored afi byte
	 */
	public byte getAFI() {
		if (isTainted) { // system info not read yet
			getSystemInformation(); // fill in the fields
		}
		return afi;
	}

	public byte getDsfId() {
		// return mynfcv.getDsfId(); // avoid re-reading
		return DSFID;
	}

	public int getblocksize() {
		return (int) blocksize;
	}

	public int getnBlocks() {
		return (int) nBlocks;
	}

	public byte[] getSystemInformation() {
		if (isTainted) { // dont reread
			mysysinfo = transceive((byte) 0x2b);
			isTainted = false; // remember: we have read it and found it valid
			if (0 == lastErrorFlags) {// no error
				isTainted = false; // remember: we have read it and found it
									// valid
				initfields(); // analyze
			}
		}
		return mysysinfo;
	}

	/**
	 * overload method transceive
	 * 
	 * @return resulting array (or error?)
	 */
	protected byte[] transceive(byte cmd) {
		return transceive(cmd, -1, -1, null);
	}

	protected byte[] transceive(byte cmd, int m) {
		return transceive(cmd, m, -1, null);
	}

	protected byte[] transceive(byte cmd, int m, int n) {
		return transceive(cmd, m, n, null);
	}

	/**
	 * prepare and run the command according to NfcV specification
	 * 
	 * @param cmd
	 *            command byte
	 * @param m
	 *            command length
	 * @param n
	 * @param in
	 *            input data
	 * @return
	 */
	protected byte[] transceive(byte cmd, int m, int n, byte[] in) {
		byte[] command;
		byte[] res = "transceive failed message".getBytes();

		ByteArrayBuffer bab = new ByteArrayBuffer(128);
		// flags: bit x=adressed,
		bab.append(0x00);
		bab.append(cmd); // cmd byte
		// 8 byte UID - or unaddressed
		// bab.append(mytag.getId(), 0, 8);
		// block Nr
		if (-1 != m)
			bab.append(m);
		if (-1 != n)
			bab.append(n);
		if (null != in)
			bab.append(in, 0, in.length);

		command = bab.toByteArray();
		Log.d(TAG, "transceive cmd: " + toHex(command));
		// Log.d(TAG,"transceive cmd length: "+command.length);

		// TODO background!
		try {
			if (!mynfcv.isConnected())
				return res;
			for (int t = maxtries; t > 0; t++) { // retry reading
				res = mynfcv.transceive(command);
				if (0 == res[0])
					break;
			}
		} catch (TagLostException e) { // TODO roll back user action
			Log.e(TAG, "Tag lost " + e.getMessage());
			try {
				mynfcv.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return e.getMessage().getBytes();
		} catch (IOException e) {
			Log.d(TAG, "transceive IOEx: " + e.getMessage() + toHex(res));
			// e.printStackTrace();
			return e.getMessage().getBytes();
		} finally {
			Log.d(TAG, "getResponseFlags: " + mynfcv.getResponseFlags());
			lastErrorFlags = res[0];
			Log.d(TAG, "Flagbyte: " + String.format("%2x", lastErrorFlags));
			if (0 != lastErrorFlags) {
				lastErrorCode = res[1];
				Log.d(TAG,
						"ErrorCodebyte: " + String.format("%2x", lastErrorCode));
			}
		}

		if (0 == mynfcv.getResponseFlags())
			return (res);
		else
			// return new String("response Flags not 0").getBytes();
			return res;
	}

	public void taintblock(int i, int n) {
		for (int j = 0; j < n; j++)
			setblocktaint(j, true);
	}

	public void taintblock(int i) {
		setblocktaint(i, true);
	}

	protected void setblocktaint(int i, boolean b) {
		blocktainted[i] = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.nfc.tech.TagTechnology#getTag()
	 */
	public Tag getTag() {
		// TODO Auto-generated method stub
		// return mytag;
		return mynfcv.getTag();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.nfc.tech.TagTechnology#close()
	 */
	public void close() throws IOException {
		try {
			mynfcv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "close failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.nfc.tech.TagTechnology#connect()
	 */
	public void connect() throws IOException {
		try {
			mynfcv.connect();
		} catch (IOException e) {
			lastErrorFlags = -1; // TODO discriminate error states
			Log.d(TAG, "connect failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.nfc.tech.TagTechnology#isConnected()
	 */
	public boolean isConnected() {
		// TODO Auto-generated method stub
		// mynfcv.getDsfId();
		return mynfcv.isConnected(); // better?
	}

	public byte[] readSingleBlock(int i) {
		byte[] read = transceive((byte) 0x20, i);

		setblocktaint(i, false); // remember we read this block
		if (0 != lastErrorFlags)
			return read; // TODO not so ignorant..

		byte[] res = new byte[read.length - 1]; // drop the (0) flag byte TODO:
												// in transceive?
		for (int l = 0; l < read.length - 1; l++) {
			res[l] = read[l + 1];
			myuserdata[i * blocksize + l] = res[l]; // sort block into our
													// buffer
		}

		return res;

	}

	/**
	 * 
	 * @param i
	 *            starting block number
	 * @param j
	 *            block count
	 * @return block content concatenated
	 */
	public byte[] readMultipleBlocks(int i, int j) {
		if (0 == blocksize) {
			Log.e(TAG, "readMult w/o initfields?");
			getSystemInformation(); // system info was not read yet
		}

		byte[] read = transceive((byte) 0x23, i, j);
		if (0 != read[0])
			return read; // error flag set: TODO left as exercise..

		byte[] res = new byte[read.length - 1]; // drop the (0) flag byte
		for (int l = 0; l < read.length - 1; l++) {
			res[l] = read[l + 1];
			myuserdata[i * blocksize + l] = res[l]; // sort block into our
													// buffer
		}

		if (res.length < j * blocksize)
			return read; // da fehlt was
		for (int k = i; k < j; k++) { // all blocks we read
			setblocktaint(k, false); // untaint blocks we read
			// @TODO reverting block order should be done on demand - or under
			// user control (done again in DDMData)
			// reverse(res,k*blocksize,blocksize); // swap string positions
		}
		return res;
	}

	public byte[] getMultiSecStatus(int i, int n) {
		byte[] read = transceive((byte) 0x2c, i, n - 1);
		Log.d(TAG, "secstatus " + toHex(read));
		if (0 != read[0])
			return read;
		int startat = 1; // TODO transceive will skip the error field soon

		for (int j = 0; j < nBlocks; j++)
			blocklocked[j] = read[startat + i + j];

		return read;
	}

	/**
	 * move anywhere to utils
	 * 
	 * @param s
	 * @return
	 */

	public static String toHex(byte[] in) {
		String text = String.format("0x");
		for (byte element : in) {
			text = text.concat(String.format("%02x", element));
		}
		return text;
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	private String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder(/* "0x" */);
		if (src == null || src.length <= 0) {
			return null;
		}

		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			// System.out.println(buffer);
			stringBuilder.append(buffer);
		}

		return stringBuilder.toString();
	}

	private String getTextData(byte[] payload) {
		if (payload == null)
			return null;
		try {
			String encoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
			int langageCodeLength = payload[0] & 0077;
			return new String(payload, langageCodeLength + 1, payload.length
					- langageCodeLength - 1, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
