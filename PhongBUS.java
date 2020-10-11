/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.PhongDAO;
import qlks.DTO.Phong;
import qlks.DTO.PhongDTO;

/**
 *
 * @author admin
 */
public class PhongBUS {
    private PhongDAO pDAO;
    
    public PhongBUS(){
        pDAO = new PhongDAO();
    }
    
    public ArrayList<Phong> docDanhSachPhong(){
        return pDAO.docDanhSachPhong();
    }

    public boolean suaPhong(PhongDTO pDTO,Phong p){
        int row = -1;

        for(int i=0;i<pDTO.docDanhSachPhong().size();i++){
            if(p.getMaPhong().compareTo(pDTO.docDanhSachPhong().get(i).getMaPhong())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã phòng");
            return false;
        }

        if(p.getMaLoaiPhong().compareTo(pDTO.docDanhSachPhong().get(row).getMaPhong())==0 &&
                p.getTenPhong().compareTo(pDTO.docDanhSachPhong().get(row).getTenPhong())==0 &&
                p.getMaLoaiPhong().compareTo(pDTO.docDanhSachPhong().get(row).getMaLoaiPhong())==0&&
                p.getTinhTrang().compareTo(pDTO.docDanhSachPhong().get(row).getTinhTrang())==0){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        pDAO.suaPhong(p);
        pDTO.suaPhong(p, row);
        return true;
    }
    
    public boolean suaPhongTuHoaDon(Phong p){
        pDAO.suaPhong(p);
        return true;
    }
}
