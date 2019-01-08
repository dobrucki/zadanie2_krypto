import Library.CalkowicieBig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class RSA {

    private final static SecureRandom random = new SecureRandom();

    private CalkowicieBig privateKey;
    private CalkowicieBig publicKey;
    private CalkowicieBig n;

    RSA(int N) {

        CalkowicieBig p;
        CalkowicieBig q;
        CalkowicieBig phi;
        CalkowicieBig e = new CalkowicieBig("65537");

        do {
            p = CalkowicieBig.probablePrime(N/2, random);
            q = CalkowicieBig.probablePrime(N/2, random);
            n = p.multiply(q);
            phi = (p.subtract(CalkowicieBig.ONE)).multiply(q.subtract(CalkowicieBig.ONE));
        } while ((!e.gcd(phi).equals(CalkowicieBig.ONE)));

        publicKey = e;
        privateKey = e.modInverse(phi);
    }

    public void setPriv(CalkowicieBig privateKey, CalkowicieBig n){
        this.privateKey = privateKey;
        this.n = n;
    }

    public void setPub(CalkowicieBig publicKey, CalkowicieBig n){
        this.publicKey = publicKey;
        this.n = n;
    }

    public CalkowicieBig getPrivateKey() {
        return privateKey;
    }

    public CalkowicieBig getPublicKey() {
        return publicKey;
    }

    public CalkowicieBig getN() {
        return n;
    }

    CalkowicieBig encrypt(CalkowicieBig message) {
            return message.modPow(publicKey, n);
    }

        CalkowicieBig decrypt(CalkowicieBig encrypted) {
        return encrypted.modPow(privateKey, n);
    }

        void encryptFile(byte[] input, String path) throws IOException {

            CalkowicieBig encrypt;
            File fileCipher = new File(path);
            FileOutputStream fop = new FileOutputStream(fileCipher);
            byte [] fileMessage = completeMessage(input);

            for (int i = 0; i < fileMessage.length / 2; i++) {

                byte[] array = new byte[4];
                byte[] modArray = new byte[returnModulus().length];
                array[0] = 0;
                array[1] = 1;
                System.arraycopy(fileMessage, i * 2, array, 2, 2);
                CalkowicieBig conv = new CalkowicieBig(array);
                encrypt = encrypt(conv);

                byte[] out = bigToByte(encrypt);
                if (out.length < returnModulus().length) {
                    System.arraycopy(out, 0, modArray, returnModulus().length - out.length, out.length);
                    fop.write(modArray);
                    fop.flush();
                } else {

                    fop.write(out);
                    fop.flush();
                }
            }
            fop.close();
        }


        void decryptFile(byte[] input, String path) throws IOException{

        File decryptFile = new File(path);
        FileOutputStream fop = new FileOutputStream(decryptFile);
        CalkowicieBig decrypt;
        byte[] n = returnModulus();

            for (int i = 0; i < input.length / (n.length); i++) {

                byte[] array = new byte[n.length];
                System.arraycopy(input, i * n.length, array, 0, n.length);

                CalkowicieBig conv = new CalkowicieBig(array);
                decrypt = decrypt(conv);
                byte[] test = bigToByte(decrypt);
                byte[] out = new byte[2];
                System.arraycopy(test, 1, out, 0, 2);

                fop.write(out);
                fop.flush();
            }
            fop.close();
        }

    byte[] bigToByte(CalkowicieBig message){

        return  message.toByteArray();
    }

    byte[] returnModulus(){

        return bigToByte(n);
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + n;
        return s;
    }

    public byte[] completeMessage(byte[] data){

        int messageLength = data.length;
        int lenght = 8 - messageLength % 8;
        if(lenght == 8) lenght = 0;
        byte[] output = new byte[messageLength+lenght];

        for(int i=0; i<messageLength; i++){
            output[i]=data[i];
            if(messageLength < 8){
                for(int j = messageLength; j < 8; j ++) {
                    if(j == messageLength) output[j] = (byte) 0x1;
                    else output[j] = 0;
                }
            }
            else if(messageLength > 8){
                int cout = messageLength;
                while((cout)%8 != 0){
                    if(cout == messageLength)output[cout] = (byte) 0x1;
                    else output[cout] = 0;
                    cout++;
                }
            }
        }
        return output;
    }

    public byte[] deleteAddedSigns(byte[] array) {
        int count = 0;
        int i = array.length - 1;
        while (array[i] == 0) {
            count++;
            i--;
        }
        byte[] output = new byte[array.length - count - 1];
        System.arraycopy(array, 0, output, 0, output.length);
        return output;
    }

}

