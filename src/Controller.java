import Library.CalkowicieBig;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;

public class Controller {

    @FXML
    TextField g1, g2, e1, e2, e3, d1, d2, d3;

    @FXML
    public void generate(){
        String publicPath = g1.getText();
        String privatePath = g2.getText();
        RSA rsa = new RSA(32);

        try {
            xd(rsa, publicPath, 0);
            xd(rsa, privatePath, 1);
            AlertBox.display("Wygenerowano klucze. ");
        }
        catch(IOException e){
            AlertBox.display("Blad operacji wejscia/wyjscia. " + e);
        }
    }

    private static void xd(RSA rsa, String path, int x) throws IOException{
        FileWriter fw = new FileWriter(path);
        PrintWriter pw = new PrintWriter(fw);
        if(x == 0) pw.println(rsa.getPublicKey());
        else if(x == 1) pw.println(rsa.getPrivateKey());
        pw.println(rsa.getN());
        pw.close();
    }

    @FXML
    public void encrypt(){

        String pubPath = e1.getText();//"/users/dobrucki/desktop/public.key";
        String file = e2.getText();//"/users/dobrucki/desktop/krecik.jpg";
        String out = e3.getText();//"/users/dobrucki/desktop/out.jpg";
        RSA rsa = new RSA(32);

        String pub = "";
        String n = "";

        File f = new File(pubPath);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fr);
            pub = bufferedReader.readLine();
            n = bufferedReader.readLine();
        }
        catch (FileNotFoundException e){
            AlertBox.display("Nie znaleziono pliku. " + e);
        }
        catch (IOException e){
            AlertBox.display("Blad operacji wejscia wyjscia. " + e);
        }

        CalkowicieBig pubB = new CalkowicieBig(pub);
        CalkowicieBig nB = new CalkowicieBig(n);

        rsa.setPub(pubB, nB);

        ReadWrite rw = new ReadWrite();
        try {
            byte[] message = rw.read(file);
            rsa.encryptFile(message, out);
            AlertBox.display("Zaszyfrowano plik. ");
        }
        catch(IOException e){
            AlertBox.display("Blad operacji wejscia wyjscia. " + e);
        }

    }

    @FXML
    public void decrypt(){
        String privatePath = d1.getText();//"/users/dobrucki/desktop/private.key";
        String cipher = d2.getText();//"/users/dobrucki/desktop/out.jpg";
        String out = d3.getText();//"/users/dobrucki/desktop/odszyfrowany.jpg";
        RSA rsa = new RSA(32);

        String pri = "";
        String n = "";

        File f = new File(privatePath);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fr);
            pri = bufferedReader.readLine();
            n = bufferedReader.readLine();
        }
        catch (FileNotFoundException e){
            AlertBox.display("Nie znaleziono pliku. " + e);
        }
        catch (IOException e){
            AlertBox.display("Blad operacji wejscia wyjscia. " + e);
        }

        CalkowicieBig priB = new CalkowicieBig(pri);
        CalkowicieBig nB = new CalkowicieBig(n);

        rsa.setPriv(priB, nB);

        ReadWrite rw = new ReadWrite();
        try {
            byte[] message = rw.read(cipher);
            rsa.decryptFile(message, out);
            AlertBox.display("Odszyfrowano plik. ");
        }
        catch(IOException e){
            AlertBox.display("Blad operacji wejscia wyjscia. " + e);
        }
    }
}
