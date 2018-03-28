package com.netease.mall.web.controller;

import com.netease.mall.dao.TransactionDao;
import com.netease.mall.domain.HostHolder;
import com.netease.mall.domain.Product;
import com.netease.mall.domain.ProductTransactionVo;
import com.netease.mall.domain.User;
import com.netease.mall.service.FileUploadService;
import com.netease.mall.service.ProductService;
import com.netease.mall.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,0:57
 * @update :
 */
@Controller
@PropertySource("classpath:properties/dev.properties")
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    HostHolder hostHolder;

    //获取配置信息
    @Value("${img.local.path}")
    private String imgPath;

    /**
     * 数据回显
     */
    private final ResourceLoader resourceLoader;

    /**
     * 数据回显
     */
    @Autowired
    public ProductController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 删除product
     * @param id
     * @return
     */
    @RequestMapping("/api/delete")
    @ResponseBody
    Map<String, Object> deleteProduct(@RequestParam(value = "id",required = false) String id){
        Map<String, Object> result = null;
        if (StringUtils.isBlank(id)){
            result = new HashMap<>();
            result.put("code",400);
            result.put("message","请输入ID");
            return result;
        }
        int productId;
        try {
            productId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            result = new HashMap<>();
            result.put("code",400);
            result.put("message","请输入整数ID");
            return result;
        }
        Product product = productService.getProductById(productId);
        if (product == null){
            result = new HashMap<>();
            result.put("code",400);
            result.put("message","请输入正确的ID");
            return result;
        }
        try {
            productService.deleteProductById(productId);
        } catch (Exception e) {
            result = new HashMap<>();
            result.put("code",400);
            result.put("message","删除失败");
            return result;
        }
        result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success!");
        return result;
    }

    /**
     * 详情页
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping("/show")
    public String show(@RequestParam("id") String productId, Model model){
        User user = hostHolder.getUser();
        if(StringUtils.isNotBlank(productId)){
            if(transactionService.isBought(user.getId(),Integer.valueOf(productId))){
                //购买过
                ProductTransactionVo productTransactionVo = productService.getProductTranscationWithBuyByUserIdAndProductId(user.getId(),Integer.valueOf(productId));
                model.addAttribute("product",productTransactionVo);
            }else {
                //未购买过
                ProductTransactionVo productTransactionVo= productService.getProductTranscationById(Integer.valueOf(productId));
                model.addAttribute("product",productTransactionVo);
            }
        }
        return "show";
    }

    /**
     * 提交发布
     * @param title
     * @param summary
     * @param image
     * @param content
     * @param price
     * @param model
     * @return
     */
    @RequestMapping("/publicSubmit")
    public String publicSubmit(@RequestParam(value = "title") String title,
                               @RequestParam(value = "summary") String summary,
                               @RequestParam(value = "image") String image,
                               @RequestParam(value = "content") String content,
                               @RequestParam(value = "price") double price,
                               Model model){
        Product product = new Product();
        product.setContent(content);
        if (StringUtils.isEmpty(image)){
            product.setImage("/image/default.jpg");
        }else {
            product.setImage(image);
        }
        product.setPrice(price);
        product.setSellerId(hostHolder.getUser().getId());
        product.setSummary(summary);
        product.setTitle(title);
        try {
            productService.saveProduct(product);
            model.addAttribute("product",product);
        } catch (Exception e) {
            LOGGER.error("save failed");
        }
        return "publicSubmit";
    }

    /**
     * 回显的秘籍
     * @param filename
     * @return
     */
    @RequestMapping("/img/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(imgPath, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/api/upload")
    @ResponseBody
    public Map<String, Object> imageUpload(@RequestParam("file") MultipartFile file) throws Exception{

        Map<String, Object> map = new HashMap<String, Object>();
        String picPath = null;
        if (!file.isEmpty()){
            picPath = fileUploadService.save(file);
        }else {
            picPath = "/image/default.jpg";
        }
        map.put("result", picPath);
        return map;
    }

    /**
     * 更改回填
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam("id") int productId,Model model){
        Product product = productService.getProductById(productId);
        if(product != null){
            model.addAttribute("product",product);
        }
        return "edit";
    }

    /**
     * 更改提交
     * @param productId
     * @param title
     * @param summary
     * @param image
     * @param content
     * @param price
     * @param model
     * @return
     */
    @RequestMapping("/editSubmit")
    public String editSubmit(@RequestParam(value = "id") int productId,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "summary") String summary,
                             @RequestParam(value = "image") String image,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "price") double price,
                             Model model){
        Product product = productService.getProductById(productId);
        Product productNew = new Product();
        productNew.setId(productId);
        productNew.setTitle(title);
        productNew.setSummary(summary);
        if (StringUtils.isEmpty(image)){
            productNew.setImage("/image/default.jpg");
        }else {
            productNew.setImage(image);
        }
        productNew.setSellerId(hostHolder.getUser().getId());
        productNew.setContent(content);
        productNew.setPrice(price);
        try {
            productService.updateProduct(productNew);
            Map<String, Object> productView = new HashMap<String, Object>();
            productView.put("id", productNew.getId());
            model.addAttribute("product",productView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "editSubmit";
    }
}
