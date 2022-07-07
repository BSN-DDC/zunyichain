package com.slabs.ddc.demo;


import com.slabs.corda.ddc.flows.event.LogInfo;
import com.slabs.corda.ddcClient.config.ConfigInfo;
import com.slabs.corda.ddcClient.config.DDCSdkClient;
import com.slabs.corda.ddcClient.dto.ddc.PageDomain;
import com.slabs.corda.ddcClient.util.ConfigUtilsKt;
import com.slabs.corda.ddcClient.util.Strings;
import net.corda.client.rpc.CordaRPCConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author joey
 * @title: BlockEventServiceImplTest
 * @projectName demo
 * @description:
 * @date 2022/4/15上午11:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlockEventServiceImplTest {
    private DDCSdkClient ddcSdkClient;
    private static final String oprName = "0428operator0_O=NodeB,L=Beijing,C=CN";
    private static final String oprDID = "did:bsn:";
    private static final String platformManageName = "0428platform0_O=NodeB,L=Beijing,C=CN";
    private static final String platformManage1 = "0428platform0_O=NodeB,L=Beijing,C=CN";
    private static final String platformManage2 = "0428platform1_O=NodeB,L=Beijing,C=CN";
    private static final String platformManage3 = "0428platform2_O=NodeB,L=Beijing,C=CN";
    private static final String consumerName = "0428consumer_O=NodeB,L=Beijing,C=CN";
    private static final String consumer1A = "0428consumer1A_O=NodeB,L=Beijing,C=CN";
    private static final String consumer1B = "0428consumer1B_O=NodeB,L=Beijing,C=CN";
    private static final String consumer1C = "0428consumer1C_O=NodeB,L=Beijing,C=CN";
    private static final String consumer1D = "0428consumer1D_O=NodeB,L=Beijing,C=CN";
    private static final String consumer1E = "0428consumer1E_O=NodeB,L=Beijing,C=CN";
    private static final String consumer1F = "0428consumer1F_O=NodeB,L=Beijing,C=CN";
    private static final String consumer2A = "0428consumer2A_O=NodeB,L=Beijing,C=CN";
    private static final String consumer2B = "0428consumer2B_O=NodeB,L=Beijing,C=CN";
    private static final String consumer2C = "0428consumer2C_O=NodeB,L=Beijing,C=CN";
    private static final String consumer2D = "0428consumer2D_O=NodeB,L=Beijing,C=CN";
    private static final String password = "test";
    private static final String opr = "0428operator0_O=NodeB,L=Beijing,C=CN";

    private static final String ddc721Addr = "ddc721";
    private static final String ddc1155Addr = "ddc1155";
    private static final String host = "opbningxia.bsngate.com";
    private static final Integer port = 18604;

    /**
     * @author joey
     * @title: ClientTest
     * @projectName corda5-customer
     * @description:
     */
//    @Before
//    public void init() {
//        ddcSdkClient = DDCSdkClient.fromFile();
//    }

    @Test
    public void getLogInfo() {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        String hashText = ddcSdkClient.getBlockEventService().getLogInfo(303).get(0).getHashText();
        System.out.println(Strings.INSTANCE.ednToJson(hashText));

    }
    @Test
    public void getLogInfoByTxHash() {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        String txhash = ddcSdkClient.getBlockEventService().getLogInfo(22).get(0).getTxHash();
        String hashText = ddcSdkClient.getBlockEventService().getLogInfoByTxHash(txhash).get(0).getHashText();
        System.out.println(Strings.INSTANCE.ednToJson(hashText));

    }


    @Test
    public void getLogInfoList() {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(1);
        pageDomain.setPageSize(1);

        List<LogInfo> list = ddcSdkClient.getBlockEventService().getLogInfoList(pageDomain);
        list.forEach(logInfo -> {
            System.out.println(logInfo.toString());
            System.out.println(logInfo.getTxDate());
        });

    }

    public void login(String host, Integer rpcPort, String username, String password, String ddc721Address, String ddc1155Address) {
        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setConTimeout(60L);
        configInfo.setReadTimeout(60L);
        configInfo.setHost(host);
        configInfo.setUsername(username);
        configInfo.setPassword(password);
        configInfo.setSslPath("C:\\Users\\DELL\\Desktop\\rpcsslkeystore.jks");
        configInfo.setSslPwd("cordauser");
        configInfo.setRpcPort(rpcPort);
        configInfo.setDdc721Address(ddc721Address);
        configInfo.setDdc1155Address(ddc1155Address);
        CordaRPCConnection rpcConnect = ConfigUtilsKt.getRpcConnect(configInfo);
        ddcSdkClient = new DDCSdkClient(rpcConnect.getProxy());

//        ddcSdkClient = DDCSdkClient.login(60L, 60L, host, rpcPort, username, password, ddc721Address, ddc1155Address);
    }

}
