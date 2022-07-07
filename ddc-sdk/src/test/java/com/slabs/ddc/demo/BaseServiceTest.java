package com.slabs.ddc.demo;

import com.slabs.corda.ddcClient.config.ConfigInfo;
import com.slabs.corda.ddcClient.config.DDCSdkClient;
import com.slabs.corda.ddcClient.util.ConfigUtilsKt;
import com.slabs.corda.ddcClient.util.Strings;
import net.corda.client.rpc.CordaRPCConnection;
import net.corda.core.crypto.Crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.Base64;

public class BaseServiceTest {

    protected DDCSdkClient ddcSdkClient;
    protected static final String oprName = "0428operator0_O=NodeB,L=Beijing,C=CN";
    protected static final String opr = "0428operator0_O=NodeB,L=Beijing,C=CN";
    protected static final String oprDID = "did:bsn:";
    protected static final String platformManageName = "0428platform0_O=NodeB,L=Beijing,C=CN";
    protected static final String platformManage1 = "0428platform0_O=NodeB,L=Beijing,C=CN";
    protected static final String platformManage2 = "0428platform1_O=NodeB,L=Beijing,C=CN";
    protected static final String platformManage3 = "0428platform2_O=NodeB,L=Beijing,C=CN";
    protected static final String consumerName = "0428consumer_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer1A = "0428consumer1A_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer1B = "0428consumer1B_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer1C = "0428consumer1C_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer1D = "0428consumer1D_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer1E = "0428consumer1E_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer1F = "0428consumer1F_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer2A = "0428consumer2A_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer2B = "0428consumer2B_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer2C = "0428consumer2C_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer2D = "0428consumer2D_O=NodeB,L=Beijing,C=CN";
    protected static final String password = "test";

    protected static final String ddc721Addr = "ddc721";
    protected static final String ddc1155Addr = "ddc1155";
    protected static final String bsn = "BSN_O=NodeB,L=Beijing,C=CN";
    protected static final String bsnPassword = "123456";
    protected static final String host = "opbningxia.bsngate.com";
    protected static final Integer port = 18604;

//    public void login(String host, Integer rpcPort, String username, String password, String ddc721Address, String ddc1155Address) {
//        ConfigInfo configInfo = new ConfigInfo();
//        configInfo.setConTimeout(60L);
//        configInfo.setReadTimeout(60L);
//        configInfo.setHost(host);
//        configInfo.setUsername(username);
//        configInfo.setPassword(password);
//        configInfo.setSslPath("C:\\Users\\DELL\\Desktop\\rpcsslkeystore.jks");
//        configInfo.setSslPwd("cordauser");
//        configInfo.setRpcPort(rpcPort);
//        configInfo.setDdc721Address(ddc721Address);
//        configInfo.setDdc1155Address(ddc1155Address);
//        CordaRPCConnection rpcConnect = ConfigUtilsKt.getRpcConnect(configInfo);
//        ddcSdkClient = new DDCSdkClient(rpcConnect.getProxy());
//    }

    public void login(String host, Integer rpcPort, String username, String password, String ddc721Address, String ddc1155Address) throws SignatureException, InvalidKeyException {

        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setConTimeout(60L);
        configInfo.setReadTimeout(60L);
        configInfo.setHost(host);
        configInfo.setSslPath("C:\\Users\\DELL\\Desktop\\rpcsslkeystore.jks");
        configInfo.setSslPwd("cordauser");
        configInfo.setRpcPort(rpcPort);
        configInfo.setDdc721Address(ddc721Address);
        configInfo.setDdc1155Address(ddc1155Address);
        configInfo.setUsername(username);

        if(Strings.INSTANCE.isEmpty(password)) {

//            String privateKeyString = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgeGOhVzGgyScFOx/yDiQ9Veiv5So3ymE+DNu/D6SwfRSgCgYIKoZIzj0DAQehRANCAARj1UaMExtP40SjGLi+Qeph1gPDEPNb8n9VrmDssIZmW3Bp864qJs9zR/fQCLOJPd3qT2Vt9mVEmmaNvXm3Wcvo";
            String privateKeyString = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgrbCjde1DqxB/ND/aW6MT5BSVDV2+bt3v8a9Yi20IjhmgCgYIKoZIzj0DAQehRANCAAR3Ht6yagrMx0/a5wDf8yV2VSUkLHmNVg1LGzJ8cmhoWGpCbKN8F+EPKxkFBWHC60sUpO5P0DIbi5TvXwpo2Odd";
            byte[] decode = Base64.getDecoder().decode(privateKeyString);
            PrivateKey privateKey = Crypto.decodePrivateKey(decode);
            // step 1 获取当前时间戳
            Long current = System.currentTimeMillis();
            // step 2 拼接签名内容
            String signString = username + "&" +current;
            // step 3 签名
            byte[] bytes = Crypto.doSign(privateKey, signString.getBytes(StandardCharsets.UTF_8));
            // step 4 将签名内容base64和时间戳拼接成password
            String connectPwd = current+ "&"+Base64.getEncoder().encodeToString(bytes);
            configInfo.setPassword(connectPwd);

        } else {
            configInfo.setPassword(password);

        }

        CordaRPCConnection rpcConnect = ConfigUtilsKt.getRpcConnect(configInfo);
        ddcSdkClient = new DDCSdkClient(rpcConnect.getProxy());
    }
}
