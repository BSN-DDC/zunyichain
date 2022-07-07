package com.slabs.ddc.demo;

import com.slabs.corda.common.contract.FreezableStatus;
import com.slabs.corda.ddcClient.config.ConfigInfo;
import com.slabs.corda.ddcClient.config.DDCSdkClient;
import com.slabs.corda.ddcClient.dto.ddc.AccountInfoBean;
import com.slabs.corda.ddcClient.dto.ddc.AccountState;
import com.slabs.corda.ddcClient.dto.ddc.PageDomain;
import com.slabs.corda.ddcClient.util.ConfigUtilsKt;
import net.corda.client.rpc.CordaRPCConnection;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.JVM)
public class AuthorityServiceImplTest extends BaseServiceTest {

    /**
     * @author joey
     * @title: ClientTest
     * @projectName corda5-customer
     * @description: TODO
     */
//    @Before
//    public void init() {
//        ddcSdkClient = DDCSdkClient.fromFile();
//    }


    /**
     * 创建运营账户
     */
    @Test
    public void addOperator() throws Exception {
        login(host, port, bsn, bsnPassword, ddc721Addr, ddc1155Addr);
        String opr = "0428operator0_O=Admin,L=Beijing,C=CN";
        String oprName = "0428operator0_O=Admin,L=Beijing,C=CN";
        String oprDID = "oprDID";
        System.out.println("创建运营账户：" + ddcSdkClient.getAuthorityService().addOperator(opr, oprName + opr, oprDID + opr));

//        System.out.println("创建运营账户：" + ddcSdkClient.getAuthorityService().addOperator(opr, oprName + opr, oprDID + opr));
    }

    /**
     * 运营添加账户
     */
    @Test
    public void addAccountByOperator() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);

//        ddcSdkClient.getAuthorityService().addAccountByOperator(consumer1A, consumer1A, oprDID + consumer1A, oprDID + platformManage1);
        System.out.println(
                ddcSdkClient.getAuthorityService().addAccountByOperator(
                "8c8b55ef97544f4c9765b09465ddb8cc_O=NodeB,L=Beijing,C=CN",
                "zunyi-test-1",
                "did:bsn:3fzzBtQVSE2RQYdMETfeDsgaFuSD",
                null));
//        System.out.println("运营添加平台账户：" + ddcSdkClient.getAuthorityService().addAccountByOperator(platformManage2, platformManageName + platformManage2, oprDID + platformManage2, null));
//        System.out.println("运营添加平台账户：" + ddcSdkClient.getAuthorityService().addAccountByOperator(platformManage1, platformManageName + platformManage1, oprDID + platformManage1, null));
//        System.out.println("运营添加平台账户：" + ddcSdkClient.getAuthorityService().addAccountByOperator(platformManage3, platformManageName + platformManage3, oprDID + platformManage3, null));
//        System.out.println("运营添加终端账户：" + ddcSdkClient.getAuthorityService().addAccountByOperator(consumer1A, consumerName + consumer1A, oprDID + consumer1A, oprDID + platformManage1));

    }

    /**
     * 平台方批量添加账户
     */
    @Test
    public void addBatchAccountByOperator() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        ArrayList<AccountInfoBean> accountInfoBeans = new ArrayList<>();
        AccountInfoBean oPR0 = new AccountInfoBean();
        oPR0.setAccount(consumer1B);
        oPR0.setAccountName(consumerName + consumer1B);
        oPR0.setAccountDID(oprDID + consumer1B);
        accountInfoBeans.add(oPR0);
