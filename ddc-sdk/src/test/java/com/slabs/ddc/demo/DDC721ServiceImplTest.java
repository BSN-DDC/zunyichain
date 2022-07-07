package com.slabs.ddc.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.slabs.corda.common.contract.TokenId;
import com.slabs.corda.ddc.contracts.tokens.Erc721Token;
import com.slabs.corda.ddc.contracts.tokens.Erc721TransferDto;
import com.slabs.corda.ddcClient.config.ConfigInfo;
import com.slabs.corda.ddcClient.config.DDCSdkClient;
import com.slabs.corda.ddcClient.dto.ddc.PageDomain;
import com.slabs.corda.ddcClient.service.AuthorityService;
import com.slabs.corda.ddcClient.service.ChargeService;
import com.slabs.corda.ddcClient.service.DDC1155Service;
import com.slabs.corda.ddcClient.service.DDC721Service;
import com.slabs.corda.ddcClient.util.ConfigUtilsKt;
import net.corda.client.rpc.CordaRPCConnection;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.json.JsonObject;
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
public class DDC721ServiceImplTest extends BaseServiceTest{

    private String platformManage1ddcId = "";
    private String consumer1ADdcId = "451cedca-15cc-491e-9f72-0d9b369fe19b";
    private String consumer1BDdcId = "4cb42008-6c9a-4abf-a91b-27963d6a2962";
    private Long indexId = 0L;

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
    public void createDDC721() throws SignatureException, InvalidKeyException {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("创建ddc: " + ddcSdkClient.getDDC721Service().setNameAndSymbol("测试数字藏品" + ddc721Addr, ddc721Addr));
        } catch (Exception e) {
            System.out.println("error 创建ddc：");
            e.printStackTrace();
        }
    }

    /**
     * 生成 指定合约类型 ddc标识为uuid
     */
    @Test
    public void mint() throws Exception {
        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);

        for (int i = 0; i < 10000; i++) {
            System.out.println("生成 指定合约类型 : " + ddcSdkClient.getDDC721Service().mint(consumer1B, "", ddc721Addr));
        }

//        Erc721Token platformManage1Token = ddcSdkClient.getDDC721Service().mint(platformManage1, "http://www.uri.com", ddc721Addr);
//        platformManage1ddcId = platformManage1Token.getTokenId().getId();
//        indexId = platformManage1Token.getIndexId();
//        Erc721Token consumer1AToken = ddcSdkClient.getDDC721Service().mint(consumer1A, "http://www.uri.com", ddc721Addr);
//        consumer1ADdcId = consumer1AToken.getTokenId().getId();
//        Erc721Token consumer1BToken = ddcSdkClient.getDDC721Service().mint(consumer1B, "http://www.uri.com", ddc721Addr);
//        consumer1BDdcId = consumer1BToken.getTokenId().getId();
    }

//    /**
//     * 批量生成
//     */
//    @Test
//    public void mintBatch() throws Exception {
//        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
//        Multimap<String, String> myMultimap = ArrayListMultimap.create();
//        myMultimap.put(consumer2C, "http://www.uri.com");
//        myMultimap.put(consumer2C, "http://www.uri.com");
//        myMultimap.put(consumer1A, "http://www.uri.com");
//        myMultimap.put(consumer1A, "http://www.uri.com");
//        myMultimap.put(consumer1E, "http://www.uri.com");
//        System.out.println("批量安全生成:" + ddcSdkClient.getDDC721Service().mintBatch(ddc721Addr, myMultimap));
//
//    }

    /**
     * 安全生成 指定合约类型 ddc标识为uuid
     */
    @Test
    public void safeMint() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("安全转移：" + ddcSdkClient.getDDC721Service().safeMint(ddc721Addr, consumer1D, "sss", new byte[]{}));

    }

    /**
     * 安全生成 指定合约类型 ddc标识为uuid
     */
