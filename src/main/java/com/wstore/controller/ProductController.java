package com.wstore.controller;


import com.wstore.service.*;
import com.wstore.util.WstoreFastDFS;
import com.wstore.common.utils.*;
import com.wstore.pojo.admin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

/**
 * @ClassName ProductController
 * @Author Koi
 * @Date 2018/7/28 16:27
 * @Version 1.0
 */
@Controller
public class ProductController {

    protected static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    SearchService searchService;

    @Autowired
    SkuService skuService;

    @Autowired
    WstoreFastDFS wstoreFastDFS;

    /**
     * 分页查询商品列表
     *
     * @param map
     * @param pn
     * @return
     */
    @GetMapping("/product/page/{pn}")
    public String getAllProduct(Map<String, Object> map, @PathVariable(value = "pn") Integer pn) {
        Map<String, Object> products = productService.getProducts(pn);
        map.put("products", products);
        return "product";
    }

    @GetMapping("/product/brands")
    @ResponseBody
    public List<Brand> getBrandsPart() {
        return brandService.getBrandsPart();
    }

    /**
     * 查询分类数据
     *
     * @return JSON
     */
    @GetMapping(value = "/product/category", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getCategoryList() {
        List<Category> categoryNameList = categoryService.getCategoryNameList();
        return WstoreJsonUtil.toJson(categoryNameList);
    }

    /**
     * 查询分类属性集合
     *
     * @param id
     * @return json格式的属性集合
     */
    @GetMapping(value = "/product/category/properties/{id}", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getCategoryProperties(@PathVariable("id") Integer id) {
        Category categoryProperties = categoryService.getCategoryProperties(id);
        return WstoreJsonUtil.toJson(categoryProperties);
    }

    /**
     * 图片集属性集以字符串方式传入，以“,”隔开
     * 在后台进行解析
     * 将对4个数据库操作
     * 1、商品表 - 添加商品信息
     * 2、商品属性表 - 添加商品属性集
     * 3、商品图片表 - 添加商品图片集
     * 4、商品扩展表 - 添加商品富文本和参数
     * 新增商品
     *
     * @param extendLength 富文本真实长度
     * @param product      封装对象，包含图片集、属性集
     * @return
     */
    @PostMapping("/product")
    @ResponseBody
    public WstoreResultMsg addProduct(Product product, Integer extendLength) {
        if (product.getName().length() < 2 || product.getName().length() > 40) {
            return WstoreResultMsg.fail().add("error", "请输2~40个字以内的商品名称");
        }

        if (product.getBrandId() == null) {
            return WstoreResultMsg.fail().add("error", "请选择品牌");
        }

        if (product.getCategory() == null) {
            return WstoreResultMsg.fail().add("error", "请选择商品分类");
        }

        if (product.getProperties().length() < 1) {
            return WstoreResultMsg.fail().add("error", "请至少为该商品选择个以上的属性集");
        }

        if (product.getImages().length() < 1) {
            return WstoreResultMsg.fail().add("error", "请至少上传一张图片");
        }

        if (product.getExtend().length() < 10) {
            if (extendLength < 10 || extendLength > 499) {
                return WstoreResultMsg.fail().add("error", "请至输入10个字以上,500字以下的商品详情");
            }
        }
        if (extendLength > 499) {
            return WstoreResultMsg.fail().add("error", "请至输入10个字以上,500字以下的商品详情");
        }
        productService.addProduct(product);
        logger.info("新增商品[" + product.getName() + "]，商品名[" + product.getName() + "]");
        return WstoreResultMsg.success().add("product", product);
    }

    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    @PostMapping("/product/update")
    @ResponseBody
    public WstoreResultMsg updateProduct(Product product, Integer extendLength, String waitDelImg) {

        if (product.getName().length() < 2 || product.getName().length() > 40) {
            return WstoreResultMsg.fail().add("error", "请输2~40个字以内的商品名称");
        }

        if (product.getBrandId() == null) {
            return WstoreResultMsg.fail().add("error", "请选择品牌");
        }

        if (product.getExtend().length() < 20) {
            if (extendLength < 10 || extendLength > 499) {
                return WstoreResultMsg.fail().add("error", "请至输入10个字以上,500字以下的商品详情");
            }
        }

        //解析待删除图片列表
        if (waitDelImg != null) {
            String[] delImgList = waitDelImg.replace("http://", "").split(",");
            for (String img : delImgList) {
                //---http://192.168.144.122/group1/M00/00/02/wKiQlFtlHhiAZ0AAAABQ_xzNAek594.jpg
                if (img.length() > 1) {
                    String imgStorage = img.substring(img.indexOf("/", 1) + 1);
                    //---group1/M00/00/02/wKiQlFtlHhiAZ0AAAABQ_xzNAek594.jpg
                    System.out.println(imgStorage);
                    wstoreFastDFS.deleteFile(imgStorage);
                }
            }
        }
        productService.updateProduct(product);
        logger.info("编辑商品[" + product.getName() + "]，编码[" + product.getCode() + "]");
        return WstoreResultMsg.success();
    }

    /**
     * 商品上架/下架
     *
     * @param status  状态码 0-下架 ， 1-上架
     * @param product
     * @return
     */
    @PostMapping("/product/status/{status}")
    @ResponseBody
    public WstoreResultMsg onSaleProduct(@PathVariable("status") Integer status, Product product) {
        if (status == 0) {
            productService.offSaleProduct(product);
            //从elasticsearch中删除商品信息
            searchService.removeProduct(product);
            logger.info("下架商品：" + product.getCode());
            return WstoreResultMsg.success();
        }
        if (status == 1) {
            //是否满足上架条件
            if (!productService.onSaleProduct(product)) {
                return WstoreResultMsg.fail();
            }
            //同步到elasticsearch
            searchService.addProductToES(product);
            logger.info("上架商品：" + product.getCode());
            return WstoreResultMsg.success();
        }
        return WstoreResultMsg.fail();
    }

    /**
     * 添加商品，跳转到添加商品页面
     *
     * @param map
     * @return
     */
    @GetMapping("/product/new")
    public String editSku(Map<String, Object> map) {
        List<Brand> brands = brandService.getBrandsPart();
        map.put("brands", brands);
        return "new-product";
    }

    /**
     * 编辑商品,查询后台跳转到页面
     *
     * @return
     */
    @GetMapping("/product/edit/{id}")
    public String editProduct(Map<String, Object> map, @PathVariable("id") Long pid) {
        Product product = productService.getProductJoint(pid);
        if (product == null) {
            return "404";
        }
        List<Brand> brands = brandService.getBrandsPart();
        map.put("product", product);
        map.put("brands", brands);
        return "edit-product";
    }

    @GetMapping("/product/json/{id}")
    @ResponseBody
    public Map<String, Object> getProductJSON(@PathVariable("id") Long pid) {
        Map<String, Object> map = new HashMap<>();
        Product product = productService.getProductJoint(pid);
        List<Brand> brands = brandService.getBrandsPart();
        map.put("product", product);
        map.put("brands", brands);
        return map;
    }

    @DeleteMapping("/product/{code}")
    @ResponseBody
    public WstoreResultMsg deleteProduct(@PathVariable("code") String code) {
        //删除商品相关数据
        //返回图片路径list，并从图片服务器中删除
        List<String> imgStorage = productService.deleteProductByCode(code);
        for (String storage : imgStorage) {
            wstoreFastDFS.deleteFile(storage);
        }
        return WstoreResultMsg.success();
    }

    /**
     * 批量上下架
     * 并同步到Elasticsearch
     *
     * @param status
     */
    @PostMapping("/products/status/{status}")
    @ResponseBody
    public WstoreResultMsg updateAllStatus(@PathVariable("status") Integer status) {
        if (status == 1) {
            long resultCount = searchService.batchOnSale();
            return WstoreResultMsg.success().add("count", resultCount);
        } else if (status == 0) {
            Integer count = productService.batchOffSale();
            return WstoreResultMsg.success().add("count", count);
        }
        return WstoreResultMsg.fail();
    }

    /**
     * 测试
     * 批量添加商品
     *
     * @throws IOException
     */
    @GetMapping("/test/batch/OIUHSB8937HG75")
    @ResponseBody
    public String generate(@RequestParam Integer n) throws IOException, InterruptedException {

        String[] names = {"农药同人", "小乔", "同人", "洋装",
                "烘焙坊", "lolita JSK", "旅人赞歌", "中腰JSK", "前开襟",
                "日常洋装", "lolita", "吊带", "连衣裙", "原创设计", "时光画卷",
                "复古洋装", "原创蝴蝶结", "公主连衣裙", "人鱼姬", "梦幻", "夏日新款",
                "高腰", "JSK", "天野", "喜孝", "合作款", "SP合集", "高级定制", "国牌原创",
                "日系软妹", "宫廷", "洋装", "萌少女", "op", "公主", "蕾丝", "精美", "可爱", "萝莉"};
        String[] props1 = {"S","M","L","XL"};
        int[] price = {59900,99900,149900,259900};
        String[] images = {"http://192.168.144.131/group1/M00/00/04/wKiQlFttyGGAAZvkAAf6XQIMR30201.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGGAOyAWAAbXANwon4Y068.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGGAAMWdAAChWXuWLq8034.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGGANcGZAARyqhvNnGQ751.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGGAXNc1AASn_3V8ikg384.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGGAF3FEAAoAI9cseCs551.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGGAFk17AAI9eDNTDB0286.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGGAY8GDAAUd5ccG4WE597.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKAP_oTAAIcZnbUPf0057.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKAd-SAAAZ3Ul4fpag908.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKAKbjfAAH3SXBq8Y8406.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKASpggAAKsRHOHvq0692.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKAXHwTAAGsll9pxJ8860.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKACHQdAAXToryNN8Y875.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKAA9nYAAsZ9O_VUjM692.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKAcLPnAAEU3ZhghVY588.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKAEQ1NAAGwWs7sqXs504.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKADSd7AAQjBxE_vgM021.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKABvBcAAU-Iq27Byo065.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGKALKY9AARtaidQyn8884.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAdn3iAA5ns7Lav00987.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAIJb_AAG_Qzw5OKk252.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAS3TEAAP_9yxezUg159.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOARyiSAAk7IyWHSkA173.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAD_b0AALEpD3lNF8213.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOATKu8AANcmCd7fCg624.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAKOmrAArctsgcvks420.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAdIuxAAFwZE1VDjw200.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAUMrrAAOiVsL6o7k953.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAeXPBAAIwYCVTv54758.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGOAEvF6AAEwj6tlzS4784.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGSASssaAAGoZdXA-uI667.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGSAI9hWAAG3YXdkI-o156.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGSAD-ZJAAUHc0hM0bc866.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGSACKetAAN2huEhGDM964.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGSABLnhAAaS0B8xzXM045.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGSAO83zAAGXon0G8rQ032.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGSAdLcgAAeUc7pYwOI410.png",
                "http://192.168.144.131/group1/M00/00/04/wKiQlFttyGSAGSGFAAaCHRsrfsE224.png",
        };
        int[] brands = {21,22,23,24,26,27,28,29,30,30,32,33,34,35,36,37,38,39,40};
        int[] categories = {12,18,19};
        String[] properties = {"样式","大小"};
        /*批量上传图片，并且在控制台打印*/
        /*String[] imageList = new File("D:\\batch").list();
        String result = "";
        for (String file : imageList) {
            File tempFile = new File("D:\\batch\\" + file);
            //读取图片流
            BufferedImage read = null;
            try {
                read = ImageIO.read(tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int width = read.getWidth();
            int height = read.getHeight();
            //获取后缀
            String fileExtensionName = WstoreStringUtils.getFileExtensionName(file);
            //图片处理为正方形
            int side = width > height ? width : height;
            result += "\"http://192.168.144.131/" + wstoreFastDFS.upload(WstoreImageUtils.fillImageSpace(read, side, side), "png") + "\",";
        }
        System.out.println(result);*/
        Product product = new Product();

        //插入属性集开始索引位置 tbl_product_property
        int propFlagId = n;
        for (int i=0;i<100;i++){
            List<Integer>  temp = new ArrayList<>();
            String productImgs = "";
            String mainPic = "";
            for (int j=0;j<5;j++){
                int i1 = new Random().nextInt(names.length);
                if (j<3){
                    int temRandom = new Random().nextInt(images.length);
                    if (j == 0){
                        mainPic = images[temRandom];
                    }
                    productImgs += images[temRandom]+",";
                }
                temp.add(i1);
            }

            String code = WStoreDateUtils.generateCode();
            product.setCode(code);
            product.setCategory(categories[new Random().nextInt(categories.length)]);
            product.setBrandId(brands[new Random().nextInt(brands.length)]);
            product.setName(names[temp.get(0)]+names[temp.get(1)]+names[temp.get(2)]);
            product.setSubName(names[temp.get(3)]+names[temp.get(4)]);
            product.setMainPic(mainPic);
            product.setImages(productImgs);
            product.setProperties("样式,大小");
            ProductExtend productExtend = new ProductExtend();
            //构建内容
            String content = "<p style=\"text-align: center;\"><img src=\"" + images[new Random().nextInt(images.length)]
                    + "\" style=\"font-size: 1.6rem; max-width: 100%;\"><br/><img src=\""
                    +images[new Random().nextInt(images.length)]+"\" style=\"font-size: 1.6rem; max-width: 100%;\"></p>";
            //构建参数
            String param = "<tbody> <tr class=\"table_group\"> <td colspan=\"2\">基本参数</td> </tr> <tr> <td>品名</td> <td>"
                    +names[temp.get(2)]+"</td> </tr> </tbody>";
            product.setExtend(content);
            product.setParam(param);
            productService.addProduct(product);
            //添加3个sku
            for (int s=0;s<3;s++){
                //构建sku
                Sku sku = new Sku();
                sku.setProductCode(code);
                SkuProperty skuProperty1 = new SkuProperty();
                SkuProperty skuProperty2 = new SkuProperty();
                List<SkuProperty> skuProperties = new ArrayList<>();
                skuProperty1.setProductPropertyId(propFlagId);
                skuProperty1.setPropertyName(properties[0]);
                skuProperty1.setPropertyValue(names[new Random().nextInt(names.length)]);
                skuProperty2.setProductPropertyId(propFlagId+1);
                skuProperty2.setPropertyName(properties[1]);
                skuProperty2.setPropertyValue(props1[new Random().nextInt(props1.length)]);
                skuProperties.add(skuProperty1);
                skuProperties.add(skuProperty2);
                sku.setSkuProperties(skuProperties);
                sku.setSkuPic(images[new Random().nextInt(images.length)]);
                sku.setAvailableStock(1000);
                sku.setFrozenStock(10);
                sku.setProductCode(code);
                //生成价格
                int p = price[new Random().nextInt(price.length)];
                sku.setMarketPrice(p);
                sku.setPrice(p-10000);
                sku.setCostPrice(p-20000);
                skuService.addSku(sku);
            }
            propFlagId = propFlagId+2;
        }
        product.setName("");
        return "success";
    }
}
