package com.example.citizenmanagement.controllers.maincontrollers.NhankhauController;

import com.example.citizenmanagement.models.MainMenuOptions;
import com.example.citizenmanagement.models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ThemMoiController implements Initializable {
    @FXML
    public Button thoat_themmoi_button;
    public ChoiceBox<String> my_choise_box;
    public Button confirm_btn;
    public TextField ton_giao_text;
    public TextField quoc_tich_text;
    public TextField nghe_text;
    public TextField nam_sinh_text;
    public TextField nguyen_quan_text;
    public TextField thuong_tru_text;
    public TextField cccd_text;
    public TextField dan_toc_text;
    public TextField ghi_chu_text;
    public TextField ho_ten_text;
    public TextField so_ho_chieu_text;
    public TextField noi_sinh_text;


    private String[] Gioitinh = {"Nam", "Nữ"};
    private void onThoatTamVangBtn() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.NHAN_KHAU);
    }

    public void clearText() {
        my_choise_box.setValue(null);
        ho_ten_text.clear(); cccd_text.clear();
        noi_sinh_text.clear();
        nguyen_quan_text.clear();
        dan_toc_text.clear();
        ton_giao_text.clear(); quoc_tich_text.clear();
        so_ho_chieu_text.clear();
        thuong_tru_text.clear();
        nghe_text.clear();
        ghi_chu_text.clear();
        nam_sinh_text.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thoat_themmoi_button.setOnAction(actionEvent -> {
        onThoatTamVangBtn();
        clearText();
        });

        my_choise_box.getItems().addAll(Gioitinh);
        my_choise_box.setOnAction(this::getGioiTinh);

        confirm_btn.setOnAction(actionEvent -> {
            int bit;
            if(my_choise_box.getValue() == "Nam") {
                bit = 1;
            }
            else bit = 0;
           int thanhcong = Model.getInstance().getDatabaseConnection().addNhanKhau(
                   ho_ten_text.getText(),cccd_text.getText(),
                   nam_sinh_text.getText(), bit,
                   noi_sinh_text.getText(), nguyen_quan_text.getText(),
                   dan_toc_text.getText(), ton_giao_text.getText(),
                   quoc_tich_text.getText(), so_ho_chieu_text.getText(),
                   thuong_tru_text.getText(), nghe_text.getText(),
                    ghi_chu_text.getText());
        if(thanhcong == 0) {
            System.out.println("Đã thêm không thành công, hãy kiểm tra lại");
        }
        else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Thành Công !!!");
            alert.setContentText("Đã thêm thành công, vui lòng ấn Thoát để xem danh sách");
            alert.showAndWait();
            clearText();

        }
        });

    }

    public String getGioiTinh(ActionEvent event) {
        String myGioiTinh = my_choise_box.getValue();

        //my_lable_gioitinh.setText(myGioiTinh);
        return myGioiTinh;
    }



}
