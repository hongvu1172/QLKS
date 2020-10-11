/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.KhuyenMaiDAO;
import qlks.DTO.KhuyenMai;
import qlks.DTO.KhuyenMaiDTO;

/**
 *
 * @author admin
 */
public class KhuyenMaiBUS {
    private KhuyenMaiDAO kmDAO;
    
    public KhuyenMaiBUS(){
        kmDAO = new KhuyenMaiDAO();
    }
    
    public ArrayList<KhuyenMai> docDanhSachKhuyenMai(){
        return kmDAO.docDanhSachKhuyenMai();
    }  
    
    public boolean themKhuyenMai(KhuyenMaiDTO kmDTO, KhuyenMai km){
        for(int i=0;i<kmDTO.docDanhSachKhuyenMai().size();i++){
            KhuyenMai kmt = kmDTO.docDanhSachKhuyenMai().get(i);
            if(kmt.getMaChuongTrinhKhuyenMai().compareTo(km.getMaChuongTrinhKhuyenMai())==0){
                if(kmt.isXoa()){
                    if(JOptionPane.showConfirmDialog(null, "Phát hiện mã chương trình khuyến mãi cũ bạn muốn ghi đè lên chương trình khuyến mãi này")
                            ==JOptionPane.YES_OPTION){
                        kmDAO.suaKhuyenMai(km);
                        kmDTO.suaKhuyenMai(km, i);
                        return true;
                    }
                    return false;
                }
                JOptionPane.showMessageDialog(null, "Mã chương trình khuyến mãi đã tồn tại");
                return false;
            }
        }
        kmDAO.themKhuyenMai(km);
        kmDTO.themKhuyenMai(km);
        return true;
    }
    
    public boolean suaKhuyenMai(KhuyenMaiDTO kmDTO,KhuyenMai km){
        int row = -1;
        for(int i=0;i<kmDTO.docDanhSachKhuyenMai().size();i++){
            if(km.getMaChuongTrinhKhuyenMai().compareTo(kmDTO.docDanhSachKhuyenMai().get(i).getMaChuongTrinhKhuyenMai())==0){
                row = i;
                break;
            }
        }
        if(row == -1 || kmDTO.docDanhSachKhuyenMai().get(row).isXoa()){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã khuyến mãi");
            return false;
        }
        if(km.getTenChuongTrinhKhuyenMai().compareTo(kmDTO.docDanhSachKhuyenMai().get(row).getTenChuongTrinhKhuyenMai())==0 &&
                km.getThoiGianBatDau().compareTo(kmDTO.docDanhSachKhuyenMai().get(row).getThoiGianBatDau())==0 &&
                km.getTHoiGianKetThuc().compareTo(kmDTO.docDanhSachKhuyenMai().get(row).getTHoiGianKetThuc())==0 &&
                km.getGiamGia()==kmDTO.docDanhSachKhuyenMai().get(row).getGiamGia()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa");
            if(chon==JOptionPane.YES_OPTION){
                kmDAO.suaKhuyenMai(km);
                kmDTO.suaKhuyenMai(km, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaKhuyenMai(KhuyenMaiDTO kmDTO, String mkm){
        int row = -1;
        for(int i=0;i<kmDTO.docDanhSachKhuyenMai().size();i++){
            if(mkm.compareTo(kmDTO.docDanhSachKhuyenMai().get(i).getMaChuongTrinhKhuyenMai())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã khuyến mãi này");
            return false;
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa khuyến mãi này");
        if(chon==JOptionPane.YES_OPTION){
            kmDAO.xoaKhuyenMai(mkm);
            kmDTO.xoaKhuyenMai(mkm, row);
            return true;
        }else{
            return false;
        }
    }
}
