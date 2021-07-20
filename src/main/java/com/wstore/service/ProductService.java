package com.wstore.service;

import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wstore.common.utils.WStoreDateUtils;
import com.wstore.common.utils.WstoreStringUtils;
import com.wstore.mapper.*;
import com.wstore.pojo.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName ProductServiceImpl
 * @Author Koi
 * @Date 2018/7/28 14:32
 * @Version 1.0
 */
@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductExtendMapper productExtendMapper;

    @Autowired
    ProductImageMapper productImageMapper;

    @Autowired
    ProductPropertyMapper productPropertyMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuPropertyMapper skuPropertyMapper;

    @Value("${page.size}")
    private Integer PAGE_SIZE;

    @Value("${page.navigate}")
    private Integer PAGE_NAVIGATE;

    /**
     * 获取所有商品
     * 分页查询，返回查询集合
     * @return category集合
     */

    public Map<String, Object> getProducts(Integer pageNum) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,PAGE_SIZE);
        List<Product> products = productMapper.selectByExample(null);
        PageInfo<Product> pageInfo = new PageInfo<Product>(products,PAGE_NAVIGATE);
        map.put("pageInfo",pageInfo);
        return map;
    }

    /**
     * 新增商品
     *
     * @param product
     */
    @Transactional

    public void addProduct(Product product) {
        //解析图片集
        String[] images = product.getImages().split(",");
        //解析属性集
        String[] properties = product.getProperties().split(",");
        //解析表格参数
        String param = WstoreStringUtils.getRealHTML(product.getParam());
        //生成商品编码
        //String productCode = WStoreDateUtils.generateCode();
        String productCode = product.getCode();
                //图片集添加
        ProductImage productImage = new ProductImage();
        productImage.setProductCode(productCode);
        productImage.setImages(images);
        addProductImages(productImage);

        //属性集添加
        ProductProperty productProperty = new ProductProperty();
        productProperty.setProductCode(productCode);
        productProperty.setProperties(properties);
        addProductProperties(productProperty);

        //扩展信息添加
        ProductExtend productExtend = new ProductExtend();
        productExtend.setProductCode(productCode);
        productExtend.setParameter(param);
        productExtend.setContent(product.getExtend());
        addProductExtend(productExtend);

        //插入商品信息
        product.setStatus(0);
        product.setMaxPrice(0);
        product.setMinPrice(0);
        product.setMainPic(images[0]);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        product.setOnsaleTime(new Date());
        product.setOffsaleTime(new Date());
        product.setCode(productCode);
        productMapper.insertSelective(product);
    }

    /**
     * 修改产品信息
     *
     * @param product
     */

    @Transactional
    public void updateProduct(Product product) {
        if (product.getImages().length()>0){
            //图片不为空即插入图片
            ProductImage productImage = new ProductImage();
            productImage.setCreateTime(new Date());
            productImage.setUpdateTime(new Date());
            productImage.setProductCode(product.getCode());

            String[] imgList = product.getImages().split(",");
            for (String img : imgList){
                productImage.setImageUrl(img);
                productImageMapper.insertSelective(productImage);
            }
        }

        //按主键删除图片
        if (product.getProperties().length()>0){
            String imgs = product.getProperties().substring(0, product.getProperties().length() - 1);
            String[] imgIdList = imgs.split(",");
            for (String id : imgIdList){
                productImageMapper.deleteByPrimaryKey(Integer.parseInt(id));
            }
        }

        //更新扩展信息
        ProductExtend productExtend = new ProductExtend();
        productExtend.setContent(product.getExtend());
        productExtend.setParameter(product.getParam());
        ProductExtendExample productExtendExample = new ProductExtendExample();
        productExtendExample.createCriteria().andProductCodeEqualTo(product.getCode());
        productExtendMapper.updateByExampleSelective(productExtend, productExtendExample);

        product.setUpdateTime(new Date());
        //下架商品
        product.setStatus(0);
        productMapper.updateByPrimaryKeySelective(product);
    }

    /**
     * 根据商品id上架商品
     *
     * @param product
     */
    @Transactional

    public boolean onSaleProduct(Product product) {
        //判断是否满足上架条件
        if (productMapper.findSkuCount(product.getCode())<1){
            return false;
        }
        product.setStatus(1);
        product.setOnsaleTime(new Date());
        productMapper.updateByPrimaryKeySelective(product);
        return true;
    }

    /**
     * 根据商品id下架商品
     *
     * @param product
     */

    public void offSaleProduct(Product product) {
        product.setStatus(0);
        product.setOffsaleTime(new Date());
        productMapper.updateByPrimaryKeySelective(product);
    }

    /**
     * 商品拆分信息，扩展属性
     *
     * @param productExtend
     */

    public void addProductExtend(ProductExtend productExtend) {

        productExtendMapper.insert(productExtend);
    }

    /**
     * 插入商品图片集
     *
     * @param productImage
     */

    public void addProductImages(ProductImage productImage) {
        productImage.setCreateTime(new Date());
        productImage.setUpdateTime(new Date());
        String[] imgs = productImage.getImages();
        for (int i=0;i<imgs.length;i++){
            productImage.setImageUrl(imgs[i]);
            productImageMapper.insert(productImage);
        }
    }

    /**
     * 插入商品属性集
     *
     * @param productProperty
     */

    public void addProductProperties(ProductProperty productProperty) {
        productProperty.setCreateTime(new Date());
        productProperty.setUpdateTime(new Date());
        String[] properties = productProperty.getProperties();
        for (int i=0;i<properties.length;i++){
            productProperty.setPropertyName(properties[i]);
            productPropertyMapper.insert(productProperty);
        }
    }

    /**
     * 联合商品扩展表
     * 按主键查询所有相关信息
     *
     * @param
     * @return
     */

    public Product getProductJoint(Long pid) {
        return productMapper.WithAllByPrimaryKey(pid);
    }

    /**
     * 根据商品编码查询此商品的属性集
     *
     * @return
     */
    public Product getProperties(String code){
        return productMapper.selectPropertiesByCode(code);
    }

    /**
     * 删除商品
     * 关联表删除！
     * 关联表删除！
     *
     * @param code
     */
    @Transactional

    public List<String> deleteProductByCode(String code) {
        //取出图片链接
        ProductImageExample example2 = new ProductImageExample();
        example2.createCriteria().andProductCodeEqualTo(code);
        List<ProductImage> productImages = productImageMapper.selectByExample(example2);
        SkuExample example3 = new SkuExample();
        example3.createCriteria().andProductCodeEqualTo(code);
        List<Sku> skus = skuMapper.selectByExample(example3);

        List<String> imgList = new ArrayList<>();
        for (Sku sku:skus){
            String imgUrl = sku.getSkuPic().replace("http://","");
            imgList.add(imgUrl.substring(imgUrl.indexOf("/", 1)+1));
        }
        for (ProductImage productImage : productImages){
            String imgUrl = productImage.getImageUrl().replace("http://","");
            imgList.add(imgUrl.substring(imgUrl.indexOf("/", 1)+1));
        }
        ProductExample example1 = new ProductExample();
        example1.createCriteria().andCodeEqualTo(code);
        productMapper.deleteWithAllByCode(code);
        return imgList;
    }

    /**
     * 批量下架商品
     * @return
     */

    public Integer batchOffSale() {
        Product product = new Product();
        product.setStatus(0);
        ProductExample example = new ProductExample();
        example.createCriteria().andStatusEqualTo(1);
        return productMapper.updateByExampleSelective(product,example);
    }
}
