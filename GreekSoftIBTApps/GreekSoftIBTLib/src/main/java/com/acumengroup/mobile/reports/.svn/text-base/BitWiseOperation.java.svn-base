package com.acumengroup.mobile.reports;

import android.annotation.SuppressLint;

@SuppressLint("ValidFragment")
public class BitWiseOperation extends BitWiseComparasion {
    short iOrderFlags;

    public short getBitResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 6) & 0x1);

        return bitRes;
    }
    public short getDayResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 3) & 0x1);
        return bitRes;
    }
    public short getIOCResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 1) & 0x1);
        return bitRes;
    }
    public short getGTCResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 2) & 0x1);
        return bitRes;
    }
    public short getEOSResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 0) & 0x1);
        return bitRes;
    }

}
