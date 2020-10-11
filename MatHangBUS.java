/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DTO.MatHang;
import qlks.DAO.MatHangDAO;
import qlks.DTO.MatHangDTO;

/**
 *
 * @author admin
 */
public class MatHangBUS {
    private MatHangDAO mhDAO;
    
    public MatHangBUS(){
        mhDAO = new MatHangDAO();
    }
    
    public ArrayList<MatHang> docDanhSachMatHang(){
        return mhDAO.docDanhSachMatHang();
    }
    
    public boolean themMatHang(MatHangDTO mhDTO, MatHang mh){
        for(int i=0;i<mhDTO.docDanhSachMatHang().size();i++){
            MatHang mht = mhDTO.docDanhSachMatHang().get(i);
            if(mht.getMaMatHang().compareTo(mh.getMaMatHang())==0){
                if(mht.isXoa()){
                    if(JOptionPane.showConfirmDialog(null, "Phát hiện mã mặt hàng cũ bạn muốn ghi đè lên mặt hàng này")
                            ==JOptionPane.YES_OPTION){
                        mhDAO.suaMatHang(mh);
                        mhDTO.suaMatHang(mh, i);
                        return true;
                    }
                    return false;
                }
                JOptionPane.showMessageDialog(null, "Trùng mã mặt hàng.");
                return false;
            }
        }
        mhDAO.themMatHang(mh);
        mhDTO.themMatHang(mh);
        return true;
    }
    
    public boolean suaMatHang(MatHangDTO mhDTO,MatHang mh){
        int row = -1;

        for(int i=0;i<mhDTO.docDanhSachMatHang().size();i++){
            if(mh.getMaMatHang().compareTo(mhDTO.docDanhSachMatHang().get(i).getMaMatHang())==0){
                row = i;
                break;
            }
        }
        if(row == -1 || mhDTO.docDanhSachMatHang().get(row).isXoa()){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã mặt hàng");
            return false;
        }

        if(mh.getTenMatHang().compareTo(mhDTO.docDanhSachMatHang().get(row).getTenMatHang())==0 &&
                mh.getSoLuong()==mhDTO.docDanhSachMatHang().get(row).getSoLuong() &&
                mh.getDonGia()==mhDTO.docDanhSachMatHang().get(row).getDonGia()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa ở mặt hàng này");
            return false;
        }
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa mặt hàng này");
            if(chon==JOptionPane.YES_OPTION){
                mhDAO.suaMatHang(mh);
                mhDTO.suaMatHang(mh, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaMatHang(MatHangDTO mhDTO, String mmh){
        int row = -1;
        for(int i=0;i<mhDTO.docDanhSachMatHang().size();i++){
            if(mmh.compareTo(mhDTO.docDanhSachMatHang().get(i).getMaMatHang())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã dịch vụ");
            return false;
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa dịch vụ này");
        if(chon==JOptionPane.YES_OPTION){
            mhDAO.xoaMatHang(mmh);
            mhDTO.xoaMatHang(mmh, row);
            return true;
        }else{
            return false;
        }
    }
    
    public void suaSoLuong(MatHang mh){
        mhDAO.suaMatHang(mh);
    }
}
