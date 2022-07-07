package com.slabs.ddc.demo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.slabs.corda.ddc.contracts.tokens.EdnCommand;
import com.slabs.corda.ddcClient.config.DDCSdkClient;
import com.slabs.corda.ddcClient.dto.ddc.PageDomain;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author joey
 * @title: ChargeServiceImplTest
 * @projectName sdkdemo
 * @description: TODO
 * @date 2022/4/10下午4:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.JVM)
public class ChargeServiceImplTest extends BaseServiceTest{

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
     * 充值
     */
    @Test
    public void recharge() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("运营给终端充值：" + ddcSdkClient.getChargeService().recharge("0428consumer1B_O=NodeB,L=Beijing,C=CN", new BigInteger("2000000")));
    }

    /**
     * 批量充值
     */
    @Test
    public void rechargeBatch() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        Multimap<String, BigInteger> myMultimap = ArrayListMultimap.create();
        myMultimap.put(consumer1B, new BigInteger("200"));
        myMultimap.put(platformManage1, new BigInteger("200"));
        myMultimap.put(platformManage1, new BigInteger("200"));
        System.out.println("批量充值：" + ddcSdkClient.getChargeService().rechargeBatch(myMultimap));

    }


    /**
     * 运营账户充值
     */
    @Test
    public void selfRecharge() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("运营账户充值：" + ddcSdkClient.getChargeService().selfRecharge(new BigInteger("200")));
    }

    /**
     * 设置服务费
     */
    @Test
    public void setFee() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Transfer.name(), new BigInteger("1")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Mint.name(), new BigInteger("2")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Burn.name(), new BigInteger("3")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Approve.name(), new BigInteger("4")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Freeze.name(), new BigInteger("5")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.unFreeze.name(), new BigInteger("6")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.SetURI.name(), new BigInteger("7")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.MintBatch.name(), new BigInteger("8")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.SetNameAndSymbol.name(), new BigInteger("9")));
        System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.Mint.name(), new BigInteger("9")));

    }

    @Test
    public void test() {
        System.out.println(EdnCommand.Type.Transfer.name());
    }

    /**
     * 查询计费设置
     */
    @Test
    public void queryFee() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("查询计费设置：" + ddcSdkClient.getChargeService().queryFee(ddc721Addr, EdnCommand.Type.Transfer.name()));
        System.out.println("查询计费设置：" + ddcSdkClient.getChargeService().queryFee(ddc721Addr, EdnCommand.Type.Mint.name()));
        System.out.println("查询计费设置：" + ddcSdkClient.getChargeService().queryFee(ddc721Addr, EdnCommand.Type.SetNameAndSymbol.name()));
    }


    /**
     * 删除服务费
     */
    @Test
    public void delFee() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("删除服务费：" + ddcSdkClient.getChargeService().delFee(ddc721Addr, EdnCommand.Type.Mint.name()));
    }

    /**
     * 余额查询
     */
    @Test
    public void balanceOf() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("余额查询：" + ddcSdkClient.getChargeService().balanceOf("ddc-fee15_O=NodeA,L=Beijing,C=CN"));
    }

    /**
     * 批量余额查询
     */
    @Test
    public void balanceOfBatch() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        /**
         * 批量余额查询
         */
        ArrayList<String> strings = new ArrayList<>();
        strings.add(platformManage1);
        strings.add(platformManage2);
        strings.add(consumer1A);
        strings.add(consumer1B);
        strings.add(consumer1C);
        strings.add(consumer1D);
        strings.add(consumer2A);
        strings.add(consumer2C);
        System.out.println("批量余额查询：" + ddcSdkClient.getChargeService().balanceOfBatch(strings));

    }

    /**
     * 删除合约
     */
    @Test
    public void delDDC() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("删除合约" + ddcSdkClient.getChargeService().delDDC(ddc721Addr));
    }

    @Test
    public void getChainAccountRechargeLog() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(1);
        pageDomain.setPageSize(10);
        System.out.println("查询充值日志：" + ddcSdkClient.getChargeService().getChainAccountRechargeLog(pageDomain));

    }

    @Test
    public void getChainAccountConsumeLog() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(1);
        pageDomain.setPageSize(10);
        System.out.println("查询充值日志：" + ddcSdkClient.getChargeService().getChainAccountConsumeLog(pageDomain));

    }

    @Test
    public void getChainConfigLog() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(1);
        pageDomain.setPageSize(10);
        ddcSdkClient.getChargeService().getChainConfigLog(pageDomain);

    }

}

