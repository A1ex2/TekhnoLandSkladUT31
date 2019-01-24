package android.a1ex.com.sklad_tsd.ConnectTo1c;

import android.a1ex.com.sklad_tsd.MainActivity;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class SOAP_Dispatcher extends Thread {

    public static final Integer soapParam_timeout = 100;
    public static String soapParam_pass = "31415926";
    public static String soapParam_user = "Админ";
    public static String soapParam_URL = "http://192.168.0.104:8090/ut_3_1/ws/AcceptingOrders.1cws";

    int timeout;
    String URL;
    String user;
    String pass;
    int ACTION;
    SoapObject soap_Response;
    final String NAMESPACE = "www.URI.com";//"ReturnPhones_XDTO";
    String mSoapParam_URL;

    public SOAP_Dispatcher(int SOAP_ACTION) {
        timeout = soapParam_timeout;
        URL = soapParam_URL;
        user = soapParam_user;
        pass = soapParam_pass;
        ACTION = SOAP_ACTION;
        mSoapParam_URL = soapParam_URL;
    }

    @Override
    public void run() {

        switch (ACTION) {
//            case LogInActivity.ACTION_GetLoginList:
//                GetLoginList();
//                break;
//            case LogInActivity.ACTION_Login:
//                Login();
//                break;
            case MainActivity.ACTION_GetCellList:
                getCells();
                break;
            case MainActivity.ACTION_GetProductList:
                getProducts();
                break;
        }

        if (soap_Response != null) {
            MainActivity.soapParam_Response = soap_Response;
            MainActivity.soapHandler.sendEmptyMessage(ACTION);
        } else {
            MainActivity.soapHandler.sendEmptyMessage(MainActivity.ACTION_ConnectionError);
        }
    }

//    void GetLoginList() {
//
//        String method = "GetLoginList";
//        String action = NAMESPACE + "#returnPhones:" + method;
//        SoapObject request = new SoapObject(NAMESPACE, method);
//        soap_Response = callWebService(request, action);
//
//    }

//    void Login() {

//        String method = "Login";
//        String action = NAMESPACE + "#Accepting:" + method;
//        SoapObject request = new SoapObject(NAMESPACE, method);
//        Log.i("TestLogin", ""+LogInActivity.wsParam_LoginID);
//        Log.i("TestLogin", ""+LogInActivity.wsParam_PassHash);
//        request.addProperty("ID", LogInActivity.wsParam_LoginID);
//        request.addProperty("Password", LogInActivity.wsParam_PassHash);
//        soap_Response = callWebService(request, action);

//    }

    void getCells() {

        String method = "GetCellList";
        String action = NAMESPACE + "#returnCells:" + method;
        SoapObject request = new SoapObject(NAMESPACE, method);
        soap_Response = callWebService(request, action);
    }

    void getProducts() {

        String method = "GetProductList";
        String action = NAMESPACE + "#returnProducts:" + method;
        SoapObject request = new SoapObject(NAMESPACE, method);
        soap_Response = callWebService(request, action);
    }

    private SoapObject callWebService(SoapObject request, String action) {

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportBasicAuthSE(URL, user, pass);
        androidHttpTransport.debug = true;

        try {
            androidHttpTransport.call(action, envelope);
            return (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

