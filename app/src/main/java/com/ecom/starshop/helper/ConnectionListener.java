package com.ecom.starshop.helper;

public interface ConnectionListener {
    // Call : runOnUiThread(() -> {...}
    void onConnectChange(boolean isConnected);
}
