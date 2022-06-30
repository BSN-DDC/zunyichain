# ddc-sdk-demo

# zuyichain java client library



### Requirements

-   **Java 1.8 or later**



### Maven 


```xml
  <dependency>
            <groupId>com.slabs.corda</groupId>
            <artifactId>ddcsdk</artifactId>
            <version>1.1.3</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/src/main/resources/lib/ddcsdk-1.1.3.jar
            </systemPath>
  </dependency>
```

### 加载方式一：yaml 配置文件
 sdk-config.yml
 
```
rpc:
  ## 超时时间
  conTimeout: 60
  ## 处理时间
  readTimeout: 60
  ## 用户名
  username: "platformManager1_O=Slabs,L=Beijing,C=CN"
  ## 密码
  password: test
  ## 连接网关
  host: localhost
  ## 端口号
  port: 12017 # relyer
  ## ssl文件路径
  sslPath:
  ## ssl密码
  sslPwd:
  ## ssl密钥
  privateKey: ""
contract:
  ## 721合约地址
  ddc721Addr: "ddc721Addr"
  ## 1155合约地址
  ddc1155Addr: "ddc1155Addr"

```
###  spring注入
```
@Bean
    public DDCSdkClient ddcSdkClient() {
        return DDCSdkClient.fromFile();
    }
```
 DDCSdkClient.fromFile();
 读取 sdk-config.yml 文件的内容

### 加载方式二：
```
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
```

### 加载方式三：
```
                    DDCSdkClient    ddcSdkClient = DDCSdkClient.login(60L, 60L, host, rpcPort, username, password, ddc721Address, ddc1155Address);

```
###  账户合约

