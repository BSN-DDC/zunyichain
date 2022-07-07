package com.slabs.ddc.demo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.slabs.corda.common.contract.FreezableStatus;
import com.slabs.corda.common.contract.TokenId;
import com.slabs.corda.ddc.contracts.tokens.EdnCommand;
import com.slabs.corda.ddc.contracts.tokens.Erc1155Token;
import com.slabs.corda.ddc.contracts.tokens.Erc721Token;
import com.slabs.corda.ddc.contracts.tokens.Erc721TransferDto;
import com.slabs.corda.ddcClient.config.ConfigInfo;
import com.slabs.corda.ddcClient.config.DDCSdkClient;
import com.slabs.corda.ddcClient.dto.ddc.AccountInfoBean;
import com.slabs.corda.ddcClient.dto.ddc.PageDomain;
import com.slabs.corda.ddcClient.service.*;
import com.slabs.corda.ddcClient.util.ConfigUtilsKt;
import net.corda.client.rpc.CordaRPCConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joey
 * @title: AuthorityServiceImplTest
 * @projectName sdkdemo
 * @description:
 * @date 2022/4/10下午3:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowTest {
    private DDCSdkClient ddcSdkClient;
    private static final String oprName = "0428operator0_O=NodeB,L=Beijing,C=CN";
    private static final String opr = "0428operator0_O=NodeB,L=Beijing,C=CN";
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

    private static final String ddc721Addr = "ddc721";
    private static final String ddc1155Addr = "ddc1155";
    private static final String bsn = "BSN_O=NodeB,L=Beijing,C=CN";
    private static final String bsnPassword = "123456";
    private static final String host = "opbningxia.bsngate.com";
    private static final Integer port = 18604;

    /**
     * 流程测试
     */
    @Test
    public void flow1155Test() {
        try {
//
            authorityService();
            chargeService();
            ddc721Service();
            ddc1155Service();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ddc1155Service() {
        /**
         * 充值(只能运营方充值)
         *
         * @return
         */
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        /**
         * 创建ddc
         */
        try {
            System.out.println("创建ddc: " + ddcSdkClient.getDDC1155Service().setNameAndSymbol("最强DDCBYUYEWGUYWEIUFWIE" + ddc1155Addr, ddc1155Addr));
        } catch (Exception e) {
            System.out.println("error 创建ddc：");
            e.printStackTrace();
        }
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);


        /**
         * 生成 指定合约类型 ddc标识为uuid
         */
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        String consumer1BDdcId = "";
        Long indexId = 0L;
        Erc1155Token erc1155Token = null;
        try {
            erc1155Token = ddcSdkClient.getDDC1155Service().safeMint(consumer1A, BigInteger.TEN, "http://www.uri.com", new byte[]{0x22,0x22});
            System.out.println("生成 安全生成 : " + erc1155Token);
            Erc1155Token consumer1BToken = ddcSdkClient.getDDC1155Service().safeMint(consumer1B, BigInteger.TEN, "http://www.uri.com", new byte[]{});
            consumer1BDdcId = consumer1BToken.getTokenId().getId();
            indexId = consumer1BToken.getIndexId();
        } catch (Exception e) {
            System.out.println("error 安全生成：");
            e.printStackTrace();
        }
        /**
         * 批量生成
         */
        try {
            Multimap<BigInteger, String> myMultimap = ArrayListMultimap.create();
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            System.out.println("批量安全生成:" + ddcSdkClient.getDDC1155Service().safeMintBatch(consumer1A, myMultimap, new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 批量安全生成：");
            e.printStackTrace();
        }

        /**
         * 授权
         */
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("授权:" + ddcSdkClient.getDDC1155Service().setApprovalForAll(consumer1C, true));
        } catch (Exception e) {
            System.out.println("error 授权：");
            e.printStackTrace();
        }
        /**
         *  账户授权查询
         */
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println(" 账户授权查询" + ddcSdkClient.getDDC1155Service().isApprovedForAll(consumer1A, consumer1C));
        } catch (Exception e) {
            System.out.println("error ddc授权查询：");
            e.printStackTrace();
        }

        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);
        /**
         * 安全转移
         */
        try {
            System.out.println("安全转移" + ddcSdkClient.getDDC1155Service().safeTransferFrom(consumer1B, consumer1A, consumer1BDdcId, new BigInteger("2"), new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 安全转移：");
            e.printStackTrace();
        }
        /**
         * 安全转移
         */
        try {
            System.out.println("安全转移" + ddcSdkClient.getDDC1155Service().safeTransferFrom(consumer1B, consumer1A, consumer1BDdcId, new BigInteger("1"), new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 安全转移：");
            e.printStackTrace();
        }
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        /**
         * 安全转移
         */
        try {
            Multimap<String, BigInteger> myMultimap = ArrayListMultimap.create();
            myMultimap.put(consumer1BDdcId, new BigInteger("2"));
            System.out.println("批量安全转移" + ddcSdkClient.getDDC1155Service().safeBatchTransferFrom(consumer1A, consumer1B, myMultimap, new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 批量安全转移：");
            e.printStackTrace();
        }
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);

        /**
         * 冻结
         */
        try {
            System.out.println(ddcSdkClient.getDDC1155Service().freeze(consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error 冻结：");
            e.printStackTrace();
        }

        /**
         * 安全转移
         */
        try {
            System.out.println("安全转移" + ddcSdkClient.getDDC1155Service().safeTransferFrom(consumer1B, consumer1A, consumer1BDdcId, new BigInteger("1"), new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 安全转移：");
            e.printStackTrace();
        }
        /**
         * 查询数量
         */
        try {
            System.out.println("查询数量：" + consumer1B + ddcSdkClient.getDDC1155Service().balanceOf(consumer1B, consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error 查询数量：");
            e.printStackTrace();
        }

        /**
         * 解冻
         */
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);

        try {
            System.out.println("解冻" + ddcSdkClient.getDDC1155Service().unFreeze(consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error 解冻：");
            e.printStackTrace();
        }


        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        /**
         * 查询数量
         */
        try {
            System.out.println("查询数量" + ddcSdkClient.getDDC1155Service().balanceOf(consumer1A, consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error 查询数量：");
            e.printStackTrace();
        }
        /**
         * 查询数量
         */
        try {
            Multimap<String, String> myMultimap = ArrayListMultimap.create();
            myMultimap.put(consumer1A, consumer1BDdcId);
            System.out.println("查询数量" + ddcSdkClient.getDDC1155Service().balanceOfBatch(myMultimap));
        } catch (Exception e) {
            System.out.println("error 查询数量：");
            e.printStackTrace();
        }
        /**
         * 查询链接
         */
        try {
            System.out.println("查询链接" + ddcSdkClient.getDDC1155Service().ddcURI(consumer1A, consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error 查询链接：");
            e.printStackTrace();
        }

        /**
         * ddc列表
         */
        try {
            System.out.println(ddcSdkClient.getDDC1155Service().getDDCList(consumer1A));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 最新DDCID查询
         */
        try {
            System.out.println(ddcSdkClient.getDDC1155Service().getLatestDDCId(ddc1155Addr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 事件查询
         */
        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum(1);
            pageDomain.setPageSize(10);
            System.out.println(ddcSdkClient.getDDC1155Service().getDDCLogInfo(pageDomain));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 安全转移
         */
        try {
            System.out.println("安全转移" + ddcSdkClient.getDDC1155Service().safeTransferFrom(consumer1B, consumer1A, consumer1BDdcId, new BigInteger("1"), new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 安全转移：");
            e.printStackTrace();
        }
        /**
         * 销毁
         */
        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("销毁：" + ddcSdkClient.getDDC1155Service().burn(consumer1B, consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error 销毁：");
            e.printStackTrace();
        }

        /**
         * 销毁
         */
        try {
            List<String> ddcIds = new ArrayList<>();
            ddcIds.add(erc1155Token.getTokenId().getId());
            System.out.println("批量销毁：" + ddcSdkClient.getDDC1155Service().burnBatch(consumer1A, ddcIds));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 安全转移
         */
        try {
            System.out.println("安全转移" + ddcSdkClient.getDDC1155Service().safeTransferFrom(consumer1B, consumer1A, consumer1BDdcId, new BigInteger("1"), new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 安全转移：");
            e.printStackTrace();
        }
        /**
         * getDDCList
         */
        try {
            System.out.println("getDDCList" + ddcSdkClient.getDDC1155Service().getDDCList(consumer1B));
        } catch (Exception e) {
            System.out.println("error getDDCList：");
            e.printStackTrace();
        }
        /**
         * getDDCList
         */
        try {
            System.out.println("getDDCList" + ddcSdkClient.getDDC1155Service().getLatestDDCId(consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error getDDCList：");
            e.printStackTrace();
        }
    }

    private void ddc721Service() {
//        /**
//         * 充值(只能运营方充值)
//         *
//         * @return
//         */
//        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
//        /**
//         * 创建ddc
//         */
//        try {
//            System.out.println("创建ddc: " + ddcSdkClient.getTokenTypeService().createTokenType("最强DDCBYUYEWGUYWEIUFWIE" + ddc721Addr, ddc721Addr));
//        } catch (Exception e) {
//            System.out.println("error 创建ddc：");
//            e.printStackTrace();
//        }
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        /**
         * 生成 指定合约类型 ddc标识为uuid
         */
        String platformManage1ddcId = "";
        Long indexId = 0L;
        try {
            System.out.println("生成 指定合约类型 : " + ddcSdkClient.getDDC721Service().mint(consumer1A, "http://www.uri.com", ddc721Addr));
            Erc721Token platformManage1Token = ddcSdkClient.getDDC721Service().mint(platformManage1, "http://www.uri.com", ddc721Addr);
            platformManage1ddcId = platformManage1Token.getTokenId().getId();
            indexId = platformManage1Token.getIndexId();
        } catch (Exception e) {
            System.out.println("error 生成：");
            e.printStackTrace();
        }
        /**
         * 生成 指定合约类型 ddc标识为uuid
         */
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        String consumer1ADdcId = "";
        try {
            System.out.println("生成 指定合约类型 : " + ddcSdkClient.getDDC721Service().mint(consumer1A, "http://www.uri.com", ddc721Addr));
            Erc721Token consumer2CToken = ddcSdkClient.getDDC721Service().mint(consumer1A, "http://www.uri.com", ddc721Addr);
            consumer1ADdcId = consumer2CToken.getTokenId().getId();
            indexId = consumer2CToken.getIndexId();
        } catch (Exception e) {
            System.out.println("error 生成：");
            e.printStackTrace();
        }
//        /**
//         * 批量生成
//         */
//        try {
//            Multimap<String, String> myMultimap = ArrayListMultimap.create();
//            myMultimap.put(consumer2C, "http://www.uri.com");
//            myMultimap.put(consumer2C, "http://www.uri.com");
//            myMultimap.put(consumer1A, "http://www.uri.com");
//            myMultimap.put(consumer1A, "http://www.uri.com");
//            myMultimap.put(consumer1E, "http://www.uri.com");
//            myMultimap.put(consumer1F, "http://www.uri.com");
//            System.out.println("批量生成:" + ddcSdkClient.getDDC721Service().mintBatch(ddc721Addr, myMultimap));
//        } catch (Exception e) {
//            System.out.println("error 批量生成：");
//            e.printStackTrace();
//        }
        /**
         * 生成 指定合约类型 ddc标识为uuid
         */
        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);
        String consumer1BDdcId = "";
        try {
            Erc721Token erc721Token = ddcSdkClient.getDDC721Service().safeMint(ddc721Addr, consumer1A, "http://www.uri.com", new byte[]{});
            System.out.println("生成 安全生成 : " + erc721Token);
            Erc721Token consumer2CToken = ddcSdkClient.getDDC721Service().mint(consumer1B, "http://www.uri.com", ddc721Addr);
            consumer1BDdcId = consumer2CToken.getTokenId().getId();
            indexId = consumer2CToken.getIndexId();
        } catch (Exception e) {
            System.out.println("error 安全生成：");
            e.printStackTrace();
        }
        /**
//         * 批量生成
//         */
//        try {
//            Multimap<String, String> myMultimap = ArrayListMultimap.create();
//            myMultimap.put(consumer2C, "http://www.uri.com");
//            myMultimap.put(consumer2C, "http://www.uri.com");
//            myMultimap.put(consumer1A, "http://www.uri.com");
//            myMultimap.put(consumer1A, "http://www.uri.com");
//            myMultimap.put(consumer1E, "http://www.uri.com");
//            myMultimap.put(consumer1F, "http://www.uri.com");
//            System.out.println("批量安全生成:" + ddcSdkClient.getDDC721Service().safeMintBatch(ddc721Addr, myMultimap, new byte[]{}));
//        } catch (Exception e) {
//            System.out.println("error 批量安全生成：");
//            e.printStackTrace();
//        }

        /**
         * 授权
         */
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("授权:" + ddcSdkClient.getDDC721Service().approve(consumer1B, consumer1ADdcId));
        } catch (Exception e) {
            System.out.println("error 授权：");
            e.printStackTrace();
        }
        /**
         * ddc授权查询
         */

        try {
            System.out.println("ddc授权查询:" + ddcSdkClient.getDDC721Service().getApproved(consumer1ADdcId));
        } catch (Exception e) {
            System.out.println("error ddc授权查询：");
            e.printStackTrace();
        }
        login(host, port, consumer1A, password, ddc721Addr, ddc1155Addr);
        /**
         * 批量授权
         */

//        try {
//            Multimap<String, String> myMultimap = ArrayListMultimap.create();
//            myMultimap.put(consumer1C, consumer1BDdcId);
//            System.out.println("批量授权：" + ddcSdkClient.getDDC721Service().approveBatch(ddc721Addr, myMultimap));
//        } catch (Exception e) {
//            System.out.println("error 批量授权：");
//            e.printStackTrace();
//        }
        /**
         * 账户授权
         */
        try {
            System.out.println("账户授权：" + ddcSdkClient.getDDC721Service().setApprovalForAll(consumer1D, true));
        } catch (Exception e) {
            System.out.println("error 账户授权：");
            e.printStackTrace();
        }

        /**
         * 账户授权查询
         */

        try {
            System.out.println("账户授权查询：" + ddcSdkClient.getDDC721Service().isApprovedForAll(consumer1A, consumer1B));
            System.out.println("账户授权查询：" + ddcSdkClient.getDDC721Service().isApprovedForAll(consumer1A, consumer1D));
        } catch (Exception e) {
            System.out.println("error 账户授权查询：");
            e.printStackTrace();
        }
        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);
        /**
         * 安全转移
         */
        try {
            System.out.println("安全转移：" + ddcSdkClient.getDDC721Service().safeTransferFrom(consumer1B, consumer1D, consumer1BDdcId, new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 安全转移：");
            e.printStackTrace();
        }
        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);
        /**
         * 安全转移
         */
//        try {
//            List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
//            erc721TransferDtos.add(new Erc721TransferDto(new TokenId(consumer1ADdcId), consumer1A, consumer1B));
//            erc721TransferDtos.add(new Erc721TransferDto(new TokenId(consumer1BDdcId), consumer1D, consumer1B));
//            System.out.println("批量安全转移：" + ddcSdkClient.getDDC721Service().safeBatchTransferFrom(ddc721Addr, erc721TransferDtos, new byte[]{}));
//        } catch (Exception e) {
//            System.out.println("error 批量安全转移：");
//            e.printStackTrace();
//        }

        /**
         * 转移
         */
        try {
            System.out.println("转移：" + ddcSdkClient.getDDC721Service().safeTransferFrom(consumer1B, consumer1D, consumer1BDdcId, new byte[]{}));
        } catch (Exception e) {
            System.out.println("error 转移：");
            e.printStackTrace();
        }

        /**
         * 冻结
         */
        try {
            System.out.println(ddcSdkClient.getDDC721Service().freeze(consumer1BDdcId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 转移
         */

//        try {
//            List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
//            Erc721TransferDto erc721TransferDto = new Erc721TransferDto(new TokenId(consumer1BDdcId), consumer1D, consumer2A);
//            erc721TransferDtos.add(erc721TransferDto);
//            System.out.println("批量转移：" + ddcSdkClient.getDDC721Service().batchTransferFrom(ddc721Addr, erc721TransferDtos));
//        } catch (Exception e) {
//            System.out.println("error 批量转移：");
//            e.printStackTrace();
//        }
        /**
         * 查询数量
         */
        try {
            System.out.println("查询数量：" + consumer2A + ddcSdkClient.getDDC721Service().balanceOf(consumer2A));
        } catch (Exception e) {
            System.out.println("error 查询数量：");
            e.printStackTrace();
        }

        /**
         * 解冻
         */
        try {
            System.out.println("解冻：" + ddcSdkClient.getDDC721Service().unFreeze(consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error 解冻：");
            e.printStackTrace();
        }
        /**
         * 转移
         */

//        try {
//            List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
//            Erc721TransferDto erc721TransferDto = new Erc721TransferDto(new TokenId(consumer1BDdcId), consumer2A, consumer2B);
//            erc721TransferDtos.add(erc721TransferDto);
//            System.out.println("批量转移：" + ddcSdkClient.getDDC721Service().batchTransferFrom(ddc721Addr, erc721TransferDtos));
//        } catch (Exception e) {
//            System.out.println("error 批量转移：");
//            e.printStackTrace();
//        }
        /**
         * 销毁
         */
        try {
            System.out.println("销毁：" + ddcSdkClient.getDDC721Service().burn(consumer1BDdcId));
        } catch (Exception e) {
            System.out.println("error 销毁：");
            e.printStackTrace();
        }
        /**
         * 转移
         */

//        try {
//            List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
//            Erc721TransferDto erc721TransferDto = new Erc721TransferDto(new TokenId(consumer1BDdcId), consumer2B, consumer2A);
//            erc721TransferDtos.add(erc721TransferDto);
//            System.out.println("销毁批量转移：" + ddcSdkClient.getDDC721Service().batchTransferFrom(ddc721Addr, erc721TransferDtos));
//        } catch (Exception e) {
//            System.out.println("error 销毁批量转移：");
//            e.printStackTrace();
//        }

        /**
         * 销毁
         */

//        try {
//            List<String> ddcIds = new ArrayList<>();
//            ddcIds.add(consumer1BDdcId);
//            System.out.println("批量销毁：" + ddcSdkClient.getDDC721Service().burnBatch(ddc721Addr, ddcIds));
//        } catch (Exception e) {
//            System.out.println("error 销毁：");
//            e.printStackTrace();
//        }
        /**
         * 转移
         */

//        try {
//            List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
//            Erc721TransferDto erc721TransferDto = new Erc721TransferDto(new TokenId(consumer1BDdcId), consumer2A, consumer2B);
//            erc721TransferDtos.add(erc721TransferDto);
//            System.out.println("批量转移：" + ddcSdkClient.getDDC721Service().batchTransferFrom(ddc721Addr, erc721TransferDtos));
//        } catch (Exception e) {
//            System.out.println("error 批量转移：");
//            e.printStackTrace();
//        }
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        /**
         * 查询数量
         */

//        try {
//            List<String> owners = new ArrayList<>();
//            owners.add(opr);
//            owners.add(platformManage1);
//            owners.add(platformManage2);
//            owners.add(consumer1A);
//            owners.add(consumer1B);
//            owners.add(consumer1C);
//            owners.add(consumer1D);
//            owners.add(consumer2A);
//            owners.add(consumer2B);
//            System.out.println("批量查询数量：" + ddcSdkClient.getDDC721Service().balanceOfBatch(owners));
//        } catch (Exception e) {
//            System.out.println("error 批量查询数量：");
//            e.printStackTrace();
//        }
        /**
         * 查询拥有者
         */
        try {
            System.out.println("查询拥有者:" + ddcSdkClient.getDDC721Service().ownerOf(consumer1ADdcId));
        } catch (Exception e) {
            System.out.println("error 查询拥有者：");
            e.printStackTrace();
        }

//        /**
//         * 查询拥有者
//         */
//        try {
//            List<String> ddcIds = new ArrayList<>();
//            ddcIds.add(consumer1ADdcId);
//            ddcIds.add(consumer1BDdcId);
//            ddcIds.add(platformManage1ddcId);
//            System.out.println("批量查询拥有者:" + ddcSdkClient.getDDC721Service().ownerOfBatch(ddc721Addr, ddcIds));
//        } catch (Exception e) {
//            System.out.println("error 查询拥有者：");
//            e.printStackTrace();
//        }


        /**
         * 查询名称
         */
        try {
            System.out.println("查询名称:" + ddcSdkClient.getDDC721Service().name(ddc721Addr));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 查询符号
         */
        try {
            System.out.println("查询符号:" + ddcSdkClient.getDDC721Service().symbol(ddc721Addr));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 查询链接
         */
        try {
            System.out.println("查询链接：" + ddcSdkClient.getDDC721Service().ddcURI(consumer1ADdcId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 设置链接
         */
        try {
            System.out.println("设置链接:" + ddcSdkClient.getDDC721Service().setURI(consumer1ADdcId, "http://www.testcc---1.com"));
        } catch (Exception e) {
            System.out.println("error 设置链接：");
            e.printStackTrace();
        }
        /**
         * 查询链接
         */
        try {
            System.out.println("查询链接：" + ddcSdkClient.getDDC721Service().ddcURI(consumer1ADdcId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 交易记录
         */

        try {
            System.out.println("交易记录：" + ddcSdkClient.getDDC721Service().history(consumer1ADdcId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 名称符号设置
         */
        try {
            System.out.println(ddcSdkClient.getDDC721Service().setNameAndSymbol("ddc721AddrUpdate", "ddc721Addr1"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 查询名称
         */
        try {
            System.out.println("查询名称:" + ddcSdkClient.getDDC721Service().name(ddc721Addr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * ddc列表
         */
        try {
            System.out.println("ddc列表" + ddcSdkClient.getDDC721Service().getDDCList(consumer1A));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 最新DDCID查询
         */
        try {
            System.out.println("最新DDCID查询:" + ddcSdkClient.getDDC721Service().getLatestDDCId("ddc721Addr1"));
            System.out.println("最新DDCID查询:" + ddcSdkClient.getDDC721Service().getLatestDDCId(ddc721Addr));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 事件查询
         */
        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum(1);
            pageDomain.setPageSize(10);
            System.out.println("事件查询:" + ddcSdkClient.getDDC721Service().getDDCLogInfo(pageDomain));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void chargeService() {
        /**
         * 充值(只能运营方充值)
         *
         * @return
         */
        login(host, port, platformManage2, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("平台方非法充值：" + ddcSdkClient.getChargeService().recharge(consumer1A, new BigInteger("200")));
        } catch (Exception e) {
            System.out.println("error 平台方非法充值：");
            e.printStackTrace();
        }
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("运营给终端充值：" + ddcSdkClient.getChargeService().recharge(consumer1A, new BigInteger("200")));
        } catch (Exception e) {
            System.out.println("error 运营给终端充值：");
            e.printStackTrace();
        }
        /**
         * 余额查询
         */
        try {
            System.out.println("运营给终端充值余额查询：" + ddcSdkClient.getChargeService().balanceOf(consumer1A));
        } catch (Exception e) {
            System.out.println("error 运营给终端充值余额查询：");
            e.printStackTrace();
        }
        try {
            System.out.println("运营给平台方充值：" + ddcSdkClient.getChargeService().recharge(platformManage1, new BigInteger("200")));
        } catch (Exception e) {
            System.out.println("error 运营给平台方充值：");
            e.printStackTrace();
        }
        /**
         * 余额查询
         */
        try {
            System.out.println("运营给平台方充值余额查询：" + ddcSdkClient.getChargeService().balanceOf(platformManage1));
        } catch (Exception e) {
            System.out.println("error 运营给平台方充值余额查询：");
            e.printStackTrace();
        }
        /**
         * 批量充值
         */
        try {
            Multimap<String, BigInteger> myMultimap = ArrayListMultimap.create();
            myMultimap.put(consumer1A, new BigInteger("200"));
            myMultimap.put(consumer1B, new BigInteger("200"));
            myMultimap.put(platformManage1, new BigInteger("200"));
            myMultimap.put(platformManage1, new BigInteger("200"));
            System.out.println("批量充值：" + ddcSdkClient.getChargeService().rechargeBatch(myMultimap));
        } catch (Exception e) {
            System.out.println("error 批量充值：");
            e.printStackTrace();
        }
        /**
         * 批量余额查询
         */
        try {
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
        } catch (Exception e) {
            System.out.println("error 批量余额查询：");
            e.printStackTrace();
        }
        /**
         * 终端用户非法批量余额查询
         */
        login(host, port, consumer1B, password, ddc721Addr, ddc1155Addr);
        try {
            ArrayList<String> strings = new ArrayList<>();
            strings.add(platformManage1);
            strings.add(platformManage2);
            strings.add(consumer1A);
            strings.add(consumer1B);
            strings.add(consumer1C);
            strings.add(consumer1D);
            strings.add(consumer2A);
            strings.add(consumer2C);
            System.out.println("终端用户非法批量余额查询：" + ddcSdkClient.getChargeService().balanceOfBatch(strings));
        } catch (Exception e) {
            System.out.println("error 终端用户非法批量余额查询：");
            e.printStackTrace();
        }
        /**
         * 终端用户非法运营账户充值
         */

        try {
            System.out.println("终端用户非法运营账户充值：" + ddcSdkClient.getChargeService().selfRecharge(new BigInteger("200")));
        } catch (Exception e) {
            System.out.println("error 终端用户非法运营账户充值：");
            e.printStackTrace();
        }
        /**
         * 运营账户充值
         */
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("运营账户充值：" + ddcSdkClient.getChargeService().selfRecharge(new BigInteger("200")));
        } catch (Exception e) {
            System.out.println("error 运营账户充值：");
            e.printStackTrace();
        }
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);

        /**
         * 设置服务费
         */
        try {
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Transfer.name(), new BigInteger("1")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Mint.name(), new BigInteger("2")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Burn.name(), new BigInteger("3")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Approve.name(), new BigInteger("4")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.Freeze.name(), new BigInteger("5")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.unFreeze.name(), new BigInteger("6")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.SetURI.name(), new BigInteger("7")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.MintBatch.name(), new BigInteger("8")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc721Addr, EdnCommand.Type.SetNameAndSymbol.name(), new BigInteger("9")));
        } catch (Exception e) {
            System.out.println("error 设置服务费：");
            e.printStackTrace();
        }
        /**
         * 查询计费设置
         */
        try {
            System.out.println("查询计费设置：" + ddcSdkClient.getChargeService().queryFee(ddc721Addr, EdnCommand.Type.Transfer.name()));
            System.out.println("查询计费设置：" + ddcSdkClient.getChargeService().queryFee(ddc721Addr, EdnCommand.Type.Mint.name()));
            System.out.println("查询计费设置：" + ddcSdkClient.getChargeService().queryFee(ddc1155Addr, EdnCommand.Type.SetNameAndSymbol.name()));
        } catch (Exception e) {
            System.out.println("error 查询计费设置：");
            e.printStackTrace();
        }


        /**
         * 设置服务费
         */
        try {
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.Transfer.name(), new BigInteger("1")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.Mint.name(), new BigInteger("2")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.Burn.name(), new BigInteger("3")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.Approve.name(), new BigInteger("4")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.Freeze.name(), new BigInteger("5")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.unFreeze.name(), new BigInteger("6")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.SetURI.name(), new BigInteger("7")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.MintBatch.name(), new BigInteger("8")));
            System.out.println("设置服务费：" + ddcSdkClient.getChargeService().setFee(ddc1155Addr, EdnCommand.Type.SetNameAndSymbol.name(), new BigInteger("9")));
        } catch (Exception e) {
            System.out.println("error 设置服务费：");
            e.printStackTrace();
        }
        /**
         * 删除服务费
         */
        try {
            System.out.println("删除服务费：" + ddcSdkClient.getChargeService().delFee(ddc721Addr, EdnCommand.Type.Mint.name()));
        } catch (Exception e) {
            System.out.println("error 删除服务费：");
            e.printStackTrace();
        }
        try {
            System.out.println("查询计费设置：" + ddcSdkClient.getChargeService().queryFee(ddc721Addr, EdnCommand.Type.Mint.name()));
        } catch (Exception e) {
            System.out.println("error 查询计费设置：");
            e.printStackTrace();
        }

        /**
         * 删除合约
         */
        try {
            System.out.println("删除合约" + ddcSdkClient.getChargeService().delDDC(ddc721Addr));
        } catch (Exception e) {
            System.out.println("error 删除合约：");
            e.printStackTrace();
        }
        try {
            System.out.println("查询计费设置：" + ddcSdkClient.getChargeService().queryFee(ddc721Addr, EdnCommand.Type.Mint.name()));
        } catch (Exception e) {
            System.out.println("error 查询计费设置：");
            e.printStackTrace();
        }
        /**
         * 查询充值日志
         */

        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum(1);
            pageDomain.setPageSize(10);
            System.out.println("查询充值日志：" + ddcSdkClient.getChargeService().getChainAccountRechargeLog(pageDomain));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum(2);
            pageDomain.setPageSize(10);
            System.out.println("业务费消费记录：" + ddcSdkClient.getChargeService().getChainAccountConsumeLog(pageDomain));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum(3);
            pageDomain.setPageSize(10);
            System.out.println("服务费配置记录：" + ddcSdkClient.getChargeService().getChainConfigLog(pageDomain));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 获取日志
         *
         * @return
         */
        BlockEventService blockEventService = ddcSdkClient.getBlockEventService();
        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum(1);
            pageDomain.setPageSize(10);
            System.out.println("获取日志：" + blockEventService.getLogInfoList(pageDomain));
        } catch (Exception e) {
            System.out.println("error 获取日志：");
            e.printStackTrace();
        }
    }

    private void authorityService() {
        login(host, port, bsn, bsnPassword, ddc721Addr, ddc1155Addr);
        /**
         * 创建运营账户
         */
        try {
            System.out.println("创建运营账户：" + ddcSdkClient.getAuthorityService().addOperator(opr, oprName, oprDID + opr));
        } catch (Exception e) {
            System.out.println("error 创建运营账户错误：");
            e.printStackTrace();
        }
        /**
         * 运营添加平台账户
         */
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println(ddcSdkClient.getAuthorityService().addAccountByOperator(platformManage1, platformManage1, oprDID + platformManage1, null));
        } catch (Exception e) {
            System.out.println("error 运营添加平台账户：");
            e.printStackTrace();
        }
        try {
            System.out.println("运营添加平台账户：" + ddcSdkClient.getAuthorityService().addAccountByOperator(platformManage2, platformManage2, oprDID + platformManage2, null));
        } catch (Exception e) {
            System.out.println("error 运营添加平台账户：");
            e.printStackTrace();
        }
        /**
         * 运营添加终端账户
         */
        try {
//            System.out.println("运营添加终端账户：" + ddcSdkClient.getAuthorityService().addAccountByOperator(consumer1A, consumer1A, oprDID + consumer1A, oprDID + platformManage1));
            System.out.println("运营添加终端账户：" + ddcSdkClient.getAuthorityService().addAccountByOperator(consumer2C, consumer2C, oprDID + consumer2C, oprDID + platformManage1));
        } catch (Exception e) {
            System.out.println("error 运营添加终端账户：");
            e.printStackTrace();
        }
        /**
         *  平台方添加账户
         */
        login(host, port, platformManage1, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("平台方添加账户：" + ddcSdkClient.getAuthorityService().addAccountByPlatform(consumer1B, consumer1B, oprDID + consumer1B));
        } catch (Exception e) {
            System.out.println("error 平台方添加账户：");
            e.printStackTrace();
        }
        /**
         * 平台方批量添加账户
         */
        try {
            ArrayList<AccountInfoBean> accountInfoBeans = new ArrayList<>();
//            AccountInfoBean oPR0 = new AccountInfoBean();
//            oPR0.setAccount(consumer1C);
//            oPR0.setAccountName(consumer1C);
//            oPR0.setAccountDID(oprDID + consumer1B);
//            accountInfoBeans.add(oPR0);
//            AccountInfoBean oPR1 = new AccountInfoBean();
//            oPR1.setAccount(consumer1D);
//            oPR1.setAccountName(consumer1D);
//            oPR1.setAccountDID(oprDID + consumer1D);
//            accountInfoBeans.add(oPR1);
//            AccountInfoBean oPRE = new AccountInfoBean();
//            oPRE.setAccount(consumer1E);
//            oPRE.setAccountName(consumer1E);
//            oPRE.setAccountDID(oprDID + consumer1E);
//            accountInfoBeans.add(oPRE);
//            AccountInfoBean oPRF = new AccountInfoBean();
//            oPRF.setAccount(consumer1F);
//            oPRF.setAccountName(consumer1F);
//            oPRF.setAccountDID(oprDID + consumer1F);
//            accountInfoBeans.add(oPRF);
//            AccountInfoBean consumer2AAccountInfo = new AccountInfoBean();
//            consumer2AAccountInfo.setAccount(consumer2A);
//            consumer2AAccountInfo.setAccountName(consumer2A);
//            consumer2AAccountInfo.setAccountDID(oprDID + consumer2A);
//            accountInfoBeans.add(consumer2AAccountInfo);
            AccountInfoBean consumer2BAccountInfo = new AccountInfoBean();
            consumer2BAccountInfo.setAccount(consumer2B);
            consumer2BAccountInfo.setAccountName(consumer2B);
            consumer2BAccountInfo.setAccountDID(oprDID + consumer2B);
            accountInfoBeans.add(consumer2BAccountInfo);
            System.out.println("平台方添加账户：" + ddcSdkClient.getAuthorityService().addBatchAccountByPlatform(accountInfoBeans));
        } catch (Exception e) {
            System.out.println("error 平台方添加账户：");
            e.printStackTrace();
        }

        /**
         * 平台方添加链账户开关设置
         */
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("平台方添加链账户开关设置：" + ddcSdkClient.getAuthorityService().setSwitcherStateOfPlatform(platformManage3, true));
        } catch (Exception e) {
            System.out.println("error 平台方添加链账户开关设置：");
            e.printStackTrace();
        }
        /**
         * 平台方添加链账户开关查询
         */
        try {
            System.out.println("平台方添加链账户开关设置：" + ddcSdkClient.getAuthorityService().switcherStateOfPlatform(platformManage3));
        } catch (Exception e) {
            System.out.println("error 平台方添加链账户开关设置：");
            e.printStackTrace();
        }

        /**
         * 查询账户
         */
        try {
            System.out.println("查询账户：" + ddcSdkClient.getAuthorityService().getAccount(consumer1E));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 查询账户：");
            e.printStackTrace();
        }
        /**
         * 查询删除账户
         */
        try {
            System.out.println("查询删除账户：" + ddcSdkClient.getAuthorityService().getAccount(consumer1F));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 查询删除账户：");
            e.printStackTrace();
        }
        /**
         * 更新账户
         */
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("更新账户：" + ddcSdkClient.getAuthorityService().updateAccState(platformManage2, FreezableStatus.Frozen, true));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 更新账户：");
            e.printStackTrace();
        }
        try {
            System.out.println("查询账户：" + ddcSdkClient.getAuthorityService().getAccount(platformManage2));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 查询账户：");
            e.printStackTrace();
        }
        /**
         *  冻结后平台方添加账户
         */
        login(host, port, platformManage2, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("冻结后平台方添加账户：" + ddcSdkClient.getAuthorityService().addAccountByPlatform(consumer1B, consumer1B, oprDID + consumer1B));
        } catch (Exception e) {
            System.out.println("error 冻结后平台方添加账户：");
            e.printStackTrace();
        }

        /**
         *  客户端非法解冻平台方账户
         */
        login(host, port, consumer1E, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("客户端非法解冻平台方账户：" + ddcSdkClient.getAuthorityService().updateAccState(platformManage2, FreezableStatus.Frozen, true));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 客户端非法解冻平台方账户：");
            e.printStackTrace();
        }
        try {
            System.out.println("查询账户：" + ddcSdkClient.getAuthorityService().getAccount(platformManage2));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 查询账户：");
            e.printStackTrace();
        }
        /**
         *  平台方非法解冻平台方账户
         *  （ 已冻结账户）
         */
        login(host, port, platformManage2, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("已冻结账户更新账户：" + ddcSdkClient.getAuthorityService().updateAccState(platformManage2, FreezableStatus.Frozen, true));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 已冻结账户更新账户：");
            e.printStackTrace();
        }
        try {
            System.out.println("查询账户：" + ddcSdkClient.getAuthorityService().getAccount(platformManage2));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 查询账户：");
            e.printStackTrace();
        }
        /**
         *  运营解冻账户
         */
        login(host, port, opr, password, ddc721Addr, ddc1155Addr);
        try {
            System.out.println("运营解冻账户：" + ddcSdkClient.getAuthorityService().updateAccState(platformManage2, FreezableStatus.Frozen, true));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 运营解冻账户：");
            e.printStackTrace();
        }
        try {
            System.out.println("查询账户：" + ddcSdkClient.getAuthorityService().getAccount(platformManage2));
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error 查询账户：");
            e.printStackTrace();
        }
        /**
         * 获取账户日志
         *
         * @return
         */
        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum(1);
            pageDomain.setPageSize(50);
            System.out.println("获取账户日志：" + ddcSdkClient.getAuthorityService().getAccountList(pageDomain));
        } catch (Exception e) {
            System.out.println("error 获取账户日志：");
            e.printStackTrace();
        }
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
    }


}
