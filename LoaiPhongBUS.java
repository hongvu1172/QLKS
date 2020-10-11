/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.LoaiPhongDAO;
import qlks.DTO.LoaiPhong;
import qlks.DTO.LoaiPhongDTO;
import qlks.DTO.Phong;

/**
 *
 * @author admin
 */
public class LoaiPhongBUS {
    private LoaiPhongDAO lpDAO;
    
    public LoaiPhongBUS(){
        lpDAO = new LoaiPhongDAO();
    }
    
    public ArrayList<LoaiPhong> docDanhSachLoaiPhong(){
        return lpDAO.docDanhSachLoaiPhong();
    }
    
    //Loai phong DTO va loai phong them
    public boolean themLoaiPhong(LoaiPhongDTO lpDTO, LoaiPhong lp){
        //Kiem tra ma loai phong da co hay chua
        for(LoaiPhong lpt: lpDTO.docDanhSachLoaiPhong()){
            if(lpt.getMaLoaiPhong().compareTo(lp.getMaLoaiPhong())==0){
                JOptionPane.showMessageDialog(null, "Mã loại phòng đã tồn tại.");
                return false;
            }
        }
        //them loai phong moi vao SQL va nhan vien DTO
        lpDAO.themLoaiPhong(lp);
        lpDTO.themLoaiPhong(lp);
        return true;
    }
    
    //row = vi tri can sua trong lpDTO
    public boolean suaLoaiPhong(LoaiPhongDTO lpDTO,LoaiPhong lp){
        int row = -1;
        //Kiem tra ma loai phong co ton tai trong lpDTO
        for(int i=0;i<lpDTO.docDanhSachLoaiPhong().size();i++){
            if(lp.getMaLoaiPhong().compareTo(lpDTO.docDanhSachLoaiPhong().get(i).getMaLoaiPhong())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã loại phòng");
            return false;
        }
        //Kiem tra loai phong co thay doi khong
        if(lp.getMaLoaiPhong().compareTo(lpDTO.docDanhSachLoaiPhong().get(row).getMaLoaiPhong())==0 &&
                lp.getTenLoaiPhong().compareTo(lpDTO.docDanhSachLoaiPhong().get(row).getTenLoaiPhong())==0 &&
                lp.getSoNguoi()==lpDTO.docDanhSachLoaiPhong().get(row).getSoNguoi()&&
                lp.getGiaTien()==lpDTO.docDanhSachLoaiPhong().get(row).getGiaTien()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa loại phòng này");
            if(chon==JOptionPane.YES_OPTION){
                lpDAO.suaLoaiPhong(lp, lpDTO.docDanhSachLoaiPhong().get(row).getMaLoaiPhong());
                lpDTO.suaLoaiPhong(lp, row);
                return true;
            }else{
                return false;
        }
    }

    public boolean xoaLoaiPhong(LoaiPhongDTO lpDTO,LoaiPhong lp){
        int row = -1;
        //Kiem tra ma loai phong co ton tai trong lpDTO
        for(int i=0;i<lpDTO.docDanhSachLoaiPhong().size();i++){
            if(lp.getMaLoaiPhong().compareTo(lpDTO.docDanhSachLoaiPhong().get(i).getMaLoaiPhong())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã loại phòng");
            return false;
        }
        
        for(Phong p: new PhongBUS().docDanhSachPhong()){
            if(p.getMaLoaiPhong().compareTo(lp.getMaLoaiPhong())==0){
                JOptionPane.showMessageDialog(null, "Vui lòng sữa mã loại phòng ở danh mục phòng trước khi xóa loại phòng này");
                return false;
            }
        }
        
        if(JOptionPane.showConfirmDialog(null, "Bạn muốn xóa")==JOptionPane.YES_OPTION){
            lpDAO.xoaLoaiPhong(lp.getMaLoaiPhong());
            lpDTO.xoaLoaiPhong(row);
            return true;
        }
        return false;
    }
}
