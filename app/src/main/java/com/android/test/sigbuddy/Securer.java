package com.android.test.sigbuddy;

class Securer
{
    private int n;

    public Securer(int n)
    {
        this.n=n;
    }

    public String getEncryptedString(String str)
    {
        String encryptedString="";

        for (int i = 0; i < str.length(); i++)
            encryptedString+=(char)(((int)str.charAt(i))+n);

        return encryptedString;
    }

    public String getDecryptedString(String str)
    {
        String decryptedString="";

        for (int i = 0; i < str.length(); i++)
            decryptedString+=(char)(((int)str.charAt(i))-n);

        return decryptedString;
    }
}