//    @Test
//    public void safeMintBatch() throws Exception {
//        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
//        Multimap<String, String> myMultimap = ArrayListMultimap.create();
//        myMultimap.put(consumer2C, "http://www.uri.com");
//        myMultimap.put(consumer2C, "http://www.uri.com");
//        myMultimap.put(consumer1A, "http://www.uri.com");
//        myMultimap.put(consumer1A, "http://www.uri.com");
//        myMultimap.put(consumer1E, "http://www.uri.com");
//        System.out.println("批量安全生成:" + ddcSdkClient.getDDC721Service().safeMintBatch(ddc721Addr, myMultimap, new byte[]{}));
//    }

    /**
     * 授权
     */
    @Test
    public void approve() throws Exception {
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        System.out.println("授权:" + ddcSdkClient.getDDC721Service().approve(consumer1B, consumer1ADdcId));

    }

    /**
     * 授权
     */
//    @Test
//    public void approveBatch() throws Exception {
//        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
//        Multimap<String, String> myMultimap = ArrayListMultimap.create();
//        myMultimap.put(consumer1C, consumer1BDdcId);
//        System.out.println("批量授权：" + ddcSdkClient.getDDC721Service().approveBatch(ddc721Addr, myMultimap));
//
//    }

    /**
     * ddc授权查询
     */
    @Test
    public void getApproved() throws Exception {
        login(host, port, consumer1C, password, ddc721Addr, ddc1155Addr);
        System.out.println("ddc授权查询:" + ddcSdkClient.getDDC721Service().getApproved(consumer1BDdcId));

    }

    /**
     * 账户授权
     */
    @Test
    public void setApprovalForAll() throws Exception {
        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);
        System.out.println("账户授权：" + ddcSdkClient.getDDC721Service().setApprovalForAll(consumer1D, true));
    }

    /**
     * 账户授权查询
     */
    @Test
    public void isApprovedForAll() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("账户授权查询：" + ddcSdkClient.getDDC721Service().isApprovedForAll(consumer1C, consumer1A));
        System.out.println("账户授权查询：" + ddcSdkClient.getDDC721Service().isApprovedForAll(consumer1C, consumer1D));

    }

    /**
     * 安全转移
     */
    @Test
    public void safeTransferFrom() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("安全转移：" + ddcSdkClient.getDDC721Service().safeTransferFrom(consumer1E, consumer1A, consumer1ADdcId, new byte[]{}));

    }

    /**
     * 安全转移
     */
//    @Test
//    public void safeBatchTransferFrom() throws Exception {
//        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
//        List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
//        erc721TransferDtos.add(new Erc721TransferDto(new TokenId(consumer1ADdcId), consumer1A, consumer1E));
//        erc721TransferDtos.add(new Erc721TransferDto(new TokenId(consumer1BDdcId), consumer1D, consumer1B));
//        System.out.println("批量安全转移：" + ddcSdkClient.getDDC721Service().safeBatchTransferFrom(ddc721Addr, erc721TransferDtos, new byte[]{}));
//
//    }

    /**
     * 转移
     */
    @Test
    public void transferFrom() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("安全转移：" + ddcSdkClient.getDDC721Service().safeTransferFrom(consumer1B, consumer1D, consumer1BDdcId, new byte[]{}));

    }

    /**
     * 转移
     */
//    @Test
//    public void batchTransferFrom() throws Exception {
//        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
//        List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
//        erc721TransferDtos.add(new Erc721TransferDto(new TokenId(consumer1ADdcId), consumer1A, consumer1E));
//        erc721TransferDtos.add(new Erc721TransferDto(new TokenId(consumer1BDdcId), consumer1D, consumer1B));
//        System.out.println("批量安全转移：" + ddcSdkClient.getDDC721Service().safeBatchTransferFrom(ddc721Addr, erc721TransferDtos, new byte[]{}));
//    }

    /**
     * 冻结
     */
    @Test
    public void freeze() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println(ddcSdkClient.getDDC721Service().freeze("4060830154272440320"));
    }

    /**
     * 解冻
     */
    @Test
    public void unFreeze() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("解冻：" + ddcSdkClient.getDDC721Service().unFreeze(consumer1ADdcId));
    }

    /**
     * 销毁
     */
    @Test
    public void burn() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("销毁：" + ddcSdkClient.getDDC721Service().burn(consumer1BDdcId));
    }

    /**
     * 销毁
     */
    @Test
    public void burnBatch() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("销毁：" + ddcSdkClient.getDDC721Service().burn(consumer1BDdcId));
    }

    /**
     * 查询数量
     */
    @Test
    public void balanceOf() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("查询数量：" + consumer2A + ddcSdkClient.getDDC721Service().balanceOf(consumer2A));
    }

    /**
     * 查询数量
     */
