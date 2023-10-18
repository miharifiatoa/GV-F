package com.sales_management_javafx.controller.product_variation;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.composent.PriceVariationGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.PriceVariationEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.PriceVariationService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ProductVariationEditController implements Initializable {
    @FXML
    private TextField productPriceTextfield;
    @FXML
    private TextField productSizeTextfield;
    @FXML
    private TextField productQualityTextfield;
    @FXML
    private TextField productBrandTextfield;
    @FXML
    private TextField productReferenceTextfield;
    @FXML
    private TextField productColorTextfield;
    @FXML
    private Button confirmEditProductButton;
    @FXML
    private Button cancelEditButton;
    @FXML
    private ScrollPane productFormScrollpane;
    @FXML
    private VBox productVariationEditVBox;
    @FXML
    ProductGridPane productGridPane =new ProductGridPane();
    private final ProductService productService;
    private final ArticleService articleService;
    private final PriceVariationService priceVariationService;

    public ProductVariationEditController() {
        this.priceVariationService = new PriceVariationService();
        this.articleService = new ArticleService();
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productVariationEditVBox.setVisible(false);
        this.formValidation();
        this.onExitEdit();
        NumberTextField.requireDouble(this.productPriceTextfield);
    }
    private void formValidation(){}

    public void initialize(PriceVariationEntity priceVariation){
        Double price = priceVariation.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        this.productPriceTextfield.setText(decimalFormat.format(price));
        this.productSizeTextfield.setText(priceVariation.getSize());
        this.productBrandTextfield.setText(priceVariation.getBrand());
        this.productReferenceTextfield.setText(priceVariation.getReference());
        this.productQualityTextfield.setText(priceVariation.getQuality());
        this.productColorTextfield.setText(priceVariation.getColor());
        this.onConfirmEditProductVariation(priceVariation.getId());
    }
    public void onConfirmEditProductVariation(Long id){
        confirmEditProductButton.setOnAction(actionEvent -> {
            PriceVariationEntity priceVariation = this.priceVariationService.getById(id);
            priceVariation.setSize(this.productSizeTextfield.getText());
            priceVariation.setPrice(Double.valueOf(this.productPriceTextfield.getText()));
            priceVariation.setBrand(this.productBrandTextfield.getText());
            priceVariation.setQuality(this.productQualityTextfield.getText());
            priceVariation.setReference(this.productReferenceTextfield.getText());
            priceVariation.setColor(this.productColorTextfield.getText());
            PriceVariationEntity priceVariationResponse = this.priceVariationService.update(priceVariation);
            if (priceVariationResponse!=null){
                ProductEntity product = (ProductEntity) FileIO.readFrom("product.dat");
                GridPane gridPane = new PriceVariationGridPane().getGridPane(new ProductService().getById(product.getId()).getPriceVariations(),3,false);
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productVariationEditVBox.getParent().getParent().getParent().getParent().getParent();
                System.out.println(productBoxLayoutScrollpane);
                productBoxLayoutScrollpane.setContent(gridPane);

            }
        });
    }
    public void onExitEdit(){
        cancelEditButton.setOnAction(actionEvent -> {
            VBox productVariationVBox = (VBox) productVariationEditVBox.getParent().lookup("#productVariationVBox");
            productVariationEditVBox.setVisible(false);
            productVariationVBox.setVisible(true);
        });
    }
}
