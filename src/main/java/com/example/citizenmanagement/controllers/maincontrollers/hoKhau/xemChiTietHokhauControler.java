package com.example.citizenmanagement.controllers.maincontrollers.hoKhau;

import com.example.citizenmanagement.models.MainMenuOptions;
import com.example.citizenmanagement.models.Model;
import com.example.citizenmanagement.models.MainHoKhauCell;
import com.example.citizenmanagement.models.thanh_vien_cua_ho_cell;
import com.example.citizenmanagement.views.MainHoKhauCellFactory;
import com.example.citizenmanagement.views.thanh_vien_cua_ho_cell_factory;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class xemChiTietHokhauControler implements Initializable {
    public ListView<thanh_vien_cua_ho_cell> listView_thanhvien;
    public TextField ma_ho_khau;
    public TextField ma_chu_ho;
    public TextField ten_chu_ho;
    public TextField dia_chi;
    public TextField ngay_tao;
    public TextField ghi_chu;
    public Button chuyen_di_but;
    public Button cancel_but;
    public Button xac_nhan_but;
    public Button thaydoi_but;
    public Button quay_lai_but;

    private MainHoKhauCell tam;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //******************************************************
        tam=Model.getHoKhauDuocChon();
        cap_nhat();
        ma_ho_khau.setText(String.valueOf(tam.getId().get()));
        ma_chu_ho.setText(String.valueOf(tam.getOwner().get()));
        dia_chi.setText(String.valueOf(tam.getAddress().get()));
        ngay_tao.setText(String.valueOf(tam.getDate_tao().get()));
        ghi_chu.setText(String.valueOf(tam.getGhi_chu().get()));
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().lay_nhan_khau(String.valueOf(tam.getOwner().get()));
        try {
            if(resultSet.isBeforeFirst()){
                resultSet.next();
                ten_chu_ho.setText(resultSet.getString(2));
            }
        }catch (Exception e){
            System.out.println(" loi o cap nhap ten nhan khau");
            e.printStackTrace();
        }
        //********************************************************
        chuyen_di_but.setOnAction(event -> {
            thaydoi_but.setVisible(false);
            quay_lai_but.setVisible(false);

            xac_nhan_but.setVisible(true);
            cancel_but.setVisible(true);
        });

        thaydoi_but.setOnAction(event -> {
            Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.THAY_DOI_HO_KHAU);
        });

        xac_nhan_but.setOnAction(event -> {

            quay_lai_but.setVisible(true);
            thaydoi_but.setVisible(true);

            cancel_but.setVisible(false);
            xac_nhan_but.setVisible(false);
            int ketqua=0;
            ketqua=Model.getInstance().getDatabaseConnection().xoaHoKhau(String.valueOf(tam.getId().get()));
            if(ketqua==1)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Xoa thanh cong!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Xoa khong thanh cong!\n Ban phai xoa cac thanh vien cua ho truoc");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });

        cancel_but.setOnAction(event -> {
            quay_lai_but.setVisible(true);
            thaydoi_but.setVisible(true);

            cancel_but.setVisible(false);
            xac_nhan_but.setVisible(false);

        });

        quay_lai_but.setOnAction(event->{
            Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.HO_KHAU);
        });

    }
    public void cap_nhat(){
        try {
            ResultSet resultSet = Model.getInstance().getDatabaseConnection().lay_cac_thanh_vien(String.valueOf(tam.getId().get()));
            listView_thanhvien.getItems().clear();
            try {
                if(resultSet.isBeforeFirst()){
                    while (resultSet.next()){
                        ResultSet resultSet1 = Model.getInstance().getDatabaseConnection().lay_nhan_khau(resultSet.getString(1));
                        if(resultSet1.isBeforeFirst()){
                            resultSet1.next();
                        String cccd = resultSet1.getString(1);
                        String hoTen = resultSet1.getString(2);
                        String quanHe = resultSet.getString(3);
                        String ngaySinh = resultSet1.getString(4);
                        String gioiTinh = resultSet1.getString(3);
                        listView_thanhvien.getItems().add(new thanh_vien_cua_ho_cell(cccd, hoTen, quanHe,ngaySinh,gioiTinh));
                    }
                    }
                }
            } catch (Exception e) {
                System.out.println("loi o xem chi tiet lay_nhan_khau");
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println("loi o xem chi tiet lay_cac_thanh_vien");
            e.printStackTrace();
        }

        listView_thanhvien.setCellFactory(param-> new thanh_vien_cua_ho_cell_factory());
    }
}