//        AccountInfoBean oPR1 = new AccountInfoBean();
//        oPR1.setAccount(consumer1D);
//        oPR1.setAccountName(consumerName + consumer1D);
//        oPR1.setAccountoprDID(oprDID + consumer1D);
//        accountInfoBeans.add(oPR1);
//        AccountInfoBean oPRE = new AccountInfoBean();
//        oPRE.setAccount(consumer1E);
//        oPRE.setAccountName(consumerName + consumer1E);
//        oPRE.setAccountoprDID(oprDID + consumer1E);
//        accountInfoBeans.add(oPRE);
//        AccountInfoBean oPRF = new AccountInfoBean();
//        oPRF.setAccount(consumer1F);
//        oPRF.setAccountName(consumerName + consumer1F);
//        oPRF.setAccountoprDID(oprDID + consumer1F);
//        accountInfoBeans.add(oPRF);
        System.out.println("平台方添加账户：" + ddcSdkClient.getAuthorityService().addBatchAccountByOperator(accountInfoBeans));
    }
    /**
     * 平台方添加账户
     */
    @Test
    public void addAccountByPlatform() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("平台方添加账户：" + ddcSdkClient.getAuthorityService().addAccountByPlatform(consumer2B, consumerName + consumer1F, oprDID + consumer1F));
        System.out.println("平台方添加账户：" + ddcSdkClient.getAuthorityService().addAccountByPlatform(consumer2C, consumerName + consumer1F, oprDID + consumer1F));
        System.out.println("平台方添加账户：" + ddcSdkClient.getAuthorityService().addAccountByPlatform(consumer2D, consumerName + consumer1F, oprDID + consumer1F));
    }

    /**
     * 平台方批量添加账户
     */
    @Test
    public void addBatchAccountByPlatform() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        ArrayList<AccountInfoBean> accountInfoBeans = new ArrayList<>();
        AccountInfoBean oPR0 = new AccountInfoBean();
        oPR0.setAccount(consumer1B);
        oPR0.setAccountName(consumerName + consumer1B);
        oPR0.setAccountDID(oprDID + consumer1B);
        accountInfoBeans.add(oPR0);
//        AccountInfoBean oPR1 = new AccountInfoBean();
//        oPR1.setAccount(consumer1D);
//        oPR1.setAccountName(consumerName + consumer1D);
//        oPR1.setAccountoprDID(oprDID + consumer1D);
//        accountInfoBeans.add(oPR1);
//        AccountInfoBean oPRE = new AccountInfoBean();
//        oPRE.setAccount(consumer1E);
//        oPRE.setAccountName(consumerName + consumer1E);
//        oPRE.setAccountoprDID(oprDID + consumer1E);
//        accountInfoBeans.add(oPRE);
//        AccountInfoBean oPRF = new AccountInfoBean();
//        oPRF.setAccount(consumer1F);
//        oPRF.setAccountName(consumerName + consumer1F);
//        oPRF.setAccountoprDID(oprDID + consumer1F);
//        accountInfoBeans.add(oPRF);
        System.out.println("平台方添加账户：" + ddcSdkClient.getAuthorityService().addBatchAccountByPlatform(accountInfoBeans));
    }

    /**
     * 平台方添加链账户开关设置
     */
    @Test
    public void setSwitcherStateOfPlatform() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("平台方添加链账户开关设置：" + ddcSdkClient.getAuthorityService().setSwitcherStateOfPlatform(platformManage1, false));

    }

    /**
     * 平台方添加链账户开关查询
     */
    @Test
    public void switcherStateOfPlatform() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("平台方添加链账户开关设置：" + ddcSdkClient.getAuthorityService().switcherStateOfPlatform(platformManage1));
    }


    /**
     * 更新账户
     */
    @Test
    public void updateAccState() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("更新账户：" + ddcSdkClient.getAuthorityService().updateAccState(platformManage2 , FreezableStatus.Active, false));
    }

    /**
     * 查询账户
     */
    @Test
    public void getAccount() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("查询账户：" + ddcSdkClient.getAuthorityService().getAccount("ddc-fee11_O=NodeA,L=Beijing,C=CN"));
//        System.out.println("查询账户：" + ddcSdkClient.getAuthorityService().getAccount("254dbb03dacc47de8c07ec3e409ccad1_O=NodeB,L=Beijing,C=CN"));
    }

    /**
     * 获取账户日志
     *
     * @return
     */
    @Test
    public void getAccountList() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(1);
        pageDomain.setPageSize(50);
        System.out.println("获取账户日志：" + ddcSdkClient.getAuthorityService().getAccountList(pageDomain));
    }


}
