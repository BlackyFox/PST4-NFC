package fr.esiea.nfc.pst4.loyalties;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.nfc.cardemulation.HostApduService;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

//@TargetApi(Build.VERSION_CODES.KITKAT)

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Service d'émulation de carte NFC                                                               */
/**************************************************************************************************/

public class EmulationService extends HostApduService {

    private String card = null;
    private int res;
    private final String TAG = "EmulationService";
    private int messageCounter;
    private static final String SAMPL_LOYALTY_CARD = "A131313131";//"F222222222";
    private static final byte[] SELEC_APDU = BuildSelectApdu(SAMPL_LOYALTY_CARD);
    private static final String SELECT_APDU_HEADER = "00A40400";
    // "OK" status word sent in response to SELECT AID command (0x9000)
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");

    public static final String SERVICE_OVER = "fr.esiea.nfc.pst4.loyalties.EmulationService";
    public static final String RESULT = "result";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("TAG", "onStartCommand");
        if(intent.getExtras() != null)
            card = intent.getStringExtra("CARD_NUM");
        Log.d(TAG, card);
        return START_NOT_STICKY;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.i(TAG, "Deactivated: " + reason);
        res = reason;
        writtingOver();
    }

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        if(Arrays.equals(SELEC_APDU, apdu)){
            Log.d(TAG, "Equals");
            Log.d(TAG, "Sending : "+ ConcatArrays(card.getBytes(), SELECT_OK_SW));
            return ConcatArrays(card.getBytes(), SELECT_OK_SW);
        }else{
            Log.d(TAG, apdu.toString());
            return UNKNOWN_CMD_SW;
        }
    }

    private boolean selectAidApdu(byte[] apdu) {

        if (apdu != null) {
            for (byte b : apdu) {
                System.out.printf("0x%02X", b);
            }
        }

        return apdu.length >= 2 && apdu[0] == (byte) 0
                && apdu[1] == (byte) 0xa4;
    }

    private byte[] getWelcomeMessage() {
        return this.card.getBytes();
    }

    private byte[] getNextMessage() {
        return ("Message from android: " + messageCounter++).getBytes();
    }

    public static byte[] BuildSelectApdu(String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X",
                aid.length() / 2) + aid);
    }

    /**
     * Utility method to convert a byte array to a hexadecimal string.
     *
     * @param bytes Bytes to convert
     * @return String, containing hexadecimal representation.
     */
    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }

    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    private void writtingOver(){
        Intent i = new Intent(SERVICE_OVER);
        i.putExtra(RESULT, this.res);
        sendBroadcast(i);
    }
}
