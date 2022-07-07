package com.slabs.ddc.demo;

import com.slabs.corda.ddcClient.config.ConfigInfo;
import com.slabs.corda.ddcClient.config.DDCSdkClient;
import com.slabs.corda.ddcClient.dto.ddc.AccountInfoBean;
import com.slabs.corda.ddcClient.service.AuthorityService;
import com.slabs.corda.ddcClient.service.ChargeService;
import com.slabs.corda.ddcClient.service.DDC1155Service;
import com.slabs.corda.ddcClient.service.DDC721Service;
import com.slabs.corda.ddcClient.util.ConfigUtilsKt;
import net.corda.client.rpc.CordaRPCConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @author joey
 * @title: AuthorityServiceImplTest
 * @projectName sdkdemo
 * @description: TODO
 * @date 2022/4/10下午3:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitTest {

    /**
     * @author joey
     * @title: ClientTest
     * @projectName corda5-customer
     * @description: TODO
     */
    @Test
    public void init() {
        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setConTimeout(60L);
        configInfo.setReadTimeout(60L);
        configInfo.setHost("localshot");
        configInfo.setUsername("BSN_O=Slabs,L=Beijing,C=CN_O=Node,L=London,C=GB");
        configInfo.setPassword("test");
        configInfo.setRpcPort(12014);
        configInfo.setSslPath("/Users/joey/IdeaProjects/corda5-customer/token/ddcsdk/src/main/resources/rpcsslkeystore.jks");
        configInfo.setSslPwd("cordauser");
        configInfo.setDdc721Address("ddc721Addr");
        configInfo.setDdc1155Address("ddc1155Addr");
        CordaRPCConnection rpcConnect = ConfigUtilsKt.getRpcConnect(configInfo);
        DDCSdkClient ddcSdkClient = new DDCSdkClient(rpcConnect.getProxy());
        AuthorityService authorityService = ddcSdkClient.getAuthorityService();
        ChargeService chargeService = ddcSdkClient.getChargeService();
        DDC721Service ddc721Service = ddcSdkClient.getDDC721Service();
        DDC1155Service ddc1155Service = ddcSdkClient.getDDC1155Service();
    }


}