AuthorityService
```
DDCSdkClient ddcSdkClient = DDCSdkClient.fromFile();
  AuthorityService  authorityService = ddcSdkClient.getAuthorityService();
 /**
     * 创建运营账户
     */
    public void addOperator() {
        try {
            System.out.println(authorityService.addOperator("OPR2_O=Slabs,L=Beijing,C=CN",
                    "OPR",
                    "DID:DDC_OPR_DID:drccfefe"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运营添加账户
     */
    public void addAccountByOperator() {
        try {
            System.out.println(authorityService.addAccountByOperator("OPR5_O=Slabs,L=Beijing,C=CN",
                    "ByOperator",
                    "DID:DDC_ByOperator_DID:111", "DID:DDC_OPR_DID:drccfefe"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 平台方添加账户
     */
    public void addAccountByPlatform() {
        try {
            System.out.println(authorityService.addAccountByPlatform("OPR4_O=Slabs,L=Beijing,C=CN",
                    "ByOperator",
                    "DID:DDC_ByOperator_DID:111"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 平台方批量添加账户
     */
    public void addBatchAccountByPlatform() {
        try {
            ArrayList<AccountInfoBean> accountInfoBeans = new ArrayList<>();
            AccountInfoBean oPR0 = new AccountInfoBean();
            oPR0.setAccount("OPR0_O=Slabs,L=Beijing,C=CN");
            oPR0.setAccountName("01121");
            oPR0.setAccountDID("DID:DDC_OPR_DID:drccfefe");
            accountInfoBeans.add(oPR0);
            AccountInfoBean oPR1 = new AccountInfoBean();
            oPR1.setAccount("OPR1_O=Slabs,L=Beijing,C=CN");
            oPR1.setAccountName("11121");
            oPR1.setAccountDID("DID:DDC_OPR_DID:drccfef1e");
            accountInfoBeans.add(oPR1);
            AccountInfoBean oPR2 = new AccountInfoBean();
            oPR2.setAccount("OPR2_O=Slabs,L=Beijing,C=CN");
            oPR2.setAccountName("21121");
            oPR2.setAccountDID("DID:DDC_OPR_DID:drcc2fefe");
            accountInfoBeans.add(oPR2);
            System.out.println(authorityService.addBatchAccountByPlatform(accountInfoBeans));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 平台方添加链账户开关设置
     */
    public void setSwitcherStateOfPlatform() {
        try {
            System.out.println(authorityService.setSwitcherStateOfPlatform("OPR0_O=Slabs,L=Beijing,C=CN", true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 平台方添加链账户开关查询
     */
    public void switcherStateOfPlatform() {
        try {
            System.out.println(authorityService.switcherStateOfPlatform("OPR0_O=Slabs,L=Beijing,C=CN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询账户
     */
    public void getAccount() {
        try {
            System.out.println(authorityService.getAccount("platformManager1_O=Slabs,L=Beijing,C=CN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新账户
     */
    public void updateAccState() {
        try {
            System.out.println(authorityService.updateAccState("platformManager1_O=Slabs,L=Beijing,C=CN", DDCAccountStatus.Frozen, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /**
     * 获取账户日志
     * @return
     */
    @Test
    public void  getAccountList() {
        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum (0);
            pageDomain.setPageSize (10);
            System.out.println(authorityService.getAccountList(pageDomain));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取账户日志
     * @return
     */
    @Test
    public void  getAccountInfo() {
        try {
            PageDomain pageDomain = new PageDomain();
            pageDomain.setPageNum (0);
            pageDomain.setPageSize (10);
            System.out.println(authorityService.getAccountInfo(pageDomain));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###  费用合约

ChargeService
```
 DDCSdkClient ddcSdkClient = DDCSdkClient.fromFile();
    ChargeService    chargeService = ddcSdkClient.getChargeService();
  /**
     * 充值
     */
    public void recharge() {
        try {
            System.out.println(chargeService.recharge("consumerA_O=Node,L=London,C=GB", new BigInteger("200")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量充值
     */
    public void rechargeBatch() {
        try {
            Multimap<String, BigInteger> myMultimap = ArrayListMultimap.create();
            myMultimap.put("consumerA_O=Node,L=London,C=GB", new BigInteger("200"));
            System.out.println(chargeService.rechargeBatch(myMultimap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 余额查询
     */
    public void balanceOf() {
        try {
            System.out.println(chargeService.balanceOf("consumerA_O=Node,L=London,C=GB"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量余额查询
     */
    public void balanceOfBatch() {
        try {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("consumerA_O=Node,L=London,C=GB");
            System.out.println(chargeService.balanceOfBatch(strings));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询计费设置
     */
    public void queryFee() {
        try {
            System.out.println(chargeService.queryFee("ddc721Addr", TokenCommand.Type.Transfer.name()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运营账户充值
     */
    public void selfRecharge() {
        try {
            System.out.println(chargeService.selfRecharge(new BigInteger("200")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置服务费
     */
    public void setFee() {
        try {
            System.out.println(chargeService.setFee("ddc721Addr", TokenCommand.Type.Transfer.name(), new BigInteger("1000")));
            System.out.println(chargeService.setFee("ddc721Addr", TokenCommand.Type.Mint.name(), new BigInteger("1000")));
            System.out.println(chargeService.setFee("ddc721Addr", TokenCommand.Type.Burn.name(), new BigInteger("1000")));
            System.out.println(chargeService.setFee("ddc721Addr", TokenCommand.Type.Approve.name(), new BigInteger("1000")));
            System.out.println(chargeService.setFee("ddc721Addr", TokenCommand.Type.SetURI.name(), new BigInteger("1000")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除服务费
     */
    public void delFee() {
        try {
            System.out.println(chargeService.delFee("ddc721Addr", TokenCommand.Type.Mint.name()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除DDC
     */
    public void delDDC() {
        try {
            System.out.println(chargeService.delDDC("ddc721Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###  721合约

DDC721Service
```
    DDCSdkClient ddcSdkClient = DDCSdkClient.fromFile();
    DDC721Service    ddc721Service = ddcSdkClient.getDDC721Service();
 /**
     * 生成 指定合约类型 ddc标识为uuid
     */
    public void mint() {
        try {
            System.out.println(ddc721Service.mint("customer1_O=Slabs,L=Beijing,C=CN", "http://www.uri.com", "ddc721Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量生成
     */
    public void mintBatch() {
        try {
            Multimap<String, String> myMultimap = ArrayListMultimap.create();
            myMultimap.put("customer1_O=Slabs,L=Beijing,C=CN", "http://www.uri.com");
            myMultimap.put("customerB_O=Slabs,L=Beijing,C=CN", "http://www.uri.com");
            System.out.println(ddc721Service.mintBatch("ddc721Addr", myMultimap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全生成 指定合约类型 ddc标识为uuid
     */
    public void safeMint() {
        try {
            System.out.println(ddc721Service.safeMint("ddc721Addr", "ByOperator_O=Slabs,L=Beijing,C=CN", "http://www.uri.com", new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全生成 指定合约类型 ddc标识为uuid
     */
    public void safeMintBatch() {
        try {
            Multimap<String, String> myMultimap = ArrayListMultimap.create();
            myMultimap.put("ByOperator_O=Slabs,L=Beijing,C=CN", "http://www.uri.com");
            myMultimap.put("ByOperator1_O=Slabs,L=Beijing,C=CN", "http://www.uri.com");
            myMultimap.put("ByOperator2_O=Slabs,L=Beijing,C=CN", "http://www.uri.com");
            myMultimap.put("ByOperator3_O=Slabs,L=Beijing,C=CN", "http://www.uri.com");
            myMultimap.put("ByOperator4_O=Slabs,L=Beijing,C=CN", "http://www.uri.com");
            System.out.println(ddc721Service.safeMintBatch("ddc721Addr", myMultimap, new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 授权
     */
    public void approve() {
        try {
            System.out.println(ddc721Service.approve("ByOperator_O=Slabs,L=Beijing,C=CN", "5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 授权
     */
    public void approveBatch() {
        try {
            Multimap<String, String> myMultimap = ArrayListMultimap.create();
            myMultimap.put("ByOperator_O=Slabs,L=Beijing,C=CN", "5de4af2a-83da-4241-b276-5da3c0fc3bf5");
            System.out.println(ddc721Service.approveBatch("ddc721Addr", myMultimap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ddc授权查询
     */
    public void getApproved() {
        try {
            System.out.println(ddc721Service.getApproved("5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账户授权
     */
    public void setApprovalForAll() {
        try {
            System.out.println(ddc721Service.setApprovalForAll("ByOperator_O=Slabs,L=Beijing,C=CN", true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账户授权查询
     */
    public void isApprovedForAll() {
        try {
            System.out.println(ddc721Service.isApprovedForAll("OPR_O=Slabs,L=Beijing,C=CN", "ByOperator1_O=Slabs,L=Beijing,C=CN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全转移
     */
    public void safeTransferFrom() {
        try {
            System.out.println(ddc721Service.safeTransferFrom("ByOperator1_O=Slabs,L=Beijing,C=CN", "OPR_O=Slabs,L=Beijing,C=CN", "5de4af2a-83da-4241-b276-5da3c0fc3bf5", new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全转移
     */
    public void safeBatchTransferFrom() {
        try {
            List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
            Erc721TransferDto erc721TransferDto = new Erc721TransferDto(new TokenId("5de4af2a-83da-4241-b276-5da3c0fc3bf5"), "ByOperator1_O=Slabs,L=Beijing,C=CN", "OPR_O=Slabs,L=Beijing,C=CN");
            erc721TransferDtos.add(erc721TransferDto);
            System.out.println(ddc721Service.safeBatchTransferFrom("ddc721Addr", erc721TransferDtos, new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转移
     */
    public void transferFrom() {
        try {
            System.out.println(ddc721Service.transferFrom("ByOperator1_O=Slabs,L=Beijing,C=CN", "OPR_O=Slabs,L=Beijing,C=CN", "5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转移
     */
    public void batchTransferFrom() {
        try {
            List<Erc721TransferDto> erc721TransferDtos = new ArrayList<>();
            Erc721TransferDto erc721TransferDto = new Erc721TransferDto(new TokenId("5de4af2a-83da-4241-b276-5da3c0fc3bf5"), "ByOperator1_O=Slabs,L=Beijing,C=CN", "OPR_O=Slabs,L=Beijing,C=CN");
            erc721TransferDtos.add(erc721TransferDto);
            System.out.println(ddc721Service.batchTransferFrom("ddc721Addr", erc721TransferDtos));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 冻结
     */
    public void freeze() {
        try {
            System.out.println(ddc721Service.freeze("5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解冻
     */
    public void unFreeze() {
        try {
            System.out.println(ddc721Service.unFreeze("5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁
     */
    public void burn() {
        try {
            System.out.println(ddc721Service.burn("5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁
     */
    public void burnBatch() {
        try {
            List<String> ddcIds = new ArrayList<>();
            ddcIds.add("5de4af2a-83da-4241-b276-5da3c0fc3bf5");
            System.out.println(ddc721Service.burnBatch("ddc721Addr", ddcIds));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数量
     */
    public void balanceOf() {
        try {
            System.out.println(ddc721Service.balanceOf("ByOperator_O=Slabs,L=Beijing,C=CN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数量
     */
    public void balanceOfBatch() {
        try {
            List<String> owners = new ArrayList<>();
            owners.add("ByOperator_O=Slabs,L=Beijing,C=CN");
            System.out.println(ddc721Service.balanceOfBatch(owners));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询拥有者
     */
    public void ownerOf() {
        try {
            System.out.println(ddc721Service.ownerOf("5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询拥有者
     */
    public void ownerOfBatch() {
        try {
            List<String> ddcIds = new ArrayList<>();
            ddcIds.add("5de4af2a-83da-4241-b276-5da3c0fc3bf5");
            System.out.println(ddc721Service.ownerOfBatch("ddc721Addr", ddcIds));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询名称
     */
    public void name() {
        try {
            System.out.println(ddc721Service.name("ddc721Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询符号
     */
    public void symbol() {
        try {
            System.out.println(ddc721Service.symbol("ddc721Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询链接
     */
    public void ddcURI() {
        try {
            System.out.println(ddc721Service.ddcURI("ddc721Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置链接
     */
    public void setURI() {
        try {
            System.out.println(ddc721Service.setURI("5de4af2a-83da-4241-b276-5da3c0fc3bf5", "http://www.cc.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 交易记录
     */
    public void history() {
        try {
            System.out.println(ddc721Service.history("5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 名称符号设置
     */
    public void setNameAndSymbol() {
        try {
            System.out.println(ddc721Service.setNameAndSymbol("ddc721Addr", "ddc721AddrUpdate", "ddc721Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ddc列表
     */
    public void getDDCList() {
        try {
            System.out.println(ddc721Service.getDDCList("customer1_O=Slabs,L=Beijing,C=CN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 最新DDCID查询
     */
    public void getLatestDDCId() {
        try {
            System.out.println(ddc721Service.getLatestDDCId("ddc721Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###  DDC1155合约

DDC721Service
```
        DDCSdkClient ddcSdkClient = DDCSdkClient.fromFile();
       DDC1155Service ddc1155Service = ddcSdkClient.getDDC1155Service();

    /**
     * 安全生成 指定合约类型 ddc标识为uuid
     */
    public void safeMint() {
        try {
            System.out.println(ddc1155Service.safeMint("customer1_O=Slabs,L=Beijing,C=CN", new BigInteger("20000"), "http://www.uri.com", new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全生成 指定合约类型 ddc标识为uuid
     */
    public void safeMintBatch() {
        try {
            Multimap<BigInteger, String> myMultimap = ArrayListMultimap.create();
            myMultimap.put(new BigInteger("20000"), "http://www.uri.com");
            System.out.println(ddc1155Service.safeMintBatch("customer1_O=Slabs,L=Beijing,C=CN", myMultimap, new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 账户授权
     */
    public void setApprovalForAll() {
        try {
            System.out.println(ddc1155Service.setApprovalForAll("customer1_O=Slabs,L=Beijing,C=CN", true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账户授权查询
     */
    public void isApprovedForAll() {
        try {
            System.out.println(ddc1155Service.isApprovedForAll("OPR_O=Slabs,L=Beijing,C=CN", "customerB_O=Slabs,L=Beijing,C=CN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全转移
     */
    public void safeTransferFrom() {
        try {
            System.out.println(ddc1155Service.safeTransferFrom("customerB_O=Slabs,L=Beijing,C=CN", "OPR_O=Slabs,L=Beijing,C=CN", "5de4af2a-83da-4241-b276-5da3c0fc3bf5", new BigInteger("200"), new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全转移
     */
    public void safeBatchTransferFrom() {
        try {
            Multimap<String, BigInteger> myMultimap = ArrayListMultimap.create();
            myMultimap.put("5de4af2a-83da-4241-b276-5da3c0fc3bf5", new BigInteger("20000"));
            System.out.println(ddc1155Service.safeBatchTransferFrom("customerB_O=Slabs,L=Beijing,C=CN", "OPR_O=Slabs,L=Beijing,C=CN", myMultimap, new byte[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 冻结
     */
    public void freeze() {
        try {
            System.out.println(ddc1155Service.freeze("5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解冻
     */
    public void unFreeze() {
        try {
            System.out.println(ddc1155Service.unFreeze("5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁
     */
    public void burn() {
        try {
            System.out.println(ddc1155Service.burn("customerB_O=Slabs,L=Beijing,C=CN", "5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁
     */
    public void burnBatch() {
        try {
            List<String> ddcIds = new ArrayList<>();
            ddcIds.add("5de4af2a-83da-4241-b276-5da3c0fc3bf5");
            System.out.println(ddc1155Service.burnBatch("customerB_O=Slabs,L=Beijing,C=CN", ddcIds));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数量
     */
    public void balanceOf() {
        try {
            System.out.println(ddc1155Service.balanceOf("customerB_O=Slabs,L=Beijing,C=CN", "5de4af2a-83da-4241-b276-5da3c0fc3bf5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数量
     */
    public void balanceOfBatch() {
        try {
            Multimap<String, String> myMultimap = ArrayListMultimap.create();
            myMultimap.put("customerB_O=Slabs,L=Beijing,C=CN", "5de4af2a-83da-4241-b276-5da3c0fc3bf5");
            System.out.println(ddc1155Service.balanceOfBatch(myMultimap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询链接
     */
    public void ddcURI() {
        try {
            System.out.println(ddc1155Service.ddcURI("customerB_O=Slabs,L=Beijing,C=CN", "ddc1155Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ddc列表
     */
    public void getDDCList() {
        try {
            System.out.println(ddc1155Service.getDDCList("customer1_O=Slabs,L=Beijing,C=CN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 最新DDCID查询
     */
    public void getLatestDDCId() {
        try {
            System.out.println(ddc1155Service.getLatestDDCId("ddc1155Addr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
