package com.slabs.ddc.demo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.slabs.corda.ddc.contracts.tokens.Erc1155Token;
import com.slabs.corda.ddcClient.config.ConfigInfo;
import com.slabs.corda.ddcClient.config.DDCSdkClient;
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

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joey
 * @title: DDC721ServiceImplTest
 * @projectName sdkdemo
 * @description: TODO
 * @date 2022/4/10下午5:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.JVM)
public class DDC1155ServiceImplTest extends BaseServiceTest{
    String consumer1BDdcId = "157659f9-ea0f-409c-9239-98184fa5f75f";
    Long indexId = 0L;
    Erc1155Token erc721Token = null;

    /**
     * @author joey
     * @title: ClientTest
     * @projectName corda5-customer
     * @description: TODO
     */
    @Before
    public void init() {
        ddcSdkClient = DDCSdkClient.fromFile();
    }

    /**
     * 创建ddc
     */
    @Test
    public void createDDC1155() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("创建ddc: " + ddcSdkClient.getDDC1155Service().setNameAndSymbol("最强DDCBYUYEWGUYWEIUFWIE" + ddc1155Addr, ddc1155Addr));

    }


    /**
     * 安全生成 指定合约类型 ddc标识为uuid
     */
    @Test
    public void safeMint() throws Exception {
        login(host, port, consumer1E, password, ddc721Addr, ddc1155Addr);
        erc721Token = ddcSdkClient.getDDC1155Service().safeMint(consumer1A, BigInteger.TEN, "http://www.uri.com", new byte[]{});
        System.out.println("生成 安全生成 : " + erc721Token);
        Erc1155Token consumer1BToken = ddcSdkClient.getDDC1155Service().safeMint(consumer1B, BigInteger.TEN, "http://www.uri.com", new byte[]{});
        consumer1BDdcId = consumer1BToken.getTokenId().getId();
        indexId = consumer1BToken.getIndexId();

    }

    /**
     * 安全生成 指定合约类型 ddc标识为uuid
     */
    @Test
    public void safeMintBatch() throws SignatureException, InvalidKeyException {
        login(host, port, consumer1E, password, ddc721Addr, ddc1155Addr);
        try {
            Multimap<BigInteger, String> myMultimap = ArrayListMultimap.create();
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            System.out.println("批量安全生成:" + ddcSdkClient.getDDC1155Service().safeMintBatch(consumer2B, myMultimap, new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 账户授权
     */
    @Test
    public void setApprovalForAll() throws Exception {
        login(host, port, consumer1E, password, ddc721Addr, ddc1155Addr);
        System.out.println("授权:" + ddcSdkClient.getDDC1155Service().setApprovalForAll(consumer2C, true));

    }

    /**
     * 账户授权查询
     */
    @Test
    public void isApprovedForAll() throws Exception {
        login(host, port, consumer1E, password, ddc721Addr, ddc1155Addr);
        System.out.println(" 账户授权查询" + ddcSdkClient.getDDC1155Service().isApprovedForAll(consumer1A, consumer2C));
    }

    /**
     * 安全转移
     */
    @Test
    public void safeTransferFrom() throws Exception {
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        System.out.println("安全转移" + ddcSdkClient.getDDC1155Service().safeTransferFrom(consumer1A, consumer1E, consumer1BDdcId, new BigInteger("200"), new byte[]{}));
    }

    /**
     * 安全转移
     */
    @Test
    public void safeBatchTransferFrom() throws Exception {
        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);
        System.out.println("安全转移" + ddcSdkClient.getDDC1155Service().safeTransferFrom(consumer1B, consumer1A, consumer1BDdcId, new BigInteger("1"), new byte[]{}));
    }


    /**
     * 冻结
     */
    @Test
    public void freeze() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println(ddcSdkClient.getDDC1155Service().freeze(consumer1BDdcId));
    }

    /**
     * 解冻
     */
    @Test
    public void unFreeze() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println(ddcSdkClient.getDDC1155Service().unFreeze(consumer1BDdcId));
    }

    /**
     * 销毁
     */
    @Test
    public void burn() throws Exception {
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        System.out.println("销毁：" + ddcSdkClient.getDDC1155Service().burn(consumer1A, consumer1BDdcId));
    }

    /**
     * 销毁
     */
    @Test
    public void burnBatch() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        List<String> ddcIds = new ArrayList<>();
        ddcIds.add(erc721Token.getTokenId().getId());
        System.out.println(ddcSdkClient.getDDC1155Service().burnBatch(consumer1A, ddcIds));

    }

    protected static final String consumer1A = "3486b9b48f86444bb088a52711e698fa_O=NodeB,L=Beijing,C=CN";
    protected static final String consumer1B = "254dbb03dacc47de8c07ec3e409ccad1_O=NodeB,L=Beijing,C=CN";

    /**
     * 查询数量
     */
    @Test
    public void balanceOf() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("查询数量" + ddcSdkClient.getDDC1155Service().balanceOf(consumer1A, "4061157125678333952"));
        System.out.println("查询数量" + ddcSdkClient.getDDC1155Service().balanceOf(consumer1A, "4061157364351008768"));
    }

    /**
     * 查询数量
     */
    @Test
    public void balanceOfBatch() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        Multimap<String, String> myMultimap = ArrayListMultimap.create();
        myMultimap.put(consumer1A, consumer1BDdcId);
        System.out.println("查询数量" + ddcSdkClient.getDDC1155Service().balanceOfBatch(myMultimap));
    }

    @Test
    public void setURI() throws SignatureException, InvalidKeyException {
        login(host, port, "3486b9b48f86444bb088a52711e698fa_O=NodeB,L=Beijing,C=CN", "", ddc721Addr, ddc1155Addr);
        System.out.println("查询链接" + ddcSdkClient.getDDC1155Service().setURI("4061806179793928192", "setU1RI"));

    }

    /**
     * 查询链接
     */
    @Test
    public void ddcURI() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
//        String uri = ddcSdkClient.getDDC1155Service().ddcURI("3486b9b48f86444bb088a52711e698fa_O=NodeB,L=Beijing,C=CN", "4060879943689207808");
        String uri = ddcSdkClient.getDDC1155Service().ddcURI("3486b9b48f86444bb088a52711e698fa_O=NodeB,L=Beijing,C=CN", "4060879943689207808");
        System.out.println("查询链接" + uri);
    }

    /**
     * ddc列表
     */
    @Test
    public void getDDCList() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println(ddcSdkClient.getDDC1155Service().getDDCList(consumer1BDdcId));
    }

    /**
     * 最新DDCID查询
     */
    @Test
    public void getLatestDDCId() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println(ddcSdkClient.getDDC1155Service().getLatestDDCId(ddc1155Addr));

    }

    /**
     * 事件查询
     */
    @Test
    public void getDDCLogInfo() throws Exception {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(1);
        pageDomain.setPageSize(10);
        System.out.println(ddcSdkClient.getDDC1155Service().getDDCLogInfo(pageDomain));

    }

}