//    @Test
//    public void balanceOfBatch() throws Exception {
//        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
//        List<String> owners = new ArrayList<>();
//        owners.add(opr);
//        owners.add(platformManage1);
//        owners.add(platformManage2);
//        owners.add(consumer1A);
//        owners.add(consumer1B);
//        owners.add(consumer1C);
//        owners.add(consumer1D);
//        owners.add(consumer2A);
//        owners.add(consumer2B);
//        System.out.println("批量查询数量：" + ddcSdkClient.getDDC721Service().balanceOfBatch(owners));
//    }

    /**
     * 查询拥有者
     */
    @Test
    public void ownerOf() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("查询拥有者:" + ddcSdkClient.getDDC721Service().ownerOf(consumer1ADdcId));
    }

    /**
     * 查询拥有者
     */
//    @Test
//    public void ownerOfBatch() throws Exception {
//        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
//        List<String> ddcIds = new ArrayList<>();
//        ddcIds.add(consumer1ADdcId);
//        ddcIds.add(consumer1BDdcId);
//        System.out.println("批量查询拥有者:" + ddcSdkClient.getDDC721Service().ownerOfBatch(ddc721Addr, ddcIds));
//    }

    /**
     * 查询名称
     */
    @Test
    public void name() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("查询名称:" + ddcSdkClient.getDDC721Service().name(ddc721Addr));

    }

    /**
     * 查询符号
     */
    @Test
    public void symbol() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println("查询符号:" + ddcSdkClient.getDDC721Service().symbol(ddc721Addr));

    }

    /**
     * 查询链接
     */
    @Test
    public void ddcURI() throws Exception {
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        System.out.println("查询符号:" + ddcSdkClient.getDDC721Service().ddcURI(platformManage1ddcId));

    }

    /**
     * 设置链接
     */
    @Test
    public void setURI() throws Exception {
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        System.out.println(ddcSdkClient.getDDC721Service().setURI(platformManage1ddcId, "http://www.cc111.com"));

    }

    /**
     * 交易记录
     */
    @Test
    public void history() throws Exception {
        System.out.println(ddcSdkClient.getDDC721Service().history(platformManage1ddcId));

    }

    /**
     * 名称符号设置
     */
    @Test
    public void setNameAndSymbol() throws Exception {
        System.out.println(ddcSdkClient.getDDC721Service().setNameAndSymbol("ddc721AddrUpdate", "ddc721Addr1"));

    }

    /**
     * ddc列表
     */
    @Test
    public void getDDCList() throws Exception {

        List<Erc721Token> list = ddcSdkClient.getDDC721Service().getDDCList(consumer1A);
        list.forEach( erc721Token ->  {
            System.out.println(erc721Token.toJsonString());
        });

//        System.out.println("ddc列表" + ddcSdkClient.getDDC721Service().getDDCList(consumer1A));

    }

    /**
     * 最新DDCID查询
     */
    @Test
    public void getLatestDDCId() throws Exception {
        System.out.println("最新DDCID查询:" + ddcSdkClient.getDDC721Service().getLatestDDCId("ddc721Addr1"));
        System.out.println("最新DDCID查询:" + ddcSdkClient.getDDC721Service().getLatestDDCId(ddc721Addr));

    }

    /**
     * 事件查询
     */
    @Test
    public void getDDCLogInfo() throws Exception {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(1);
        pageDomain.setPageSize(10);
        System.out.println("事件查询:" + ddcSdkClient.getDDC721Service().getDDCLogInfo(pageDomain));

    }

}
